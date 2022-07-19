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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Profile extends AppCompatActivity {
  // TODO: 18/07/2022 colocar spinner ao atualizar

  private CircleImageView userPhoto;
  private EditText editUsername;
  private Button btnUpdateData, btnEditSelectPhoto;

  private Uri selectUri;
  private String userID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);

    getComponentsID();

    btnEditSelectPhoto.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        selectPhotoFromPhone();
      }
    });

    btnUpdateData.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String name = editUsername.getText().toString();
        if (name.isEmpty()) {
          Snackbar snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT);
          snackbar.show();
        } else {
          updateData(view);
        }
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

  private void updateData(View view) {
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
            String photho = uri.toString();

            String name = editUsername.getText().toString();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> users = new HashMap<>();
            users.put("name", name);
            users.put("photo", photho);

            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            db.collection("Users").document(userID)
                    .update("name", name, "photo", photho)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                        Snackbar snackbar = Snackbar.make(
                                view,
                                "Sucesso ao atualizar dados",
                                Snackbar.LENGTH_INDEFINITE
                        ).setAction("OK", new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                            finish();
                          }
                        });
                        snackbar.show();
                      }
                    }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                        // TODO: 18/07/2022 resolver a falha
                      }
                    });

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

  private void getComponentsID() {
    userPhoto = findViewById(R.id.img_edit_user_photo);
    editUsername = findViewById(R.id.edit_edit_username);
    btnUpdateData = findViewById(R.id.btn_update_data);
    btnEditSelectPhoto = findViewById(R.id.btn_edit_select_photo);
  }
}