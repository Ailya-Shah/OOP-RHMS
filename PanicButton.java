/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
class PanicButton {
    private String patientId;
    private NotificationService notificationService;

    public PanicButton(String patientId, NotificationService notificationService) {
        this.patientId = patientId;
        this.notificationService = notificationService;
    }

    public void triggerEmergency() {
        String emergencyMessage = "EMERGENCY ALERT! Patient " + patientId + " needs immediate assistance!";
        // Send to admin/emergency contacts
        notificationService.sendNotification(emergencyMessage, "emergency@hospital.com");
        // Could also send to assigned doctors
    }
}
