package br.com.orbitall.channels.controllers;

import br.com.orbitall.channels.canonicals.TransactionInput;
import br.com.orbitall.channels.canonicals.TransactionOutput;
import br.com.orbitall.channels.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService service;

    @PostMapping
    public TransactionOutput create(@Valid @RequestBody TransactionInput input) {
        return service.create(input);
    }

//    @GetMapping
//    public TransactionInput retrieve(@RequestParam("customerId") UUID customerId) {
//        return service.retrieve(customerId);
//    }
}
