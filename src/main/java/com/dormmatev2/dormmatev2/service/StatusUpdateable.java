package com.dormmatev2.dormmatev2.service;

public interface StatusUpdateable {
    void updateStatus(String status);
    Long getId(); // Method to get the ID
    String getStatus(); // Method to get the status
}