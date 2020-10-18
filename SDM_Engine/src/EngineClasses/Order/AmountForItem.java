package EngineClasses.Order;

import EngineClasses.Interfaces.Containable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AmountForItem implements Containable<Integer, Number> {
    private final Map<Integer, Number> orderItemsIdToAmount;

    public AmountForItem(Map<Integer, Number> itemIdToAmount) {
        this.orderItemsIdToAmount = itemIdToAmount;
    }

    public AmountForItem() {
        this.orderItemsIdToAmount = new HashMap<>();
    }

    @Override
    public boolean isExist(Integer id) {
        return this.orderItemsIdToAmount.containsKey(id);
    }

    @Override
    public Number get(Integer id) {
        return this.orderItemsIdToAmount.get(id);
    }

    @Override
    public void add(Integer id, Number newObj) {
        this.orderItemsIdToAmount.put(id, newObj);
    }

    @Override
    public Iterable<Number> getIterable() {
        return this.orderItemsIdToAmount.values();
    }

    public Set<Integer> getItemsId() {
        return this.orderItemsIdToAmount.keySet();
    }

    public int length() {
        return this.orderItemsIdToAmount.size();
    }
}