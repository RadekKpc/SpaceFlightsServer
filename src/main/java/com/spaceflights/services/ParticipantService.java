package com.spaceflights.services;


import com.spaceflights.connection.ConnectionUrl;
import com.spaceflights.dataStructure.Participant;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ParticipantService {

    public static List<Participant> getAllParticipants(){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        List<Participant> result = new LinkedList<Participant>();

        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM Participants";
            ResultSet rs = stmt.executeQuery(SQL);
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                result.add(new Participant(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));

            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Participant getParticipantById(String id){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        Participant result = null;
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM Participants WHERE ParticipantID=" + id;
            ResultSet rs = stmt.executeQuery(SQL);
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                result = new Participant(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String deleteParticipant(String id){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "exec RemoveParticipant @ParticipantID=" + id;
            stmt.execute(SQL);
            return "Success deleting!";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    public static String addParticipant(Participant participant){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "exec AddParticipant @Name ='" + participant.getName() + "',@Surname='"+participant.getSurname()+"',@Gender='"+participant.getGender()+"',@Country='" + participant.getCountry() +"',@BirthDate='" + participant.getBirthDate() +"',@Notes='" + participant.getNotes() +"'";
            stmt.execute(SQL);
            return "Success Adding!";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
