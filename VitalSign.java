/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
// Vital signs class
class VitalSign {
    String patientID;
    int heartRate;
    int oxygenLevel;
    String bloodPressure;
    float temperature;
    
    public VitalSign(String pid, int hr, int ol, String bp, float temp) {
        this.patientID = pid;
        this.heartRate = hr;
        this.oxygenLevel = ol;
        this.bloodPressure = bp;
        this.temperature = temp;
    }
}
