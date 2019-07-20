package com.example.jeffe.login.FragmentsOption;

public class ReservationsClass {

    String name_park;
    String name_user;
    String license;
    String hours;

    public ReservationsClass(String name_park, String name_user, String license, String hours) {
        this.name_park = name_park;
        this.name_user = name_user;
        this.license = license;
        this.hours = hours;
    }

    public String getName_park() {
        return name_park;
    }

    public void setName_park(String name_park) {
        this.name_park = name_park;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }
}
