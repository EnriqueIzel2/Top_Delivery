package com.enriqueizel.topdelivery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Profile extends AppCompatActivity {

  private CircleImageView userPhoto;
  private TextView userName, userEmail;
  private Button editProfile;

  private String userID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile);

    getComponentsID();

    editProfile.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(User_Profile.this, Edit_Profile.class);
        startActivity(intent);
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    DocumentReference documentReference = db.collection("Users").document(userID);
    documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
      @Override
      public void onEvent(
              @Nullable DocumentSnapshot documentSnapshot,
              @Nullable FirebaseFirestoreException error
      ) {
        if (documentSnapshot != null) {
          Glide.with(getApplicationContext()).load(documentSnapshot.getString("photo"))
                  .into(userPhoto);
          userName.setText(documentSnapshot.getString("name"));
          userEmail.setText(email);
        }
      }
    });
  }

  public void getComponentsID() {
    userPhoto = findViewById(R.id.img_user_photo);
    userName = findViewById(R.id.txt_user_name);
    userEmail = findViewById(R.id.txt_user_email);
    editProfile = findViewById(R.id.btn_edit_profile);
  }
}