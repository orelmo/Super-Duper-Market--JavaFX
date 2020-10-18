package Interfaces;

import ItemsDetailsContainer.ItemDetailsContainer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface ItemsAdder {
    public void addItemToShow(ItemDetailsContainer item) throws NotImplementedException;
}