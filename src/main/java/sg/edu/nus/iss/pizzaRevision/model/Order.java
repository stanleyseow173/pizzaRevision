package sg.edu.nus.iss.pizzaRevision.model;

import java.io.StringReader;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order {
    private String orderID;
    private String name;
    private String address;
    private String phone;
    private boolean rush;
    private String comments;
    private String pizza;
    private String size;
    private int quantity;
    private float total;
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRush() {
        return rush;
    }

    public void setRush(boolean rush) {
        this.rush = rush;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

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

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Order() {
        UUID uuid = UUID.randomUUID();
        this.setOrderID(uuid.toString().substring(0, 8));
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public static Order create(String jsonStr){
        JsonReader r = Json.createReader(new StringReader(jsonStr));
        JsonObject o = r.readObject();
        Order ord = new Order();
        ord.setOrderID(o.getString("orderId"));
        ord.setName(o.getString("name"));
        ord.setAddress(o.getString("address"));
        ord.setPhone(o.getString("phone"));
        ord.setRush(o.getBoolean("rush"));
        ord.setComments(o.getString("comments"));
        ord.setPizza(o.getString("pizza"));
        ord.setQuantity(o.getInt("quantity"));
        ord.setSize(o.getString("size"));
        ord.setTotal((float)o.getJsonNumber("total").doubleValue());
        return ord;
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                    .add("orderId", this.orderID)
                    .add("name", this.getName())
                    .add("address", this.getAddress())
                    .add("phone",this.getPhone())
                    .add("rush",this.isRush())
                    .add("comments", this.getComments())
                    .add("pizza", this.getPizza())
                    .add("quantity", this.getQuantity())
                    .add("size",this.getSize())
                    .add("total", this.getTotal())
                    .build();
    }
}
