package com.banking.api.automation.utils;

import com.banking.api.automation.config.DatabaseConfig;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtil {

    /**
     * Get Loan Application from Database by Loan ID
     */
    public static Map<String, Object> getLoanApplicationById(String loanId) {
        Map<String, Object> resultMap = new HashMap<>();

        String query = "SELECT * FROM banking.loan_applications WHERE loan_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                resultMap.put("loan_id", rs.getString("loan_id"));
                resultMap.put("customer_id", rs.getString("customer_id"));
                resultMap.put("loan_amount", rs.getDouble("loan_amount"));
                resultMap.put("loan_status", rs.getString("loan_status"));
                resultMap.put("application_date", rs.getDate("application_date"));
                resultMap.put("approval_date", rs.getDate("approval_date"));
                resultMap.put("tenure_months", rs.getInt("tenure_months"));
                resultMap.put("interest_rate", rs.getDouble("interest_rate"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving loan application: " + e.getMessage());
            e.printStackTrace();
        }

        return resultMap;
    }

    /**
     * Get Loan Status from Database
     */
    public static String getLoanStatus(String loanId) {
        String query = "SELECT loan_status FROM banking.loan_applications WHERE loan_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("loan_status");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving loan status: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get Calculated EMI from Database
     */
    public static double getCalculatedEMI(String loanId) {
        String query = "SELECT emi_amount FROM banking.loan_emi WHERE loan_id = ? LIMIT 1";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loanId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("emi_amount");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving EMI: " + e.getMessage());
            e.printStackTrace();
        }

        return 0.0;
    }

    /**
     * Get Customer Credit Score from Database
     */
    public static int getCustomerCreditScore(String customerId) {
        String query = "SELECT credit_score FROM banking.customers WHERE customer_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("credit_score");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving credit score: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Count Active Loans for a Customer
     */
    public static int getActiveLoansCount(String customerId) {
        String query = "SELECT COUNT(*) as loan_count FROM banking.loan_applications " +
                "WHERE customer_id = ? AND loan_status = 'ACTIVE'";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("loan_count");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error counting active loans: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Get Customer Details by Customer ID
     */
    public static Map<String, Object> getCustomerById(String customerId) {
        Map<String, Object> resultMap = new HashMap<>();

        String query = "SELECT * FROM banking.customers WHERE customer_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                resultMap.put("customer_id", rs.getString("customer_id"));
                resultMap.put("full_name", rs.getString("full_name"));
                resultMap.put("email", rs.getString("email"));
                resultMap.put("phone", rs.getString("phone"));
                resultMap.put("credit_score", rs.getInt("credit_score"));
                resultMap.put("annual_income", rs.getDouble("annual_income"));
                resultMap.put("employment_type", rs.getString("employment_type"));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
            e.printStackTrace();
        }

        return resultMap;
    }

    /**
     * Delete Loan Application (for cleanup in tests)
     */
    public static boolean deleteLoanApplication(String loanId) {
        String query = "DELETE FROM banking.loan_applications WHERE loan_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loanId);
            int rowsDeleted = stmt.executeUpdate();

            return rowsDeleted > 0;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting loan application: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get Total Loans for a Customer
     */
    public static int getTotalLoansCount(String customerId) {
        String query = "SELECT COUNT(*) as loan_count FROM banking.loan_applications " +
                "WHERE customer_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("loan_count");
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error counting total loans: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

}