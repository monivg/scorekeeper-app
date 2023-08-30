package org.launchcode.scorekeeperapp.controllers;

import org.launchcode.scorekeeperapp.models.User;
import org.launchcode.scorekeeperapp.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationController authenticationController;


    @GetMapping("dashboard")
    public String displayDashboardPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("title", "Dashboard");
        model.addAttribute("loggedIn", user!=null);

        return "user/dashboard";
    }
}
