package com.example.demo.controller;

import com.example.demo.entity.Pizza;
import com.example.demo.service.PizzaService;
import com.example.demo.service.impl.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Pizza> addPizza(@RequestBody Pizza pizza) {
        pizza.setId(sequenceGenerator.getSequenceNumber(Pizza.SEQUENCE_NAME));
        return new ResponseEntity<>(pizzaService.addPizza(pizza), HttpStatus.CREATED);
    }

    @GetMapping("/getAllPizzas")
    public ResponseEntity<List<Pizza>> getAllPizzas() {
        return ResponseEntity.ok(pizzaService.getAllPizzas());
    }

    @DeleteMapping("/deletePizza/{pizzaId}")
    public ResponseEntity<String> deletePizza(@PathVariable Long pizzaId) {
        pizzaService.deleteById(pizzaId);
        return ResponseEntity.ok("Pizza was deleted");
    }
    @GetMapping("/getPizza/{pizzaId}")
    public ResponseEntity<Pizza> getPizzaById(@PathVariable Long pizzaId) {
        return ResponseEntity.ok(pizzaService.getPizzaById(pizzaId));
    }
    @GetMapping("/getPizzaByName/{pizzaName}")
    public ResponseEntity<Pizza> getPizzaByName(@PathVariable String pizzaName) {
        return ResponseEntity.ok(pizzaService.getPizzaByName(pizzaName));
    }

    @PutMapping("/updatePizzaById/{pizzaId}")
    public ResponseEntity<Pizza> updatePizzaById(@PathVariable Long pizzaId, @RequestBody Pizza pizza) {
        return ResponseEntity.ok(pizzaService.updatePizzaById(pizzaId, pizza));
    }
}
