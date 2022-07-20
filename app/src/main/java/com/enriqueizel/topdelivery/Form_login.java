package com.enriqueizel.topdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Form_login extends AppCompatActivity {

  private EditText editPassword, editEmail;
  private Button btnLogin;
  private TextView txtCreateAccount, txtErrorMessage;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form_login);

    getSupportActionBar().hide();
    getComponentsID();

    txtCreateAccount.setOnClickListener(view -> {
      Intent intent = new Intent(Form_login.this, Form_Register.class);
      startActivity(intent);
    });

    btnLogin.setOnClickListener(view -> {
      String email = editEmail.getText().toString();
      String password = editPassword.getText().toString();

      if (email.isEmpty() || password.isEmpty()) {
        txtErrorMessage.setText("Preencha todos os campos");
      } else {
        txtErrorMessage.setText("");
        userLogin();
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    if (currentUser != null) {
      goToProducts();
    }
  }

  public void userLogin() {
    String email = editEmail.getText().toString();
    String password = editPassword.getText().toString();

    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(
            task -> {
              if (task.isSuccessful()) {
                progressBar.setVisibility(View.VISIBLE);

                new Handler(Looper.getMainLooper()).postDelayed(() -> goToProducts(), 3000);
              } else {
                String error;
                try {
                  throw task.getException();
                } catch (Exception e) {
                  error = "Erro ao logar usuário";
                }
                txtErrorMessage.setText(error);
              }
            }
    );
  }

  public void goToProducts() {
    Intent intent = new Intent(Form_login.this, Products_List.class);
    startActivity(intent);
    finish();
  }

  public void getComponentsID() {
    txtCreateAccount = findViewById(R.id.txt_create_account);
    btnLogin = findViewById(R.id.btn_login);
    editEmail = findViewById(R.id.edit_email);
    editPassword = findViewById(R.id.edit_password);
    txtErrorMessage = findViewById(R.id.txt_error_message);
    progressBar = findViewById(R.id.progress_bar_login);
  }
}