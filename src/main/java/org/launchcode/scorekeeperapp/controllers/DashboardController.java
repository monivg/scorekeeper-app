package org.launchcode.scorekeeperapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class DashboardController {
    @GetMapping("selectHostOrJoin")
    public String displayDashboardPage(Model model){
        model.addAttribute("title", "Please select host or join.");
        return "user/selectHostOrJoin";
    }
}
