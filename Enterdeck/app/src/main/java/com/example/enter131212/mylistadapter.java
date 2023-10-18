package com.example.enter131212;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class mylistadapter extends FirestoreRecyclerAdapter<myliststs, mylistadapter.listholder> {


    public mylistadapter(@NonNull FirestoreRecyclerOptions<myliststs> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull listholder holder, int position, @NonNull myliststs model) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String projectid = model.getProject();
        String userid = model.getInvestorid();
        String eee;
        final Boolean[] layout = {false};
        final Boolean[] hundred = {false};
        final Boolean[] fifityy = {false};
        final Boolean[] teenn = {false};
        final Boolean[] thirt = {false};

        String paymentvalue = model.getPayment();
        if (paymentvalue.equals("NO")) {
            holder.paymrntstatus.setText("PENDING");
        } else {

            holder.paymrntstatus.setText(paymentvalue+"%");
        }


        db.collection("Projects").document(projectid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // Set the TextViews in the list item with the corresponding values from the document
                holder.title.setText(documentSnapshot.getString("Title"));
                holder.date.setText(documentSnapshot.getString("Submission_Date"));
                holder.discrption.setText(documentSnapshot.getString("Descripion"));
                int itemNumber = position + 1;
                holder.textView105.setText(String.valueOf(itemNumber));

                holder.fund.setText(documentSnapshot.getString("Fund"));
            /*    Double rating=documentSnapshot.getDouble("Rating");
                int value=rating.intValue();
                holder.rating.setText(Integer.toString(value));*/
                String imageuriproject = documentSnapshot.getString("URI");
                Picasso.get().load(imageuriproject).into(holder.projectprofile);
                String entid = documentSnapshot.getString("Enterprnuer_id");
                holder.dumy.setText(entid);

                db.collection("Profiles").document(entid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        holder.entname.setText(documentSnapshot.getString("Name"));
                        String profileuri = documentSnapshot.getString("URI");
                        Picasso.get().load(profileuri).into(holder.profile);

                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to delete this project?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Call delete function here
                        deleteitem(holder.getAdapterPosition());
                        // String documemtId= getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                        //   Toast.makeText(v.getContext(), "Deleted document with ID: " +documemtId, Toast.LENGTH_SHORT).show();


                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.sendapproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (teenn[0]==true || fifityy[0]==true || hundred[0]==true || thirt[0]==true ){

                    String paymentv = "";
                    if (teenn[0] == true) {
                       paymentv="20";
                    } else if (fifityy[0] == true) {
                        paymentv="50";

                    } else if (hundred[0] == true) {
                        paymentv="100";

                    } else if (thirt[0] == true) {
                        paymentv="30";

                    }


                 /* payment.put("Projectid",projectid);
                    payment.put("Inverstorid", userid);
                    payment.put("Entid", entId);
                    payment.put("Status","Waiting");*/

                    if (paymentvalue.equals("NO")){
                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(position);
                        String documentId = snapshot.getId();
                        Date d= new Date();
                        Map<String,Object>  updaterrr=new HashMap<>();
                        updaterrr.put("Projectid",projectid);
                        updaterrr.put("Inverstorid",userid);
                        updaterrr.put("Entid",holder.dumy.getText().toString());
                        updaterrr.put("Status","Waiting");
                        updaterrr.put("Payment",paymentv);
                        updaterrr.put("DATE",d);
                        updaterrr.put("listid",documentId);
                        db.collection("Payments").add(updaterrr).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(v.getContext(), "Approval sended!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(v.getContext(), "Network Issue!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    } else {
                        DocumentSnapshot snapshot = getSnapshots().getSnapshot(position);
                        String documentId = snapshot.getId();
                        Date d= new Date();
                        Map<String,Object>  updaterrr=new HashMap<>();
                        updaterrr.put("Projectid",projectid);
                        updaterrr.put("Inverstorid",userid);
                        updaterrr.put("Entid",holder.dumy.getText().toString());
                        updaterrr.put("Status","Waiting");
                        updaterrr.put("Payment",paymentv);
                        updaterrr.put("DATE",d);
                        updaterrr.put("listid",documentId);

                        int integerNumber = Integer.parseInt(paymentvalue);
                        int integerNumber2= Integer.parseInt(paymentv);
                        int sum=integerNumber+integerNumber2;
                         if (sum>100){

                             Toast.makeText(v.getContext(), "Amount Exceeding", Toast.LENGTH_SHORT).show();
                         }
                         else {
                             db.collection("Payments").add(updaterrr).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                 @Override
                                 public void onComplete(@NonNull Task<DocumentReference> task) {
                                     if (task.isSuccessful()){
                                         Toast.makeText(v.getContext(), "Approval sended!", Toast.LENGTH_SHORT).show();
                                     }
                                     else {
                                         Toast.makeText(v.getContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                                     }

                                 }
                             });




                         }







                    }



















                }
                else {
                    Toast.makeText(v.getContext(), "Selcted Any option", Toast.LENGTH_SHORT).show();
                }


            }
        });
        holder.hunder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDrawable2 = v.getContext().getResources().getDrawable(R.drawable.newbac2);
                Drawable myDrawable = v.getContext().getResources().getDrawable(R.drawable.newbac);
                holder.hunder.setBackgroundDrawable(myDrawable);
                holder.fifty.setBackgroundDrawable(myDrawable2);
                holder.thirty.setBackgroundDrawable(myDrawable2);
                holder.ten.setBackgroundDrawable(myDrawable2);

                holder.hunder.setTextColor(v.getContext().getResources().getColor(R.color.white));

                holder.ten.setTextColor(v.getContext().getResources().getColor(R.color.black));
                holder.fifty.setTextColor(v.getContext().getResources().getColor(R.color.black));
                holder.thirty.setTextColor(v.getContext().getResources().getColor(R.color.black));
                hundred[0] = true;
                teenn[0] = false;
                fifityy[0] = false;

                thirt[0] = false;

            }
        });
        holder.fifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDrawable2 = v.getContext().getResources().getDrawable(R.drawable.newbac2);
                Drawable myDrawable = v.getContext().getResources().getDrawable(R.drawable.newbac);
                holder.fifty.setBackgroundDrawable(myDrawable);
                holder.hunder.setBackgroundDrawable(myDrawable2);
                holder.thirty.setBackgroundDrawable(myDrawable2);
                holder.ten.setBackgroundDrawable(myDrawable2);
                holder.fifty.setTextColor(v.getContext().getResources().getColor(R.color.white));
                holder.hunder.setTextColor(v.getContext().getResources().getColor(R.color.black));
                holder.ten.setTextColor(v.getContext().getResources().getColor(R.color.black));
                holder.thirty.setTextColor(v.getContext().getResources().getColor(R.color.black));
                fifityy[0] = true;
                teenn[0] = false;

                hundred[0] = false;
                thirt[0] = false;

            }
        });
        holder.thirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drawable myDrawable2 = v.getContext().getResources().getDrawable(R.drawable.newbac2);
                Drawable myDrawable = v.getContext().getResources().getDrawable(R.drawable.newbac);

                holder.thirty.setBackgroundDrawable(myDrawable);
                holder.fifty.setBackgroundDrawable(myDrawable2);
                holder.hunder.setBackgroundDrawable(myDrawable2);
                holder.ten.setBackgroundDrawable(myDrawable2);

                holder.thirty.setTextColor(v.getContext().getResources().getColor(R.color.white));

                holder.hunder.setTextColor(v.getContext().getResources().getColor(R.color.black));
                holder.ten.setTextColor(v.getContext().getResources().getColor(R.color.black));
                holder.fifty.setTextColor(v.getContext().getResources().getColor(R.color.black));

                thirt[0] = true;
                teenn[0] = false;
                fifityy[0] = false;
                hundred[0] = false;



            }
        });
        holder.ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable myDrawable2 = v.getContext().getResources().getDrawable(R.drawable.newbac2);
                Drawable myDrawable = v.getContext().getResources().getDrawable(R.drawable.newbac);
                holder.ten.setBackgroundDrawable(myDrawable);
                holder.fifty.setBackgroundDrawable(myDrawable2);
                holder.thirty.setBackgroundDrawable(myDrawable2);
                holder.hunder.setBackgroundDrawable(myDrawable2);

                holder.ten.setTextColor(v.getContext().getResources().getColor(R.color.white));

                holder.hunder.setTextColor(v.getContext().getResources().getColor(R.color.black));
                holder.fifty.setTextColor(v.getContext().getResources().getColor(R.color.black));
                holder.thirty.setTextColor(v.getContext().getResources().getColor(R.color.black));
                teenn[0] = true;

                fifityy[0] = false;
                hundred[0] = false;
                thirt[0] = false;

            }
        });


        holder.sendpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (layout[0] == false) {
                    holder.approcallayout.setVisibility(View.VISIBLE);
                    layout[0] = true;
                } else if (layout[0] == true) {
                    holder.approcallayout.setVisibility(View.GONE);
                    holder.ten.setTextColor(v.getContext().getResources().getColor(R.color.black));
                    holder.hunder.setTextColor(v.getContext().getResources().getColor(R.color.black));
                    holder.fifty.setTextColor(v.getContext().getResources().getColor(R.color.black));
                    holder.thirty.setTextColor(v.getContext().getResources().getColor(R.color.black));

                    Drawable myDrawable2 = v.getContext().getResources().getDrawable(R.drawable.newbac2);
                    holder.ten.setBackgroundDrawable(myDrawable2);
                    holder.fifty.setBackgroundDrawable(myDrawable2);
                    holder.thirty.setBackgroundDrawable(myDrawable2);
                    holder.hunder.setBackgroundDrawable(myDrawable2);
                    teenn[0] = false;
                    fifityy[0] = false;
                    hundred[0] = false;
                    thirt[0] = false;
                    layout[0] = false;

                }





             /*   db.collection("Projects").document(projectid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String entId = documentSnapshot.getString("Enterprnuer_id");
                        Map<String, Object> payment = new HashMap<>();
                        payment.put("Projectid",projectid);
                        payment.put("Inverstorid", userid);
                        payment.put("Entid", entId);
                        payment.put("Status","Waiting");

                        db.collection("Payments").whereEqualTo("Projectid",projectid).whereEqualTo("Inverstorid",userid).whereEqualTo("Entid",entId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (queryDocumentSnapshots.isEmpty()){
                                    db.collection("Payments").add(payment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Toast.makeText(v.getContext(), "Approval sended!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                else {

                                    Toast.makeText(v.getContext(), "Waiting!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });









*/
            }


        });


    }

    public void deleteitem(int positin) {
        getSnapshots().getSnapshot(positin).getReference().delete();
    }

    @NonNull
    @Override
    public listholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.mylistlauout, parent, false);

        return new listholder(vv);
    }

    class listholder extends RecyclerView.ViewHolder {
        TextView entname, date, title, discrption, fund, paymrntstatus, textView105,dumy;
        ImageView profile, projectprofile, delete;
        Button sendpayment, sendapproval, hunder, fifty, ten, thirty;
        ConstraintLayout approcallayout;


        public listholder(@NonNull View itemView) {
            super(itemView);
            entname = itemView.findViewById(R.id.textView92);
            approcallayout = itemView.findViewById(R.id.ajiiii);
            profile = itemView.findViewById(R.id.imageView88);
            projectprofile = itemView.findViewById(R.id.imageView49);
            sendpayment = itemView.findViewById(R.id.button12);
            delete = itemView.findViewById(R.id.imageView50);
            date = itemView.findViewById(R.id.textView102);
            title = itemView.findViewById(R.id.textView100);
            discrption = itemView.findViewById(R.id.textView101);
            fund = itemView.findViewById(R.id.textView103);
            textView105 = itemView.findViewById(R.id.textView105);
            paymrntstatus = itemView.findViewById(R.id.textView107);
            sendapproval = itemView.findViewById(R.id.button18);
            hunder = itemView.findViewById(R.id.button19);
            fifty = itemView.findViewById(R.id.button20);
            thirty = itemView.findViewById(R.id.button21);
            ten = itemView.findViewById(R.id.button22);

                    dumy = itemView.findViewById(R.id. textView132);

        }
    }
}
