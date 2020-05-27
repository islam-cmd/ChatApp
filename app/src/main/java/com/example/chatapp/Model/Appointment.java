package com.example.chatapp.Model;

public class Appointment {
    String docName;
    double date;
    double docID;
    String additionalInfo;


    public Appointment(String docName, double docID, double date, String additionalInfo) {
        this.additionalInfo = additionalInfo;
        this.docID = docID;
        this.date = date;
        this.docName = docName;
    }

    public String getDocName() {
        return docName;
    }

    public double getDate() {
        return date;
    }

    public void setDate(double date) {
        this.date = date;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public double getDocID() {
        return docID;
    }

    public void setDocID(double docID) {
        this.docID = docID;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }



}
