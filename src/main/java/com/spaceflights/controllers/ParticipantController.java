package com.spaceflights.controllers;

import com.spaceflights.dataStructure.Participant;
import com.spaceflights.services.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ParticipantController {



    @GetMapping("/participant")
    @ResponseBody
    public List<Participant> participant(@RequestParam(name="name", required=false, defaultValue="Stranger") String name)
    {
        return ParticipantService.getAllParticipants();
    }

    @GetMapping("/participant/{id}")
    @ResponseBody
    public ResponseEntity<Participant> getParticipantById(@PathVariable("id") String ParticipantID){
        Participant participant = ParticipantService.getParticipantById(ParticipantID);
        if(participant == null){
            return new ResponseEntity<Participant>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Participant>(participant,HttpStatus.OK);
    }

    @DeleteMapping("/participant/delete/{id}")
    @ResponseBody
    public String deleteParticipant(@PathVariable("id") String participantID){
        return ParticipantService.deleteParticipant(participantID);
    }

    @RequestMapping(path= "/participant/add",method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String addParticipant(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String gender,
            @RequestParam String country,
            @RequestParam String birthDate,
            @RequestParam String notes
    )
    {
        Participant participant = new Participant(0,name,surname,gender,country,birthDate,notes);
        return ParticipantService.addParticipant(participant);

    }
}
