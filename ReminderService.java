/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
class ReminderService {
    private Notifiable notifier;

    public ReminderService(Notifiable notifier) {
        this.notifier = notifier;
    }

    public void sendVideoCallNotification(VideoConsultation consultation, String patientEmail, String patientPhone) {
        String message = "New video consultation scheduled with Dr. " + consultation.getDoctorId() + 
                        " at " + consultation.getScheduledTime() + 
                        "\nMeeting Link: " + consultation.getMeetingLink();
        
        // Send email notification
        notifier.sendNotification(message, patientEmail);
        
        // For SMS, you might want a shorter message
        String smsMessage = "Video consult with Dr. " + consultation.getDoctorId() + 
                           " at " + consultation.getScheduledTime().toLocalTime() + 
                           ". Link: " + consultation.getMeetingLink();
        // notifier.sendNotification(smsMessage, patientPhone);
    }
}
