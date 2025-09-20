package br.com.orbitall.channels.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    public String index() {
        return "Welcome to Orbital Channels 2025!";
    }

}
