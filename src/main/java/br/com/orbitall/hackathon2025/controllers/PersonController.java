package br.com.orbitall.hackathon2025.controllers;

import br.com.orbitall.hackathon2025.models.Person;
import br.com.orbitall.hackathon2025.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping
    public Person create(@RequestBody Person person) {
        return service.create(person);
    }
}
