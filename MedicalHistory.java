/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
import java.util.ArrayList;
import java.util.List;
// Medical history class
class MedicalHistory {
    String patientID;
    List<Feedback> feedbackList;
    
    public MedicalHistory(String patientID) {
        this.patientID = patientID;
        this.feedbackList = new ArrayList<>();
    }
    
    public void addFeedback(Feedback feedback) {
        feedbackList.add(feedback);
    }
    
    public void viewMedicalHistory() {
        for (Feedback f : feedbackList) {
            System.out.println("Doctor: " + f.doctorName);
            System.out.println("Notes: " + f.feedbackText);
            System.out.println("Meds: " + f.medication);
            System.out.println("-----");
        }
    }
}

