package com.spaceflights.controllers;

import com.spaceflights.dataStructure.Participant;
import com.spaceflights.services.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
public class ParticipantController {



    @GetMapping("/participant")
    @ResponseBody
    public List<Participant> participant()
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

    @ResponseBody
    @RequestMapping(path= "/participant/add",method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<String> addParticipant(
            @RequestBody Participant participant
    )
    {
        String message = ParticipantService.addParticipant(participant);
        return new ResponseEntity<String>(message,HttpStatus.OK);

    }
}
