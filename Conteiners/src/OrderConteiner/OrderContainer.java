package OrderConteiner;

import EngineClasses.Customer.Customer;
import EngineClasses.Location.Location;
import EngineClasses.Store.Store;
import SDMSystem.SDMSystemCustomer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderContainer {
    private int orderId;
    private LocalDate arrivalDate;
    private Location arrivalLocation;
    private int numberOfDifferentItems;
    private float totalOrderPrice;
    private float itemsPrice;
    private int totalUnits;
    private float deliveryPrice;
    private SDMSystemCustomer customer = new SDMSystemCustomer();
    private Map<Integer, ItemsPerStoreContainer> storeIdToSeller = new HashMap<>();
    private Map<Integer, ItemsPerStoreContainer> storeIdToDeals = new HashMap<>();

    public SDMSystemCustomer getCustomer(){
        return this.customer;
    }

    public void setCustomer(SDMSystemCustomer customer) {
        this.customer = customer;
    }

    public void setStoreIdToSeller(Map<Integer, ItemsPerStoreContainer> storeIdToSeller) {
        this.storeIdToSeller = storeIdToSeller;
    }

    public void setStoreIdToDeals(Map<Integer, ItemsPerStoreContainer> storeIdToDeals) {
        this.storeIdToDeals = storeIdToDeals;
    }

    public Map<Integer, ItemsPerStoreContainer> getStoreIdToDeals() {
        return storeIdToDeals;
    }

    public void addItemsPerStoreContainer(int storeId, ItemsPerStoreContainer itemsPerStoreContainer){
        this.storeIdToSeller.put(storeId, itemsPerStoreContainer);
        this.itemsPrice += itemsPerStoreContainer.getItemsPrice();
        this.deliveryPrice += itemsPerStoreContainer.getDeliveryPrice();
        this.totalOrderPrice += itemsPerStoreContainer.getItemsPrice() + itemsPerStoreContainer.getDeliveryPrice();
    }

    public Collection<ItemsPerStoreContainer> geItemsPerStore() {
        return storeIdToSeller.values();
    }

    public Map<Integer, ItemsPerStoreContainer> getStoreIdToSeller() {
        return this.storeIdToSeller;
    }

    public ItemsPerStoreContainer getSeller(int sellerId){
        return this.storeIdToSeller.get(sellerId);
    }

    public int getNumberOfDifferentItems() {
        return this.numberOfDifferentItems;
    }

    public void setNumberOfDifferentItems(int numberOfDifferentItems) {
        this.numberOfDifferentItems = numberOfDifferentItems;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTotalUnits() {
        return this.totalUnits;
    }

    public void setTotalUnits(int totalUnits) {
        this.totalUnits = totalUnits;
    }

    public float getItemsPrice() {
        return this.itemsPrice;
    }

    public void setItemsPrice(float itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public float getTotalOrderPrice() {
        return this.totalOrderPrice;
    }

    public void setTotalOrderPrice(float totalOrderPrice) {
        this.totalOrderPrice = totalOrderPrice;
    }

    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Location getArrivalLocation() {
        return this.arrivalLocation;
    }

    public void setArrivalLocation(Location arrivalLocation) {
        this.arrivalLocation = arrivalLocation;
    }

    public void addSeller(int storeId,ItemsPerStoreContainer itemsPerStoreContainer) {
        this.storeIdToSeller.put(storeId, itemsPerStoreContainer);
    }

    public float getDeliveryPrice() {
        return this.deliveryPrice;
    }

    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
}