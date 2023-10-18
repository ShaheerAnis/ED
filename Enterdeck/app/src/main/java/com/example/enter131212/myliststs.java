package com.example.enter131212;

public class myliststs {

    private  String Project;
    private  String Investorid;
    private  String Payment;
    private  String STATUSs;

    public myliststs() {

    }

    public myliststs(String project, String investorid, String payment, String STATUSs) {
        Project = project;
        Investorid = investorid;
        Payment = payment;
        this.STATUSs = STATUSs;
    }

    public String getProject() {
        return Project;
    }

    public void setProject(String project) {
        Project = project;
    }

    public String getInvestorid() {
        return Investorid;
    }

    public void setInvestorid(String investorid) {
        Investorid = investorid;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getSTATUSs() {
        return STATUSs;
    }

    public void setSTATUSs(String STATUSs) {
        this.STATUSs = STATUSs;
    }
}
