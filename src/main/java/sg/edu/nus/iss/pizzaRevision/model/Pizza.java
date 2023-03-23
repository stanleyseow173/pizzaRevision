package sg.edu.nus.iss.pizzaRevision.model;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Pizza {
    @NotNull(message = "Item name cannot be empty or null")
    private String pizza;

    private String size;

    @Min(value=1, message="Quantity must be greater than 0")
    @Max(value=10, message="Quantity must be less than or equal to 10")
    private int quantity;

    public String getPizza() {
        return pizza;
    }
    public void setPizza(String pizza) {
        this.pizza = pizza;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    

}
