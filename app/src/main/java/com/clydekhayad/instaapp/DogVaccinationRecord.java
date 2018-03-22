package com.clydekhayad.instaapp;

/**
 * Created by Clyde A. Khayad on 3/15/2018.
 */

public class DogVaccinationRecord {

    private String date;
    private String clinic;
    private String vet;
    private String vaccine;

    public DogVaccinationRecord() {

    }

    public DogVaccinationRecord(String dateOfVaccination, String nameOfClinic, String nameOfVet, String nameOfVaccine) {

        this.date = dateOfVaccination;
        this.clinic = nameOfClinic;
        this.vet = nameOfVet;
        this.vaccine = nameOfVaccine;

    }

    public String getVet() {
        return vet;
    }

    public String getClinic() {
        return clinic;
    }

    public String getDate() {
        return date;
    }

    public String getVaccine() {
        return vaccine;
    }

    public void setVet(String nameOfVet) {
        this.vet = nameOfVet;
    }

    public void setClinic(String nameOfClinic) {
        this.clinic = nameOfClinic;
    }

    public void setDate(String dateOfVaccination) {
        this.date = dateOfVaccination;
    }

    public void setVaccine(String nameOfVaccine) {
        this.vaccine = nameOfVaccine;
    }
}
