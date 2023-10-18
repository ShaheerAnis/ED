package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminDashboard extends AppCompatActivity {

    ImageView imageView5;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseUser user=mAuth.getCurrentUser();
    String uid=user.getUid();
    CardView enterpernuer,inverstor,projects,admin;
    ConstraintLayout addadminconstraint;
    ImageView imageView20;

    ProgressBar progressBar3;
    EditText  adminemail,adminpassword,adminname;
    Button addadmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        enterpernuer=(CardView) findViewById(R.id.cardView9);
        inverstor=(CardView) findViewById(R.id.cardView8) ;
        projects=(CardView) findViewById(R.id.cardView11);
        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,adminprojctsmanagment.class));

            }
        });
        addadminconstraint=(ConstraintLayout) findViewById(R.id.addadminconstraint);
        admin=(CardView) findViewById(R.id.cardView10);
        adminemail=(EditText) findViewById(R.id.editTextTextEmailAddress3);
        adminpassword=(EditText) findViewById(R.id.editTextTextPassword);
        adminname=(EditText) findViewById(R.id.editTextTextPersonName4);

        addadmin=(Button) findViewById(R.id.button6) ;


        addadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,password,name;
                email=adminemail.getText().toString().trim();
                password=adminpassword.getText().toString().trim();
                name=adminname.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(AdminDashboard.this, "Email unvalid", Toast.LENGTH_SHORT).show();

                }else if(password.length()<8){
                    Toast.makeText(AdminDashboard.this, "Create Strong Password", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(name)){
                    Toast.makeText(AdminDashboard.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar3.setVisibility(View.VISIBLE);
                    addadmin.setEnabled(false);
                      mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              if (task.isSuccessful()){
                                  Toast.makeText(AdminDashboard.this, "Creating Account", Toast.LENGTH_SHORT).show();
                                        FirebaseUser newuser=mAuth.getCurrentUser();
                                        String newuserid=newuser.getUid();
                                  Map<String, String> adminprofile=new HashMap<>();
                                  adminprofile.put("Name",name);

                                        db.collection("Admins").document(newuserid).set(adminprofile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(AdminDashboard.this, "Profile added", Toast.LENGTH_SHORT).show();
                                                    progressBar3.setVisibility(View.GONE);
                                                    addadmin.setEnabled(true);
                                                    adminemail.setText(null);
                                                    adminpassword.setText(null);
                                                    adminname.setText(null);
                                                    addadminconstraint.setVisibility(View.GONE);


                                                }else {

                                                    Toast.makeText(AdminDashboard.this, "Profile not added", Toast.LENGTH_SHORT).show();
                                                    progressBar3.setVisibility(View.GONE);
                                                    addadmin.setEnabled(true);
                                                    adminemail.setText(null);
                                                    adminpassword.setText(null);
                                                    adminname.setText(null);
                                                    addadminconstraint.setVisibility(View.GONE);
                                                }

                                            }
                                        });


                              }
                              else {

                                  Toast.makeText(AdminDashboard.this, "Try Again", Toast.LENGTH_SHORT).show();
                                  progressBar3.setVisibility(View.GONE);
                                  addadmin.setEnabled(true);
                                  adminemail.setText(null);
                                  adminpassword.setText(null);
                                  adminname.setText(null);
                                  addadminconstraint.setVisibility(View.GONE);
                              }

                          }
                      });


                }
            }
        });

        progressBar3=(ProgressBar) findViewById(R.id.progressBar3);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addadminconstraint.setVisibility(View.VISIBLE);
            }
        });
        imageView20=(ImageView) findViewById(R.id.imageView20);

        imageView20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addadminconstraint.setVisibility(View.GONE);
            }
        });





        projects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this,adminprojctsmanagment.class));
            }
        });

        inverstor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("message_key", "Investor");
                startActivity(intent);
            }
        });

        enterpernuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("message_key", "Entrepreneur");
                startActivity(intent);

            }
        });
        db.collection("Admins").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String test= documentSnapshot.getString("NAME");
            }
        });

        imageView5=(ImageView) findViewById(R.id.imageView5);

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AdminDashboard.this, MainActivity.class));
                finish();

            }
        });

    }
}