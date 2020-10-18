package SDMSystem;

import EngineClasses.Customer.Customer;
import jaxbClasses.SDMCustomer;

public class SDMSystemCustomer extends Customer {
    private int numberOfOrders;
    private float avgOrdersItemsPrice;
    private float avgOrdersDeliveryPrice;

    public SDMSystemCustomer(){}

    public SDMSystemCustomer(SDMCustomer sdmCustomer, SDMSystem mySDMSystem) {
        super(sdmCustomer, mySDMSystem);
        this.numberOfOrders = 0;
        this.avgOrdersDeliveryPrice = 0;
        this.avgOrdersItemsPrice = 0;
    }

    public float getAvgOrdersItemsPrice() {
        return this.avgOrdersItemsPrice;
    }

    public float getAvgOrdersDeliveryPrice() {
        return this.avgOrdersDeliveryPrice;
    }

    public int getNumberOfOrders() {
        return this.numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public void setAvgOrdersItemsPrice(float avgOrdersItemsPrice) {
        this.avgOrdersItemsPrice = avgOrdersItemsPrice;
    }

    public void setAvgOrdersDeliveryPrice(float avgOrdersDeliveryPrice) {
        this.avgOrdersDeliveryPrice = avgOrdersDeliveryPrice;
    }
}
