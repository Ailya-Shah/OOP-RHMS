/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
// Feedback class
class Feedback {
    String feedbackID;
    String patientID;
    String feedbackText;
    String medication;
    String doctorName;
    
    public Feedback(String fid, String pid, String text, String meds, String doc) {
        this.feedbackID = fid;
        this.patientID = pid;
        this.feedbackText = text;
        this.medication = meds;
        this.doctorName = doc;
    }
}
