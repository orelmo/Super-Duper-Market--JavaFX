package Interfaces;

import EngineClasses.Store.Store;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface StoresAdder {
    public void addStoreToShow(Store store);

    public void addStoreToShow(String name, int id) throws NotImplementedException;
}