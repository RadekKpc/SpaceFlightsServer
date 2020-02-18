package com.spaceflights.services;

import com.spaceflights.connection.ConnectionUrl;
import com.spaceflights.dataStructure.Flight;
import com.spaceflights.dataStructure.Reservation;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ReservationService {

    List<Reservation> result = new LinkedList<Reservation>();

    public static List<Reservation> getAllReservations(){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        List<Reservation> result = new LinkedList<Reservation>();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM FlightsReservation";
            ResultSet rs = stmt.executeQuery(SQL);
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                result.add(new Reservation(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getInt(6)));

            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public static Reservation getReservationById(String id){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        Reservation result = null;
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM FlightsReservation WHERE FlightsReservationID=" + id;
            ResultSet rs = stmt.executeQuery(SQL);
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                result = new Reservation(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getString(5),rs.getInt(6));

            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String deleteReservation(String id){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "exec RemoveReservation @@FlightsReservationID=" + id;
            stmt.execute(SQL);
            return "Success deleting!";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    public static String addReservation(String flightID,String participantID){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "exec AddReservation @FlightID =" + flightID + ",@ParticipantID="+participantID;
            stmt.execute(SQL);
            return "Success Adding!";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    public static String paidReservation(String id){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "exec UpdateReservationToPaid @FlightsReservationID =" + id;
            stmt.execute(SQL);
            return "Success updating!";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

}
