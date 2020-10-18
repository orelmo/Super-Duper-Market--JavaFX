package EngineClasses.Store.Discount;


import EngineClasses.Interfaces.Containable;

import java.util.ArrayList;
import java.util.List;

public class OptionalBenefitItems implements Containable<Integer, Offer> {
    List<Offer> offers = new ArrayList();

    @Override
    public boolean isExist(Integer key) {
        return this.offers.contains(key);
    }

    @Override
    public Offer get(Integer key) {
        return this.offers.get(key);
    }

    @Override
    public void add(Integer key, Offer newObj) {
        this.offers.add(newObj);
    }

    @Override
    public Iterable<Offer> getIterable() {
        return this.offers;
    }
}
