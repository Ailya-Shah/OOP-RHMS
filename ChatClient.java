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

class ChatClient implements Runnable {
    private String userId;
    private ChatServer server;
    private boolean active;
    private Thread messageListener;

    public ChatClient(String userId, ChatServer server) {
        this.userId = userId;
        this.server = server;
        this.active = true;
        this.messageListener = new Thread(this);
        this.messageListener.start();
    }

    public void sendMessage(String receiverId, String cmessage) {
        ChatMessage msg = new ChatMessage(userId, receiverId, cmessage);
        server.sendMessage(msg);
    }

    public List<ChatMessage> getUnreadMessages() {
        return server.getUnreadMessages(userId);
    }

    public void viewChatHistory(String otherUserId) {
        List<ChatMessage> history = server.getMessagesBetween(userId, otherUserId);
        if (history.isEmpty()) {
            System.out.println("No messages found.");
            return;
        }
        
        System.out.println("\nCHAT HISTORY WITH " + otherUserId + ":");
        for (ChatMessage msg : history) {
            String prefix = msg.getSenderId().equals(userId) ? "You: " : "Them: ";
            System.out.println(prefix + msg.getContent() + 
                             " (" + msg.getTimestamp() + ")");
        }
    }

    public void stop() {
        this.active = false;
    }

    @Override
    public void run() {
        while (active) {
            try {
                List<ChatMessage> newMessages = getUnreadMessages();
                if (!newMessages.isEmpty()) {
                    System.out.println("\n=== NEW MESSAGES ===");
                    for (ChatMessage msg : newMessages) {
                        System.out.println("From " + msg.getSenderId() + ": " + 
                                         msg.getContent() + " (" + msg.getTimestamp() + ")");
                    }
                    System.out.println("===================");
                }
                Thread.sleep(3000); // Check for new messages every 3 seconds
            } catch (InterruptedException e) {
                System.out.println("Message listener interrupted");
            }
        }
    }
}