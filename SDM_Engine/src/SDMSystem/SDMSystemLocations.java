package SDMSystem;

import EngineClasses.Interfaces.Containable;
import EngineClasses.Interfaces.Locationable;
import EngineClasses.Location.Location;

import java.util.HashMap;
import java.util.Map;

public class SDMSystemLocations implements Containable<Location, Locationable> {
    private Map<Location, Locationable> locationToLocationable = new HashMap<>();

    @Override
    public boolean isExist(Location key) {
        return this.locationToLocationable.containsKey(key);
    }

    @Override
    public Locationable get(Location key) {
        return this.locationToLocationable.get(key);
    }

    @Override
    public void add(Location key, Locationable newObj) {
        this.locationToLocationable.put(key, newObj);
    }

    @Override
    public Iterable<Locationable> getIterable() {
        return this.locationToLocationable.values();
    }
}
