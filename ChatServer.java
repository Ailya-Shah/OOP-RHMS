/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject2;

/**
 *
 * @author NMC
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
// ChatServer.java - Central message hub
class ChatServer {
    private Map<String, List<ChatMessage>> cmessageHistory;

    public ChatServer() {
        this.cmessageHistory = new HashMap<>();
    }

    public synchronized void sendMessage(ChatMessage cmessage) {
        String key = getConversationKey(cmessage.getSenderId(), cmessage.getReceiverId());
        cmessageHistory.computeIfAbsent(key, k -> new ArrayList<>()).add(cmessage);
    }

    public synchronized List<ChatMessage> getMessagesBetween(String user1, String user2) {
        String key1 = getConversationKey(user1, user2);
        String key2 = getConversationKey(user2, user1);
        
        List<ChatMessage> history = new ArrayList<>();
        if (cmessageHistory.containsKey(key1)) history.addAll(cmessageHistory.get(key1));
        if (cmessageHistory.containsKey(key2)) history.addAll(cmessageHistory.get(key2));
        
        return history;
    }

    public synchronized List<ChatMessage> getUnreadMessages(String userId) {
        List<ChatMessage> unread = new ArrayList<>();
        for (List<ChatMessage> conversation : cmessageHistory.values()) {
            for (ChatMessage msg : conversation) {
                if (msg.getReceiverId().equals(userId) && !msg.isRead()) {
                    unread.add(msg);
                    msg.markAsRead();
                }
            }
        }
        return unread;
    }

    private String getConversationKey(String user1, String user2) {
        return user1 + "-" + user2;
    }
}
