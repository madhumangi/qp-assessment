package com.questionpro.assessment.repositories;

import com.questionpro.assessment.models.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryRepository extends JpaRepository<Grocery,Long> {

}
