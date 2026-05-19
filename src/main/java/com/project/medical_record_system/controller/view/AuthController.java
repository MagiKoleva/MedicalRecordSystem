package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.data.entity.User;
import com.project.medical_record_system.data.repository.RoleRepository;
import com.project.medical_record_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        userService.createUser(user);
        return "redirect:/login";
    }
}
