package com.spaceflights.services;

import com.spaceflights.connection.ConnectionUrl;
import com.spaceflights.dataStructure.Flight;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class FlightService {

    public static List<Flight> getAllFlights(){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        List<Flight> result = new LinkedList<Flight>();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM Flights";
            ResultSet rs = stmt.executeQuery(SQL);
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                result.add(new Flight(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getFloat(5)));

            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Flight getFlightById(String id){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        Flight result = null;
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "SELECT * FROM Flights WHERE FlightID=" + id;
            ResultSet rs = stmt.executeQuery(SQL);
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                result = new Flight(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getFloat(5));

            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String deleteFlight(String id){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "exec RemoveFlight @FlightID=" + id;
            stmt.execute(SQL);
            return "Success deleting!";
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    public static String addFlight(Flight flight){
        // Create a variable for the ConnectionURL string.
        ConnectionUrl conUrl = new ConnectionUrl();
        String connectionUrl = conUrl.getConnectionUrl();
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            String SQL = "exec AddFlight @StartDate ='" + flight.getStartData() + "',@EndDate='"+flight.getEndData()+"',@ParticipantCapacity="+flight.getParticipantCapacity()+",@Price=" + flight.getPrice();
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
