package com.experis.course.springpizzeria.repository;

import com.experis.course.springpizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
