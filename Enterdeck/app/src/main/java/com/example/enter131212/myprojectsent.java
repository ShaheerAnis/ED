package com.example.enter131212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class myprojectsent extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth mAUTH=FirebaseAuth.getInstance();
    FirebaseUser user=mAUTH.getCurrentUser();
    String id=user.getUid();
    RecyclerView recyclerViewww;
    private  projectadaperentprenuer profilesdadapter;
    LinearLayoutManager linearLayoutManagerr4 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprojectsent);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerViewww=(RecyclerView) findViewById(R.id.myprojectenttt);

        setupRecyclervieew();
        profilesdadapter.startListening();



    }
    private void setupRecyclervieew() {
        Query query = db.collection("Projects").whereEqualTo("Enterprnuer_id",id);
        FirestoreRecyclerOptions<projectclass> options = new FirestoreRecyclerOptions.Builder<projectclass>().
                setQuery(query, projectclass.class).build();
        profilesdadapter = new projectadaperentprenuer(options);
        recyclerViewww.setHasFixedSize(true);
        recyclerViewww.setLayoutManager(linearLayoutManagerr4);
        recyclerViewww.setAdapter(profilesdadapter);
    }
}