package com.clydekhayad.instaapp;

/**
 * Created by Clyde A. Khayad on 3/15/2018.
 */

public class DogTreatmentRecord {

    private String date;
    private String clinic;
    private String vet;
    private String operation;

    public DogTreatmentRecord() {

    }

    public DogTreatmentRecord(String dateOfVaccination, String nameOfClinic, String nameOfVet, String nameOfOperation) {

        this.date = dateOfVaccination;
        this.clinic = nameOfClinic;
        this.vet = nameOfVet;
        this.operation = nameOfOperation;

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

    public String getOperation() {
        return operation;
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

    public void setOperation(String nameOfOperation) {
        this.operation = nameOfOperation;
    }
}
