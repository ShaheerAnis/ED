package com.example.enter131212;

public class dummy {
    private  String Descripion;
    private  String Fund;
    private  int trending;


    public dummy(String descripion, String fund, int trending) {
        Descripion = descripion;
        Fund = fund;
        this.trending = trending;
    }

    public String getDescripion() {
        return Descripion;
    }

    public void setDescripion(String descripion) {
        Descripion = descripion;
    }

    public String getFund() {
        return Fund;
    }

    public void setFund(String fund) {
        Fund = fund;
    }

    public int getTrending() {
        return trending;
    }

    public void setTrending(int trending) {
        this.trending = trending;
    }
}
