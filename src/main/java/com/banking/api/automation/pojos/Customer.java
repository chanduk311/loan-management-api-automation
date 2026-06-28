package com.banking.api.automation.pojos;

public class Customer {

    private String customerId;
    private String fullName;
    private String email;
    private String phone;
    private int creditScore;
    private double annualIncome;
    private String employmentType;

    // Default Constructor
    public Customer() {
    }

    // Constructor with all parameters
    public Customer(String customerId, String fullName, String email,
                    String phone, int creditScore, double annualIncome,
                    String employmentType) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.creditScore = creditScore;
        this.annualIncome = annualIncome;
        this.employmentType = employmentType;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }
}