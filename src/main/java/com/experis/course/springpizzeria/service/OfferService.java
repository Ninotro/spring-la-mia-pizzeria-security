package com.experis.course.springpizzeria.service;

import com.experis.course.springpizzeria.exceptions.OfferNotFoundException;
import com.experis.course.springpizzeria.exceptions.PizzaNotFoundException;
import com.experis.course.springpizzeria.model.Offer;
import com.experis.course.springpizzeria.model.Pizza;
import com.experis.course.springpizzeria.repository.OfferRepository;
import com.experis.course.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OfferService {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OfferRepository offerRepository;


    public Offer createNewOffer(Integer pizzaId) throws PizzaNotFoundException {
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(() -> new PizzaNotFoundException("Pizza with ID " + pizzaId + ": Not Found"));
        Offer offer = new Offer();
        offer.setStartDate(LocalDate.now());
        offer.setEndDate(LocalDate.now().plusMonths(1));
        // Collego la relazione offerta/pizza passando la pizza recuperata tramite id
        offer.setPizza(pizza);
        return offer;
    }

    public Offer saveOffer(Offer offer) {
        // Salvo l'offerta
        return offerRepository.save(offer);
    }

    public Offer getOffer(Integer id) throws OfferNotFoundException {
        // Provo a recuperare offerta da ID
        // Se non ci riesco lancio eccezione
        return offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException("Offer with ID " + id + ": Not Found"));
    }
}