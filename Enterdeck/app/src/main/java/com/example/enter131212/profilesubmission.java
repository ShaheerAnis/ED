package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class profilesubmission extends AppCompatActivity {
    ProgressBar progressBar2;
  private   ArrayAdapter<CharSequence> categoryeadapter;
    Spinner spinner2;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    String UID = firebaseUser.getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RadioGroup arg, srg;
    RadioButton ayes, ano, syes, sno;

    EditText gender, date,nationid;
    DatePickerDialog picker;
    EditText category, fullname, country, contact;
    ImageView profilepic;
    Button saveprofile;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference("ProfileImages");
    DocumentReference documentReference;

    //image
    private Uri imageUri;
    private static final int PICK_IMAGE = 1;
    UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilesubmission);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar2=(ProgressBar) findViewById(R.id.progressbar34) ;
        category = (EditText) findViewById(R.id.categoryy);
        fullname = (EditText) findViewById(R.id.editTextTextPersonName);
        country = (EditText) findViewById(R.id.editTextTextPersonName2);
        contact = (EditText) findViewById(R.id.editTextPhone);
        gender = (EditText) findViewById(R.id.editTextTextPersonName3);
        nationid = (EditText) findViewById(R.id.editTextPhone3);

        ayes = (RadioButton) findViewById(R.id.ayes);
        ano = (RadioButton) findViewById(R.id.ano);
        arg = (RadioGroup) findViewById(R.id.arg);
        syes = (RadioButton) findViewById(R.id.syes);
        sno = (RadioButton) findViewById(R.id.sno);
        srg = (RadioGroup) findViewById(R.id.srg);
        profilepic = (ImageView) findViewById(R.id.imageView6);
        saveprofile = (Button) findViewById(R.id.button3);

        spinner2=(Spinner) findViewById(R.id.spinner2);
        categoryeadapter=ArrayAdapter.createFromResource(this,R.array.countries_array,R.layout.spinner_layout);
        categoryeadapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(categoryeadapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country.setText(spinner2.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        documentReference = db.collection("Profiles").document(UID);


        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uploaddata();
            }
        });


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();


            }
        });
        srg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.syes) {
                    category.setText(syes.getText());

                } else if (checkedId == R.id.sno) {
                    category.setText(sno.getText());

                }
            }
        });


        Calendar cal = Calendar.getInstance();
        // submitted_date= DateFormat.getDateInstance().format(cal.getTime());

        arg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ayes) {
                    gender.setText(ayes.getText());

                } else if (checkedId == R.id.ano) {
                    gender.setText(ano.getText());

                }

            }
        });
        date = (EditText) findViewById(R.id.editTextPhone2);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int DAY = calendar.get(Calendar.DAY_OF_MONTH);
                int MONTH = calendar.get(Calendar.MONTH);
                int YEAT = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(profilesubmission.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, YEAT, MONTH, DAY);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();

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
            Picasso.get().load(imageUri).into(profilepic);
        }
    }

    private void Uploaddata() {
        progressBar2.setVisibility(View.VISIBLE);
        saveprofile.setEnabled(false);
        String name = fullname.getText().toString().trim();
        String countryy = country.getText().toString().trim();
        String Contacr = contact.getText().toString().trim();
        String dob = date.getText().toString().trim();
        String gend = gender.getText().toString().trim();
        String cat = category.getText().toString().trim();
        String national=nationid.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Email Cannot be empty", Toast.LENGTH_SHORT).show();
            fullname.setError("");
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);

        } else if (TextUtils.isEmpty(Contacr)) {
            Toast.makeText(this, "missing contact", Toast.LENGTH_SHORT).show();
            contact.setError("");
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);

        } else if (TextUtils.isEmpty(dob)) {
            Toast.makeText(this, "missing Date of birth ", Toast.LENGTH_SHORT).show();
            date.setError("");
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);

        } else if (TextUtils.isEmpty(gend)) {
            Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
            gender.setError("");
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);

        } else if (TextUtils.isEmpty(cat)) {
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
            category.setError("");
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);

        } else if (TextUtils.isEmpty(countryy) || countryy.equals("Choose Country")) {
            Toast.makeText(this, "Select Country", Toast.LENGTH_SHORT).show();
            country.setError("");
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);
        } else if(imageUri == null){
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);

            Toast.makeText(this, "Select Profile image", Toast.LENGTH_SHORT).show();
        }
        else if(Contacr.length()<11){
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);

            Toast.makeText(this, "Enter Valid Number", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(national)){
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);

            Toast.makeText(this, "National ID is missing", Toast.LENGTH_SHORT).show();

        }
        else if (national.length()<11){
            progressBar2.setVisibility(View.GONE);
            saveprofile.setEnabled(true);
            nationid.setError("");
            Toast.makeText(this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
            
        }
        else {

            final  StorageReference  sf =storageReference.child(System.currentTimeMillis()+"."+getFileExt(imageUri));
            uploadTask= sf.putFile(imageUri);

            Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();



                    }
                    return  sf.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){

                        int rating = 5;
                        int one = 0, two = 0, three = 0, four = 0, five = 1;
                        Uri downloaduri=task.getResult();
                        Map<String,Object> profile= new HashMap<>();
                        profile.put("Name",name);
                        profile.put("Rating",rating);
                        profile.put("no_of_onestar", one);
                        profile.put("no_of_twostar", two);
                        profile.put("no_of_threestar", three);
                        profile.put("no_of_fourstar", four);
                        profile.put("no_of_fivestar", five);
                        profile.put("Country",countryy);
                        profile.put("Contact",Contacr);
                        profile.put("Date_of_Birth",dob);
                        profile.put("Gender",gend);
                        profile.put("Category",cat);
                        profile.put("National_id",national);
                        profile.put("URI",downloaduri.toString());
                        db.collection("Profiles").document(UID).set(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){

                                    progressBar2.setVisibility(View.VISIBLE);
                                    saveprofile.setEnabled(true);
                                    Toast.makeText(profilesubmission.this, "ProfileCompleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(profilesubmission.this,MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(profilesubmission.this, "Failed", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar2.setVisibility(View.GONE);
                    saveprofile.setEnabled(true);

                }
            });

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        mAuth.signOut();
    }

    private String getFileExt(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}