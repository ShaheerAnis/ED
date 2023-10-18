package com.example.enter131212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class myprojectsinvestor extends AppCompatActivity {

    FirebaseFirestore db=FirebaseFirestore.getInstance();

    RecyclerView recyclerViewww;
    LinearLayoutManager linearLayoutManagerr4 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    private mylistadapter profilesdadapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprojectsinvestor);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerViewww=(RecyclerView) findViewById(R.id.mylistview);
        Intent intent = getIntent();
        uid = intent.getStringExtra("message_key");


       setupRecyclervieew();
       profilesdadapter.startListening();

    }

    private void setupRecyclervieew() {
        Query query = db.collection("Mylists").whereEqualTo("Investorid",uid).whereNotEqualTo("STATUSs","Accepted");
        FirestoreRecyclerOptions<myliststs> options = new FirestoreRecyclerOptions.Builder<myliststs>().
                setQuery(query, myliststs.class).build();
        profilesdadapter = new mylistadapter(options);
        recyclerViewww.setHasFixedSize(true);
        recyclerViewww.setLayoutManager(linearLayoutManagerr4);
        recyclerViewww.setAdapter(profilesdadapter);
    }
}