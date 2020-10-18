package EngineClasses.Order;

import EngineClasses.Customer.Customer;
import EngineClasses.Location.Location;
import OrderConteiner.ItemsPerStoreContainer;
import OrderConteiner.OrderContainer;
import ItemsDetailsContainer.*;
import SDMSystem.SDMSystemCustomer;
import SDMSystem.SDMSystemItems;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Order {
    private static int idFactory = 1;
    private int id;
    private LocalDate arrivalDate;
    private Location arrivalLocation;
    private float itemsPrice;
    private float totalPrice;
    private float deliveryPrice;
    private int numberOfUnits;
    private StoresOfOrder storesOfOrder;
    private SDMSystemCustomer customer;

    public int getNumberOfUnits() {
        return this.numberOfUnits;
    }

    public float getTotalPrice() {
        return this.totalPrice;
    }

    public float getItemsPrice() {
        return this.itemsPrice;
    }

    public SDMSystemCustomer getCustomer() {
        return customer;
    }

    public Order(OrderContainer orderContainer) {
        this.id = idFactory++;
        this.customer = orderContainer.getCustomer();
        this.storesOfOrder = new StoresOfOrder();
        this.storesOfOrder.extractStores(orderContainer);
        this.arrivalDate = orderContainer.getArrivalDate();
        this.arrivalLocation = orderContainer.getArrivalLocation();
        if (orderContainer.getItemsPrice() == 0) {
            this.itemsPrice = calcItemsPrice(orderContainer);
        } else {
            this.itemsPrice = orderContainer.getItemsPrice();
        }
        this.deliveryPrice = calcDeliveryPrice(orderContainer.getStoreIdToSeller());
        this.numberOfUnits = orderContainer.getTotalUnits();
        this.totalPrice = this.deliveryPrice + this.itemsPrice;
    }

    private Order(){}

    private float calcDeliveryPrice(Map<Integer, ItemsPerStoreContainer> storeIdToSeller) {
        float deliveryPrice = 0;
        for(ItemsPerStoreContainer itemsPerStoreContainer : storeIdToSeller.values()){
            deliveryPrice += itemsPerStoreContainer.getDeliveryPrice();
        }

        return deliveryPrice;
    }

    public static void setIdFactory(int newIdFactory) {
        idFactory = newIdFactory;
    }

    private float calcItemsPrice(OrderContainer orderContainer) {
        float price = 0;
        for (ItemsPerStoreContainer itemsPerStoreContainer : orderContainer.getStoreIdToSeller().values()) {
            for(ItemDetailsContainer itemDetailsContainer : itemsPerStoreContainer.getItems()){
                price += itemDetailsContainer.getPriceAtStore();
            }
        }
        for (ItemsPerStoreContainer itemsPerStoreContainer : orderContainer.getStoreIdToDeals().values()) {
            for(ItemDetailsContainer itemDetailsContainer : itemsPerStoreContainer.getItems()){
                price += itemDetailsContainer.getPriceAtStore();
            }
        }
            return price;
    }

    public float getDeliveryPricesFromStore(int storeId) {
        return this.storesOfOrder.getDeliveryPriceFromStore(storeId);
    }

    public float getAmountForItem(int itemId) {
        return this.storesOfOrder.getAmountForItem(itemId);
    }

    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public int getId() {
        return this.id;
    }

    public int getUnitsNumber(int storeId, SDMSystemItems systemItems) {
        return this.storesOfOrder.getUnitsNumberOfStore(storeId, systemItems);
    }

    public Collection<Integer> getOrderItems() {
        List<Integer> itemsIds = new ArrayList<>();
        for(StoreOfOrder storeOfOrder : this.storesOfOrder.getStoresOfOrder()){
            for(Integer itemId : storeOfOrder.getItemsIds()){
                itemsIds.add(itemId);
            }
        }

        return itemsIds;
    }

    public Collection<Integer> getStoresOfOrderIds() {
       return this.storesOfOrder.getStoresIds();
    }

    public Order getSubOrderPerStore(Integer storeId, SDMSystemItems systemItems) {
        Order subOrder = new Order();
        subOrder.id = this.id;
        subOrder.arrivalLocation = this.arrivalLocation;
        subOrder.arrivalDate = this.arrivalDate;
        subOrder.storesOfOrder = new StoresOfOrder();
        subOrder.storesOfOrder.addStoreOfOrder(storeId, this.storesOfOrder.getStoreOfOrder(storeId));
        subOrder.deliveryPrice = this.storesOfOrder.getDeliveryPriceFromStore(storeId);
        subOrder.itemsPrice = this.storesOfOrder.getItemsPriceFromStore(storeId);
        subOrder.numberOfUnits = this.storesOfOrder.getStoreOfOrder(storeId).getNumberOfUnits(systemItems);
        subOrder.totalPrice = subOrder.deliveryPrice + subOrder.itemsPrice;

        return subOrder;
    }

    public float getDeliveryPrice() {
        return this.deliveryPrice;
    }

    public float getDeliveryPricePerStore(Integer storeId) {
        return this.storesOfOrder.getDeliveryPriceFromStore(storeId);
    }

    public AmountForItem getItemsPerStore(Integer storeId) {
        return this.storesOfOrder.getStoreOfOrder(storeId).getAmountForItem();
    }

    public StoreOfOrder getStoreOfOrder(Integer storeId) {
        return this.storesOfOrder.getStoreOfOrder(storeId);
    }

    /*public String getStringToFile() {
        StringBuilder orderToString = new StringBuilder();
        orderToString.append(this.id).append("\n");
        orderToString.append(this.amountForItem.length()).append("\n");
        for (Integer id : this.amountForItem.getItemsId()) {
            orderToString.append(id).append("\n").append(this.amountForItem.get(id)).append("\n");
        }
        orderToString.append(new SimpleDateFormat("dd/MM-HH:mm").format(this.arrivalDate)).append('\n');
        orderToString.append(this.arrivalLocation.getX()).append("\n").append(this.arrivalLocation.getY()).append("\n");
        orderToString.append(this.seller.getId()).append("\n");
        orderToString.append(this.itemsPrice).append("\n");
        orderToString.append(this.deliveryPrice).append("\n");
        orderToString.append(this.totalPrice).append("\n");
        return orderToString.toString();
    }*/
}