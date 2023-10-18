package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class Registrationactivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    DocumentReference documentReference;

    //image
    private Uri imageUri;
    private static final int PICK_IMAGE = 1;
    UploadTask uploadTask;

    private EditText email, password, confirmpassword;
    private TextView loginshift, textView7;
    private Button SignUp;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressBar progressBar3;
    ImageView V2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        password = (EditText) findViewById(R.id.ppppp2);
        confirmpassword = (EditText) findViewById(R.id.ppppp3);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar2);
        SignUp = (Button) findViewById(R.id.button2);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar3.setVisibility(View.VISIBLE);
                SignUp.setEnabled(false);


                String semail, spassword, scp;
                semail = email.getText().toString().trim();
                spassword = password.getText().toString().trim();
                scp = confirmpassword.getText().toString().trim();


                if (TextUtils.isEmpty(semail)) {
                    email.setError("Enter Your Email Address");
                    progressBar3.setVisibility(View.GONE);
                    SignUp.setEnabled(true);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
                    email.setError("Enter Valid Email Address");
                    progressBar3.setVisibility(View.GONE);
                    SignUp.setEnabled(true);

                } else if (TextUtils.isEmpty(spassword)) {

                    password.setError("Password Cannot be Empty");
                    progressBar3.setVisibility(View.GONE);
                    SignUp.setEnabled(true);
                } else if (spassword.length() < 8) {
                    password.setError("Create a Strong Password");
                    progressBar3.setVisibility(View.GONE);
                    SignUp.setEnabled(true);
                } else if (TextUtils.isEmpty(scp)) {

                    confirmpassword.setError("Password Cannot be Empty");
                    progressBar3.setVisibility(View.GONE);
                    SignUp.setEnabled(true);
                } else if (!spassword.equals(scp)) {
                    confirmpassword.setError("Password not matched");
                    progressBar3.setVisibility(View.GONE);
                    SignUp.setEnabled(true);
                }
                else {

                    RegisterUser(semail, spassword);
                }


            }
        });


        loginshift = (TextView) findViewById(R.id.textView6);
        V2= (ImageView) findViewById(R.id.imageView4);
       V2.setOnClickListener(v -> shifter());
        loginshift.setOnClickListener(v -> shifter());








    }
    private void shifter() {
        startActivity(new Intent(Registrationactivity.this, MainActivity.class));
        finish();
    }
    private void RegisterUser(String e, String p) {

        mAuth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(Registrationactivity.this, "Please Complete Your Profile", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Registrationactivity.this, profilesubmission.class));
                    finish();
                } else {
                    progressBar3.setVisibility(View.GONE);
                    SignUp.setEnabled(true);
                    Toast.makeText(Registrationactivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });


    }



}