package com.project.medical_record_system.controller.view;

import com.project.medical_record_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/users")
public class UserViewController {

    private final UserService userService;

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", this.userService.getAllUsers());
        return "users";
    }
}