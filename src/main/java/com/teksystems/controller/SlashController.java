package com.teksystems.controller;

import com.teksystems.database.dao.*;
import com.teksystems.database.entity.*;
import com.teksystems.formbeans.CreateUserFormBean;
import com.teksystems.formbeans.DraftPlayerFormBean;
import com.teksystems.security.AuthenticatedUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = {"/index", "/", "/index.html"}, method = RequestMethod.GET)
public class SlashController {

    private static int count = 1;
    private static int teamId = 1;

    private boolean countUp = true;

    @Autowired
    private TeamPlayerDAO teamPlayerDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private TeamDAO teamDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PlayerDAO playerDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        log.debug("In the index controller method");
        ModelAndView response = new ModelAndView("index");

        return response;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView response = new ModelAndView("signup");
        log.debug("In the signup controller method");
        return response;
    }

    @PostMapping("/signupSubmit")
    public ModelAndView signup(@Valid CreateUserFormBean form, BindingResult bindingResult, HttpSession session) {
        ModelAndView response = new ModelAndView("signup");
        log.debug("In the signup controller post method");

        response.addObject("form", form);

        if (StringUtils.equals(form.getPassword(), form.getConfirmPassword()) == false) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match");
        }

        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.debug("Validation Error on field : " + error.getField() + " with message : "
                        + error.getDefaultMessage());
            }

            response.addObject("bindingResult", bindingResult);

            return response;
        }

        User user = new User();
        user.setEmail(form.getEmail());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());

        String encryptedPassword = passwordEncoder.encode(form.getPassword());
        user.setPassword(encryptedPassword);

        // will auto generate the ID and it will populate the field in the user entity
        userDAO.save(user);
        response.addObject("form", form);
        response.addObject("success", true);

        UserRole userRole = new UserRole();
        userRole.setRoleName("USER");
        // so when we go to set the user id FK on the user role entity the user id has
        // already populated.
        userRole.setUserId(user.getId());

        userRoleDAO.save(userRole);

        authenticatedUserService.changeLoggedInUsername(session, form.getEmail(), form.getPassword());

        log.debug(form.toString());

        return response;
    }

    @RequestMapping(value = "/draftboard", method = RequestMethod.GET)
    public ModelAndView draftboard() {
        ModelAndView response = new ModelAndView("draftboard");
        log.debug("In the draftboard controller method");

        List<Player> players = playerDAO.getAllUndraftedPlayers();
        List<Team> teams = teamDAO.getAllTeams();
        List<Map<String, Object>> teamPlayers = teamPlayerDAO.getAllTeamPlayers();
        response.addObject("teams", teams);
        response.addObject("players", players);
        response.addObject("teamPlayers", teamPlayers);

        return response;
    }

    @PostMapping("/draftboardSubmit")
    public ModelAndView draft(@Valid DraftPlayerFormBean form, BindingResult bindingResult) {
        ModelAndView response = new ModelAndView("draftboard");

        List<Team> teams = teamDAO.getAllTeams();
        List<Player> players = playerDAO.getAllUndraftedPlayers();
        List<Map<String, Object>> teamPlayers = teamPlayerDAO.getAllTeamPlayers();
        response.addObject("teams", teams);
        response.addObject("players", players);
        response.addObject("teamPlayers", teamPlayers);

        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.debug("Validation Error on field : " + error.getField() + " with message : "
                        + error.getDefaultMessage());
            }

            response.addObject("form", form);
            response.addObject("bindingResult", bindingResult);

            return response;
        }

        log.debug("In draft post controller method");
        log.debug(form.toString());

        TeamPlayer teamPlayer = new TeamPlayer();

        teamPlayer.setTeam(teamDAO.findById(teamId));
        teamPlayer.setPlayer(playerDAO.findById(form.getPlayerId()));
        teamPlayer.setDraftPickNumber(count);

        teamPlayerDAO.save(teamPlayer);

        if (countUp) {
            if (teamId < 12) {
                teamId++;
            } else {
                countUp = false;
                teamId = 12;
            }
        } else {
            if (teamId > 1) {
                teamId--;
            } else {
                countUp = true;
                teamId = 1;
            }
        }
        count++;

        System.out.println(teamId);
        System.out.println(count);

        response.setViewName("redirect:/draftboard");

        return response;
    }

}
