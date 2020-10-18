package EngineClasses.Customer;

import EngineClasses.Interfaces.Locationable;
import EngineClasses.Location.Location;
import Exceptions.LocationException;
import SDMSystem.SDMSystem;

public class Customer implements Locationable {
    private int id;
    private String name;
    private Location location;
    private SDMSystem mySDMSystem;

    public Customer(){}

    public Customer(jaxbClasses.SDMCustomer sdmCustomer, SDMSystem mySDMSystem) {
        this.id = sdmCustomer.getId();
        this.name = sdmCustomer.getName();
        setLocation(sdmCustomer.getLocation());
        this.mySDMSystem = mySDMSystem;
    }

    private void setLocation(jaxbClasses.Location location) {
        try {
            this.location = new Location(location);
        } catch (LocationException e) {
            throw new LocationException("Customer with id " + this.getId() + " " + e.getMessage());
        }
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString(){
        return "Serial Number: " + this.getId() + ", Name: " + this.getName();
    }
}