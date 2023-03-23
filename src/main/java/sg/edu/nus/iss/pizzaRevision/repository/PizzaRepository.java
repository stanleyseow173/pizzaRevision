package sg.edu.nus.iss.pizzaRevision.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.pizzaRevision.model.Order;

@Repository
public class PizzaRepository {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void save(Order order){
        redisTemplate.opsForValue().set(order.getOrderID(), order.toJSON().toString());
    }

    public Optional<Order> get(String orderId){
        String json = redisTemplate.opsForValue().get(orderId);
        if(null==json || json.trim().length() <=0){
            return Optional.empty();
        }
        return Optional.of(Order.create(json));
    }
}
