package com.example.enter131212;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class historyadapter extends FirestoreRecyclerAdapter <paymentclass,historyadapter.historyholder>{



    public historyadapter(@NonNull FirestoreRecyclerOptions<paymentclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull historyholder holder, int position, @NonNull paymentclass model) {
        String projectid = model.getProjectid();
        String entid = model.getEntid();
        String listid= model.getListid();
        String Status= model.getStatus();
        holder.status.setText(Status);
        String investerid = model.getInverstorid();
        String Paymentamount=model.getPayment();
        holder.textView137.setText(holder.textView137.getText().toString()+" "+Paymentamount+"%"+" amount");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Projects").document(projectid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    holder.title.setText(documentSnapshot.getString("Title"));
                    holder.fund.setText(documentSnapshot.getString("Fund"));
                }

            }
        });
        db.collection("Profiles").document(investerid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    holder.name.setText(documentSnapshot.getString("Name"));
                    String URI = documentSnapshot.getString("URI");
                    Picasso.get().load(URI).into(holder.profile);

                }

            }
        });






    }

    @NonNull
    @Override
    public historyholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.historylayout, parent, false);

        return new historyholder(v);
    }

    class historyholder extends RecyclerView.ViewHolder{

        TextView name, fund, title,textView110,status,textView137;
        ImageView profile;



        public historyholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView136);
            fund = itemView.findViewById(R.id.textView140);
            title = itemView.findViewById(R.id.textView141);
            profile = itemView.findViewById(R.id.imageView66);
            status = itemView.findViewById(R.id.textView142);
            textView137  = itemView.findViewById(R.id.textView137);




        }
    }

}
