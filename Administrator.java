/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
import java.util.Scanner;
import java.util.List;
// Admin class 
public class Administrator extends User {
    private NotificationService notificationService;
    
    public Administrator(String id, String name, String email, String password) {
        super(id, name, email, password);
    }

    public void adminMenu(List<Doctor> doctors, List<Patient> patients, VitalsDatabase vitalsDatabase, ChatServer chatServer) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nADMIN MENU");
            System.out.println("1. View All Doctors");
            System.out.println("2. View All Patients");
            System.out.println("3. Add New Doctor");
            System.out.println("4. Remove Doctor");
            System.out.println("5. Add New Patient");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllDoctors(doctors);
                    break;
                case 2:
                    viewAllPatients(patients);
                    break;
                case 3:
                    addNewDoctor(doctors, scanner, chatServer);
                    break;
                case 4:
                    removeDoctor(doctors, scanner);
                    break;
                case 5:
                    addNewPatient(patients, scanner, vitalsDatabase, chatServer);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // method to view doctors 
    public void viewAllDoctors(List<Doctor> doctors) {
        System.out.println("\nLIST OF ALL DOCTORS");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-8s %-20s %-25s %-15s\n", "ID", "Name", "Email", "Specialization");
        System.out.println("------------------------------------------------------------");
        
        for (Doctor doctor : doctors) {
            System.out.printf("%-8s %-20s %-25s %-15s\n", 
                doctor.id, doctor.name, doctor.email, doctor.specialization);
        }
    }

    //method view patients
    public void viewAllPatients(List<Patient> patients) {
        System.out.println("\nLIST OF ALL PATIENTS");
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-8s %-20s %-25s %-5s %-6s %-15s\n", 
            "ID", "Name", "Email", "Age", "Gender", "Phone");
        System.out.println("------------------------------------------------------------------");
        
        for (Patient patient : patients) {
            System.out.printf("%-8s %-20s %-25s %-5d %-6c %-15s\n", 
                patient.id, patient.name, patient.email, patient.age, patient.gender, patient.phone);
        }
    }

    private void addNewDoctor(List<Doctor> doctors, Scanner scanner, ChatServer chatServer) {
        System.out.println("\nADD NEW DOCTOR");
        System.out.print("Enter Doctor ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Specialization: ");
        String specialization = scanner.nextLine();
        
        Doctor newDoctor = new Doctor(id, name, email, password, specialization, chatServer, notificationService);
        doctors.add(newDoctor);
        System.out.println("Doctor added successfully!");
    }

    private void removeDoctor(List<Doctor> doctors, Scanner scanner) {
        System.out.println("\nREMOVE DOCTOR");
        viewAllDoctors(doctors);
        
        System.out.print("Enter Doctor ID to remove: ");
        String idToRemove = scanner.nextLine();
        
        boolean removed = doctors.removeIf(doctor -> doctor.id.equals(idToRemove));
        
        if (removed) {
            System.out.println("Doctor removed successfully!");
        } else {
            System.out.println("Doctor with ID " + idToRemove + " not found!");
        }
    }

    private void addNewPatient(List<Patient> patients, Scanner scanner, VitalsDatabase vitalsDatabase, ChatServer chatServer) {
        System.out.println("\nADD NEW PATIENT");
        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        System.out.print("Enter Gender (M/F): ");
        char gender = scanner.nextLine().charAt(0);
        
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        
        Patient newPatient = new Patient(id, name, email, password, age, gender, phone, vitalsDatabase, chatServer, notificationService);
        patients.add(newPatient);
        System.out.println("Patient added successfully!");
    }
}
