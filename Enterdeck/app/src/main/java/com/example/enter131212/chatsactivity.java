package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class chatsactivity extends AppCompatActivity implements requestadapter.OnListItemClicked3 ,chattrackeradapter.OnListItemClicked2 {
    String uidd;
    String imageurl;
    ImageView profileimage;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ConstraintLayout chatss, requestss;
    TextView chatstext, requeststext;
    ;
    RecyclerView recyclerViewww2,recyclerViewww;
    LinearLayoutManager linearLayoutManagerr9 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    LinearLayoutManager linearLayoutManagerr8 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    private requestadapter profilesdadapter2;
    private  chattrackeradapter profilesdadapter3;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatsactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerViewww = (RecyclerView) findViewById(R.id.chattrackerr);
        recyclerViewww2 = (RecyclerView) findViewById(R.id.reqcttt);
        chatstext = (TextView) findViewById(R.id.textView77);
        chatss = (ConstraintLayout) findViewById(R.id.chatlayouttt);
        requestss = (ConstraintLayout) findViewById(R.id.requestlayouttt);
        profileimage = (ImageView) findViewById(R.id.imageView30);
        Intent intent = getIntent();
        uidd = intent.getStringExtra("message_key");
        imageurl = intent.getStringExtra("imageurl");
        Username=intent.getStringExtra("Username");
        Picasso.get().load(imageurl).into(profileimage);
        requeststext = (TextView) findViewById(R.id.textView79);
        setupRecyclervieew();
        profilesdadapter3.startListening();
        setupRecyclervieew2();
        profilesdadapter2.startListening();
        chatstext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatss.setVisibility(View.VISIBLE);
                requestss.setVisibility(View.GONE);
                Toast.makeText(chatsactivity.this, "Done", Toast.LENGTH_SHORT).show();
            }
        });
        requeststext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatss.setVisibility(View.GONE);
                requestss.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupRecyclervieew2() {
        Query query=db.collection("requests").whereEqualTo("Reciverid",uidd).whereEqualTo("Statuschat","Waiting");
        FirestoreRecyclerOptions<requests> options=new FirestoreRecyclerOptions.Builder<requests>().setQuery(query, requests.class).build();
        profilesdadapter2= new requestadapter(options,this);
        recyclerViewww2.setHasFixedSize(true);
        recyclerViewww2.setLayoutManager(linearLayoutManagerr8);
        recyclerViewww2.setAdapter(profilesdadapter2);
    }

    private void setupRecyclervieew() {
        Query query=db.collection("chat").document(uidd).collection("chating");
        FirestoreRecyclerOptions<chattracker> options=new FirestoreRecyclerOptions.Builder<chattracker>().setQuery(query, chattracker.class).build();
        profilesdadapter3= new chattrackeradapter(options,this);
        recyclerViewww.setHasFixedSize(true);
        recyclerViewww.setLayoutManager(linearLayoutManagerr9);
        recyclerViewww.setAdapter(profilesdadapter3);
    }


    @Override
    public void onitemclicked3(DocumentSnapshot documentSnapshot, int position) {

        requests userModel = documentSnapshot.toObject(requests.class);
        Date datee=new Date();
        String requestid = documentSnapshot.getId();
        String sendeerid=documentSnapshot.getString("Senderid");
        String reciverid=documentSnapshot.getString("Reciverid");

        db.collection("Profiles").document(sendeerid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name;
                    String URii;

                    Map<String,Object> Namer=new HashMap<>();
                    name=documentSnapshot.getString("Name");
                    URii=documentSnapshot.getString("URI");
                    Namer.put("Name",name);
                    Namer.put("URI",URii);
                    Namer.put("DATE",datee);
                    db.collection("chat").document(reciverid).collection("chating").document(sendeerid).set(Namer).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                String URii2;
                                Map<String,Object> Namer2=new HashMap<>();
                                Namer2.put("Name",Username);
                                Namer2.put("URI",imageurl);
                                Namer.put("DATE",datee);

                                db.collection("chat").document(sendeerid).collection("chating").document(reciverid).set(Namer2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Map<String,String> updater=new HashMap<>();
                                            updater.put("Statuschat","Accepted");
                                            db.collection("requests").document(requestid).update(new HashMap<>(updater)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(chatsactivity.this, "Inverstor added", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                        }

                                    }
                                });

                            }

                        }
                    });


                }

            }
        });








    }

    @Override
    public void onitemclicked2(DocumentSnapshot documentSnapshot, int position) {
        chattracker userModel = documentSnapshot.toObject(chattracker.class);
        String reciverid=documentSnapshot.getId();
        String senderid=uidd;
        String reciverimg=documentSnapshot.getString("URI");
        String recivername=documentSnapshot.getString("Name");
        Intent intent = new Intent(getApplicationContext(), chatingactvity.class);
        intent.putExtra("Reciverid",reciverid );
        intent.putExtra("recvivername",recivername);
        intent.putExtra("reciverimg",reciverimg);
        intent.putExtra("senderid",senderid);
        intent.putExtra("senderimg",imageurl);
        startActivity(intent);

    }
}