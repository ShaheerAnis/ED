package com.example.enter131212;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Fundadapter extends FirestoreRecyclerAdapter<Fundclass, Fundadapter.fundholer> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Fundadapter(@NonNull FirestoreRecyclerOptions<Fundclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull fundholer holder, int position, @NonNull Fundclass model) {
        String entid= model.getEnterpnuer_id();
        String projectid= model.getProject();
        String insetorid= model.getInvestorid();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("Profiles").document(insetorid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    holder.name.setText(documentSnapshot.getString("Name"));
                    String uri=documentSnapshot.getString("URI");
                    Picasso.get().load(uri).into(holder.profile);
                }
            }
        });
        db.collection("Projects").document(projectid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    holder.title.setText(documentSnapshot.getString("Title"));

                }
            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String ,Object> map=new HashMap<>();
                map.put("Investorid",insetorid);
               //Amount tranfered
                // 50% . 60% 
                map.put("Payment","NO");
                map.put("STATUSs","PENDING");
                map.put("Project",projectid);
                db.collection("Mylists").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(v.getContext(), "Accepted", Toast.LENGTH_SHORT).show();
                            deleteitem(holder.getAdapterPosition());


                        }

                    }
                });




            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Rejected", Toast.LENGTH_SHORT).show();
                deleteitem(holder.getAdapterPosition());

            }
        });











    }
    public void deleteitem(int positin) {
        getSnapshots().getSnapshot(positin).getReference().delete();
    }
    @NonNull
    @Override
    public fundholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.fundlayout, parent, false);

        return new fundholer(vv);
    }

    class  fundholer extends RecyclerView.ViewHolder{
        TextView name,title;
        Button accept,reject;
        ImageView profile;


        public fundholer(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView127);
            title=itemView.findViewById(R.id.textView130);
            accept=itemView.findViewById(R.id.button16);
            reject=itemView.findViewById(R.id.button17);
            profile=itemView.findViewById(R.id.imageView62);








        }
    }
}
