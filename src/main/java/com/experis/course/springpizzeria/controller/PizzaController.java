package com.experis.course.springpizzeria.controller;


import com.experis.course.springpizzeria.exceptions.PizzaNotFoundException;
import com.experis.course.springpizzeria.model.Pizza;
import com.experis.course.springpizzeria.services.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;


    @GetMapping
    public String index(@RequestParam Optional<String> search,
                        Model model) {


        // passo al template la lista di libri
        model.addAttribute("pizzaList", pizzaService.getPizzaList(search));
        return "pizzalist";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) throws ChangeSetPersister.NotFoundException {

        try {
            Pizza pizza = pizzaService.getPizzaById(id);
            model.addAttribute("pizza", pizza);
            return "show";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }


    }


    @GetMapping("/create")
    public String createGet(Model model) {
        // Istanzio un nuovo oggetto Pizza e lo passo con il model
        model.addAttribute("pizza", new Pizza());
        return "form";
    }


    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return "form";
        }


        try {
            Pizza savedPizza = pizzaService.createPizza(formPizza);
            return "redirect:/pizzas/show/" + savedPizza.getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/edit/{id}")

    public String edit(@PathVariable int id, Model model) {
        try {
            model.addAttribute("pizza", pizzaService.getPizzaById(id));
            return "form";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }


    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable int id, @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "pizzaList";
        Pizza savedPizza = null;
        try {
            savedPizza = pizzaService.editPizza(formPizza);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return "redirect:/pizzas/show/" + savedPizza.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) throws ChangeSetPersister.NotFoundException {
        try {
            Pizza pizzaToDelete = pizzaService.getPizzaById(id);
            pizzaService.deletePizza(id);
            redirectAttributes.addFlashAttribute("message", "pizza" + pizzaToDelete.getName() + "deleted!");
            return "redirect:/pizzas";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}

