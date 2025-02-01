package com.dormmatev2.dormmatev2.controller;

/**
 * The LoginRequest class used to access the login credentials (username and password) from a client request.
 * This class is typically used in authentication process to provide their credentials to access a system.
 */
public class LoginRequest {

    // The username provided by the user during login.
    private String username;

    // The password provided by the user during login.
    private String password;

    /**
     * Default constructor for creating an empty LoginRequest object.
     */
    public LoginRequest() {
    }

    /**
     * Parameterized constructor for creating a LoginRequest object
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Setters and Getters 
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}