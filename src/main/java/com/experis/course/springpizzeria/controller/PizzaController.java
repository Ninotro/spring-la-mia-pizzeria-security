package com.experis.course.springpizzeria.controller;


import com.experis.course.springpizzeria.model.Pizza;
import com.experis.course.springpizzeria.services.PizzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

        Pizza pizza = pizzaService.getPizzaById(id);
        model.addAttribute("pizza", pizza);
        return "show";


    }


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

        Pizza savedPizza = pizzaService.createPizza(formPizza);
        return "redirect:/pizzas/show/" + savedPizza.getId();
    }

    @GetMapping("/edit/{id}")

    public String edit(@PathVariable int id, Model model) {
        try {
            model.addAttribute("pizza", pizzaService.getPizzaById(id));
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        return "form";

    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable int id, @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "pizzaList";
        Pizza savedPizza = null;
        try {
            savedPizza = pizzaService.editPizza(formPizza);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        return "redirect:show/" + savedPizza.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) throws ChangeSetPersister.NotFoundException {
        Pizza pizzaToDelete = pizzaService.getPizzaById(id);
        pizzaService.deletePizza(id);
        redirectAttributes.addFlashAttribute("message", "book" + pizzaToDelete.getName() + "deleted!");
        return "redirect:/pizzas";
    }


}

