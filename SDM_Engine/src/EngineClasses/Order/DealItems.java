package EngineClasses.Order;

import EngineClasses.Item.ePurchaseCategory;
import ItemsDetailsContainer.ItemDetailsContainer;
import OrderConteiner.ItemsPerStoreContainer;

import java.util.ArrayList;
import java.util.List;

public class DealItems {
   private List<DealItem> itemIdToDealItem = new ArrayList<>();

    public void addDeal(ItemsPerStoreContainer itemsPerStoreContainer) {
        for(ItemDetailsContainer item: itemsPerStoreContainer.getItems()){
            this.itemIdToDealItem.add(new DealItem(item));
        }
    }

    public int getNumberOfUnits() {
        int numberOfUnits =0;
        for(DealItem dealItem : this.itemIdToDealItem){
            if(dealItem.getPurchaseCategory().equals(ePurchaseCategory.Quantity)){
                numberOfUnits+=dealItem.getAmount();
            }else {
                numberOfUnits += 1;
            }
        }
        return numberOfUnits;
    }

    public List<DealItem> getDealItems() {
        return this.itemIdToDealItem;
    }
}
