package com.example.enter131212;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class drawer extends AppCompatActivity {

    String name, phone, imgeurl, gender, uid, country;
    TextView namet, phonet, gendert, countryt;
    ImageView imgeurlt;
    Button update;
    Button chat;
    Button logout;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    Button mylistshifter,myprojectsshifter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        mylistshifter=(Button) findViewById(R.id.dbutton10);

        myprojectsshifter=(Button) findViewById(R.id.dbutton11);
        myprojectsshifter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(drawer.this,Mainprojectsinvestor3.class));

            }
        });




        mylistshifter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), myprojectsinvestor.class);
                intent.putExtra("message_key", uid);
                startActivity(intent);
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        chat=(Button) findViewById(R.id.dbutton8);
        update=(Button) findViewById(R.id.button10);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Profiles").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString("Name");
                            String country = documentSnapshot.getString("Country");
                            String contact = documentSnapshot.getString("Contact");
                            String cnic = documentSnapshot.getString("National_id");

                            // Create a dialog for editing the user's profile data
                            AlertDialog.Builder builder = new AlertDialog.Builder(drawer.this);
                            builder.setTitle("Edit Profile");
                            ConstraintLayout dialogLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.editprofie, null);
                            builder.setView(dialogLayout);


                            // Get references to the EditText fields in the dialog
                            EditText nameEditText = dialogLayout.findViewById(R.id.name_input);
                            EditText countryEditText = dialogLayout.findViewById(R.id.country_input);
                            EditText contactEditText = dialogLayout.findViewById(R.id.cnic_input2);
                            EditText cnicEditText = dialogLayout.findViewById(R.id.cnic_input);

                            // Set the EditText fields with the user's profile data
                            nameEditText.setText(name);
                            countryEditText.setText(country);
                            contactEditText.setText(contact);
                            cnicEditText.setText(cnic);

                            // Add a "Save" button to the dialog
                            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Get the updated profile data from the EditText fields
                                    String newName = nameEditText.getText().toString();
                                    String newCountry = countryEditText.getText().toString();
                                    String newContact = contactEditText.getText().toString();
                                    String newCnic = cnicEditText.getText().toString();

                                    if (newName.isEmpty() || newCountry.isEmpty() || newContact.isEmpty() || newCnic.isEmpty()){
                                        Toast.makeText(drawer.this, "Empty Feilds", Toast.LENGTH_SHORT).show();
                                    }
                                    // Create a Map object with the updated profile data
                                    else {
                                        Map<String, Object> updatedData = new HashMap<>();
                                        updatedData.put("Name", newName);
                                        updatedData.put("Country", newCountry);
                                        updatedData.put("Contact", newContact);
                                        updatedData.put("National_id", newCnic);
                                        db.collection("Profiles").document(uid).update(updatedData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(drawer.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();

                                                namet.setText(newName);
                                                phonet.setText(newContact);
                                                countryt.setText(newCountry);
                                            }
                                        }) .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(drawer.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
                                            }
                                        });



                                    }


                                    // Update the user's profile data in Firestore

                                }
                            });

                            // Add a "Cancel" button to the dialog
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builder.show();
                        } else {
                            Log.d(TAG, "No profile data found for user " + uid);
                        }
                    }
                });


            }
        });
        logout=(Button) findViewById(R.id.dbutton9);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(drawer.this, MainActivity.class));
                finish();

            }
        });
        namet = (TextView) findViewById(R.id.textView76);
        phonet = (TextView) findViewById(R.id.textView84);
        imgeurlt = (ImageView) findViewById(R.id.imageView38);
        gendert = (TextView) findViewById(R.id.textView87);
        countryt = (TextView) findViewById(R.id.textView85);


        Intent intent = getIntent();
        uid = intent.getStringExtra("message_key");
        imgeurl = intent.getStringExtra("imageurl");
        Picasso.get().load(imgeurl).into(imgeurlt);
        name = intent.getStringExtra("Username");
        namet.setText(name);
        phone = intent.getStringExtra("phonenumber");
        phonet.setText(phone);
        gender = intent.getStringExtra("gender");
        gendert.setText(gender);
        country = intent.getStringExtra("country");
        countryt.setText(country);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), chatsactivity.class);
                intent.putExtra("message_key", uid);
                intent.putExtra("imageurl",imgeurl);
                intent.putExtra("Username",namet.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}