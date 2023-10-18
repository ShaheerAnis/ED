package com.example.enter131212;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class chatingactvity extends AppCompatActivity {

    private TextView username;
    private ImageView profilepic;

    private DatabaseReference mDatabaseReference;
    private EditText mMessageEditText;
    private ImageView mSendButton;
    private RecyclerView mMessageRecyclerView;
    private MessageAdapter mAdapter;

    private String mSenderId;
    private String mReceiverId;
    private String imgurirecv;
    private String imguresend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatingactvity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize views
        username = findViewById(R.id.textView78);
        profilepic = findViewById(R.id.imageView36);
        mMessageEditText = findViewById(R.id.message);
        mSendButton = findViewById(R.id.imageView35);
        mMessageRecyclerView = findViewById(R.id.messageboxx);

        // Get intent extras
        Intent intent = getIntent();
        mSenderId = intent.getStringExtra("senderid");
        mReceiverId = intent.getStringExtra("Reciverid");
        imgurirecv = intent.getStringExtra("reciverimg");
        imguresend = intent.getStringExtra("senderimg");

        // Set username and profile picture
        username.setText(intent.getStringExtra("recvivername"));
        Picasso.get().load(imgurirecv).into(profilepic);

        // Set up RecyclerView and adapter
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MessageAdapter(new ArrayList<Message>(), this, mSenderId);
        mMessageRecyclerView.setAdapter(mAdapter);

        // Load existing messages from the database
        loadMessages();

        // Set click listener for send button
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessageEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    // Create a new message object
                    long timestamp = System.currentTimeMillis();
                    Message newMessage = new Message(message, mSenderId, mReceiverId, timestamp);

                    // Save the message to the database
                    mDatabaseReference.child("messages").push().setValue(newMessage);

                    // Clear the message edit text
                    mMessageEditText.setText("");
                }
            }
        });
    }

    private void loadMessages() {
        Query messageQuery = mDatabaseReference.child("messages").orderByChild("timestamp");
        messageQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    if (message != null && ((message.getSenderId().equals(mSenderId) && message.getReceiverId().equals(mReceiverId))
                            || (message.getSenderId().equals(mReceiverId) && message.getReceiverId().equals(mSenderId)))) {
                        messages.add(message);
                    }
                }
                mAdapter.setMessages(messages);
                mAdapter.notifyDataSetChanged();
                mMessageRecyclerView.smoothScrollToPosition(mAdapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ChatActivity", "Failed to load messages.", databaseError.toException());
            }
        });
    }
}


