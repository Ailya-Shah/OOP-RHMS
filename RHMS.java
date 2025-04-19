/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
/**
 * Remote Health Management System
 * 
 * This program implements a comprehensive healthcare management system with 
 * functionality for administrators, doctors, and patients including:
 * - Patient record management
 * - Appointment scheduling
 * - Medical history tracking
 * - Vital signs monitoring
 * - Secure messaging
 * - Email notifications
 * 
 * @author [Your Name]
 * @version 1.0
 * @date [Current Date]
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Main class implementing the healthcare system
public class RHMS {
    // Class variables - accessible throughout the application
    private static Scanner scanner = new Scanner(System.in); // For user input
    private static ChatServer chatServer = new ChatServer(); // Handles all messaging
    private static NotificationService notificationService; // For system notifications
    private static ReminderService reminderService; // For appointment reminders
    private static List<Doctor> doctors = new ArrayList<>(); // Stores all doctors
    private static List<Patient> patients = new ArrayList<>(); // Stores all patients

    /**
     * Main method - entry point of the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Initialize data storage structures
        List<MedicalHistory> medicalHistories = new ArrayList<>(); // Patient medical records
        AppointmentManager appointmentManager = new AppointmentManager(); // Manages appointments
        VitalsDatabase vitalsDatabase = new VitalsDatabase(); // Stores patient vitals
        
        // Initialize notification services
        Notifiable emailNotifier = new EmailNotification(); // Email notification handler
        Notifiable smsNotifier = new SMSNotification(); // SMS notification handler
        notificationService = new NotificationService(emailNotifier);
        reminderService = new ReminderService(smsNotifier);

        /* 
         * Sample Data Initialization
         * In a production system, this would be loaded from a database
         */
        
        // Add sample doctors
        doctors.add(new Doctor("D001", "Dr. Smith", "smith@example.com", 
            "doc123", "Cardiologist", chatServer, notificationService));
        doctors.add(new Doctor("D002", "Dr. Johnson", "johnson@example.com", 
            "doc456", "Dermatologist", chatServer, notificationService));
        
        // Create admin account
        Administrator admin = new Administrator("A001", "Admin", 
            "admin@example.com", "admin123");
        
        // Create and register sample patient Ali
        Patient ali = new Patient("P001", "Ali", "ali@example.com", "ali123", 
            23, 'M', "1234567890", vitalsDatabase, chatServer, notificationService);
        patients.add(ali);
        vitalsDatabase.addVitalSign(new VitalSign("P001", 75, 98, "120/80", 36.5f));
        
        // Create medical history for Ali
        MedicalHistory aliHistory = new MedicalHistory("P001");
        aliHistory.addFeedback(new Feedback("F001", "P001", 
            "Patient is stable", "Take meds", "Dr. Smith"));
        medicalHistories.add(aliHistory);

        // Add neurologist Dr. Wajeeh
        doctors.add(new Doctor("D003", "Dr. Wajeeh", "wajeeh@example.com", 
            "Wajeeh123", "Neurologist", chatServer, notificationService));

        // Create and register sample patient Ailya
        VitalSign ailyaVitals = new VitalSign("P002", 82, 97, "110/70", 36.7f);
        vitalsDatabase.addVitalSign(ailyaVitals);
        Patient ailya = new Patient("P002", "Ailya", "ailya@example.com", 
            "Ailya1234", 20, 'F', "9876543210", vitalsDatabase, 
            chatServer, notificationService);
        patients.add(ailya);

        // Assign patients to doctors
        doctors.get(2).addPatient(ailya); // Dr. Wajeeh gets Ailya
        doctors.get(0).addPatient(ali); // Dr. Smith gets Ali

        // Create medical history for Ailya
        MedicalHistory ailyaHistory = new MedicalHistory("P002");
        ailyaHistory.addFeedback(new Feedback(
            "F002", "P002", 
            "Patient shows improvement in neurological symptoms.", 
            "Neurocalm 5mg twice daily", 
            "Dr. Wajeeh"
        ));
        medicalHistories.add(ailyaHistory);

        // System welcome message
        System.out.println("=== Remote Health Management System ===");

        // Main application loop
        while (true) {
            System.out.println("\n1. Admin\n2. Doctor\n3. Patient\n4. Exit");
            System.out.print("Choose role: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            // Role selection
            if (choice == 1) {
                adminLogin(admin, doctors, patients, vitalsDatabase, chatServer);
            } else if (choice == 2) {
                doctorLogin(doctors, patients, medicalHistories, appointmentManager);
            } else if (choice == 3) {
                patientLogin(patients, medicalHistories, appointmentManager, vitalsDatabase);
            } else if (choice == 4) {
                System.out.println("Thank you for using the system. Goodbye!");
                break; // Exit the application
            }
        }
    }

    /**
     * Validates user password
     * @param input The password attempt
     * @param real The actual password
     * @return true if passwords match, false otherwise
     */
    private static boolean checkPassword(String input, String real) {
        return input.equals(real); // Simple string comparison
    }

    /**
     * Handles administrator login and functionality
     * @param admin The administrator account
     * @param doctors List of all doctors
     * @param patients List of all patients
     * @param vitalsDatabase Vital signs database
     * @param chatServer Messaging system
     */
    public static void adminLogin(Administrator admin, List<Doctor> doctors, 
                                List<Patient> patients, VitalsDatabase vitalsDatabase, 
                                ChatServer chatServer) {
        // Authentication
        System.out.print("Admin ID: ");
        String id = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        if (!id.equals(admin.id) || !password.equals(admin.password)) {
            System.out.println("Invalid admin credentials!");
            return; // Return to main menu
        }

        // Launch admin menu if authenticated
        admin.adminMenu(doctors, patients, vitalsDatabase, chatServer);
    }

    /**
     * Handles doctor login and functionality
     * @param doctors List of all doctors
     * @param patients List of all patients
     * @param medicalHistories List of medical records
     * @param appointmentManager Appointment scheduling system
     */
    private static void doctorLogin(List<Doctor> doctors, List<Patient> patients, 
                                  List<MedicalHistory> medicalHistories, 
                                  AppointmentManager appointmentManager) {
        // Authentication
        System.out.print("Doctor ID: ");
        String id = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Find matching doctor
        Doctor currentDoc = null;
        for (Doctor d : doctors) {
            if (d.id.equals(id) && checkPassword(password, d.password)) {
                currentDoc = d;
                break;
            }
        }

        if (currentDoc == null) {
            System.out.println("Invalid login!");
            return; // Return to main menu
        }

        System.out.println("\nWelcome Dr. " + currentDoc.name);
        
        // Doctor menu loop
        while (true) {
            System.out.println("\n1. View Patients\n2. Give Feedback\n3. View Appointments");
            System.out.println("4. Chat with Patient\n5. Schedule Video Consultation");
            System.out.println("6. View Scheduled Consultations\n7. Send Email to Patient\n8. Logout");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                // View assigned patients
                currentDoc.viewPatients();
            }
            else if (choice == 2) {
                // Add medical feedback
                System.out.print("Patient ID: ");
                String pid = scanner.nextLine();
                System.out.print("Feedback: ");
                String feedback = scanner.nextLine();
                System.out.print("Medication: ");
                String meds = scanner.nextLine();
                
                // Find patient's medical history
                for (MedicalHistory mh : medicalHistories) {
                    if (mh.patientID.equals(pid)) {
                        mh.addFeedback(new Feedback(
                            "F"+(mh.feedbackList.size()+1), // Generate unique ID
                            pid, feedback, meds, currentDoc.name));
                        System.out.println("Feedback added!");
                        break;
                    }
                }
            }
            else if (choice == 3) {
                // View appointments
                appointmentManager.displayAppointmentsForDoctor(currentDoc.id);
            }
            else if (choice == 4) {
                // Send message to patient
                System.out.print("Enter patient ID: ");
                String pid = scanner.nextLine();
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                currentDoc.sendMessageToPatient(pid, message);
            }
            else if (choice == 5) {
                // Schedule video consultation
                System.out.println("Your Patients:");
                currentDoc.viewPatients();
                
                System.out.print("Enter patient ID: ");
                String pid = scanner.nextLine();
                
                System.out.print("Enter date/time (YYYY-MM-DD HH:MM): ");
                String dateTimeStr = scanner.nextLine();
                
                System.out.print("Enter meeting link (Zoom/Meet): ");
                String meetingLink = scanner.nextLine();
                
                try {
                    // Parse and validate datetime
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, 
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    currentDoc.scheduleVideoConsultation(pid, dateTime, meetingLink);
                } catch (Exception e) {
                    System.out.println("Invalid date format! Use YYYY-MM-DD HH:MM");
                }
            }
            else if (choice == 6) {
                // View scheduled consultations
                currentDoc.viewScheduledConsultations();
            }
            else if (choice == 7) {
                // Send email to patient
                System.out.println("\nSend Email to Patient");
                System.out.print("Enter patient ID: ");
                String patientId = scanner.nextLine();
                
                // Find patient
                Patient patient = patients.stream()
                    .filter(p -> p.id.equals(patientId))
                    .findFirst()
                    .orElse(null);
                
                if (patient != null) {
                    // Get email credentials
                    System.out.print("Enter your email: ");
                    String senderEmail = scanner.nextLine();
                    System.out.print("Enter your email password: ");
                    String senderPassword = scanner.nextLine();
                    
                    System.out.print("Enter email subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter email message: ");
                    String message = scanner.nextLine();
                    
                    // Send email
                    emailsender.sendEmail(
                        senderEmail,
                        senderPassword,
                        patient.email,
                        subject,
                        message
                    );
                } else {
                    System.out.println("Patient not found!");
                }
            }
            else if (choice == 8) {
                break; // Logout
            }
        }
    }
    
    /**
     * Handles patient login and functionality
     * @param patients List of all patients
     * @param medicalHistories List of medical records
     * @param appointmentManager Appointment scheduling system
     * @param vitalsDatabase Vital signs database
     */
    private static void patientLogin(List<Patient> patients,
                                   List<MedicalHistory> medicalHistories,
                                   AppointmentManager appointmentManager,
                                   VitalsDatabase vitalsDatabase) {
        // Authentication
        System.out.print("Patient ID: ");
        String id = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Find matching patient
        Patient currentPatient = null;
        for (Patient p : patients) {
            if (p.id.equals(id) && checkPassword(password, p.password)) {
                currentPatient = p;
                break;
            }
        }

        if (currentPatient == null) {
            System.out.println("Invalid login!");
            return; // Return to main menu
        }

        System.out.println("\nWelcome " + currentPatient.name);

        // Patient menu loop
        while (true) {
            System.out.println("\n1. View Vitals\n2. Enter New Vitals\n3. View Feedback");
            System.out.println("4. Make Appointment\n5. Chat with Doctor");
            System.out.println("6. View Scheduled Consultations\n7. Trigger Emergency Alert");
            System.out.println("8. Send Email to Doctor\n9. Logout");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                // View vital signs
                currentPatient.viewVitals();
            }
            else if (choice == 2) {
                // Manually enter new vital signs
                currentPatient.enterVitalsManually();
            }
            else if (choice == 3) {
                // View medical feedback
                for (MedicalHistory mh : medicalHistories) {
                    if (mh.patientID.equals(currentPatient.id)) {
                        mh.viewMedicalHistory();
                        break;
                    }
                }
            }
            else if (choice == 4) {
                // Schedule appointment
                System.out.print("Doctor ID: ");
                String docId = scanner.nextLine();
                System.out.print("Date (MM/DD/YYYY): ");
                String date = scanner.nextLine();
                System.out.print("Time (HH:MM AM/PM): ");
                String time = scanner.nextLine();

                appointmentManager.addAppointment(new Appointment(
                    "A" + (appointmentManager.appointments.size()+1), // Generate ID
                    new Date(), time, docId, currentPatient.id, "Pending"
                ));
                System.out.println("Appointment made!");
            }
            else if (choice == 5) {
                // Send message to doctor
                System.out.print("Enter doctor ID: ");
                String did = scanner.nextLine();
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                currentPatient.sendMessage(did, message);
            }
            else if (choice == 6) {
                // View scheduled consultations
                currentPatient.viewVideoConsultations();
            }
            else if (choice == 7) {
                // Emergency alert
                currentPatient.triggerEmergency();
                System.out.println("Emergency alert triggered! Help is on the way.");
            }
            else if (choice == 8) {
                // Send email to doctor
                System.out.println("\nSend Email to Doctor");
                System.out.print("Enter doctor ID: ");
                String doctorId = scanner.nextLine();
                
                // Find doctor
                Doctor doctor = doctors.stream()
                    .filter(d -> d.id.equals(doctorId))
                    .findFirst()
                    .orElse(null);
                
                if (doctor != null) {
                    // Get email credentials
                    System.out.print("Enter your email: ");
                    String senderEmail = scanner.nextLine();
                    System.out.print("Enter your email password: ");
                    String senderPassword = scanner.nextLine();
                    
                    System.out.print("Enter email subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter email message: ");
                    String message = scanner.nextLine();
                    
                    // Send email
                    emailsender.sendEmail(
                        senderEmail,
                        senderPassword,
                        doctor.email,
                        subject,
                        message
                    );
                } else {
                    System.out.println("Doctor not found!");
                }
            }
            else if (choice == 9) {
                break; // Logout
            }
        }
    }
}