package SDMDeleteItemFromStorePage;

import EngineClasses.Store.Store;
import Interfaces.ItemsAdder;
import Interfaces.StoresAdder;
import ItemsDetailsContainer.ItemDetailsContainer;
import SDMSystem.SDMSystemItem;
import javafx.application.Platform;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UIAdapter implements ItemsAdder, StoresAdder {
    private VBox storesVbox;
    private VBox itemsVbox;
    private ToggleGroup storesGroup;
    private ToggleGroup itemsGroup;
    private SDMDeleteItemFromStorePageController controller;

    public UIAdapter(VBox storesVbox, VBox itemsVbox, SDMDeleteItemFromStorePageController controller){
        this.storesVbox = storesVbox;
        this.itemsVbox = itemsVbox;
        this.controller = controller;
        this.storesGroup = new ToggleGroup();
        this.itemsGroup = new ToggleGroup();
    }

    @Override
    public void addStoreToShow(Store store) {
        addStoreToShow(store.getName(), store.getId());
    }

    @Override
    public void addStoreToShow(String name, int id) {
        Platform.runLater(()->{
            RadioButton newStore = new RadioButton();
            newStore.setMnemonicParsing(false);
            newStore.setOnAction(this.controller::onStoreSelected);
            newStore.setText("Name: " + name + ", Id: " + id);
            newStore.setToggleGroup(storesGroup);

            this.storesVbox.getChildren().add(newStore);
        });
    }

    @Override
    public void addItemToShow(ItemDetailsContainer item) {
        Platform.runLater(() -> {
            RadioButton newItem = new RadioButton();
            newItem.setMnemonicParsing(false);
            newItem.setOnAction(this.controller::onItemSelected);
            newItem.setText(item.getName() + " by " + item.getPurchaseCategory() + ", Id: " + item.getId());
            newItem.setToggleGroup(itemsGroup);

            this.itemsVbox.getChildren().add(newItem);
        });
    }
}