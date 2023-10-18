package com.example.enter131212;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class messagesadapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<msgmodelclass> messagesAdapterarraylist;
    int ITEM_SEND=1;
    int ITEM_RECIVE=2;

    public messagesadapter(Context context, ArrayList<msgmodelclass> messagesAdapterarraylist) {
        this.context = context;
        this.messagesAdapterarraylist = messagesAdapterarraylist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if (viewType==ITEM_SEND){

          View view= LayoutInflater.from(context).inflate(R.layout.senderlayout,parent,false);
          return  new senderViewholer(view);
      }
      else {
          View view= LayoutInflater.from(context).inflate(R.layout.reciver_layout,parent,false);
          return  new reciverViewholder(view);
      }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgmodelclass messages= messagesAdapterarraylist.get(position);
        if (holder.getClass()==senderViewholer.class){
            senderViewholer viewholer=(senderViewholer) holder;
            viewholer.textView82.setText(messages.getMessage());


        }else {
           reciverViewholder viewholer=(reciverViewholder) holder;
            viewholer.textView81.setText(messages.getMessage());
        }

    }

    @Override
    public int getItemCount() {
   return messagesAdapterarraylist.size();
       // return  0;
    }

    @Override
    public int getItemViewType(int position) {
        msgmodelclass messages=messagesAdapterarraylist.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderid())){
            return  ITEM_SEND;

        }
        else {
            return  ITEM_RECIVE;
        }
    }

    class senderViewholer extends  RecyclerView.ViewHolder{
        TextView textView82;

        public senderViewholer(@NonNull View itemView) {
            super(itemView);
            textView82=itemView.findViewById(R.id.textView82);
        }
    }

    class  reciverViewholder extends RecyclerView.ViewHolder{

        TextView textView81;

        public reciverViewholder(@NonNull View itemView) {
            super(itemView);
            textView81=itemView.findViewById(R.id.textView81);
        }
    }
}
