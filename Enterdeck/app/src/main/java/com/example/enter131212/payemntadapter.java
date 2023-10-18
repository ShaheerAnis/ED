package com.example.enter131212;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class payemntadapter extends FirestoreRecyclerAdapter<paymentclass, payemntadapter.payemntholder> {


    public payemntadapter(@NonNull FirestoreRecyclerOptions<paymentclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull payemntholder holder, int position, @NonNull paymentclass model) {
        String projectid = model.getProjectid();
        String entid = model.getEntid();
        String listid= model.getListid();
        String investerid = model.getInverstorid();
        String Paymentamount=model.getPayment();
        int aa=Integer.parseInt(Paymentamount);
        holder.textView110.setText(holder.textView110.getText().toString()+" "+Paymentamount+"%"+" amount");


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

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to reject this Approval?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            DocumentSnapshot snapshot = getSnapshots().getSnapshot(position);
                            String documentId = snapshot.getId();


                            Map<String, Object>  updater=new HashMap<>();
                            updater.put("Status","Rejected");
                            db.collection("Payments").document(documentId).update(new HashMap<>(updater));
                            Toast.makeText(v.getContext(), "Recjected", Toast.LENGTH_SHORT).show();

                        }


                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
        holder.accpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            db.collection("Mylists").document(listid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(position);
                        String documentId = snapshot.getId();

                        String prepayment=documentSnapshot.getString("Payment");
                        if (prepayment.equals("NO")){

                           if (aa==100){
                               Map<String,Object> up=new HashMap<>();
                               up.put("STATUSs","Accepted");
                               up.put("Payment",Paymentamount);
                               db.collection("Mylists").document(listid).update(new HashMap<>(up));
                               Map<String, Object>  updater=new HashMap<>();
                               updater.put("Status","Accepted");
                               db.collection("Payments").document(documentId).update(new HashMap<>(updater));
                               Map<String, Object>  updater2=new HashMap<>();
                               updater2.put("Payment","YES");
                               updater2.put("Inverstor_id",investerid);
                               db.collection("Projects").document(projectid).update(new HashMap<>(updater2));
                               Toast.makeText(v.getContext(), "Accepted", Toast.LENGTH_SHORT).show();

                           }
                           else {
                               Map<String,Object> up=new HashMap<>();
                               up.put("STATUSs","Biding");
                               up.put("Payment",Paymentamount);
                               db.collection("Mylists").document(listid).update(new HashMap<>(up));
                               Map<String, Object>  updater=new HashMap<>();
                               updater.put("Status","Accepted");
                               db.collection("Payments").document(documentId).update(new HashMap<>(updater));
                               Toast.makeText(v.getContext(), "Accepted", Toast.LENGTH_SHORT).show();
                           }
                        }

                        else {

                            int bb=Integer.parseInt(prepayment);
                            int sum=aa+bb;
                            if (sum>100){
                                Map<String, Object>  updater=new HashMap<>();
                                updater.put("Status","Wrong Biding by the Investor");
                                db.collection("Payments").document(documentId).update(new HashMap<>(updater));
                                Toast.makeText(v.getContext(), "Wrong Biding by the Investor", Toast.LENGTH_SHORT).show();
                            }
                            else if (sum==100){

                                String newamount=Integer.toString(sum);
                                Map<String,Object> up=new HashMap<>();
                                up.put("STATUSs","Accepted");
                                up.put("Payment",newamount);
                                db.collection("Mylists").document(listid).update(new HashMap<>(up));
                                Map<String, Object>  updater=new HashMap<>();
                                updater.put("Status","Accepted");
                                db.collection("Payments").document(documentId).update(new HashMap<>(updater));
                                Map<String, Object>  updater2=new HashMap<>();
                                updater2.put("Payment","YES");
                                updater2.put("Inverstor_id",investerid);
                                db.collection("Projects").document(projectid).update(new HashMap<>(updater2));
                                Toast.makeText(v.getContext(), "Accepted", Toast.LENGTH_SHORT).show();



                            }
                            else if (sum<100){
                                String newamount=Integer.toString(sum);
                                Map<String,Object> up=new HashMap<>();
                                up.put("STATUSs","Biding");
                                up.put("Payment",newamount);
                                db.collection("Mylists").document(listid).update(new HashMap<>(up));
                                Map<String, Object>  updater=new HashMap<>();
                                updater.put("Status","Accepted");
                                db.collection("Payments").document(documentId).update(new HashMap<>(updater));
                                Toast.makeText(v.getContext(), "Accepted", Toast.LENGTH_SHORT).show();
                            }









                        }
                    }
                    else {
                        Toast.makeText(v.getContext(), "OOPS! PROJECT REMOVES BY THE INVESTOR", Toast.LENGTH_SHORT).show();
                    }





                  /*  if (documentSnapshot.exists()){

                      String prepayment=documentSnapshot.getString("Payment");
                        int prepaymennnt=Integer.parseInt(prepayment);
                        if (prepayment.equals("NO")){
                            Map<String,Object> up=new HashMap<>();
                            up.put("STATUSs","Accepted");
                            up.put("Payment",Paymentamount);
                            db.collection("Mylists").document(listid).update(new HashMap<>(up)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(v.getContext(), "Accepted", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(v.getContext(), "Network Error", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                        }else {
                            int prepaymennnt2=Integer.parseInt(Paymentamount);
                           int sum = prepaymennnt+prepaymennnt2;
                            if (sum>100){
                                Toast.makeText(v.getContext(), "Request is increasing  from Total Fund", Toast.LENGTH_SHORT).show();
                            }else {
                                Map<String,Object> up=new HashMap<>();
                                up.put("STATUSs","Accepted");
                                up.put("Payment",Integer.toString(sum));
                                db.collection("Mylists").document(listid).update(new HashMap<>(up)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(v.getContext(), "Accepted", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(v.getContext(), "Network Error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });


                            }


                        }

                    }else {

                        Toast.makeText(v.getContext(), "Fund Raising canceled by the investor", Toast.LENGTH_SHORT).show();
                    }   */

                }

            });




                /*
                Map<String, String> updater = new HashMap<>();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String Uid = user.getUid();
                updater.put("Inverstor_id", investerid);
                updater.put("Payment", "YES");
                updater.put("Status","Completed");
                db.collection("Projects").document(projectid).update(new HashMap<>(updater));
                Map<String, String> updater2 = new HashMap<>();
                updater.put("Payment", "YES");
                db.collection("Mylists").whereEqualTo("Investorid",investerid).whereEqualTo("Project",projectid).get().addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                       String docid=document.getId();
                                        db.collection("Mylists").document(docid).delete();

                                    }
                                }

                            }
                        }
                );


                Toast.makeText(v.getContext(), "Confirmed: ", Toast.LENGTH_SHORT).show();

                    */



            }
        });


    }



    @NonNull
    @Override
    public payemntholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.paymentapprovals, parent, false);

        return new payemntholder(v);
    }

    class payemntholder extends RecyclerView.ViewHolder {


        TextView name, fund, title,textView110;
        ImageView profile;
        Button accpet, reject;


        public payemntholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView109);
            fund = itemView.findViewById(R.id.textView112);
            title = itemView.findViewById(R.id.textView113);
            profile = itemView.findViewById(R.id.imageView53);
            accpet = itemView.findViewById(R.id.button13);
            reject = itemView.findViewById(R.id.button14);
            textView110 = itemView.findViewById(R.id.textView110);


        }
    }
}
