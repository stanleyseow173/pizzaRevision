package sg.edu.nus.iss.pizzaRevision.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.pizzaRevision.model.Delivery;
import sg.edu.nus.iss.pizzaRevision.model.Order;
import sg.edu.nus.iss.pizzaRevision.model.Pizza;
import sg.edu.nus.iss.pizzaRevision.repository.PizzaRepository;

@Service
public class PizzaService {
    
    @Autowired
    PizzaRepository pizzaRepo;

    public static final List<String> pizzaSelection = 
        Arrays.asList("bella","margherita","marinara","spianatacalabrese","trioformaggio");

    public static final List<String> pizzaSize = 
        Arrays.asList("sm","md","lg");

    public boolean isCorrectPizza(String pizza) {
        return pizzaSelection.contains(pizza);
    }

    public boolean isCorrectSize(String size) {
        return pizzaSize.contains(size);
    }
    
    public float calcCost(Delivery delivery, Pizza pizza){
        String pizzaName = pizza.getPizza();
        Float pizzaCost;
        if (pizzaName.equals("margherita")){
            pizzaCost = 22f;
        } else if (pizzaName.equals("trioformaggio")){
            pizzaCost = 25f;
        } else{
            pizzaCost = 30f;
        }
        Float multiplier = 1.0f;
        if(pizza.getSize().equals("md")){
            multiplier = 1.2f;
        }else if(pizza.getSize().equals("lg")){
            multiplier = 1.5f;
        }
        Float totalCost;
        totalCost = pizzaCost*pizza.getQuantity()*multiplier;
        if (delivery.isRush()){
            totalCost +=2;
        }
        
        return totalCost;
    }

    public void save(Order order){
        pizzaRepo.save(order);
    }

    public Optional<Order> getOrder(String orderId){
        return pizzaRepo.get(orderId);
    }
}
