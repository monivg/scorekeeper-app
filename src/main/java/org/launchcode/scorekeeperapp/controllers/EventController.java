package org.launchcode.scorekeeperapp.controllers;

import com.google.zxing.WriterException;
import org.launchcode.scorekeeperapp.models.Event;
import org.launchcode.scorekeeperapp.models.QRCodeGenerator;
import org.launchcode.scorekeeperapp.models.Scores;
import org.launchcode.scorekeeperapp.models.User;
import org.launchcode.scorekeeperapp.models.data.EventRepository;
import org.launchcode.scorekeeperapp.models.data.ScoreRepository;
import org.launchcode.scorekeeperapp.models.data.UserRepository;
import org.launchcode.scorekeeperapp.models.dto.userEventScoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("create")
    public String displayCreateEventForm(Model model){
        model.addAttribute(new Event());
        model.addAttribute("title","Create Event");
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event event, Errors errors, Model model){
        if (errors.hasErrors()){
            model.addAttribute(new Event());
            model.addAttribute("title","Create Event");
            return "events/create";
        }
        eventRepository.save(event);
        return "events/create";
    }

    @GetMapping("index")
    public String displayAllEvents(Model model){
        model.addAttribute("title","All Events");
        model.addAttribute("events",eventRepository.findAll());
        return "events/index";
    }

    @GetMapping("{eventId}")
    public String displayViewEventPage(Model model, @PathVariable int eventId) {
        String eventLink = "https://localhost:8080/events/" + eventId;
        byte[] image = new byte[0];
        try {
            image = QRCodeGenerator.getQRCodeImage(eventLink, 250, 250);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String qrcode = Base64.getEncoder().encodeToString(image);

        model.addAttribute("eventLink", eventLink);
        model.addAttribute("qrcode", qrcode);
        return "events/view";
    }

    @PostMapping("scoreboard")
    public String displayScoresInScoreboard(Model model, int eventId) {
        Optional<Event> optEvent = eventRepository.findById(eventId);
        Scores score = scoreRepository.findByEventId(eventId);

        if (optEvent.isPresent()) {
            Event event = (Event) optEvent.get();
            model.addAttribute("event", event);
            model.addAttribute("scores", score);
            return "events/view";
        } else {
            return "redirect:../";
            //if the event doesn't exist this redirects you to the main index, prevents 404'ing.
        }
    }


    @GetMapping("{eventId}/play")
    public String showCreateForm(Model model, @PathVariable int eventId) {
        userEventScoreDTO uesdto = new userEventScoreDTO();

        for (int i = 1; i <= 9; i++) {
            Scores tempScore = new Scores();
            tempScore.setEventId(eventId);
            uesdto.addScore(tempScore);
        }
        model.addAttribute("form", uesdto);
        return "events/play";
    }

    @PostMapping("save")
    public String saveScores(@ModelAttribute userEventScoreDTO dto, Model model) {
        scoreRepository.saveAll(dto.getScores());

        model.addAttribute("scores", scoreRepository.findAll());
        return "events/index"; //Temp redirect to index.
    }


}
