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
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class projectadpaterone extends FirestoreRecyclerAdapter<projectclass, projectadpaterone.projectholder> {
    private OnListItemClicked3 onListItemClicked3;

    public projectadpaterone(@NonNull FirestoreRecyclerOptions<projectclass> options,OnListItemClicked3 onListItemClicked3) {
        super(options);
        this.onListItemClicked3 = onListItemClicked3;
    }

    @Override
    protected void onBindViewHolder(@NonNull projectholder holder, int position, @NonNull projectclass model) {
        holder.title.setText(model.getTitle());
        holder.discription.setText(model.getDescripion());
        holder.funding.setText(model.getFund());
        holder.date.setText(model.getSubmission_Date());
        Picasso.get().load(model.getURI()).into(holder.imageView34);
        int itemNumber = position + 1;
        holder.numberTextView.setText(String.valueOf(itemNumber));

    }

    @NonNull
    @Override
    public projectholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vv= LayoutInflater.from(parent.getContext()).inflate(R.layout.projectlay,parent,false);

        return new projectholder(vv);
    }

    class projectholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, discription, funding, date,numberTextView;
        ImageView imageView34;


        public projectholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.TextView34);
            discription = itemView.findViewById(R.id.textView39);
            funding = itemView.findViewById(R.id.textView40);
            date = itemView.findViewById(R.id.textView38);
            imageView34=itemView.findViewById(R.id.imageView34);
            numberTextView=itemView.findViewById(R.id.textView121);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            onListItemClicked3.onitemclicked3(getSnapshots().getSnapshot(position), position);
        }
    }
    public interface OnListItemClicked3 {
        void onitemclicked3(DocumentSnapshot documentSnapshot, int position);
    }
}
