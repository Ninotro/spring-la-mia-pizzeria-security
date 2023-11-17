package com.experis.course.springpizzeria.controller;

import com.experis.course.springpizzeria.exceptions.OfferNotFoundException;
import com.experis.course.springpizzeria.exceptions.PizzaNotFoundException;
import com.experis.course.springpizzeria.model.Offer;
import com.experis.course.springpizzeria.service.OfferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/offers")
public class OfferController {
    @Autowired
    OfferService offerService;


    @GetMapping("/create")

    public String createGet(@RequestParam Integer pizzaId, Model model) {
        try {
   
            model.addAttribute("offer", offerService.createNewOffer(pizzaId));
            return "offers/create_update";
        } catch (PizzaNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Rotta "/offers/create" (POST)
    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "offers/create_update";
        }

        offerService.saveOffer(formOffer);
        return "redirect:/pizzas/show/" + formOffer.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(@PathVariable Integer id, Model model) {
        try {
            Offer offer = offerService.getOffer(id);
            model.addAttribute("offer", offer);
            return "offers/create_update";
        } catch (OfferNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Integer id, @Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "offers/create_update";
        }
        offerService.saveOffer(formOffer);
        return "redirect:/pizzas/show/" + formOffer.getPizza().getId();
    }
}