package com.example.enter131212;

public class Fundclass {
    private String Enterpnuer_id;
    private String Investorid;
    private String Project;

    public Fundclass() {




    }

    public Fundclass(String enterpnuer_id, String investorid, String project) {
        Enterpnuer_id = enterpnuer_id;
        Investorid = investorid;
        Project = project;
    }

    public String getEnterpnuer_id() {
        return Enterpnuer_id;
    }

    public void setEnterpnuer_id(String enterpnuer_id) {
        Enterpnuer_id = enterpnuer_id;
    }

    public String getInvestorid() {
        return Investorid;
    }

    public void setInvestorid(String investorid) {
        Investorid = investorid;
    }

    public String getProject() {
        return Project;
    }

    public void setProject(String project) {
        Project = project;
    }
}
