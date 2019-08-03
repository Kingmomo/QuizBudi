package com.darmajayaquizz.quizbudi.Model;

public class Users {
    private String fullname, userrname, email, gender;

    public Users(String fullname, String userrname, String email, String gender) {
        this.fullname = fullname;
        this.userrname = userrname;
        this.email = email;
        this.gender = gender;
    }

    public Users() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUserrname() {
        return userrname;
    }

    public void setUserrname(String userrname) {
        this.userrname = userrname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
