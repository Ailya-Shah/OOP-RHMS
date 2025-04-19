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
// Vitals database class
class VitalsDatabase {
    List<VitalSign> vitals;
    
    public VitalsDatabase() {
        this.vitals = new ArrayList<>();
    }
    
    public void addVitalSign(VitalSign vs) {
        vitals.add(vs);
    }
    
    public void displayVitals(String pid) {
        for (VitalSign v : vitals) {
            if (v.patientID.equals(pid)) {
                System.out.println("Heart Rate: " + v.heartRate);
                System.out.println("Oxygen: " + v.oxygenLevel);
                System.out.println("BP: " + v.bloodPressure);
                System.out.println("Temp: " + v.temperature);
                return;
            }
        }
        System.out.println("No vitals found");
    }
}