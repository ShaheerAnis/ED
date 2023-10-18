package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Descriptionactivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth MMAuth = FirebaseAuth.getInstance();
    FirebaseUser user = MMAuth.getCurrentUser();
    String uid = user.getUid();
    TextView textView70, textView58, textView59, textView66, textView63, textView60;
    TextView Username;  //textView57
    ImageView profileimage;
    String clickeruri;
    String enterprunrid, Title, Rating, Inverstor_id, Category, Submission_Date, Descripion, Status, Fund, Payment, trending, projectid;

    ImageView imageView19,imageView21,canlprofile;
    Button button5;
    ConstraintLayout testlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptionactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        CardView cardView28=findViewById(R.id.cardView28);

        testlayout=(ConstraintLayout) findViewById(R.id.testlayout);
        button5 =(Button) findViewById(R.id.button5);
        canlprofile=(ImageView) findViewById(R.id.imageView57);
        canlprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testlayout.setVisibility(View.GONE);
            }
        });
        imageView21=(ImageView) findViewById(R.id.imageView21);
        imageView21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testlayout.setVisibility(View.VISIBLE);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Date D=new Date();

                String buttonText = button5.getText().toString();

             if (buttonText.equals("REQUEST FUND")){

                 db.collection("Fundrequest").whereEqualTo("Investorid",uid).whereEqualTo("Enterpnuer_id",enterprunrid).
                 whereEqualTo("Project",projectid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                             @Override
                             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                 if (queryDocumentSnapshots.isEmpty())
                                 {
                                     Map<String, Object> adder=new HashMap<>();
                                     adder.put("Project",projectid);
                                     adder.put("Investorid",uid);
                                     adder.put("Enterpnuer_id",enterprunrid);
                                     adder.put("Date",D);
                                     db.collection("Fundrequest").add(adder).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                         @Override
                                         public void onComplete(@NonNull Task<DocumentReference> task) {
                                             if (task.isSuccessful()){

                                                 button5.setText("CANCEL");
                                                 Drawable myDrawable = getResources().getDrawable(R.drawable.cancelbackground);
                                                 button5.setBackgroundDrawable(myDrawable);


                                             }

                                         }
                                     });

                                 } else {
                                     Toast.makeText(Descriptionactivity.this, "Request Exits: check network Connection", Toast.LENGTH_SHORT).show();
                                 }
                             }
                         })    ;








             }
             else if(buttonText.equals("CANCEL")) {

                 db.collection("Fundrequest")
                         .whereEqualTo("Investorid", uid)
                         .whereEqualTo("Enterpnuer_id", enterprunrid)
                         .whereEqualTo("Project", projectid)
                         .get()
                         .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                             @Override
                             public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                 for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                     // Delete the document
                                     documentSnapshot.getReference().delete()
                                             .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                 @Override
                                                 public void onSuccess(Void aVoid) {
                                                     Toast.makeText(Descriptionactivity.this, "Request Cancled", Toast.LENGTH_SHORT).show();
                                                     button5.setText("REQUEST FUND");
                                                     Drawable myDrawable = getResources().getDrawable(R.drawable.btnbackkk);
                                                     button5.setBackgroundDrawable(myDrawable);
                                                 }
                                             })
                                             .addOnFailureListener(new OnFailureListener() {
                                                 @Override
                                                 public void onFailure(@NonNull Exception e) {
                                                     // Handle any errors
                                                 }
                                             });
                                 }
                             }
                         })
                         .addOnFailureListener(new OnFailureListener() {
                             @Override
                             public void onFailure(@NonNull Exception e) {
                                 // Handle any errors
                             }
                         });


             }












             /*   Map<String, String> adder=new HashMap<>();
                adder.put("Project",projectid);
                adder.put("Investorid",uid);
                adder.put("Payment","NO");
                db.collection("Mylists").whereEqualTo("Project",projectid).whereEqualTo("Investorid",uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()){
                            db.collection("Mylists").add(adder).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Descriptionactivity.this, "Project Added ", Toast.LENGTH_SHORT).show();
                                        Map<String,String> updater=new HashMap<>();
                                        updater.put("Status","Added by investors");
                                        db.collection("Projects").document(projectid).update(new HashMap<>(updater));
                                    }

                                }
                            });
                        }
                        else {

                            Toast.makeText(Descriptionactivity.this, "Check Mylist", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

*/
            }
        });
        imageView19=(ImageView) findViewById(R.id.imageView19);
        imageView19.setOnClickListener(v -> sendmessagerequest());

        textView58 = (TextView) findViewById(R.id.textView58);
        textView59 = (TextView) findViewById(R.id.textView59);
        textView66 = (TextView) findViewById(R.id.textView66);
        textView63 = (TextView) findViewById(R.id.textView63);
        textView60 = (TextView) findViewById(R.id.textView60);


        Username = (TextView) findViewById(R.id.textView57);
        profileimage = (ImageView) findViewById(R.id.imageView12);
        Intent intent = getIntent();
        enterprunrid = intent.getStringExtra("message_key");
        projectid = intent.getStringExtra("message_key2");

        db.collection("Fundrequest").whereEqualTo("Investorid",uid).whereEqualTo("Enterpnuer_id",enterprunrid).
                whereEqualTo("Project",projectid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty())
                        {

                            button5.setVisibility(View.VISIBLE);
                            button5.setText("REQUEST FUND");
                            Drawable myDrawable = getResources().getDrawable(R.drawable.btnbackkk);
                            button5.setBackgroundDrawable(myDrawable);


                        }
                        else {
                            button5.setVisibility(View.VISIBLE);
                            button5.setText("CANCEL");
                            Drawable myDrawable = getResources().getDrawable(R.drawable.cancelbackground);
                            button5.setBackgroundDrawable(myDrawable);
                        }
                    }
                });

        textView70 = (TextView) findViewById(R.id.textView70);
        textView70.setText(enterprunrid);
        db.collection("Profiles").document(textView70.getText().toString().trim()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Username.setText(documentSnapshot.getString("Name"));
                    String imgeuri = documentSnapshot.getString("URI");

                    TextView textView125=findViewById(R.id.textView125);
                    int rate=documentSnapshot.getLong("Rating")
                                    .intValue();

                    textView125.setText(""+rate);

                    TextView totalproejcts=findViewById(R.id.textView124);
                    TextView pendingprojects=findViewById(R.id.textView123);

                    Query query2 =  db.collection("Projects").whereEqualTo("Enterprnuer_id",textView70.getText().toString().trim());



                    AggregateQuery countQuery2 = query2.count();
                    countQuery2.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // Count fetched successfully
                                AggregateQuerySnapshot snapshot = task.getResult();
                                long count = snapshot.getCount();
                                totalproejcts.setText(Long.toString(count));

                                }
                        }
                    });


                    Query query3 =  db.collection("Projects").whereEqualTo("Enterprnuer_id",textView70.getText().toString().trim()).whereEqualTo("Status","Pending");
                    AggregateQuery countQuery3 = query3.count();
                    countQuery2.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                // Count fetched successfully
                                AggregateQuerySnapshot snapshot = task.getResult();
                                long count = snapshot.getCount();
                               pendingprojects.setText(Long.toString(count));

                            }
                        }
                    });











                    Picasso.get().load(imgeuri).into(profileimage);
                    db.collection("Projects").document(projectid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Title = documentSnapshot.getString("Title");
                                textView58.setText(Title);
                                textView66.setText(documentSnapshot.getString("Submission_Date"));
                                Double ratinggg = documentSnapshot.getDouble("Rating");


                                int value = ratinggg.intValue();
                                Rating = Integer.toString(value);




                                Descripion = documentSnapshot.getString("Descripion");
                                textView59.setText(Descripion);
                                Status = documentSnapshot.getString("Status");
                                textView63.setText(Status);
                                Fund = documentSnapshot.getString("Fund");
                                textView60.setText(Fund);


                            } else {

                                Toast.makeText(Descriptionactivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {

                    Toast.makeText(Descriptionactivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void sendmessagerequest() {
        db.collection("requests").whereEqualTo("Senderid",uid).whereEqualTo("Reciverid",enterprunrid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()){
                    Map<String,Object>  request= new HashMap<>();
                    Date date=new Date();
                    request.put("Senderid",uid);
                    request.put("Reciverid",enterprunrid);
                    request.put("Statuschat","Waiting");
                    request.put("DATE",date);
                    db.collection("requests").add(request).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Descriptionactivity.this, "Request Sended", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    db.collection("requests").whereEqualTo("Senderid",uid).whereEqualTo("Reciverid",enterprunrid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                      String test=document.getString("Statuschat");
                                      if (test.equals("Waiting")){
                                          Toast.makeText(Descriptionactivity.this, "Your request is in waiting", Toast.LENGTH_SHORT).show();
                                      }
                                      else if (test.equals("Accepted")){
                                          Toast.makeText(Descriptionactivity.this, "Enterprnuer already present in chat", Toast.LENGTH_SHORT).show();
                                      }


                                    }


                            }else {
                                Toast.makeText(Descriptionactivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //  String  enterprunrid,Title,Rating,Inverstor_id,Category,Submission_Date,Descripion,Status,Fund, Payment,trending;
        db.collection("Profiles").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    clickeruri = documentSnapshot.getString("URI");


                } else {


                }

            }
        });


    /*    db.collection("Profiles").document(enterprunrid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Username.setText(documentSnapshot.getString("Name"));
                }
                else {
                    Toast.makeText(Descriptionactivity.this, "NetworK Error", Toast.LENGTH_SHORT).show();

                }

            }
        });
*/


    }
}