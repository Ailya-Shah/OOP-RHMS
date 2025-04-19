/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
import java.util.Date;
// Appointment class
class Appointment {
    String appointmentID;
    Date date;
    String time;
    String doctorID;
    String patientID;
    String status;
    
    public Appointment(String aid, Date date, String time, String did, String pid, String status) {
        this.appointmentID = aid;
        this.date = date;
        this.time = time;
        this.doctorID = did;
        this.patientID = pid;
        this.status = status;
    }
    
    public void display() {
        System.out.println("Appt " + appointmentID + " with Dr." + doctorID + " at " + time);
    }
}
