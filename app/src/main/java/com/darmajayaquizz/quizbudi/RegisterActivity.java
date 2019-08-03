package com.darmajayaquizz.quizbudi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.darmajayaquizz.quizbudi.Model.Users;
import com.darmajayaquizz.quizbudi.Utils.Validate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mrntlu.toastie.Toastie;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtFullname, txtUserrname, txtEmail, txtPassword;
    private Button btnDaftar, btnKembali;
    private RadioButton radioGenderMale, radioGenderFemale;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ProgressDialog pDialog;
    private String gender = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        pDialog = new ProgressDialog(this);

        //Casting View
        txtFullname = findViewById(R.id.txtFullname);
        txtUserrname = findViewById(R.id.txtUserrname);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnDaftar = findViewById(R.id.btnDaftar);
        radioGenderMale = findViewById(R.id.radioMale);
        radioGenderFemale = findViewById(R.id.radioFemale);
        btnKembali = findViewById(R.id.btnKembali);

        if (radioGenderMale.isChecked()) {

            gender = "Male";
        }
        if (radioGenderFemale.isChecked()) {

            gender = "Female";
        }

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesdaftar();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity( new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }

        });

    }

    private void prosesdaftar() {
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();


        if (!Validate.cek(txtFullname) && !Validate.cek(txtUserrname) && !Validate.cek(txtEmail) && !Validate.cek(txtPassword)) {
            mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString().toLowerCase().trim(), txtPassword.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Users users = new Users(txtFullname.getText().toString().toLowerCase().trim(), txtFullname.getText().toString().trim(), txtEmail.getText().toString().trim(), gender.trim());
                                db.collection("users").document(mAuth.getUid()).set(users);
                                pDialog.dismiss();
                                Toastie.success(RegisterActivity.this, "Pendaftaran Sukses!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                pDialog.hide();
                                Toastie.warning(RegisterActivity.this, "Registrasi Gagal", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            pDialog.hide();
        }
    }


}
