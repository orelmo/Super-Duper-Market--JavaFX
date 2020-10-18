package SDMNewOrderPage.SDMOrderSummary;

import ItemsDetailsContainer.ItemDetailsContainer;
import OrderConteiner.OrderContainer;
import SDMNewOrderPage.SDMNewOrderPageController;
import SDMSystem.SDMSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SDMOrderSummaryPageController {

    @FXML
    private Accordion storesAccordion;

    @FXML
    private Label itemsPriceLabel;

    @FXML
    private Label deliveryPriceLabel;

    @FXML
    private Label totalPriceLabel;

    private SDMNewOrderPageController orderPageController;
    private OrderContainer orderContainer;
    private Stage summaryStage;
    private SDMSystem logic;

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }

    public void setSummaryStage(Stage summaryStage) {
        this.summaryStage = summaryStage;
    }

    public void setOrderContainer(OrderContainer orderContainer) {
        this.orderContainer = orderContainer;
    }

    public void setOrderPageController(SDMNewOrderPageController orderPageController) {
        this.orderPageController = orderPageController;
    }

    public void buildOrderSummaryPage() {
        for (Integer storeId : orderContainer.getStoreIdToSeller().keySet()) {
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
            Label distanceLabel = new Label("Distance from client: " + orderContainer.getStoreIdToSeller().get(storeId).getDistanceFromClient());
            Label deliveryPriceLabel = new Label("Delivery price: " + orderContainer.getStoreIdToSeller().get(storeId).getDeliveryPrice());

            detailsVBox.getChildren().addAll(idLabel, PPKLabel, distanceLabel, deliveryPriceLabel);
            storeDetails.setContent(detailsVBox);

            TitledPane items = new TitledPane();
            items.setText("Items");
            items.setAnimated(true);

            Accordion itemsAccordion = new Accordion();
            itemsAccordion.setPadding(new Insets(0, 0, 0, 10));
            items.setContent(itemsAccordion);

            for (ItemDetailsContainer item : orderContainer.getStoreIdToSeller().get(storeId).getItems()) {
                TitledPane itemTitlePane = new TitledPane();
                itemTitlePane.setText(this.logic.getItem(item.getId()).getName());
                itemTitlePane.setAnimated(true);

                VBox itemDetailsVbox = new VBox();
                itemDetailsVbox.setSpacing(5);

                Label itemIdLabel = new Label("Id: " + item.getId());
                Label itemPurchaseCategoryLabel = new Label("Purchase category: " + item.getPurchaseCategory());
                Label itemAmountLabel = new Label("Amount: " + item.getAmount());
                Label itemPriceAtStoreLabel = new Label("Price per unit: " + item.getPriceAtStore());
                Label itemsLabel = new Label("Total price: " + item.getPriceAtStore() * item.getAmount());
                Label itemIsDealLabel = new Label("Is part of deal: No");

                itemDetailsVbox.getChildren().addAll(itemIdLabel, itemPurchaseCategoryLabel, itemAmountLabel,
                        itemPriceAtStoreLabel, itemsLabel, itemIsDealLabel);

                itemTitlePane.setContent(itemDetailsVbox);

                itemsAccordion.getPanes().add(itemTitlePane);
            }

            if(orderContainer.getStoreIdToDeals().get(storeId) != null) {
                for (ItemDetailsContainer dealItem : orderContainer.getStoreIdToDeals().get(storeId).getItems()) {
                    TitledPane itemTitlePane = new TitledPane();
                    itemTitlePane.setText(this.logic.getItem(dealItem.getId()).getName());
                    itemTitlePane.setAnimated(true);

                    VBox itemDetailsVbox = new VBox();
                    itemDetailsVbox.setSpacing(5);

                    Label itemIdLabel = new Label("Id: " + dealItem.getId());
                    Label itemPurchaseCategoryLabel = new Label("Purchase category: " + dealItem.getPurchaseCategory());
                    Label itemAmountLabel = new Label("Amount: " + dealItem.getAmount());
                    Label itemPriceAtStoreLabel = new Label("Price per unit: " + dealItem.getPriceAtStore());
                    Label itemsLabel = new Label("Total price: " + dealItem.getPriceAtStore() * dealItem.getAmount());
                    Label itemIsDealLabel = new Label("Is part of deal: Yes");

                    itemDetailsVbox.getChildren().addAll(itemIdLabel, itemPurchaseCategoryLabel, itemAmountLabel,
                            itemPriceAtStoreLabel, itemsLabel, itemIsDealLabel);

                    itemTitlePane.setContent(itemDetailsVbox);

                    itemsAccordion.getPanes().add(itemTitlePane);
                }
            }

            storeAccordion.getPanes().addAll(storeDetails, items);
            storeTitlePane.setContent(storeAccordion);
            this.storesAccordion.getPanes().add(storeTitlePane);
        }

        this.itemsPriceLabel.setText(this.itemsPriceLabel.getText() + this.orderContainer.getItemsPrice());
        this.deliveryPriceLabel.setText(this.deliveryPriceLabel.getText() + this.orderContainer.getDeliveryPrice());
        this.totalPriceLabel.setText(this.totalPriceLabel.getText() + this.orderContainer.getTotalOrderPrice());
    }

    @FXML
    void onCancelPressed(ActionEvent event) {
        this.orderPageController.setApprovalOrder(false);
        this.summaryStage.close();
    }

    @FXML
    void onOKPressed(ActionEvent event) {
        this.orderPageController.setApprovalOrder(true);
        this.summaryStage.close();
    }

}
