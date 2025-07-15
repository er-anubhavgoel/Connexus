package com.connexus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.connexus.entities.User;
import com.connexus.forms.UserForm;
import com.connexus.helpers.Message;
import com.connexus.helpers.MessageType;
import com.connexus.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    // Home Route
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    // Home Route
    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home Page Handler");
        model.addAttribute("name", "Connexus Application");
        model.addAttribute("message", "Welcome to the Home Page!");
        model.addAttribute("githubRepo", "https://github.com/er-anubhavgoel/Connexus");
        return "home";
    }

    // About Route
    @RequestMapping("/about")
    public String aboutPage(Model model) {
        System.out.println("About Page Loading");
        return "about";
    }

    // Services Route
    @RequestMapping("/services")
    public String servicesPage(Model model) {
        System.out.println("Services Page Loading");
        return "services";
    }

    // Contacts Route
    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    // Login Route -> This is Login Controller: view
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    // Signup Route -> This is Registration Controller: view
    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();

        model.addAttribute("userForm", userForm);
        return "register";
    }

    // Processing Register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {
        System.out.println("Processing Registration");

        // Fetch form data

        // Validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }

        // TODO: Add logic to handle registration

        // *Saving user to database
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("/images/Connexus-Monogram.png");

        User savedUser = userService.saveUser(user);
        System.out.println("User saved: " + savedUser);

        // * Add success message to model
        Message message = Message.builder().content("User registered successfully!").type(MessageType.green).build();

        session.setAttribute("message", message);

        // * Redirect to login page after successful registration
        return "redirect:/register";
    }
}
