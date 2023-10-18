package com.example.enter131212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class adminprojctsmanagment extends AppCompatActivity {
    RecyclerView recyclerVieww4;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    LinearLayoutManager linearLayoutManagerr4 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
  private  projectsadaptertw projectsadaptert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminprojctsmanagment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerVieww4 = (RecyclerView)findViewById(R.id.rctt);
        setupRecyclervieew("nb");


    }

    private void setupRecyclervieew(String nb) {
        Query query=db.collection("Projects");
        FirestoreRecyclerOptions<projectclass> options=new FirestoreRecyclerOptions.Builder<projectclass>().
                setQuery(query, projectclass.class).build();
        projectsadaptert= new projectsadaptertw(options);
        recyclerVieww4.setHasFixedSize(true);
        recyclerVieww4.setLayoutManager(linearLayoutManagerr4);
        recyclerVieww4.setAdapter(projectsadaptert);

    }


    @Override
    protected void onStart() {
        super.onStart();
     projectsadaptert.startListening();


    }
}