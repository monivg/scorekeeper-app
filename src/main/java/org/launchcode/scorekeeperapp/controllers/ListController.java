package org.launchcode.scorekeeperapp.controllers;

import org.launchcode.scorekeeperapp.models.User;
import org.launchcode.scorekeeperapp.models.data.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
@RequestMapping("list")
public class ListController {

    @Autowired
    private EventRepository eventRepository;
    static HashMap<String, String> searchChoices = new HashMap<>();
    @Autowired
    private AuthenticationController authenticationController;

    public ListController() {
        searchChoices.put("tournamentName", "Tournament Name");
        searchChoices.put("username", "Username");
    }

    @RequestMapping({""})
    public String listTournaments(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("title", "Tournament List");
        model.addAttribute("tournaments", this.eventRepository.findAll());
        return "list";
    }

    }
