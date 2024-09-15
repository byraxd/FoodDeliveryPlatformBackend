package com.example.demo.service;

import com.example.demo.entity.Pizza;

import java.util.List;

public interface PizzaService {

    Pizza addPizza(Pizza pizza);

    List<Pizza> getAllPizzas();

    void deleteById(Long pizzaId);

    Pizza getPizzaById(Long pizzaId);

    Pizza getPizzaByName(String pizzaName);

    Pizza updatePizzaById(Long pizzaId, Pizza pizza);
}
