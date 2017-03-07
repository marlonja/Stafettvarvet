package com.parse.stafettvarvet15;

public class ModelToplist {

    String rank;
    String team;
    String time;
    String company;


    ModelToplist(String rank, String team, String time, String company) {
        this.rank = rank;
        this.team = team;
        this.time = time;
        this.company = company;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}