/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */
class NotificationService {
    private Notifiable notifier;
    
    public NotificationService(Notifiable notifier) {
        this.notifier = notifier;
    }
    
    public void sendNotification(String message, String recipient) {
        notifier.sendNotification(message, recipient);
    }
}
