package SDMSystem;

import EngineClasses.Interfaces.Containable;
import EngineClasses.Order.Order;

import java.util.ArrayList;
import java.util.List;

public class SDMSystemOrdersHistory implements Containable<Integer, Order> {
    private List<Order> ordersHistory;

    public SDMSystemOrdersHistory() {
        this.ordersHistory = new ArrayList<>();
    }

    @Override
    public boolean isExist(Integer id) {
        for (Order order : ordersHistory) {
            if (order.getId() == id) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Order get(Integer id) {
        for (Order order : ordersHistory) {
            if (order.getId() == id) {
                return order;
            }
        }

        return null;
    }

    public void add(Order order) {
        this.add(order.getId(), order);
    }

    public int size() {
        return this.ordersHistory.size();
    }

    @Override
    public void add(Integer id, Order newObj) {
        this.ordersHistory.add(newObj);
    }

    @Override
    public Iterable<Order> getIterable() {
        return this.ordersHistory;
    }
}