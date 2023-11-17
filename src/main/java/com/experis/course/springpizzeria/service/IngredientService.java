package com.experis.course.springpizzeria.service;

import com.experis.course.springpizzeria.exceptions.IngredientNameUniqueException;
import com.experis.course.springpizzeria.model.Ingredient;
import com.experis.course.springpizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getAll() {
        return ingredientRepository.findByOrderByName();
    }

    public Ingredient save(Ingredient ingredient) throws IngredientNameUniqueException {
        // verifico che questo nome non esista già
        if (ingredientRepository.existsByName(ingredient.getName())) {
            throw new IngredientNameUniqueException(ingredient.getName());
        }
        // trasformo il nome in lowercase
        ingredient.setName(ingredient.getName().toLowerCase());
        // salvo su database
        return ingredientRepository.save(ingredient);
    }
}