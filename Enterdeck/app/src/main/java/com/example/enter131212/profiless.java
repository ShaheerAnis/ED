package com.example.enter131212;

public class profiless {
    private String Category;
    private  String Contact;
    private  String URI;
    private String Name;
    private  String Gender;
    private  String Date_of_Birth;
    private  String National_id;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    private String Country;

    public  profiless(){

    }


    public profiless(String category, String contact, String URI, String name, String gender, String date_of_Birth, String national_id, String country) {
        Category = category;
        Contact = contact;
        this.URI = URI;
        Name = name;
        Gender = gender;
        Date_of_Birth = date_of_Birth;
        National_id = national_id;
        Country = country;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDate_of_Birth() {
        return Date_of_Birth;
    }

    public void setDate_of_Birth(String date_of_Birth) {
        Date_of_Birth = date_of_Birth;
    }

    public String getNational_id() {
        return National_id;
    }

    public void setNational_id(String national_id) {
        National_id = national_id;
    }
}
