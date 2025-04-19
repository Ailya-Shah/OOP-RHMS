/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
import java.time.LocalDateTime;
import java.util.UUID;
class VideoConsultation {
    private String consultationId;
    private String doctorId;
    private String patientId;
    private String meetingLink;
    private LocalDateTime scheduledTime;
    private boolean isCompleted;

    public VideoConsultation(String doctorId, String patientId, LocalDateTime scheduledTime, String meetingLink) {
        this.consultationId = "VC-" + UUID.randomUUID().toString().substring(0, 8);
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.scheduledTime = scheduledTime;
        this.meetingLink = meetingLink;
        this.isCompleted = false;
    }

    // Getters
    public String getMeetingLink() { return meetingLink; }
    public LocalDateTime getScheduledTime() { return scheduledTime; }
    public String getConsultationId() { return consultationId; }
    public boolean isCompleted() { return isCompleted; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    
    public void markAsCompleted() { isCompleted = true; }
}
