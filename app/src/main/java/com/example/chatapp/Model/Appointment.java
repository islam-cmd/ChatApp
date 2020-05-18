package com.example.chatapp.Model;

import java.util.HashMap;

public class Appointment {
    private String patientId;
    private String clinic;
    private String doctor;
    private String appointmentTime;
    private int status;   // this will show if the appointment is accepted or not 0 if it si not accepted, 1 if it is accpeted o ru can make it a string and put it as accpeted or inprocess or something

//... add any other variables that will be displayed like date and time etc, if u cannot get the id of both the doctor and the patient let me know


    public Appointment(String patientId, String clinic,String doctor,String appointmentTime ,int status) {
        this.patientId = patientId;
        this.clinic = clinic;
        this.doctor = doctor;
        this.appointmentTime = appointmentTime;
        this.status = status;
// add any additional variables


    }

    public String getPatientId() {
        return patientId;
    }

    public String getClinic() {
        return clinic;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public int getStatus() {
        return status;
    }

    // This needs to be  here or the app will crash
    public Appointment() {
    }

//generate all setters and getters
}