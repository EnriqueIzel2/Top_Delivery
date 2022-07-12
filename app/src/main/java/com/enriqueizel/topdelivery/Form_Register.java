package com.enriqueizel.topdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Form_Register extends AppCompatActivity {

  private CircleImageView userPhoto;
  private Button btnSelectPhoto, btnRegister;
  private EditText editName, editEmail, editPassword;
  private TextView txtErrorMessage;

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


  }

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