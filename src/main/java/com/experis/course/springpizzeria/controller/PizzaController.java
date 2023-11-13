package com.experis.course.springpizzeria.controller;


import com.experis.course.springpizzeria.model.Pizza;
import com.experis.course.springpizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public String create(Model model) {
        model.addAttribute("Pizza", new Pizza());
        return "create";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza,
                           BindingResult bindingResult) {


        // validare che i dati siano corretti
        if (bindingResult.hasErrors()) {
            // ci sono errori, devo ricaricare il form
            return "create";
        }

        Pizza savedPizza = null;
        try {
            savedPizza = pizzaRepository.save(formPizza);
        } catch (RuntimeException e) {
            // aggiungo un errore di validazione per isbn
            bindingResult.addError(new FieldError("Pizza", "description", formPizza.getDescription(), false, null, null,
                    "ISBN must be unique"));
            // ti rimando alla pagina col form
            return "create";
        }
        return "redirect:/pizzas/show/" + savedPizza.getId();
    }
}
