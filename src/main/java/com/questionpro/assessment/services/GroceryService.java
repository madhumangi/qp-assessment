package com.questionpro.assessment.services;

import com.questionpro.assessment.models.Grocery;
import com.questionpro.assessment.repositories.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryService {
    @Autowired
    private GroceryRepository groceryRepository;

    public List<Grocery> getAllGroceries() {
        return groceryRepository.findAll();
    }

    public Grocery addGrocery(Grocery grocery) {
        return groceryRepository.save(grocery);
    }

    public void deleteGrocery(Long id) {
        groceryRepository.deleteById(id);
    }

    public Grocery updateGrocery(Long id, Grocery updatedGrocery) {
        Optional<Grocery> grocery = groceryRepository.findById(id);
        if (grocery.isPresent()) {
            Grocery g = grocery.get();
            g.setName(updatedGrocery.getName());
            g.setPrice(updatedGrocery.getPrice());
            g.setQuantity(updatedGrocery.getQuantity());
            return groceryRepository.save(g);
        }
        return null;
    }
}
