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

public class chattrackeradapter extends FirestoreRecyclerAdapter  <chattracker,chattrackeradapter.chattrackerholder>{

    private OnListItemClicked2 onListItemClicked2;

    public chattrackeradapter(@NonNull FirestoreRecyclerOptions<chattracker> options, OnListItemClicked2 onListItemClicked2) {
        super(options);
        this.onListItemClicked2 = onListItemClicked2;
    }

    @Override
    protected void onBindViewHolder(@NonNull chattrackerholder holder, int position, @NonNull chattracker model) {
         holder.name.setText(model.getName());
        Picasso.get().load(model.getURI()).into(holder.profiles);

        String date;

    }

    @NonNull
    @Override
    public chattrackerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vvv= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chatssystemlayout,parent,false);

        return new chattrackerholder(vvv);
    }

    class  chattrackerholder extends RecyclerView.ViewHolder implements  View.OnClickListener{
       TextView name;
       ImageView profiles;

        public chattrackerholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView75);
            profiles=itemView.findViewById(R.id.imageView31);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            onListItemClicked2.onitemclicked2(getSnapshots().getSnapshot(position), position);
        }

    }
    public interface OnListItemClicked2 {
        void onitemclicked2(DocumentSnapshot documentSnapshot, int position);
    }
}
