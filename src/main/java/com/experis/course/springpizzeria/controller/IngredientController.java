package com.experis.course.springpizzeria.controller;

import com.experis.course.springpizzeria.exceptions.IngredientNameUniqueException;
import com.experis.course.springpizzeria.model.Ingredient;
import com.experis.course.springpizzeria.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;


    @GetMapping
    public String index(Model model) {
        // passa al model categoryList con la lista di categorie
        model.addAttribute("ingredientList", ingredientService.getAll());

        model.addAttribute("ingredientObj", new Ingredient());
        return "ingredients/index";
    }

    @PostMapping
    public String doSave(@Valid @ModelAttribute("ingredientObj") Ingredient formIngredient,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientList", ingredientService.getAll());
            return "ingredients/index";
        }
        try {
            // salvo la nuova categoria su database
            ingredientService.save(formIngredient);
            return "redirect:/ingredients";
        } catch (IngredientNameUniqueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A ingredient " + e.getMessage() + " already exists");
        }
    }
}
