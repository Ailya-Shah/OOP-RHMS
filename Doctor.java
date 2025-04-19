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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// Doctor class
class Doctor extends User {
    String specialization;
    List<Patient> patients; 
    private ChatClient chatClient;
    private List<VideoConsultation> scheduledConsultations;
    private NotificationService notificationService;
    
    public Doctor(String id, String name, String email, String password,String specialization, ChatServer chatServer,NotificationService notificationService) {
        super(id, name, email, password);
        this.specialization = specialization;
        this.patients = new ArrayList<>();
        this.chatClient = new ChatClient(id, chatServer);
        this.scheduledConsultations = new ArrayList<>();
        this.notificationService = notificationService;
    }
    
    //method to add patients
    public void addPatient(Patient patient) {
        patients.add(patient);
    }
    
    //method to view patients
    public void viewPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients assigned.");
            return;
        }
        System.out.println("My Patients:");
        for (Patient p : patients) {
            System.out.println(p.id + " - " + p.name);
        }
    }
    
    public void sendMessageToPatient(String patientId, String message) {
        chatClient.sendMessage(patientId, message);
    }

    public List<ChatMessage> getUnreadMessages() {
        return chatClient.getUnreadMessages();
    }
    
    public ChatClient getChatClient() {
        return this.chatClient;
    }
    
    public String getEmail() {
        return this.email;
    }

    public void viewChatHistory(String patientId) {
        chatClient.viewChatHistory(patientId);
    }
    
    public void scheduleVideoConsultation(String patientId, LocalDateTime dateTime, String meetingLink) {
        VideoConsultation vc = new VideoConsultation(this.id, patientId, dateTime, meetingLink);
        scheduledConsultations.add(vc);
        
        // Find the patient and add the consultation to their list
        for (Patient p : patients) {
            if (p.id.equals(patientId)) {
                p.addVideoConsultation(vc);
                System.out.println("Video consultation scheduled!");
                
                // Send notification to patient
                String notificationMsg = "Dr. " + this.name + " has scheduled a video consultation with you at " + 
                                       dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a")) + 
                                       "\nMeeting Link: " + meetingLink;
                
                notificationService.sendNotification(notificationMsg, p.email);
                return;
            }
        }
        System.out.println("Patient not found!");
    }
    
    public void viewScheduledConsultations() {
        if (scheduledConsultations.isEmpty()) {
            System.out.println("No video consultations scheduled.");
            return;
        }
        
        System.out.println("\nSCHEDULED VIDEO CONSULTATIONS:");
        for (VideoConsultation vc : scheduledConsultations) {
            if (!vc.isCompleted()) {
                System.out.println("With Patient " + vc.getPatientId() + 
                                 " at " + vc.getScheduledTime() +
                                 "\nMeeting Link: " + vc.getMeetingLink());
            }
        }
    }
}

