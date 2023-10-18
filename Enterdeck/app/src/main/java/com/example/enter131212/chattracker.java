package com.example.enter131212;

public class chattracker {
 private  String Name;
 private  String URI;

 private  Object DATE;

    public chattracker(String name, String URI, Object DATE) {
        Name = name;
        this.URI = URI;
        this.DATE = DATE;
    }

    public Object getDATE() {
        return DATE;
    }

    public void setDATE(Object DATE) {
        this.DATE = DATE;
    }

    public chattracker() {

    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
