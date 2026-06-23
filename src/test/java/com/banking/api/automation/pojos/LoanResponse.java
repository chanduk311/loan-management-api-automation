package com.banking.api.automation.pojos;

public class LoanResponse {

    private String loanId;
    private String status;
    private String message;
    private double approvedAmount;
    private double interestRate;

    // Default Constructor
    public LoanResponse() {
    }

    // Constructor with all parameters
    public LoanResponse(String loanId, String status, String message,
                        double approvedAmount, double interestRate) {
        this.loanId = loanId;
        this.status = status;
        this.message = message;
        this.approvedAmount = approvedAmount;
        this.interestRate = interestRate;
    }

    // Getters and Setters
    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}