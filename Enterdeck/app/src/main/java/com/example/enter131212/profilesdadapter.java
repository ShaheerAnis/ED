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
import com.squareup.picasso.Picasso;

public class profilesdadapter extends FirestoreRecyclerAdapter<profiless, profilesdadapter.profilehilder> {


    public profilesdadapter(@NonNull FirestoreRecyclerOptions<profiless> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull profilehilder holder, int position, @NonNull profiless model) {
        Picasso.get().load(model.getURI()).into(holder.profileimage);
        holder.Name.setText(model.getName());
        holder.id.setText(model.getNational_id());
        holder.contact.setText(model.getContact());
        holder.category.setText(model.getCategory());
        holder.cuntry.setText(model.getCountry());
        holder.dob.setText(model.getDate_of_Birth());


        int itemNumber = position + 1;
      //  holder.numberTextView.setText(String.valueOf(itemNumber));
    }

    @NonNull
    @Override
    public profilehilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profiles_lay, parent, false);

        return new profilehilder(v);
    }

    class profilehilder extends RecyclerView.ViewHolder {
        TextView Name, dob, id, contact, cuntry, category,numberTextView;
        ImageView profileimage;

        public profilehilder(@NonNull View itemView) {

            super(itemView);

            profileimage = itemView.findViewById(R.id.imageView16);
            Name = itemView.findViewById(R.id.textView23);
            category = itemView.findViewById(R.id.textView24);
            dob = itemView.findViewById(R.id.textView28);
            id = itemView.findViewById(R.id.textView26);
            cuntry = itemView.findViewById(R.id.textView27);
            contact = itemView.findViewById(R.id.textView25);


        }
    }


}
