package com.experis.course.springpizzeria.repository;

import com.experis.course.springpizzeria.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
