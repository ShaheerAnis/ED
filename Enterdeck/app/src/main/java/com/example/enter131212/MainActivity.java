package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private TextView forgetpassword;
    private EditText useremail, userpassword;
    private Button logintbn;
    private TextView shifter1, shifter2;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String Uid;

    private ProgressBar progressBar;
    ImageView V;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        useremail = (EditText) findViewById(R.id.editTextTextEmailAddress);
        forgetpassword=(TextView) findViewById(R.id.textView119);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,forgetpasswortactivity.class));
            }
        });
        userpassword = (EditText) findViewById(R.id.ppppp);
        shifter1 = (TextView) findViewById(R.id.textView);
        V = (ImageView) findViewById(R.id.imageView);
        logintbn = (Button) findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        //  funcation calling
        shifter1.setOnClickListener(v -> signactivity());
        V.setOnClickListener(v -> signactivity());

        logintbn.setOnClickListener(v -> checker());


    }

    private void checker() {
        String email, password;
        email = useremail.getText().toString().trim();
        password = userpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            useremail.setError("Email cannot be empty");

        } else if (TextUtils.isEmpty(password)) {
            userpassword.setError("Password cannot be empty");

        } else if (password.length() < 8) {
            userpassword.setError("Wrong Password");
        } else {
            progressBar.setVisibility(View.VISIBLE);
            logintbn.setEnabled(false);
            loginuser(email, password);
        }


    }

    private void loginuser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        Uid = firebaseUser.getUid();
                        db.collection("Admins").document(Uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    startActivity(new Intent(MainActivity.this, AdminDashboard.class));
                                    finish();


                                } else {

                                    db.collection("Profiles").document(Uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {

                                                startActivity(new Intent(MainActivity.this, Dashboard.class));
                                                finish();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Complete Your Profile", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(MainActivity.this, profilesubmission.class));
                                                finish();

                                            }

                                        }
                                    });


                                }

                            }
                        });


                    } else {
                        progressBar.setVisibility(View.GONE);
                        logintbn.setEnabled(true);
                        Toast.makeText(MainActivity.this, "Network Error Try Again", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    progressBar.setVisibility(View.GONE);
                    logintbn.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void signactivity() {

        startActivity(new Intent(MainActivity.this, Registrationactivity.class));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
        System.exit(0);


    }
}