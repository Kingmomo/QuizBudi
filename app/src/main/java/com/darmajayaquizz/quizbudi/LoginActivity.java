package com.darmajayaquizz.quizbudi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.darmajayaquizz.quizbudi.Utils.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mrntlu.toastie.Toastie;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressDialog pDialog;
    private FirebaseUser currentUser;
    private EditText inputEmail, inputPassword;
    private Button btnMasuk;
    private TextView daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        inputEmail =  findViewById(R.id.txtEmail);
        inputPassword = findViewById(R.id.txtPassword);
        daftar =  findViewById(R.id.txtDaftar);
        btnMasuk = findViewById(R.id.btnMasuk);
        pDialog = new ProgressDialog(this);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proseslogin();
            }
        });

        //Membuka DaftarActivity
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void proseslogin() {
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();


        if (!Validate.cek(inputEmail) && !Validate.cek(inputPassword)) {
            mAuth.signInWithEmailAndPassword(inputEmail.getText().toString().toLowerCase(), inputPassword.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toastie.success(LoginActivity.this, "Login Sukses!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                pDialog.dismiss();

                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                pDialog.hide();
                                Toastie.warning(LoginActivity.this, "Login Gagal", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            pDialog.hide();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        }
    }

}
