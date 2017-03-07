package com.parse.stafettvarvet15;

public class ModelTeam {

    String name;
    String company;
    int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */

    ModelTeam(String name, String company, int value){
        this.name = name;
        this.company = company;
        this.value = value;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}