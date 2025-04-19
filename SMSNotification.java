/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
class SMSNotification implements Notifiable {
    @Override
    public void sendNotification(String message, String recipient) {
        System.out.println("SMS sent to: " + recipient);
        System.out.println("Message: " + message);
    }
}
