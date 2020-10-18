package SDMShowStoresPage;

import EngineClasses.Store.Discount.Discount;
import EngineClasses.Store.Discount.Offer;
import EngineClasses.Store.Discount.StoreDiscounts;
import EngineClasses.Store.Discount.eOperator;
import ItemsDetailsContainer.ItemDetailsContainer;
import OrderConteiner.OrderContainer;
import StoresDetailsConteiner.StoreDetailsContainer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class UIAdapter {
    private Accordion accordionToFill;

    public UIAdapter(Accordion accordionToFill){
        this.accordionToFill = accordionToFill;
    }

    public void addStoreToShow(StoreDetailsContainer store) {
        Platform.runLater(() -> {
            TitledPane storePane = new TitledPane();
            storePane.animatedProperty().setValue(true);
            storePane.setText(store.getName());

            storePane.setContent(createStoreAccordion(store));
            
            this.accordionToFill.getPanes().add(storePane);
        });
    }

    private Accordion createStoreAccordion(StoreDetailsContainer store) {
        Accordion storeAccordion = new Accordion();
        storeAccordion.setPadding(new Insets(0, 0, 0, 20));

        TitledPane details = new TitledPane();
        details.animatedProperty().setValue(true);
        details.setText("Details");
        storeAccordion.getPanes().add(details);

        TitledPane items = new TitledPane();
        items.animatedProperty().setValue(true);
        items.setText("Items");
        storeAccordion.getPanes().add(items);

        TitledPane orders = new TitledPane();
        orders.animatedProperty().setValue(true);
        orders.setText("Orders");
        storeAccordion.getPanes().add(orders);

        TitledPane deals = new TitledPane();
        deals.animatedProperty().setValue(true);
        deals.setText("Deals");
        storeAccordion.getPanes().add(deals);

        fillDeals(deals,store);
        fillDetails(details, store);
        filItems(items, store);
        fillOrders(orders, store);

        return storeAccordion;
    }

    private void fillDeals(TitledPane deals, StoreDetailsContainer store) {
        Accordion dealsAccordion = new Accordion();
        dealsAccordion.setPadding(new Insets(0, 0, 0, 20));
        deals.setContent(dealsAccordion);

        for(Discount deal : store.getStoreDiscounts().getIterable()){
            TitledPane dealTitlePane = new TitledPane();
            dealTitlePane.setAnimated(true);
            dealTitlePane.setText(deal.getName());
            dealsAccordion.getPanes().add(dealTitlePane);

            Accordion dealAccordion = new Accordion();
            dealAccordion.setPadding(new Insets(0, 0, 0, 20));
            dealTitlePane.setContent(dealAccordion);

            TitledPane ifYouBuyTitlePane = new TitledPane();
            ifYouBuyTitlePane.setAnimated(true);
            ifYouBuyTitlePane.setText("If You Buy");

            VBox ifYouBuyVBox = new VBox();
            ifYouBuyVBox.setSpacing(10);
            ifYouBuyVBox.setPadding(new Insets(20, 0, 0, 20));

            ifYouBuyVBox.getChildren().add(new Label("Item name: " + store.getItem(deal.getTrigger().getItemIdNeedToBeBought()).getName()));
            ifYouBuyVBox.getChildren().add(new Label("Item id: " + deal.getTrigger().getItemIdNeedToBeBought()));
            ifYouBuyVBox.getChildren().add(new Label("Purchase category: " + store.getItem(deal.getTrigger().getItemIdNeedToBeBought()).getPurchaseCategory()));
            ifYouBuyVBox.getChildren().add(new Label("Quantity: " + deal.getTrigger().getQuantityNeedToBeBought()));

            ifYouBuyTitlePane.setContent(ifYouBuyVBox);
            dealAccordion.getPanes().add(ifYouBuyTitlePane);

            TitledPane thanYouGetTitlePane = new TitledPane();
            thanYouGetTitlePane.setAnimated(true);
            if(deal.getBenefit().getOperator().equals(eOperator.ALL_OR_NOTHING)){
                thanYouGetTitlePane.setText("Than You Get All Or Nothing");
            }else if(deal.getBenefit().getOperator().equals(eOperator.ONE_OF)){
                thanYouGetTitlePane.setText("Than You Get One Of");
            }else{
                thanYouGetTitlePane.setText("Than You Get");
            }
            dealAccordion.getPanes().add(thanYouGetTitlePane);

            Accordion itemsAccordion = new Accordion();
            itemsAccordion.setPadding(new Insets(0, 0, 0, 20));
            thanYouGetTitlePane.setContent(itemsAccordion);

            for(Offer offer : deal.getBenefit().getOptionalBenefitItems().getIterable()) {
                TitledPane itemTitlePane = new TitledPane();
                itemTitlePane.setText(store.getItem(offer.getItemId()).getName());
                itemTitlePane.setAnimated(true);
                itemsAccordion.getPanes().add(itemTitlePane);

                VBox itemVBox = new VBox();
                itemVBox.setSpacing(10);
                itemTitlePane.setContent(itemVBox);

                itemVBox.getChildren().add(new Label("Item id: " + offer.getItemId()));
                itemVBox.getChildren().add(new Label("Purchase category: " + store.getItem(offer.getItemId()).getPurchaseCategory()));
                itemVBox.getChildren().add(new Label("Quantity: " + offer.getQuantity()));
                itemVBox.getChildren().add(new Label("Additional price per unit: " +offer.getQuantity()));
            }
        }
    }

    private void fillOrders(TitledPane orders, StoreDetailsContainer store) {
        Accordion ordersAccordion = new Accordion();
        ordersAccordion.setPadding(new Insets(0, 0, 0, 20));

        for(OrderContainer order : store.getOrdersHistory()) {
            TitledPane orderPane = new TitledPane();
            orderPane.setText("Order Serial Number: " + order.getOrderId());

            VBox vBox = new VBox();
            vBox.prefHeight(200);
            vBox.prefWidth(100);
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20, 0, 0, 20));

            vBox.getChildren().add(new Label("Date: " + order.getArrivalDate()));
            vBox.getChildren().add(new Label("Total units: " + order.getTotalUnits()));
            vBox.getChildren().add(new Label("Total items price: " + order.getItemsPrice()));
            vBox.getChildren().add(new Label("Delivery price: " + order.getDeliveryPrice()));
            vBox.getChildren().add(new Label("Total price: " + order.getTotalOrderPrice()));

            orderPane.setContent(vBox);

            ordersAccordion.getPanes().add(orderPane);
        }

        orders.setContent(ordersAccordion);
    }

    private void filItems(TitledPane items, StoreDetailsContainer store) {
        Accordion itemsAccordion = new Accordion();
        itemsAccordion.setPadding(new Insets(0, 0, 0, 20));

        for (ItemDetailsContainer item : store.getItemsDetails()) {
            TitledPane itemPane = new TitledPane();
            itemPane.setText(item.getName());

            VBox vBox = new VBox();
            vBox.prefHeight(200);
            vBox.prefWidth(100);
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20, 0, 0, 20));

            vBox.getChildren().add(new Label("Id: " + item.getId()));
            vBox.getChildren().add(new Label("Name: " + item.getName()));
            vBox.getChildren().add(new Label("Purchase category: " + item.getPurchaseCategory()));
            vBox.getChildren().add(new Label("Price: " + item.getPriceAtStore()));
            vBox.getChildren().add(new Label("Total sold units: " + item.getSoldCounter()));

            itemPane.setContent(vBox);

            itemsAccordion.getPanes().add(itemPane);
        }

        items.setContent(itemsAccordion);
    }

    private void fillDetails(TitledPane details, StoreDetailsContainer store) {
        VBox vBox = new VBox();
        vBox.prefHeight(200);
        vBox.prefWidth(100);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20, 0, 0, 20));

        vBox.getChildren().add(new Label("Id: " + store.getId()));
        vBox.getChildren().add(new Label("Name: " + store.getName()));
        vBox.getChildren().add(new Label("PPK: " + store.getPpk()));
        vBox.getChildren().add(new Label("Total delivery income: " + store.getTotalDeliveriesIncome()));

        details.setContent(vBox);
    }
}