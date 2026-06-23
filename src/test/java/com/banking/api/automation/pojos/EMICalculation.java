package com.banking.api.automation.pojos;

public class EMICalculation {

    private String loanId;
    private double emiAmount;
    private int totalPayments;
    private double totalInterest;

    // Default Constructor
    public EMICalculation() {
    }

    // Constructor with all parameters
    public EMICalculation(String loanId, double emiAmount,
                          int totalPayments, double totalInterest) {
        this.loanId = loanId;
        this.emiAmount = emiAmount;
        this.totalPayments = totalPayments;
        this.totalInterest = totalInterest;
    }

    // Getters and Setters
    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public double getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(double emiAmount) {
        this.emiAmount = emiAmount;
    }

    public int getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(int totalPayments) {
        this.totalPayments = totalPayments;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }
}