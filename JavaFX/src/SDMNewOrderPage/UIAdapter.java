package SDMNewOrderPage;

import EngineClasses.Customer.Customer;
import EngineClasses.Store.Store;
import Interfaces.CustomersAdder;
import Interfaces.ItemsAdder;
import Interfaces.StoresAdder;
import ItemsDetailsContainer.ItemDetailsContainer;
import SDMSystem.SDMSystemCustomer;
import SDMSystem.SDMSystemItem;
import StoresDetailsConteiner.StoreDetailsContainer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UIAdapter implements ItemsAdder, StoresAdder, CustomersAdder {
    private ComboBox<Store> storesComboBox;
    private ComboBox<SDMSystemCustomer> customersComboBox;
    private TableView<TableViewItem> itemsTableView;
    private SDMNewOrderPageController controller;
    private Accordion suppliersAccordion;

    public UIAdapter(ComboBox<Store> storesComboBox, ComboBox<SDMSystemCustomer> customersComboBox,
                     TableView<TableViewItem> itemsTableView, Accordion suppliersAccordion,
                     SDMNewOrderPageController controller) {
        this.customersComboBox = customersComboBox;
        this.storesComboBox = storesComboBox;
        this.itemsTableView = itemsTableView;
        this.suppliersAccordion = suppliersAccordion;
        this.controller = controller;
    }

    @Override
    public void addStoreToShow(Store store) {
        Platform.runLater(() -> {
            this.storesComboBox.getItems().add(store);
        });
    }

    @Override
    public void addStoreToShow(String name, int id) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public void addCustomerToShow(SDMSystemCustomer customer) {
        Platform.runLater(() -> {
            this.customersComboBox.getItems().add(customer);
        });
    }

    @Override
    public void addItemToShow(ItemDetailsContainer item) {
        this.itemsTableView.getItems().add(new TableViewItem(item, controller));
    }

    public void addStoreInSummary(StoreDetailsContainer storeDetailsContainer) {
        Platform.runLater(() -> {
            TitledPane titledPane = new TitledPane();
            titledPane.animatedProperty().setValue(true);
            titledPane.setText(storeDetailsContainer.getName());

            VBox vBox = new VBox();
            vBox.prefHeight(200);
            vBox.prefWidth(100);
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20, 0, 0, 20));

            vBox.getChildren().add(new Label("Serial number: " + storeDetailsContainer.getId()));
            vBox.getChildren().add(new Label("Location: " + storeDetailsContainer.getLocation()));
            vBox.getChildren().add(new Label("Distance from customer: " + storeDetailsContainer.getDistanceFromCustomer()));
            vBox.getChildren().add(new Label("PPK: " + storeDetailsContainer.getPpk()));
            vBox.getChildren().add(new Label("Delivery price: " + storeDetailsContainer.getDeliveryPrice()));
            vBox.getChildren().add(new Label("Number of different items: " + storeDetailsContainer.getNumberOfDifferentItems()));
            vBox.getChildren().add(new Label("Items price: " + storeDetailsContainer.getItemsPrice()));

            titledPane.setContent(vBox);

            this.suppliersAccordion.getPanes().add(titledPane);
        });
    }

    public void updateStoreInSummary(StoreDetailsContainer storeInSummary) {
        Platform.runLater(() -> {
            for (TitledPane titledPane : this.suppliersAccordion.getPanes()) {
                if (((Label)((VBox) titledPane.getContent()).getChildren().get(0)).getText()
                        .equals("Serial number: " + storeInSummary.getId())) {
                    ((Label) ((VBox) titledPane.getContent()).getChildren().get(5))
                            .setText("Number of different items: " + storeInSummary.getNumberOfDifferentItems());
                    ((Label) ((VBox) titledPane.getContent()).getChildren().get(6))
                            .setText("Items price: " + storeInSummary.getItemsPrice());
                }
            }
        });
    }

    public void removeStoreFromDynamicOrderSummary(StoreDetailsContainer storeInSummary) {
        Platform.runLater(()->{
            for (TitledPane titledPane : this.suppliersAccordion.getPanes()) {
                if (((Label)((VBox) titledPane.getContent()).getChildren().get(0)).getText()
                        .equals("Serial number: " + storeInSummary.getId())) {
                    this.suppliersAccordion.getPanes().remove(titledPane);
                    return;
                }
            }
        });
    }
}