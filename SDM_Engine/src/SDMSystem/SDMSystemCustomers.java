package SDMSystem;

import EngineClasses.Interfaces.Containable;

import java.util.HashMap;
import java.util.Map;

public class SDMSystemCustomers implements Containable<Integer, SDMSystemCustomer> {

    private Map<Integer, SDMSystemCustomer> idToCustomer = new HashMap<>();

    @Override
    public boolean isExist(Integer key) {
        return this.idToCustomer.containsKey(key);
    }

    @Override
    public SDMSystemCustomer get(Integer key) {
        return this.idToCustomer.get(key);
    }

    @Override
    public void add(Integer key, SDMSystemCustomer newObj) {
        this.idToCustomer.put(key, newObj);
    }

    @Override
    public Iterable<SDMSystemCustomer> getIterable() {
        return this.idToCustomer.values();
    }
}