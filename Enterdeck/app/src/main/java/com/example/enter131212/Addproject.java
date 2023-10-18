package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Addproject extends AppCompatActivity {

    Spinner spinner;
    ProgressBar progressBar6;
    String cate;
    private ArrayAdapter<CharSequence> categoryeadapter;
    EditText titlee, discription, fund;
    String title1, discriptionn;
    Button addproject;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uidd;
    ImageView projectmage;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference("ProjectImages");
    DocumentReference documentReference;

    //image
    private Uri imageUri;
    private static final int PICK_IMAGE = 1;
    UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        titlee = (EditText) findViewById(R.id.titlee);
        //  only alphbets
        titlee.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder();
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (!Character.isDigit(c)) {
                        builder.append(c);
                    }
                }
                // If all characters are non-digits, return the filtered string
                boolean allCharactersAreNonDigits = (builder.length() == end - start);
                return allCharactersAreNonDigits ? builder.toString() : "";
            }
        };
        titlee.setFilters(new InputFilter[]{filter});



        fund = (EditText) findViewById(R.id.titlee2);
        discription = (EditText) findViewById(R.id.discription);
        discription.setInputType(InputType.TYPE_CLASS_TEXT);
        InputFilter filterr = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // Check if the first character is a letter
                if (dstart == 0 && source.length() > 0 && !Character.isLetter(source.charAt(0))) {
                    // Display a toast message and return an empty string to prevent the character from being entered
                    Toast.makeText(getApplicationContext(), "You can only start with a letter", Toast.LENGTH_SHORT).show();
                    return "";
                }
                // Allow all other characters
                return null;
            }
        };
        discription.setFilters(new InputFilter[] { filterr });



        progressBar6 = (ProgressBar) findViewById(R.id.progressBar6);
        addproject = (Button) findViewById(R.id.button);

        Intent intent = getIntent();
        uidd = intent.getStringExtra("message_key");
        projectmage = (ImageView) findViewById(R.id.imageView29);
        projectmage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();
            }
        });


        addproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title1 = titlee.getText().toString().trim();
                discriptionn = discription.getText().toString().trim();
                String fundd = fund.getText().toString().trim();

                if (cate.equals("Choose Category")) {
                    Toast.makeText(Addproject.this, "Choose Category", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(title1)) {

                    titlee.setError("Enter Title");

                } else if (TextUtils.isEmpty(discriptionn)) {

                    discription.setError("Enter description");

                } else if (discriptionn.length() < 50) {
                    discription.setError("Please provide more details in description");


                } else if (imageUri == null) {
                    Toast.makeText(Addproject.this, "Select image For Project", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(fundd)) {
                   fund.setError("Enter Fund");
                   // Toast.makeText(Addproject.this, "Some Feilds are missing", Toast.LENGTH_SHORT).show();
                } else {
                    // uploading data into the datebase
                    Uploaddata(title1, discriptionn, cate, fundd);
                }
            }
        });

// category choosser
        spinner = (Spinner) findViewById(R.id.spinner);
        categoryeadapter = ArrayAdapter.createFromResource(this, R.array.Category, R.layout.spinner_layout);
        categoryeadapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(categoryeadapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cate = spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void chooseimage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(projectmage);
        } else {
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        }

    }

    private void Uploaddata(String title1, String discriptionn, String cate, String fund) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        progressBar6.setVisibility(View.VISIBLE);
                        addproject.setEnabled(false);


                        final StorageReference sf = storageReference.child(System.currentTimeMillis() + "." + getFileExt(imageUri));
                        uploadTask = sf.putFile(imageUri);

                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();


                                }
                                return sf.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloaduri = task.getResult();
                                    Date d = new Date();
                                    String submitted_date = DateFormat.getDateInstance(DateFormat.SHORT).format(d);
// add priority;
                                    long treanding = 0;
                                    int rating = 5;
                                    int one = 0, two = 0, three = 0, four = 0, five = 1;
                                    Map<String, Object> projects = new HashMap<>();
                                    projects.put("Title", title1);
                                    projects.put("Descripion", discriptionn);
                                    projects.put("Category", cate);
                                    projects.put("Submission_Date", submitted_date);
                                    projects.put("Enterprnuer_id", uidd);
                                    projects.put("Status", "Pending");
                                    projects.put("Payment", "No");
                                    projects.put("Trending", treanding);
                                    projects.put("Fund", fund);
                                    projects.put("Rating", rating);
                                    projects.put("no_of_onestar", one);
                                    projects.put("no_of_twostar", two);
                                    projects.put("no_of_threestar", three);
                                    projects.put("no_of_fourstar", four);
                                    projects.put("no_of_fivestar", five);
                                    projects.put("Inverstor_id", "Null");
                                    projects.put("URI", downloaduri.toString());
                                    db.collection("Projects").add(projects).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                progressBar6.setVisibility(View.GONE);
                                                addproject.setEnabled(true);
                                                Toast.makeText(Addproject.this, "Project Added", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Addproject.this, Dashboard.class));
                                                finish();
                                            } else {
                                                progressBar6.setVisibility(View.GONE);

                                                addproject.setEnabled(true);
                                                Toast.makeText(Addproject.this, "Network Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            progressBar6.setVisibility(View.GONE);
                                            addproject.setEnabled(true);
                                            Toast.makeText(Addproject.this, "Network Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar6.setVisibility(View.GONE);
                                addproject.setEnabled(true);
                            }
                        });

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();




    }

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}