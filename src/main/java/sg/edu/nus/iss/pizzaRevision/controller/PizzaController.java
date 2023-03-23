package sg.edu.nus.iss.pizzaRevision.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.pizzaRevision.model.Delivery;
import sg.edu.nus.iss.pizzaRevision.model.Order;
import sg.edu.nus.iss.pizzaRevision.model.Pizza;
import sg.edu.nus.iss.pizzaRevision.service.PizzaService;

@Controller
public class PizzaController {
    
    @Autowired
    PizzaService pizzaSvc;

    @GetMapping(path={"/","/index.html"})
    public String getIndex(Model m, HttpSession sess){
        m.addAttribute("pizza", new Pizza());
        return "index";
    }

    @PostMapping(path={"/pizza"}, produces = {"application/x-www-form-urlencoded"})
    public String postPizza(Model m, HttpSession sess, @ModelAttribute @Valid Pizza pizza, BindingResult bindings){
        if(bindings.hasErrors()){
            return "index";
        }

        //Check if pizza selected is ok
        if(!pizzaSvc.isCorrectPizza(pizza.getPizza())){
            FieldError pizzaError = new FieldError("pizza","pizza","Pizza must be from list");
            bindings.addError(pizzaError);
            return "index";
        }

        if(!pizzaSvc.isCorrectSize(pizza.getSize())){
            FieldError pizzaError = new FieldError("pizza","size","Pizza size must be sm md or lg");
            bindings.addError(pizzaError);
            return "index";
        }

        m.addAttribute("delivery", new Delivery());
        sess.setAttribute("pizza", pizza);
        return "pizza";
    }

    @PostMapping(path={"/pizza/order"}, produces = {"application/x-www-form-urlencoded"})
    public String postOrder(Model m, HttpSession sess, @ModelAttribute @Valid Delivery delivery, BindingResult bindings){
        if(bindings.hasErrors()){
            return "pizza";
        }

        Pizza pizza = (Pizza)sess.getAttribute("pizza");
        Float totalCost = pizzaSvc.calcCost(delivery, pizza);

        Order order = new Order();
        order.setName(delivery.getName());
        order.setAddress(delivery.getAddress());
        order.setPhone(delivery.getPhone());
        order.setRush(delivery.isRush());
        order.setComments(delivery.getComments());
        order.setPizza(pizza.getPizza());
        order.setSize(pizza.getSize());
        order.setQuantity(pizza.getQuantity());
        order.setTotal(totalCost);

        m.addAttribute("order", order);
        pizzaSvc.save(order);

        //m.addAttribute("delivery", new Order());
        return "order";
    }

    @ResponseBody
    @GetMapping(path={"/order/{orderID}"})
    public ResponseEntity<String> getID(@PathVariable String orderID){
        Optional<Order> oporder = pizzaSvc.getOrder(orderID);
        if(oporder.isEmpty()){
            JsonObject error = Json.createObjectBuilder().add("message", "Order %s not found".formatted(orderID)).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
        }
        return ResponseEntity.ok(oporder.get().toJSON().toString());
    }
}
