package com.questionpro.assessment.services;

import com.questionpro.assessment.models.Grocery;
import com.questionpro.assessment.models.Order;
import com.questionpro.assessment.models.OrderItem;
import com.questionpro.assessment.repositories.GroceryRepository;
import com.questionpro.assessment.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GroceryRepository groceryRepository;

    public Order placeOrder(Order order) {
        for (OrderItem item : order.getItems()) {
            item.setOrder(order);

            Grocery grocery = groceryRepository.findById(item.getGrocery().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Grocery item not found: " + item.getGrocery().getId()));

            item.setGrocery(grocery);
        }

        return orderRepository.save(order);
    }
}
