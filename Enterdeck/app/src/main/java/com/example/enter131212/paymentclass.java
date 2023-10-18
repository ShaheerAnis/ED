package com.example.enter131212;

public class paymentclass {
    private  String Entid;
    private  String Inverstorid;
    private  String Projectid;
    private  String Status;
    private  String Payment;
    private  String listid;

    public paymentclass() {


    }

    public paymentclass(String entid, String inverstorid, String projectid, String status, String payment, String listid) {
        Entid = entid;
        Inverstorid = inverstorid;
        Projectid = projectid;
        Status = status;
        Payment = payment;
        this.listid = listid;
    }

    public String getEntid() {
        return Entid;
    }

    public void setEntid(String entid) {
        Entid = entid;
    }

    public String getInverstorid() {
        return Inverstorid;
    }

    public void setInverstorid(String inverstorid) {
        Inverstorid = inverstorid;
    }

    public String getProjectid() {
        return Projectid;
    }

    public void setProjectid(String projectid) {
        Projectid = projectid;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }
}
