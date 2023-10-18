package com.example.enter131212;

public class requests {
    private String Statuschat;
    private String Senderid;
    private String Reciverid;
    private Object DATE;

    public requests() {

    }

    public requests(String statuschat, String senderid, String reciverid, Object DATE) {
        Statuschat = statuschat;
        Senderid = senderid;
        Reciverid = reciverid;
        this.DATE = DATE;
    }

    public String getStatuschat() {
        return Statuschat;
    }

    public void setStatuschat(String statuschat) {
        Statuschat = statuschat;
    }

    public String getSenderid() {
        return Senderid;
    }

    public void setSenderid(String senderid) {
        Senderid = senderid;
    }

    public String getReciverid() {
        return Reciverid;
    }

    public void setReciverid(String reciverid) {
        Reciverid = reciverid;
    }

    public Object getDATE() {
        return DATE;
    }

    public void setDATE(Object DATE) {
        this.DATE = DATE;
    }
}
