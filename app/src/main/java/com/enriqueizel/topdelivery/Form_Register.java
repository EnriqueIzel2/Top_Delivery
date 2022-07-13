package com.enriqueizel.topdelivery;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Form_Register extends AppCompatActivity {

  private CircleImageView userPhoto;
  private Button btnSelectPhoto, btnRegister;
  private EditText editName, editEmail, editPassword;
  private TextView txtErrorMessage;

  private Uri selectUri;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form_register);

    getComponentsID();

    editName.addTextChangedListener(registerTextWatcher);
    editEmail.addTextChangedListener(registerTextWatcher);
    editPassword.addTextChangedListener(registerTextWatcher);

    btnRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        createAccount(view);
      }
    });

    btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        selectPhotoFromPhone();
      }
    });

  }

  public void selectPhotoFromPhone() {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    activityResultLauncher.launch(intent);
  }

  ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
              if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                selectUri = data.getData();

                try {
                  userPhoto.setImageURI(selectUri);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
          }
  );

  public void getComponentsID() {
    userPhoto = findViewById(R.id.photo_user);
    btnSelectPhoto = findViewById(R.id.btn_select_photo);
    btnRegister = findViewById(R.id.btn_register);
    editName = findViewById(R.id.edit_username);
    editEmail = findViewById(R.id.edit_email);
    editPassword = findViewById(R.id.edit_password);
    txtErrorMessage = findViewById(R.id.txt_error_message);
  }

  public void createAccount(View view) {
    String email = editEmail.getText().toString();
    String password = editPassword.getText().toString();

    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  saveDataUser();
                  Snackbar snackbar = Snackbar.make(
                          view,
                          "Cadastro realizado com sucesso",
                          Snackbar.LENGTH_INDEFINITE
                  ).setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      finish();
                    }
                  });

                  snackbar.show();
                } else {
                  String error;

                  try {
                    throw task.getException();
                  } catch (FirebaseAuthWeakPasswordException e) {
                    error = "A senha deve ter no mínimo 6 caracteres";
                  } catch (FirebaseAuthInvalidCredentialsException e) {
                    error = "Email inválido";
                  } catch (FirebaseAuthUserCollisionException e) {
                    error = "Este email já possui cadastrado";
                  } catch (FirebaseNetworkException e) {
                    error = "Sem conexão com Internet";
                  } catch (Exception e) {
                    error = "Erro ao cadastrar. Tente mais tarde";
                  }

                  txtErrorMessage.setText(error);
                }
              }
            });
  }

  public void saveDataUser() {
    String fileName = UUID.randomUUID().toString();

    final StorageReference reference = FirebaseStorage.getInstance().getReference(
            "/images/" + fileName
    );
    reference.putFile(selectUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
          @Override
          public void onSuccess(Uri uri) {
            // TODO: 12/07/2022 manda pro firebase
          }
        }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
            // TODO: 12/07/2022 trata erro de download
          }
        });
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        // TODO: 12/07/2022 trata erro em salvar no firebase
      }
    });
  }

  TextWatcher registerTextWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      String name = editName.getText().toString();
      String email = editEmail.getText().toString();
      String password = editPassword.getText().toString();

      if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
        btnRegister.setEnabled(true);
        btnRegister.setBackgroundColor(getResources().getColor(R.color.dark_red));
      } else {
        btnRegister.setEnabled(false);
        btnRegister.setBackgroundColor(getResources().getColor(R.color.gray));
      }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
  };
}