package com.spaceflights.controllers;

import java.util.List;

import com.spaceflights.dataStructure.Flight;
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
    public List<Flight> flight(@RequestParam(name="name", required=false, defaultValue="Stranger") String name)
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

    @DeleteMapping("/flight/delete/{id}")
    @ResponseBody
    public String deleteFlight(@PathVariable("id") String flightID){
        return FlightService.deleteFlight(flightID);
    }

    @RequestMapping(path= "/flight/add",method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String addFlight(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String participantCapacity,
            @RequestParam String price
    )
    {
        Flight flight = new Flight(0,startDate,endDate,Integer.parseInt(participantCapacity),Float.parseFloat(price));
        return FlightService.addFlight(flight);

    }
}
