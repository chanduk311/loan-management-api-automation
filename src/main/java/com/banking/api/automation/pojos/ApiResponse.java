package com.banking.api.automation.pojos;

public class ApiResponse {

    private int statusCode;
    private String message;
    private Object data;
    private long timestamp;

    // Default Constructor
    public ApiResponse() {
    }

    // Constructor with all parameters
    public ApiResponse(int statusCode, String message, Object data, long timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}