/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
import java.util.List;
import java.util.ArrayList;
// Appointment manager class
class AppointmentManager {
    List<Appointment> appointments;
    
    public AppointmentManager() {
        this.appointments = new ArrayList<>();
    }
    
    public void addAppointment(Appointment a) {
        appointments.add(a);
    }
    
    public void displayAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        System.out.println("\nALL APPOINTMENTS:");
        for (Appointment a : appointments) {
            a.display();
        }
    }
    
    public void displayAppointmentsForDoctor(String doctorID) {
        boolean found = false;
        System.out.println("\nAPPOINTMENTS FOR DOCTOR " + doctorID + ":");
        for (Appointment a : appointments) {
            if (a.doctorID.equals(doctorID)) {
                a.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments found for this doctor.");
        }
    }
}
