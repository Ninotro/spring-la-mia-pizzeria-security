package com.experis.course.springpizzeria.services;

import com.experis.course.springpizzeria.model.Pizza;
import com.experis.course.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepository;


    public List<Pizza> getPizzaList(Optional<String> search) {

        if (search.isPresent()) {
            // se il parametro di ricerca è presente filtro la lista dei libri
            return pizzaRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    search.get(),
                    search.get());
        } else {
            // altrimenti prendo tutti i libri non filtrati
            // bookRepository recupera da database la lista di tutti i libri
            return pizzaRepository.findAll();
        }
    }

    public Pizza getPizzaById(int id) throws ChangeSetPersister.NotFoundException {
        Optional<Pizza> result = pizzaRepository.findById(id);
        // verifico se il risultato è presente
        if (result.isPresent()) {
            // passo al template l'oggetto Book
            return result.get();

        } else {
            // se non ho trovato il libro sollevo un'eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    public Pizza createPizza(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    public Pizza editPizza(Pizza pizza) throws ChangeSetPersister.NotFoundException {
        Pizza pizzaToEdit = getPizzaById(pizza.getId());
        // sostituisco i valori dei campi previsti
        pizzaToEdit.setName(pizza.getName());
        pizzaToEdit.setDescription(pizza.getDescription());
        pizzaToEdit.setPrice(pizza.getPrice());
        pizzaToEdit.setPhoto(pizza.getPhoto());
        ;

        return pizzaRepository.save(pizzaToEdit);
    }

    public void deletePizza(int id) {
        pizzaRepository.deleteById(id);
    }
}
