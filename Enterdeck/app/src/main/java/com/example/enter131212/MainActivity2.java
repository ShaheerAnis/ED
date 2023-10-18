package com.example.enter131212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerVieww;
    LinearLayoutManager linearLayoutManagerr = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private String Userid;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private  profilesdadapter profilesdadapter;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerVieww=(RecyclerView) findViewById(R.id.rcp);

        Intent intent = getIntent();
        category = intent.getStringExtra("message_key");

        if (category.equals("Entrepreneur")){


            setupRecyclervieew(category);


        }
        else if (category.equals("Investor")){

            setupRecyclervieew(category);

        }
    }
    private void setupRecyclervieew(String cat) {

        Query query=db.collection("Profiles").whereEqualTo("Category",cat);
        FirestoreRecyclerOptions<profiless> options=new FirestoreRecyclerOptions.Builder<profiless>().
                setQuery(query, profiless.class).build();
        profilesdadapter= new profilesdadapter(options);
        recyclerVieww.setHasFixedSize(true);
        recyclerVieww.setLayoutManager(linearLayoutManagerr);
        recyclerVieww.setAdapter(profilesdadapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        profilesdadapter.startListening();
    }
}