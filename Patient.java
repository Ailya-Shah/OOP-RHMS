/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
// Patient class
class Patient extends User {
    int age;
    char gender;
    String phone;
    VitalsDatabase vitalsDb;
    private ChatClient chatClient;
    private List<VideoConsultation> videoConsultations;
    private PanicButton panicButton;
    private static final int MAX_HEART_RATE = 120;
    private static final int MIN_HEART_RATE = 60;
    private static final int MIN_OXYGEN = 90;
    private static final float MAX_TEMP = 38.0f; // 38°C (100.4°F)
    private static final float MIN_TEMP = 36.0f; // 36°C (96.8°F)
    private static final String NORMAL_BP_RANGE = "90/60-120/80";
    
    public Patient(String id, String name, String email, String password, 
                  int age, char gender, String phone, VitalsDatabase vitalsDb,
                  ChatServer chatServer, NotificationService notificationService) {
        super(id, name, email, password);
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.vitalsDb = vitalsDb;
        this.chatClient = new ChatClient(id, chatServer);
        this.videoConsultations = new ArrayList<>();
        this.panicButton = new PanicButton(id, notificationService);
    }
    
    public void sendMessage(String receiverId, String message) {
        chatClient.sendMessage(receiverId, message);
    }

    public List<ChatMessage> getUnreadMessages() {
        return chatClient.getUnreadMessages();
    }

    public void viewChatHistory(String otherUserId) {
        chatClient.viewChatHistory(otherUserId);
    }

    public void addVideoConsultation(VideoConsultation vc) {
        videoConsultations.add(vc);
    }

    public void viewVitals() {
        vitalsDb.displayVitals(this.id);
    }
    
    public void triggerEmergency() {
        panicButton.triggerEmergency();
    }
    
    public ChatClient getChatClient() {
        return this.chatClient;
    }
    
    public void viewScheduledConsultations() {
        if (videoConsultations.isEmpty()) {
            System.out.println("No video consultations scheduled.");
            return;
        }
        
        System.out.println("\nSCHEDULED VIDEO CONSULTATIONS:");
        for (VideoConsultation vc : videoConsultations) {
            if (!vc.isCompleted()) {
                System.out.println("With Dr. " + vc.getDoctorId() + 
                                 " at " + vc.getScheduledTime() +
                                 "\nMeeting Link: " + vc.getMeetingLink());
            }
        }
    }
    
    public void enterVitalsManually() {
        Scanner scanner = new Scanner(System.in);
        boolean emergencyDetected = false;
        StringBuilder warningMessage = new StringBuilder("WARNING: Abnormal values detected - ");
        
        System.out.println("\n=== Enter Your Vital Signs ===");
        
        // Heart Rate
        System.out.print("Enter heart rate (bpm): ");
        int heartRate = scanner.nextInt();
        if (heartRate > MAX_HEART_RATE || heartRate < MIN_HEART_RATE) {
            emergencyDetected = true;
            warningMessage.append(String.format("Heart rate %d (Normal: %d-%d). ", 
                heartRate, MIN_HEART_RATE, MAX_HEART_RATE));
        }
        
        // Oxygen Level
        System.out.print("Enter oxygen level (%): ");
        int oxygenLevel = scanner.nextInt();
        if (oxygenLevel < MIN_OXYGEN) {
            emergencyDetected = true;
            warningMessage.append(String.format("Oxygen %d%% (Normal > %d%%). ", 
                oxygenLevel, MIN_OXYGEN));
        }
        
        // Blood Pressure
        boolean validBloodPressure = false;
        String bloodPressure = "";
        while (!validBloodPressure) {
            scanner.nextLine(); // Clear buffer
            System.out.print("Enter blood pressure (e.g., 120/80): ");
            bloodPressure = scanner.nextLine();
            try {
                // Attempt to split the input and parse the values
                String[] parts = bloodPressure.split("/");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid blood pressure format. Use systolic/diastolic (e.g., 120/80)");
                }
                int systolic = Integer.parseInt(parts[0]);
                int diastolic = Integer.parseInt(parts[1]);
        
                if (systolic < 0 || diastolic < 0) {
                    throw new IllegalArgumentException("Values cannot be negative");
                }
        
                // Simple BP check (could be enhanced)
                if (systolic > 140 || diastolic > 90) {
                    emergencyDetected = true;
                    warningMessage.append(String.format("BP %s (Normal: %s). ",  
                    bloodPressure, NORMAL_BP_RANGE));
                }
                validBloodPressure = true; // If we get here, the input was valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format in blood pressure: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        // Temperature
        System.out.print("Enter temperature (°C): ");
        float temperature = scanner.nextFloat();
        if (temperature > MAX_TEMP || temperature < MIN_TEMP) {
            emergencyDetected = true;
            warningMessage.append(String.format("Temp %.1f°C (Normal: %.1f-%.1f). ",  
            temperature, MIN_TEMP, MAX_TEMP));
        }
        
        // Save vitals
        VitalSign newVital = new VitalSign(this.id, heartRate, oxygenLevel,  
        bloodPressure, temperature);
        vitalsDb.addVitalSign(newVital);
        
        // Check for emergency
        if (emergencyDetected) {
            System.out.println("\n" + warningMessage.toString());
            System.out.println("Sending emergency alert to your doctor!");
            triggerEmergency();
        } else {
            System.out.println("\nVitals recorded successfully. All values normal.");
        }
    }
    
    public void viewVideoConsultations() {
        if (videoConsultations.isEmpty()) {
            System.out.println("\nNo video consultations scheduled.");
            return;
        }
        
        System.out.println("\n=== YOUR SCHEDULED VIDEO CONSULTATIONS ===");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-12s %-20s %-15s %-30s %-10s\n", 
                         "Consult ID", "Date/Time", "Doctor", "Meeting Link", "Status");
        System.out.println("--------------------------------------------------");
        
        for (VideoConsultation vc : videoConsultations) {
            String status = vc.isCompleted() ? "Completed" : "Upcoming";
            String formattedTime = vc.getScheduledTime().format(
                DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));
            
            System.out.printf("%-12s %-20s %-15s %-30s %-10s\n",
                vc.getConsultationId(),
                formattedTime,
                "Dr. " + vc.getDoctorId(),  // In real app, you'd look up doctor name
                vc.getMeetingLink(),
                status);
        }
        
        System.out.println("--------------------------------------------------");
        System.out.println("Total: " + videoConsultations.size() + " consultation(s)");
    }
}

