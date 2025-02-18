package com.questionpro.assessment.controllers;

import com.questionpro.assessment.models.Grocery;
import com.questionpro.assessment.services.GroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/groceries")
public class AdminController {

    @Autowired
    private GroceryService groceryService;

    @GetMapping()
    public ResponseEntity<List<Grocery>> getGroceries() {
        return ResponseEntity.ok().body(groceryService.getAllGroceries());
    }

    @PostMapping()
    public ResponseEntity<Grocery> addGrocery(@RequestBody Grocery grocery) {
        return ResponseEntity.ok().body(groceryService.addGrocery(grocery));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrocery(@PathVariable Long id) {
        groceryService.deleteGrocery(id);
        return ResponseEntity.ok().body("Deleted Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grocery> updateGrocery(@PathVariable Long id, @RequestBody Grocery grocery) {
        return ResponseEntity.ok().body(groceryService.updateGrocery(id, grocery));
    }
}
