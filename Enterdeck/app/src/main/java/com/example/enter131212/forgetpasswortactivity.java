package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpasswortactivity extends AppCompatActivity {
    EditText emailgetter;
    Button reset;
    ProgressBar progressBar;
 FirebaseAuth muth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpasswortactivity);
        emailgetter=(EditText) findViewById(R.id.editTextTextEmailAddress4);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        reset=(Button) findViewById(R.id.button15);
        progressBar=(ProgressBar) findViewById(R.id.progressBar8);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String email=emailgetter.getText().toString().trim();
               if (TextUtils.isEmpty(email)){
                   Toast.makeText(forgetpasswortactivity.this, "Enter Email Address", Toast.LENGTH_SHORT).show();
               }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                   Toast.makeText(forgetpasswortactivity.this, "Enter Valid Address", Toast.LENGTH_SHORT).show();
               }

               else {
                   progressBar.setVisibility(View.VISIBLE);
                   muth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()){

                               Toast.makeText(forgetpasswortactivity.this, "Check Your Email Address", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               Toast.makeText(forgetpasswortactivity.this, "Something went Wrong try Again", Toast.LENGTH_SHORT).show();
                           }

                       }
                   });
               }




            }
        });






    }
}