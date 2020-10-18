package SDMShowOrderHistory;

import EngineClasses.Order.DealItem;
import EngineClasses.Order.Order;
import SDMSystem.SDMSystem;
import SDMSystem.SDMSystemOrdersHistory;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class SDMShowOrderHistoryController {

    @FXML
    private Accordion orderHistoryAccordion;

    private SDMSystem logic;
    private SDMSystemOrdersHistory orderHistory = new SDMSystemOrdersHistory();

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }

    public void setOrderHistory(SDMSystemOrdersHistory orderHistory) {
        this.orderHistory = orderHistory;
    }

    public void showOrderHistory() {
        for (Order order : this.orderHistory.getIterable()) {
            this.orderHistoryAccordion.setMinWidth(250);

            VBox orderVBox = new VBox();
            orderVBox.setSpacing(5);
            orderVBox.setPadding(new Insets(0,0,0,10));

            TitledPane orderTitlePane = new TitledPane();
            orderTitlePane.setText("Order serial number: " + order.getId());
            orderTitlePane.setAnimated(true);

            Accordion orderAccordion = new Accordion();

            for (Integer storeId : order.getStoresOfOrderIds()) {
                TitledPane storeTitlePane = new TitledPane();
                storeTitlePane.setText(this.logic.getStore(storeId).getName());
                storeTitlePane.setAnimated(true);

                Accordion storeAccordion = new Accordion();
                storeAccordion.setPadding(new Insets(0, 0, 0, 10));

                TitledPane storeDetails = new TitledPane();
                storeDetails.setText("Details");
                storeDetails.setAnimated(true);

                VBox detailsVBox = new VBox();
                detailsVBox.setSpacing(5);

                Label idLabel = new Label("Id: " + storeId);
                Label PPKLabel = new Label("PPK: " + this.logic.getStore(storeId).getPPK());
                Label distanceLabel = new Label("Distance from client: " + order.getStoreOfOrder(storeId).getDistanceFromClient());
                Label deliveryPriceLabel = new Label("Delivery price: " + order.getStoreOfOrder(storeId).getDeliveryPrice());

                detailsVBox.getChildren().addAll(idLabel, PPKLabel, distanceLabel, deliveryPriceLabel);
                storeDetails.setContent(detailsVBox);

                TitledPane items = new TitledPane();
                items.setText("Items");
                items.setAnimated(true);

                Accordion itemsAccordion = new Accordion();
                itemsAccordion.setPadding(new Insets(0, 0, 0, 10));
                items.setContent(itemsAccordion);

                for (Integer itemId : order.getStoreOfOrder(storeId).getAmountForItem().getItemsId()) {
                    TitledPane itemTitlePane = new TitledPane();
                    itemTitlePane.setText(this.logic.getItem(itemId).getName());
                    itemTitlePane.setAnimated(true);

                    VBox itemDetailsVbox = new VBox();
                    itemDetailsVbox.setSpacing(5);

                    Label itemIdLabel = new Label("Id: " + itemId);
                    Label itemPurchaseCategoryLabel = new Label("Purchase category: " + this.logic.getItem(itemId).getPurchaseCategory());
                    Label itemAmountLabel = new Label("Amount: " + order.getStoreOfOrder(storeId).getAmount(itemId));
                    Label itemPriceAtStoreLabel = new Label("Price per unit: " + this.logic.getItemPriceFromStore(storeId, itemId));
                    Label itemsLabel = new Label("Total price: " + this.logic.getItemPriceFromStore(storeId, itemId) * order.getStoreOfOrder(storeId).getAmount(itemId));
                    Label itemIsDealLabel = new Label("Is part of deal: No");

                    itemDetailsVbox.getChildren().addAll(itemIdLabel, itemPurchaseCategoryLabel, itemAmountLabel,
                            itemPriceAtStoreLabel, itemsLabel, itemIsDealLabel);

                    itemTitlePane.setContent(itemDetailsVbox);

                    itemsAccordion.getPanes().add(itemTitlePane);
                }

                if (order.getStoreOfOrder(storeId).getDealItems() != null) {
                    for (DealItem dealItem : order.getStoreOfOrder(storeId).getDealItems()) {
                        TitledPane itemTitlePane = new TitledPane();
                        itemTitlePane.setText(this.logic.getItem(dealItem.getId()).getName());
                        itemTitlePane.setAnimated(true);

                        VBox itemDetailsVbox = new VBox();
                        itemDetailsVbox.setSpacing(5);

                        Label itemIdLabel = new Label("Id: " + dealItem.getId());
                        Label itemPurchaseCategoryLabel = new Label("Purchase category: " + dealItem.getPurchaseCategory());
                        Label itemAmountLabel = new Label("Amount: " + dealItem.getAmount());
                        Label itemPriceAtStoreLabel = new Label("Price per unit: " + dealItem.getPrice());
                        Label itemsLabel = new Label("Total price: " + dealItem.getPrice() * dealItem.getAmount());
                        Label itemIsDealLabel = new Label("Is part of deal: Yes");

                        itemDetailsVbox.getChildren().addAll(itemIdLabel, itemPurchaseCategoryLabel, itemAmountLabel,
                                itemPriceAtStoreLabel, itemsLabel, itemIsDealLabel);

                        itemTitlePane.setContent(itemDetailsVbox);

                        itemsAccordion.getPanes().add(itemTitlePane);
                    }
                }

                storeAccordion.getPanes().addAll(storeDetails, items);
                storeTitlePane.setContent(storeAccordion);
                orderAccordion.getPanes().add(storeTitlePane);
            }

            Label customerLabel = new Label("Customer: " + order.getCustomer().getName() + ", Id: " + order.getCustomer().getId());
            Label itemsPriceLabel = new Label("Order items price: " + order.getItemsPrice());
            Label deliveryPriceLabel = new Label("Order delivery price: " + order.getDeliveryPrice());
            Label totalPriceLabel = new Label("Order total price: " + order.getTotalPrice());

            orderVBox.getChildren().addAll(orderAccordion, customerLabel, itemsPriceLabel, deliveryPriceLabel, totalPriceLabel);

            orderTitlePane.setContent(orderVBox);

            this.orderHistoryAccordion.getPanes().add(orderTitlePane);
        }
    }
}