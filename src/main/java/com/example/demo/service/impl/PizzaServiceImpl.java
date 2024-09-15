package com.example.demo.service.impl;

import com.example.demo.entity.Pizza;
import com.example.demo.exception.ResourceNotFoundedException;
import com.example.demo.repository.PizzaRepository;
import com.example.demo.service.PizzaService;
import com.mongodb.internal.async.function.RetryingSyncSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;
    private final SequenceGeneratorService sequenceGenerator;


    @Autowired
    public PizzaServiceImpl(PizzaRepository pizzaRepository, SequenceGeneratorService sequenceGenerator) {
        this.pizzaRepository = pizzaRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public Pizza addPizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @Override
    public void deleteById(Long pizzaId) {
        Pizza pizzaForDelete = pizzaRepository.findById(pizzaId).orElseThrow(() -> new ResourceNotFoundedException("Pizza with following id is not founded, id: " + pizzaId));
        pizzaRepository.delete(pizzaForDelete);
    }

    @Override
    public Pizza getPizzaById(Long pizzaId) {
        Pizza foundedPizza = pizzaRepository.findPizzaById(pizzaId);

        return isNull(foundedPizza);
    }

    @Override
    public Pizza getPizzaByName(String pizzaName) {
        Pizza foundedPizza = pizzaRepository.findPizzaByName(pizzaName);

        return isNull(foundedPizza);
    }

    @Override
    public Pizza updatePizzaById(Long pizzaId, Pizza pizza) {
        Pizza pizzaForUpdate = pizzaRepository.findById(pizzaId).orElseThrow(() -> new ResourceNotFoundedException("Pizza with following id is not founded, id: " + pizzaId));

        pizzaForUpdate.setId(pizzaId);
        pizzaForUpdate.setName(pizza.getName());
        pizzaForUpdate.setDescription(pizza.getDescription());
        pizzaForUpdate.setPrice(pizza.getPrice());
        pizzaForUpdate.setIsAvailable(pizza.getIsAvailable());

        return pizzaRepository.save(pizzaForUpdate);
    }

    private Pizza isNull(Pizza pizza) {
        if(pizza == null) {
            throw new ResourceNotFoundedException("Pizza with following value is not found" );
        }else{
            return pizza;
        }
    }
}
