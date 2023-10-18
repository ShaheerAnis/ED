package com.example.enter131212;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class projectsadaptertw extends FirestoreRecyclerAdapter<projectclass, projectsadaptertw.projectsholder> {


    public projectsadaptertw(@NonNull FirestoreRecyclerOptions<projectclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull projectsholder holder, int position, @NonNull projectclass model) {
        holder.title.setText(model.getTitle());
        holder.discription.setText(model.getDescripion());
        holder.date.setText(model.getSubmission_Date());
        holder.fund.setText(model.getFund());
        holder.status.setText(model.getStatus());
        holder.payment.setText(model.getPayment());

        int a=model.getRating();

        holder.rating.setText(Integer.toString(a));
        holder.userid.setText(model.getEnterprnuer_id());
        holder.investorid.setText(model.getInverstor_id());
    }

    @NonNull
    @Override
    public projectsholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.projectslayaoutadminenterpruner, parent, false);
        return new projectsholder(vv);
    }

    class projectsholder extends RecyclerView.ViewHolder {
        TextView title, discription, date, fund, status, payment, rating, userid, investorid;

        public projectsholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView42);
            discription = itemView.findViewById(R.id.textView43);
            date = itemView.findViewById(R.id.textView18);
            fund = itemView.findViewById(R.id.textView47);
            status = itemView.findViewById(R.id.textView45);
            payment = itemView.findViewById(R.id.textView49);
            rating = itemView.findViewById(R.id.textView51);
            userid = itemView.findViewById(R.id.textView54);
            investorid = itemView.findViewById(R.id.textView56);
        }
    }


}
