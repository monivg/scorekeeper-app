package org.launchcode.scorekeeperapp.controllers;

import org.launchcode.scorekeeperapp.models.Event;
import org.launchcode.scorekeeperapp.models.TournamentData;
import org.launchcode.scorekeeperapp.models.data.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping({"search"})
public class SearchController {

    private List<Event> events = new ArrayList<>();

    @Autowired
    private EventRepository eventRepository;

        @RequestMapping({""})
        public String searchByIdOrName(Model model) {
        model.addAttribute("categories", ListController.searchChoices);
        return "search";
    }

    @PostMapping({"results"})
    public String displaySearchResults(Model model, @RequestParam String searchCategory) {
        Event eventInst = new Event();
        ArrayList<Event> tournaments = new ArrayList<>();
        if (searchCategory.equals("tournamentName")) {
            tournaments = TournamentData.findTournamentByName(searchCategory, this.eventRepository.findAll());
        } else if (searchCategory.equals("tournamentId")) {
            tournaments = TournamentData.findTournamentById(eventInst.getId(), this.eventRepository.findAll());
        }

        model.addAttribute("categories", ListController.searchChoices);
        model.addAttribute("tournaments", tournaments);
        return "search/search";
    }
}
