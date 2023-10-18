package com.example.enter131212;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity implements projectadpaterone.OnListItemClicked3 {

    CardView cardView16,messagesshifter,addprojecracticyty;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String uid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String category;
    TextView textView20, textView21,textView31;
    ImageView imageView14;
  CardView button4;

    RecyclerView recyclerViewww;
    LinearLayoutManager linearLayoutManagerr4 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    private projectadpaterone profilesdadapter;
    private profilesdadapter profilesdadaptertest;
    ConstraintLayout ent, interprenuer;
    ProgressBar progressBar4;
    ImageView trending, fashion, medical, education, technolgy;
    String imagetest;
    CardView drawerr;
    ImageView imageView13;
    String phonenumber,gender,country;
    CardView cardView13,cardView12,cardView25;
    TextView resultcounting,resultcounting2;

    ImageView imageView54;


    //payemnts approvals

    ImageView  lgout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        imageView54=(ImageView) findViewById(R.id.imageView54);
        resultcounting2=(TextView) findViewById(R.id.textView134);

        resultcounting=(TextView) findViewById(R.id.textView117);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //payments approvasl
        cardView25=(CardView) findViewById(R.id.cardView25);
        cardView25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Myprojects.class);
                intent.putExtra("message_key", resultcounting.getText().toString());
                startActivity(intent);

            }
        });















        cardView12=(CardView) findViewById(R.id.cardView12);
        cardView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,myprojectsent.class));
            }
        });

        cardView13=(CardView) findViewById(R.id.cardView13);
        cardView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,Graphsactivity.class));
            }
        });
        drawerr=(CardView) findViewById(R.id.te);
        imageView13=(ImageView) findViewById(R.id.imageView13);
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateprofile(uid);
            }
        });
        drawerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), drawer.class);
                intent.putExtra("message_key", uid);
                intent.putExtra("imageurl",imagetest);
                intent.putExtra("Username",textView20.getText().toString());
                intent.putExtra("phonenumber",phonenumber);
                intent.putExtra("gender",gender);
                intent.putExtra("country",country);
                startActivity(intent);
            }
        });
        messagesshifter=(CardView) findViewById(R.id.messagesshifter);





        trending = (ImageView) findViewById(R.id.imageView);
        fashion = (ImageView) findViewById(R.id.imageView2);
        medical = (ImageView) findViewById(R.id.imageView3);
        education = (ImageView) findViewById(R.id.imageView4);
        technolgy = (ImageView) findViewById(R.id.imageView5);
        messagesshifter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), chatsactivity.class);
                intent.putExtra("message_key", uid);
                intent.putExtra("imageurl",imagetest);
                intent.putExtra("Username",textView20.getText().toString());
                startActivity(intent);
            }
        });



       trending.setOnClickListener(v-> categoreireid("trending"));
        fashion.setOnClickListener(v-> categoreireid("fashion"));
        medical.setOnClickListener(v-> categoreireid("medical"));
        education.setOnClickListener(v-> categoreireid("education"));
        technolgy.setOnClickListener(v-> categoreireid("technology"));

        addprojecracticyty = (CardView) findViewById(R.id.cardView16);
        ent = (ConstraintLayout) findViewById(R.id.ent);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        interprenuer = (ConstraintLayout) findViewById(R.id.investor);


        addprojecracticyty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Addproject.class);
                intent.putExtra("message_key", uid);
                startActivity(intent);


            }
        });

        lgout=(ImageView) findViewById(R.id.imageView59);
        lgout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
                finish();
            }
        });

        button4 = (CardView) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Dashboard.this, fundacticity.class));

            }
        });


    }

    private void updateprofile(String uid) {
        // Assume that you have a reference to Firestore and the current user's UID in a variable called "db" and "uid" respectively

// Get a reference to the "Profiles" collection


// Get a reference to the current user's document in the "Profiles" collection


// Get the user's profile data from Firestore and display it in a dialog for editing
        db.collection("Profiles").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("Name");
                    String country = documentSnapshot.getString("Country");
                    String contact = documentSnapshot.getString("Contact");
                    String cnic = documentSnapshot.getString("National_id");

                    // Create a dialog for editing the user's profile data
                    AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
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
                                Toast.makeText(Dashboard.this, "Empty Feilds", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(Dashboard.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Dashboard.this, Dashboard.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                    }
                                }) .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Dashboard.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
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

////////
    }

    private void categoreireid(String trending) {

        Intent intent = new Intent(getApplicationContext(), Categories.class);
        intent.putExtra("message_key", trending);
        startActivity(intent);


    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerViewww = (RecyclerView) findViewById(R.id.reee);
        //    projectadpateron.startListening();
        cardView16 = (CardView) findViewById(R.id.cardView16);

        textView20 = (TextView) findViewById(R.id.textView20);

        textView21 = (TextView) findViewById(R.id.textView19);
        textView31=(TextView) findViewById(R.id.textView31) ;
        imageView14 = (ImageView) findViewById(R.id.imageView14);


        if (user != null) {
            uid = user.getUid();
            progressBar4.setVisibility(View.VISIBLE);

            db.collection("Admins").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        progressBar4.setVisibility(View.GONE);
                        startActivity(new Intent(Dashboard.this, AdminDashboard.class));

                        finish();
                    } else {
                        db.collection("Profiles").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    setupRecyclervieew("nb");
                                    profilesdadapter.startListening();


                                    category = documentSnapshot.getString("Category");
                                    if (category.equals("Entrepreneur")) {
                                        textView31.setText(documentSnapshot.getString("Category"));
                                        textView21.setVisibility(View.GONE);
                                        drawerr.setVisibility(View.GONE);
                                        imageView13.setVisibility(View.VISIBLE);
                                        lgout.setVisibility(View.VISIBLE);
                                        textView20.setText(documentSnapshot.getString("Name"));
                                        phonenumber=documentSnapshot.getString("Contact");
                                        gender=documentSnapshot.getString("Gender");
                                        country=documentSnapshot.getString("Country");
                                        imagetest = documentSnapshot.getString("URI");
                                        Picasso.get().load(imagetest).into(imageView14);
                                        ent.setVisibility(View.VISIBLE);
                                        interprenuer.setVisibility(View.GONE);
                                        progressBar4.setVisibility(View.GONE);
                                        Query query = db.collection("Fundrequest").whereEqualTo("Enterpnuer_id", uid);
                                        AggregateQuery countQuery = query.count();
                                        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    // Count fetched successfully
                                                    AggregateQuerySnapshot snapshot = task.getResult();
                                                    long count = snapshot.getCount();
                                                    if (count>0){
                                                      //  imageView54.setVisibility(View.VISIBLE);
                                                        resultcounting.setText(Long.toString(count));
                                                        resultcounting.setVisibility(View.VISIBLE);
                                                    }else {
                                                        resultcounting.setVisibility(View.GONE);
                                                      //  imageView54.setVisibility(View.VISIBLE);
                                                    }

                                                } else {

                                                }
                                            }
                                        });

                                        Query query2 = db.collection("Payments").whereEqualTo("Entid", uid).whereEqualTo("Status","Waiting");
                                        AggregateQuery countQuery2 = query2.count();
                                        countQuery2.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    // Count fetched successfully
                                                    AggregateQuerySnapshot snapshot = task.getResult();
                                                    long count = snapshot.getCount();
                                                    if (count>0){
                                                        //  imageView54.setVisibility(View.VISIBLE);
                                                        resultcounting2.setText(Long.toString(count));
                                                        resultcounting2.setVisibility(View.VISIBLE);
                                                    }else {
                                                        resultcounting2.setVisibility(View.GONE);
                                                        //  imageView54.setVisibility(View.VISIBLE);
                                                    }

                                                } else {

                                                }
                                            }
                                        });



                                    } else if (category.equals("Investor")) {
                                        interprenuer.setVisibility(View.VISIBLE);
                                        imageView13.setVisibility(View.GONE);
                                        ent.setVisibility(View.GONE);
                                        lgout.setVisibility(View.GONE);
                                        drawerr.setVisibility(View.VISIBLE);
                                        textView31.setVisibility(View.GONE);
                                        textView21.setText(documentSnapshot.getString("Category"));
                                        textView21.setVisibility(View.VISIBLE);
                                        textView20.setText(documentSnapshot.getString("Name"));
                                        phonenumber=documentSnapshot.getString("Contact");
                                        gender=documentSnapshot.getString("Gender");
                                        country=documentSnapshot.getString("Country");
                                        imagetest = documentSnapshot.getString("URI");
                                        Picasso.get().load(imagetest).into(imageView14);
                                        progressBar4.setVisibility(View.GONE);




                                    }

                                } else {
                                    progressBar4.setVisibility(View.GONE);
                                    startActivity(new Intent(Dashboard.this, profilesubmission.class));
                                    finish();
                                }

                            }
                        });


                    }

                }
            });


        } else {
            progressBar4.setVisibility(View.GONE);
            startActivity(new Intent(Dashboard.this, MainActivity.class));
            finish();
        }


    }

    private void setupRecyclervieew(String cat) {

        Query query = db.collection("Projects").whereEqualTo("Inverstor_id", "Null");
        FirestoreRecyclerOptions<projectclass> options = new FirestoreRecyclerOptions.Builder<projectclass>().
                setQuery(query, projectclass.class).build();
        profilesdadapter = new projectadpaterone(options, this);
        recyclerViewww.setHasFixedSize(true);
        recyclerViewww.setLayoutManager(linearLayoutManagerr4);
        recyclerViewww.setAdapter(profilesdadapter);
    }


    @Override
    public void onitemclicked3(DocumentSnapshot documentSnapshot, int position) {

        projectclass userModel = documentSnapshot.toObject(projectclass.class);
        String projectid = documentSnapshot.getId();
        String entrpiunerid = documentSnapshot.getString("Enterprnuer_id");
        progressBar4.setVisibility(View.VISIBLE);

        db.collection("Projects").document(projectid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    progressBar4.setVisibility(View.VISIBLE);

                    Double trending = documentSnapshot.getDouble("Trending");
                    trending = trending + 1;

                    HashMap<String, Object> trennding = new HashMap<>();
                    trennding.put("Trending", trending);

                    db.collection("Projects").document(projectid).update(new HashMap<>(trennding)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), Descriptionactivity.class);
                                intent.putExtra("message_key", entrpiunerid);
                                intent.putExtra("message_key2", projectid);
                                progressBar4.setVisibility(View.GONE);
                                startActivity(intent);

                            } else {
                                progressBar4.setVisibility(View.GONE);
                                Toast.makeText(Dashboard.this, "Network Error", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                } else {
                    progressBar4.setVisibility(View.GONE);
                    Toast.makeText(Dashboard.this, "Network Error", Toast.LENGTH_SHORT).show();
                }


            }
        });
        progressBar4.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        System.exit(0);

    }


}