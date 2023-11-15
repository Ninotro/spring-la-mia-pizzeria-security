package com.experis.course.springpizzeria.controller;

import com.experis.course.springpizzeria.exceptions.PizzaNotFoundException;
import com.experis.course.springpizzeria.services.OfferServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferServices offerServices;

    @GetMapping("/create")
    public String create(@RequestParam Integer pizzaId, Model model) {
        model.addAttribute("pizzaId", pizzaId);
        return "offers/form";
    }

    @PostMapping("/create")
    public String handleOfferForm(@RequestParam Integer pizzaId, @RequestParam String dataInizio, @RequestParam String dataFine, @RequestParam String titolo) {
        try {
            LocalDate startDate = LocalDate.parse(dataInizio);
            LocalDate endDate = LocalDate.parse(dataFine);
            offerServices.createNewOffer(pizzaId, startDate, endDate, titolo);
            return "redirect:/pizzas/show/" + pizzaId;
        } catch (PizzaNotFoundException e) {
            // Gestione dell'eccezione
            return "errore";
        }
    }
}