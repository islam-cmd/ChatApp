package com.example.chatapp.Model;

public class OnlineConsultation {
    private String URI;
    private String description;
    private String patientID;
    private String docname;
    private String patientName;
    private String docopinion;
    private String subject;



    public OnlineConsultation(String URI, String description, String subject, String docname, String docopinion, String patientID, String patientName ) {
        this.URI = URI;
        this.description = description;
        this.patientID = patientID;
        this.docname = docname;
        this.docopinion = docopinion;
        this.patientName = patientName;
        this.subject =subject;
    }

    public OnlineConsultation() {
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = this.URI;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    @Override
    public String   toString() {
        return "OnlineConsultation{" +
                "URI='" + URI + '\'' +
                ", description='" + description + '\'' +
                ", patientID='" + patientID + '\'' +
                ", docname='" + docname + '\'' +
                ", patientName='" + patientName + '\'' +
                ", docopinion='" + docopinion + '\'' +
                '}';
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDocopinion() {
        return docopinion;
    }

    public void setDocopinion(String docopinion) {
        this.docopinion = docopinion;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}