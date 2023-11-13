package com.experis.course.springpizzeria.controller;


import com.experis.course.springpizzeria.model.Pizza;
import com.experis.course.springpizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;


    @GetMapping
    public String index(@RequestParam Optional<String> search,
                        Model model) {
        List<Pizza> PizzaList;

        if (search.isPresent()) {
            // se il parametro di ricerca è presente filtro la lista dei libri
            PizzaList = pizzaRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(

                    search.get(),
                    search.get());
        } else {
            // altrimenti prendo tutti i libri non filtrati
            // bookRepository recupera da database la lista di tutti i libri
            PizzaList = pizzaRepository.findAll();
        }

        // passo al template la lista di libri
        model.addAttribute("pizzaList", PizzaList);
        return "pizzalist";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        // verifico se il risultato è presente
        if (result.isPresent()) {
            // passo al template l'oggetto Book
            model.addAttribute("pizza", result.get());
            return "show";
        } else {
            // se non ho trovato il libro sollevo un'eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }

    }

    //    metodo per mostrare il form
    @GetMapping("/create")
    public String createGet(Model model) {
        // Istanzio un nuovo oggetto Pizza e lo passo con il model
        model.addAttribute("pizza", new Pizza());
        return "create";
    }

    // Rotta "/pizzas/create" (POST)
    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        // Controllo se ci sono errori
        if (bindingResult.hasErrors()) {
            // Se ci sono ricarico la pagina mantendendo i dati (grazie al model)
            return "create";
        }

        Pizza savedPizza = pizzaRepository.save(formPizza);
        return "redirect:/pizzas/show/" + savedPizza.getId();
    }
}