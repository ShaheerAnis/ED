package com.example.enter131212;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

public class projectclass  {
    private  String  Category;
    private  String Descripion;
    private  String Enterprnuer_id;
    private  String Fund;
    private  String Inverstor_id;
    private  String  URI;

    public projectclass(String URI) {
        this.URI = URI;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }


    private  String Payment;
    private  int Rating;
    private  String Status;
    private  String Submission_Date;
    private  String Title;
    private  int trending;
    public  projectclass(){



    }

    public projectclass(String category, String descripion, String enterprnuer_id, String fund, String inverstor_id, String payment, int rating, String status, String submission_Date, String title, int trending) {
        Category = category;
        Descripion = descripion;
        Enterprnuer_id = enterprnuer_id;
        Fund = fund;
        Inverstor_id = inverstor_id;
        Payment = payment;
        Rating = rating;
        Status = status;
        Submission_Date = submission_Date;
        Title = title;
        this.trending = trending;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public int getTrending() {
        return trending;
    }

    public void setTrending(int trending) {
        this.trending = trending;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescripion() {
        return Descripion;
    }

    public void setDescripion(String descripion) {
        Descripion = descripion;
    }

    public String getEnterprnuer_id() {
        return Enterprnuer_id;
    }

    public void setEnterprnuer_id(String enterprnuer_id) {
        Enterprnuer_id = enterprnuer_id;
    }

    public String getFund() {
        return Fund;
    }

    public void setFund(String fund) {
        Fund = fund;
    }

    public String getInverstor_id() {
        return Inverstor_id;
    }

    public void setInverstor_id(String inverstor_id) {
        Inverstor_id = inverstor_id;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }



    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSubmission_Date() {
        return Submission_Date;
    }

    public void setSubmission_Date(String submission_Date) {
        Submission_Date = submission_Date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


}
