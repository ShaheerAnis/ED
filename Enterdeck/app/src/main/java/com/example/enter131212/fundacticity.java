package com.example.enter131212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class fundacticity extends AppCompatActivity {

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    FirebaseUser user=mAuth.getCurrentUser();
    String Uid=user.getUid();

    private  Fundadapter profilesdadapter;
    RecyclerView recyclerViewww;
    LinearLayoutManager linearLayoutManagerr4 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundacticity);
        recyclerViewww=(RecyclerView) findViewById(R.id.rrr);



        setupRecyclervieew();
        profilesdadapter.startListening();


    }

    private void setupRecyclervieew() {

        Query query = db.collection("Fundrequest").whereEqualTo("Enterpnuer_id",Uid);
        FirestoreRecyclerOptions<Fundclass> options = new FirestoreRecyclerOptions.Builder<Fundclass>().
                setQuery(query, Fundclass.class).build();
        profilesdadapter = new Fundadapter(options);
        recyclerViewww.setHasFixedSize(true);
        recyclerViewww.setLayoutManager(linearLayoutManagerr4);
        recyclerViewww.setAdapter(profilesdadapter);
    }
}