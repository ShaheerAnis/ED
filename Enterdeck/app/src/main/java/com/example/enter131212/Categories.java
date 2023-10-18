package com.example.enter131212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Categories extends AppCompatActivity  implements  projectadpaterone.OnListItemClicked3{


    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth muth=FirebaseAuth.getInstance();
    FirebaseUser user=muth.getCurrentUser();
    String Uid= user.getUid();
    RecyclerView recyclerViewww2;
    LinearLayoutManager linearLayoutManagerr8 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    private projectadpaterone profilesdadapter2;
    Button button11;

    String category;
    ImageView cat;
    TextView catnam;
    ProgressBar progressBar5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerViewww2=(RecyclerView) findViewById(R.id.categoryrc);
        button11=(Button) findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Categories.this,Graphsactivity.class));
            }
        });
        progressBar5=(ProgressBar) findViewById(R.id.progressBar5);
        cat=(ImageView) findViewById(R.id.imageView28);
        catnam=(TextView) findViewById(R.id.textView73);
        Intent intent = getIntent();
        category = intent.getStringExtra("message_key");
        if (category.equals("trending")){
            progressBar5.setVisibility(View.VISIBLE);
            button11.setVisibility(View.VISIBLE);
            cat.setImageResource(R.drawable.graphs);
            catnam.setText("Trending");
            setupRecyclervieew2();
           profilesdadapter2.startListening();

        }else if(category.equals("fashion")){
            progressBar5.setVisibility(View.VISIBLE);
            cat.setImageResource(R.drawable.artph);
            catnam.setText("Fashion");
            setupRecyclervieew("Fashion");
            profilesdadapter2.startListening();

        }
        else if(category.equals("medical")){
            progressBar5.setVisibility(View.VISIBLE);
            cat.setImageResource(R.drawable.medical);
            catnam.setText("Medical");
            setupRecyclervieew("Medical");
            profilesdadapter2.startListening();

        }
        else if(category.equals("education")){
            progressBar5.setVisibility(View.VISIBLE);
            cat.setImageResource(R.drawable.educational);
            catnam.setText("Education");
            setupRecyclervieew("Education");
            profilesdadapter2.startListening();

        }
        else if(category.equals("technology")){
            progressBar5.setVisibility(View.VISIBLE);
            cat.setImageResource(R.drawable.tech);
            catnam.setText("Technology");
            setupRecyclervieew("Technology");
            profilesdadapter2.startListening();

        }






    }

    private void setupRecyclervieew(String cat) {

        Query query=db.collection("Projects").whereEqualTo("Category",cat).whereEqualTo("Inverstor_id", "Null");
        FirestoreRecyclerOptions<projectclass> options=new FirestoreRecyclerOptions.Builder<projectclass>().
                setQuery(query, projectclass.class).build();
        profilesdadapter2= new projectadpaterone(options,this);
        recyclerViewww2.setHasFixedSize(true);
        recyclerViewww2.setLayoutManager(linearLayoutManagerr8);
        recyclerViewww2.setAdapter(profilesdadapter2);
        progressBar5.setVisibility(View.GONE);
    }
    private void setupRecyclervieew2() {

        Query query=db.collection("Projects").orderBy("Trending",Query.Direction.ASCENDING).whereEqualTo("Inverstor_id", "Null").limit(5);
        FirestoreRecyclerOptions<projectclass> options=new FirestoreRecyclerOptions.Builder<projectclass>().
                setQuery(query, projectclass.class).build();
        profilesdadapter2= new projectadpaterone(options,this);
        recyclerViewww2.setHasFixedSize(true);
        recyclerViewww2.setLayoutManager(linearLayoutManagerr8);
        recyclerViewww2.setAdapter(profilesdadapter2);
        progressBar5.setVisibility(View.GONE);
    }



    @Override
    public void onitemclicked3(DocumentSnapshot documentSnapshot, int position) {
        projectclass userModel = documentSnapshot.toObject(projectclass.class);
        String projectid = documentSnapshot.getId();
        String entrpiunerid = documentSnapshot.getString("Enterprnuer_id");
        Intent intent = new Intent(getApplicationContext(), Descriptionactivity.class);
        intent.putExtra("message_key", entrpiunerid);
        intent.putExtra("message_key2", projectid);
        startActivity(intent);
    }
}