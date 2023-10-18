package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Myprojects extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String Uid = user.getUid();
    RecyclerView recyclerViewww50;
    private payemntadapter profilesdadapter2;
    LinearLayoutManager linearLayoutManagerr6 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    private historyadapter profilesdadapter;
    RecyclerView recyclerViewww51;

    LinearLayoutManager linearLayoutManagerr7= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    TextView textView116, textView133;
    String number;
    ImageView cancel;
    CardView history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprojects);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textView116 = (TextView) findViewById(R.id.textView116);
        textView133 = (TextView) findViewById(R.id.textView133);
        cancel = (ImageView) findViewById(R.id.imageView65);
        history = (CardView) findViewById(R.id.history);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history.setVisibility(View.GONE);
            }
        });
        textView133.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history.setVisibility(View.VISIBLE);
            }
        });


        //  db.collection("Payments").whereEqualTo("Entid",Uid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
        //    @Override
        //  public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

        //}
        // });
        Intent intent = getIntent();
        number = intent.getStringExtra("message_key");
        ;
        int num = Integer.parseInt(number);
        if (num == 0) {
            textView116.setVisibility(View.VISIBLE);
        }
        recyclerViewww50 = (RecyclerView) findViewById(R.id.approvalsrrecyclerview);
        recyclerViewww51 = (RecyclerView) findViewById(R.id.rggg);
        setupRecyclervieew2();
        profilesdadapter2.startListening();


        setupRecyclervieew3();
        profilesdadapter.startListening();


    }

    private void setupRecyclervieew3() {
        Query query = db.collection("Payments").whereEqualTo("Entid", Uid).whereNotEqualTo("Status","Waiting");
        FirestoreRecyclerOptions<paymentclass> options = new FirestoreRecyclerOptions.Builder<paymentclass>().
                setQuery(query, paymentclass.class).build();
        profilesdadapter = new historyadapter(options);
        recyclerViewww51.setHasFixedSize(true);
        recyclerViewww51.setLayoutManager(linearLayoutManagerr7);
        recyclerViewww51.setAdapter(profilesdadapter);




    }

    private void setupRecyclervieew2() {
        Query query = db.collection("Payments").whereEqualTo("Entid", Uid).whereEqualTo("Status", "Waiting");
        FirestoreRecyclerOptions<paymentclass> options = new FirestoreRecyclerOptions.Builder<paymentclass>().
                setQuery(query, paymentclass.class).build();
        profilesdadapter2 = new payemntadapter(options);
        recyclerViewww50.setHasFixedSize(true);
        recyclerViewww50.setLayoutManager(linearLayoutManagerr6);
        recyclerViewww50.setAdapter(profilesdadapter2);
    }

}