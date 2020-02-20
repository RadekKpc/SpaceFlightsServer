package com.spaceflights.controllers;

import java.util.List;

import com.spaceflights.dataStructure.Flight;
import com.spaceflights.dataStructure.FreePlaces;
import com.spaceflights.services.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
public class FlightController {


    @GetMapping("/flight")
    @ResponseBody
    public List<Flight> flight()
    {
        return FlightService.getAllFlights();
    }

    @GetMapping("/flight/{id}")
    @ResponseBody
    public ResponseEntity<Flight> getFlightById(@PathVariable("id") String flightID){
        Flight flight = FlightService.getFlightById(flightID);
        if(flight == null){
            return new ResponseEntity<Flight>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Flight>(flight,HttpStatus.OK);
    }

    //Flights with free places
    @GetMapping("/flight/places")
    @ResponseBody
    public List<Flight> getAllFreeFlights()
    {
        return FlightService.getAllFreeFlights();
    }

    @DeleteMapping("/flight/delete/{id}")
    @ResponseBody
    public String deleteFlight(@PathVariable("id") String flightID){
        return FlightService.deleteFlight(flightID);
    }

    @ResponseBody
    @RequestMapping(path= "/flight/add",method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<String> addFlight(
            @RequestBody Flight flight
    )
    {
        String message =  FlightService.addFlight(flight);
        return new ResponseEntity<String>(message,HttpStatus.OK);
    }
    @GetMapping("/flight-participant/{isPaid}/{id}")
    @ResponseBody
    public List<Flight> getFlightIdByParticipant(@PathVariable("id") String ParticipantID, @PathVariable("isPaid") String isPaid){
        return FlightService.getFlightIdByParticipant(ParticipantID,isPaid);
    }
    //free places pairs id - freePlaces
    @GetMapping("/flight/free")
    @ResponseBody
    public List<FreePlaces> freePlaces()
    {
        return FlightService.getFreePlaces();
    }

}
