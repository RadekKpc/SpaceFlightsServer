package com.spaceflights.controllers;

import com.spaceflights.dataStructure.Flight;
import com.spaceflights.dataStructure.Reservation;
import com.spaceflights.services.FlightService;
import com.spaceflights.services.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
public class ReservationController {
    @GetMapping("/reservation")
    @ResponseBody
    public List<Reservation> reservation(@RequestParam(name="name", required=false, defaultValue="Stranger") String name)
    {
        return ReservationService.getAllReservations();
    }
    @GetMapping("/reservation/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> getReservationById(@PathVariable("id") String reservationID){
        Reservation reservation = ReservationService.getReservationById(reservationID);
        if(reservation == null){
            return new ResponseEntity<Reservation>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Reservation>(reservation,HttpStatus.OK);
    }

    @DeleteMapping("/reservation/delete/{id}")
    @ResponseBody
    public String deleteReservation(@PathVariable("id") String reservationID){
        return ReservationService.deleteReservation(reservationID);
    }

    @RequestMapping(path= "/reservation/add",method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String addReservation(
            @RequestParam String flightID,
            @RequestParam String participantID
    )
    {
        return ReservationService.addReservation(flightID,participantID);

    }
    @PutMapping("/reservation/paid/{id}")
    @ResponseBody
    public String paidReservation(@PathVariable("id") String reservationID){
        return ReservationService.paidReservation(reservationID);
    }
}
