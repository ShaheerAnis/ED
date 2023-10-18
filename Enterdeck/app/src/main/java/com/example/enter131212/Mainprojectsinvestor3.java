package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Mainprojectsinvestor3 extends AppCompatActivity implements newprojectadapterinvestor.OnListItemClicked3 {


    RecyclerView recyclerViewww;
    String projectid, entrpiunerid;
    LinearLayoutManager linearLayoutManagerr4 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    private newprojectadapterinvestor profilesdadapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String uid = user.getUid();
    CardView ratingcard;
    Button submit, cancel;
    RatingBar ratingBar2;

    int newrating=0;


    int Totalrating, onestar, twostar, threestar, fourstar, fivestar;
    boolean checker=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainprojectsinvestor3);
        ratingcard = (CardView) findViewById(R.id.ratingcard);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);


        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float ratingg, boolean fromUser) {

              if (ratingg>=1){
                  checker=false;
                 newrating= Math.round(ratingg);
              }
              else {
                  checker=true;

              }

            }
        });

        submit = (Button) findViewById(R.id.yes_button2);
        cancel = (Button) findViewById(R.id.cancel_button2);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checker){
                    ratingcard.setVisibility(View.GONE);
                    // wite code here
                    switch (newrating) {
                        case 1:
                            onestar++;
                            break;
                        case 2:
                            twostar++;
                            break;
                        case 3:
                            threestar++;
                            break;
                        case 4:
                            fourstar++;
                            break;
                        case 5:
                            fivestar++;
                            break;
                    }
                    int sum = (1 * onestar) + (2 * twostar) + (3 * threestar) + (4 * fourstar) + (5 * fivestar);
                    int totalRatings = onestar + twostar + threestar + fourstar + fivestar;
                    float averageRating = (float) sum / totalRatings;

                    averageRating = Math.min(averageRating, 5.0f);

                    int  newratinggg=Math.round(averageRating);

                    Map<String , Object> update= new HashMap<>();
                    update.put("Rating",newratinggg);
                    update.put("no_of_onestar",onestar);
                    update.put("no_of_twostar",twostar);
                    update.put("no_of_threestar",threestar);
                    update.put("no_of_fourstar",fourstar);
                    update.put("no_of_fivestar",fourstar);
                    db.collection("Profiles").document(entrpiunerid).update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Mainprojectsinvestor3.this, "Rating submmited", Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(Mainprojectsinvestor3.this, "Network error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else {
                    Toast.makeText(Mainprojectsinvestor3.this, "Select Starts", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingcard.setVisibility(View.GONE);

            }
        });


        recyclerViewww = (RecyclerView) findViewById(R.id.simplerecylcerview);
        setupRecyclervieew(uid);
        profilesdadapter.startListening();
    }

    private void setupRecyclervieew(String cat) {

        Query query = db.collection("Projects").whereEqualTo("Inverstor_id", uid);
        FirestoreRecyclerOptions<projectclass> options = new FirestoreRecyclerOptions.Builder<projectclass>().
                setQuery(query, projectclass.class).build();
        profilesdadapter = new newprojectadapterinvestor(options, this);
        recyclerViewww.setHasFixedSize(true);
        recyclerViewww.setLayoutManager(linearLayoutManagerr4);
        recyclerViewww.setAdapter(profilesdadapter);
    }

    @Override
    public void onitemclicked3(DocumentSnapshot documentSnapshot, int position) {
        ratingcard.setVisibility(View.VISIBLE);
        projectclass userModel = documentSnapshot.toObject(projectclass.class);
        projectid = documentSnapshot.getId();
        entrpiunerid = documentSnapshot.getString("Enterprnuer_id");

        db.collection("Profiles").document(entrpiunerid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String url = documentSnapshot.getString("URI");
                    ImageView imageView68 = findViewById(R.id.imageView68);
                    TextView name = findViewById(R.id.textView143);
                    Picasso.get().load(url).into(imageView68);
                    name.setText(documentSnapshot.getString("Name"));
                    Totalrating = documentSnapshot.getLong("Rating").intValue();
                    onestar = documentSnapshot.getLong("no_of_onestar").intValue();
                    twostar = documentSnapshot.getLong("no_of_twostar").intValue();
                    threestar = documentSnapshot.getLong("no_of_threestar").intValue();
                    fourstar = documentSnapshot.getLong("no_of_fourstar").intValue();
                    fivestar = documentSnapshot.getLong("no_of_fivestar").intValue();


                }

            }
        });


    }
}