package OrderConteiner;

import java.util.ArrayList;
import java.util.List;

public class OrdersContainer {
    List<OrderContainer> orders =new ArrayList<>();

    public List<OrderContainer> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderContainer> orders) {
        this.orders = orders;
    }

    public void addOrder(OrderContainer newOrder){
        this.orders.add(newOrder);
    }
}