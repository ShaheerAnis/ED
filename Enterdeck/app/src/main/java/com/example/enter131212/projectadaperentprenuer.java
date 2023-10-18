package com.example.enter131212;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class projectadaperentprenuer extends FirestoreRecyclerAdapter<projectclass, projectadaperentprenuer.viewholerr> {


    public projectadaperentprenuer(@NonNull FirestoreRecyclerOptions<projectclass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholerr holder, int position, @NonNull projectclass model) {
        holder.title.setText(model.getTitle());
        Picasso.get().load(model.getURI()).into(holder.projectsphoto);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        holder.description.setText(model.getDescripion());
        holder.date.setText(model.getSubmission_Date());
        holder.fund.setText(model.getFund());
        holder.payment.setText(model.getPayment());
        holder.status.setText(model.getStatus());
        int ratingg = model.getRating();
        holder.rating.setText(Integer.toString(ratingg));
        String testt = model.getInverstor_id();
        if (testt.equals("Null")) {
            holder.investerid.setText("None");
        } else if (testt.length() > 5) {

            db.collection("Profiles").document(model.getInverstor_id()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    holder.investerid.setText(documentSnapshot.getString("Name"));

                }
            });


        }

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
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View view = inflater.inflate(R.layout.updateproject, null);
                EditText titleEditText = view.findViewById(R.id.title_input);
                EditText descriptionEditText = view.findViewById(R.id.description_input);
                EditText fundEditText = view.findViewById(R.id.fund_input);
                titleEditText.setText(getItem(holder.getAdapterPosition()).getTitle());
                descriptionEditText.setText(getItem(holder.getAdapterPosition()).getDescripion());
                fundEditText.setText(getItem(holder.getAdapterPosition()).getFund());
                builder.setView(view)
                        .setCancelable(false)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })


                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newTitle = titleEditText.getText().toString();
                                String newDescription = descriptionEditText.getText().toString();
                                String newFund = fundEditText.getText().toString();
                                // TODO: Update the project with new data
                                if (newTitle.isEmpty()) {
                                    // Toast title cannot be empty
                                    Toast.makeText(v.getContext(), "Title cannot be empty", Toast.LENGTH_SHORT).show();

                                } else if (newFund.isEmpty()) {
                                    //toast enter fund
                                    Toast.makeText(v.getContext(), "Enter Fund", Toast.LENGTH_SHORT).show();


                                } else if (newDescription.length() < 50) {
                                    //toast provide more detailes
                                    Toast.makeText(v.getContext(), "Provide more details", Toast.LENGTH_SHORT).show();

                                } else {
                                    // code to update the fields in firestore with haspmap  fields name Fund,Title,Descripion
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    CollectionReference projectsRef = db.collection("Projects");
                                    String documentId = getSnapshots().getSnapshot(holder.getAdapterPosition()).getId();
                                    DocumentReference projectDocRef = projectsRef.document(documentId);
                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("Title", newTitle);
                                    updates.put("Descripion", newDescription);
                                    updates.put("Fund", newFund);

                                    projectDocRef.update(updates)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    dialog.dismiss();
                                                    Toast.makeText(v.getContext(), "Project updated successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();
                                                    Toast.makeText(v.getContext(), "Failed to update project.", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }


                            }
                        })
                        .create().show();
            }
        });
    }

    public void deleteitem(int positin) {
        getSnapshots().getSnapshot(positin).getReference().delete();
    }


    @NonNull
    @Override
    public viewholerr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vv = LayoutInflater.from(parent.getContext()).inflate(R.layout.newprojectlayoutdesign, parent, false);

        return new viewholerr(vv);
    }

    class viewholerr extends RecyclerView.ViewHolder {

        TextView title, description, fund, payment, status, rating, investerid, date;
        ImageView projectsphoto, delete, edit;

        public viewholerr(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lalal);
            date = itemView.findViewById(R.id.textView38layout);
            fund = itemView.findViewById(R.id.textView40layout);
            description = itemView.findViewById(R.id.tststst);
            payment = itemView.findViewById(R.id.textView95);
            status = itemView.findViewById(R.id.textView93);
            rating = itemView.findViewById(R.id.textView97);
            investerid = itemView.findViewById(R.id.textView99);
            projectsphoto = itemView.findViewById(R.id.imageView34layout);
            delete = itemView.findViewById(R.id.imageView46);
            edit = itemView.findViewById(R.id.imageView47);


        }


    }


}
