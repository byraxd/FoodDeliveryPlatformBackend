package com.example.demo.controller;

import com.example.demo.entity.Pizza;
import com.example.demo.service.PizzaService;
import com.example.demo.service.impl.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pizza")
public class PizzaController {

    private final PizzaService pizzaService;

    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public PizzaController(PizzaService pizzaService, SequenceGeneratorService sequenceGenerator) {
        this.pizzaService = pizzaService;
        this.sequenceGenerator = sequenceGenerator;
    }

    @PostMapping("/addPizza")
    @ResponseStatus(HttpStatus.CREATED)
    public Pizza addPizza(@RequestBody Pizza pizza) {
        pizza.setId(sequenceGenerator.getSequenceNumber(Pizza.SEQUENCE_NAME));
        return pizzaService.addPizza(pizza);
    }


}
