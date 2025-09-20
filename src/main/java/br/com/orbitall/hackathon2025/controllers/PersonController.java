package br.com.orbitall.hackathon2025.controllers;

import br.com.orbitall.hackathon2025.canonicals.PersonInput;
import br.com.orbitall.hackathon2025.canonicals.PersonOutput;
import br.com.orbitall.hackathon2025.models.Person;
import br.com.orbitall.hackathon2025.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService service;

    @PostMapping
    public PersonOutput create(@Valid @RequestBody PersonInput person) { return service.create(person); }

    @GetMapping("/{id}")
    public PersonOutput retrieve(@PathVariable UUID id) { return service.retrieve(id); }

    @PutMapping("/{id}")
    public PersonOutput update(@PathVariable UUID id, @RequestBody PersonInput person) { return service.update(id, person); }

    @DeleteMapping("/{id}")
    public PersonOutput delete(@PathVariable UUID id) {
        return service.delete(id);
    }
}
