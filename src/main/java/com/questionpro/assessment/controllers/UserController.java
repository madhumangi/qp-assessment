package com.questionpro.assessment.controllers;

import com.questionpro.assessment.models.Grocery;
import com.questionpro.assessment.models.Order;
import com.questionpro.assessment.services.GroceryService;
import com.questionpro.assessment.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private GroceryService groceryService;
    @Autowired
    private OrderService orderService;



    @GetMapping("/groceries")
    public ResponseEntity<List<Grocery>> viewGroceries() {
        return ResponseEntity.ok().body(groceryService.getAllGroceries());
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity.ok().body(orderService.placeOrder(order));
    }
}
