package EngineClasses.Order;

import Exceptions.NoSellerException;
import ItemsDetailsContainer.ItemDetailsContainer;
import OrderConteiner.ItemsPerStoreContainer;
import OrderConteiner.OrderContainer;
import SDMSystem.SDMSystemItems;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StoresOfOrder {
    private Map<Integer, StoreOfOrder> storeIdToStoreOfOrder = new HashMap<>();

    public void extractStores(OrderContainer orderContainer) {
        for (Integer storeId : orderContainer.getStoreIdToSeller().keySet()) {
            StoreOfOrder storeOfOrder = new StoreOfOrder();
            storeOfOrder.setDeliveryPrice(orderContainer.getStoreIdToSeller().get(storeId).getDeliveryPrice());
            storeOfOrder.setDistanceFromClient(orderContainer.getStoreIdToSeller().get(storeId).getDistanceFromClient());
            storeOfOrder.setItemsPrice(orderContainer.getStoreIdToSeller().get(storeId).getItemsPrice());
            for (ItemDetailsContainer itemDetailsContainer : orderContainer.getStoreIdToSeller().get(storeId).getItems()) {
                storeOfOrder.addItem(itemDetailsContainer);
            }

            storeIdToStoreOfOrder.put(storeId, storeOfOrder);
        }

        for (Integer storeId : orderContainer.getStoreIdToDeals().keySet()) {
            StoreOfOrder storeOfOrder = storeIdToStoreOfOrder.get(storeId);
            storeOfOrder.addDeal(orderContainer.getStoreIdToDeals().get(storeId));
            storeOfOrder.setItemsPrice(storeOfOrder.getItemsPrice() + orderContainer.getStoreIdToDeals().get(storeId).getItemsPrice());
        }
    }

    public float getDeliveryPriceFromStore(int storeId) {
        return this.storeIdToStoreOfOrder.get(storeId).getDeliveryPrice();
    }

    public float getAmountForItem(int itemId) {
        for (Integer storeId : this.storeIdToStoreOfOrder.keySet()) {
            if (this.storeIdToStoreOfOrder.get(storeId).isSellingItemInOrder(itemId)) {
                return this.storeIdToStoreOfOrder.get(storeId).getAmount(itemId);
            }
        }

        throw new NoSellerException("No seller of item with id: " + itemId);
    }

    public Collection<StoreOfOrder> getStoresOfOrder() {
        return this.storeIdToStoreOfOrder.values();
    }

    public Collection<Integer> getStoresIds() {
        return this.storeIdToStoreOfOrder.keySet();
    }

    public void addStoreOfOrder(int storeId, StoreOfOrder storeOfOrder) {
        this.storeIdToStoreOfOrder.put(storeId, storeOfOrder);
    }

    public StoreOfOrder getStoreOfOrder(Integer storeId) {
        return this.storeIdToStoreOfOrder.get(storeId);
    }

    public float getItemsPriceFromStore(Integer storeId) {
        return this.storeIdToStoreOfOrder.get(storeId).getItemsPrice();
    }

    public int getUnitsNumberOfStore(int storeId, SDMSystemItems systemItems) {
        return this.storeIdToStoreOfOrder.get(storeId).getNumberOfUnits(systemItems);
    }
}