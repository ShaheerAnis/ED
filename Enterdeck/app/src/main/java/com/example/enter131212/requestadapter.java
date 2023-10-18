package com.example.enter131212;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class requestadapter extends FirestoreRecyclerAdapter<requests,requestadapter.requestholder> {
   private FirebaseFirestore db;
    private OnListItemClicked3 onListItemClicked3;

    public requestadapter(@NonNull FirestoreRecyclerOptions<requests> options, OnListItemClicked3 onListItemClicked3) {
        super(options);
        this.onListItemClicked3 = onListItemClicked3;
       db = FirebaseFirestore.getInstance();

    }


    @Override
    protected void onBindViewHolder(@NonNull requestholder holder, int position, @NonNull requests model) {


        db.collection("Profiles").document(model.getSenderid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){

                    String uri=documentSnapshot.getString("URI");
                    Picasso.get().load(uri).into(holder.profile);
                    String test=documentSnapshot.getString("Name");
                    String test2=documentSnapshot.getString("Country");
                    holder.country.setText(test2);
                    holder.name.setText(test);
                }

            }
        });
          holder.rejected.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  deleteitem2(holder.getAdapterPosition());
              }
          });
    }

    @NonNull
    @Override
    public requestholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv= LayoutInflater.from(parent.getContext()).inflate(R.layout.requetslayoutt,parent,false);

        return new requestholder(vv);
    }
    public void deleteitem2( int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class requestholder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        TextView name, country;
        Button accpet, rejected;
        ImageView profile;


        public requestholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView80);
            country = itemView.findViewById(R.id.textView82);
            accpet = itemView.findViewById(R.id.button8);
            rejected = itemView.findViewById(R.id.button9);
            profile = itemView.findViewById(R.id.imageView32);
            accpet.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onListItemClicked3.onitemclicked3(getSnapshots().getSnapshot(position), position);
        }
    }
    public interface OnListItemClicked3 {
        void onitemclicked3(DocumentSnapshot documentSnapshot, int position);
    }
}
