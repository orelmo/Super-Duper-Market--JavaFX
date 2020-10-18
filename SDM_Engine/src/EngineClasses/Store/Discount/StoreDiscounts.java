package EngineClasses.Store.Discount;

import EngineClasses.Interfaces.Containable;

import java.util.HashMap;
import java.util.Map;

public class StoreDiscounts implements Containable<String, Discount> {
    Map <String,Discount> discountNameToDiscount = new HashMap<>();

    @Override
    public boolean isExist(String dealName) {
        return this.discountNameToDiscount.containsKey(dealName);
    }

    @Override
    public Discount get(String dealName) {
        return this.discountNameToDiscount.get(dealName);
    }

    @Override
    public void add(String dealName, Discount newObj) {
        this.discountNameToDiscount.put(dealName, newObj);
    }

    @Override
    public Iterable<Discount> getIterable() {
        return this.discountNameToDiscount.values();
    }
}
