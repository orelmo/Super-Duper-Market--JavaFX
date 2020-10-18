package SDMNewOrderPage.SDMDiscounts;

import EngineClasses.Store.Discount.Discount;
import EngineClasses.Store.Discount.Offer;
import EngineClasses.Store.Discount.eOperator;
import EngineClasses.Store.Store;
import ItemsDetailsContainer.ItemDetailsContainer;
import OrderConteiner.ItemsPerStoreContainer;
import SDMSystem.SDMSystem;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDMStoreDealsController {

    @FXML
    private GridPane storeDealsGrid;

    @FXML
    private Label storeNameLabel;

    private SDMSystem logic;
    private int rowIndex = 1;
    private List<Discount> discounts = new ArrayList<>();

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }

    public void buildDeal(Store store, Discount discount) {
        this.discounts.add(discount);

        this.storeNameLabel.setText(this.storeNameLabel.getText() + store.getName());

        GridPane.setColumnIndex(this.storeNameLabel, 0);
        GridPane.setRowIndex(this.storeNameLabel, 0);

        VBox checkBoxVbox = new VBox();
        checkBoxVbox.setAlignment(Pos.CENTER);
        CheckBox approvalCheckBox = new CheckBox();
        checkBoxVbox.getChildren().add(approvalCheckBox);
        this.storeDealsGrid.add(checkBoxVbox, 1, rowIndex);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color:transparent;");

        VBox scrollPaneVBox = new VBox();
        scrollPaneVBox.setAlignment(Pos.CENTER_LEFT);
        scrollPaneVBox.getChildren().add(scrollPane);

        if (discount.getBenefit().getOperator().equals(eOperator.ONE_OF)) {
            VBox vBox = new VBox();
            vBox.setSpacing(5);

            ToggleGroup newToggleGroup = new ToggleGroup();
            approvalCheckBox.disableProperty().bind(newToggleGroup.selectedToggleProperty().isNull());

            for (Offer offer : discount.getBenefit().getOptionalBenefitItems().getIterable()) {
                RadioButton newItem = new RadioButton();
                newItem.setMnemonicParsing(false);
                newItem.setText("Get " + offer.getQuantity() + " " + this.logic.getItem(offer.getItemId()).getName() +
                        " for " + offer.getAdditionalPrice() * offer.getQuantity());
                newItem.setToggleGroup(newToggleGroup);

                vBox.getChildren().add(newItem);
            }
            scrollPane.setContent(vBox);
            this.storeDealsGrid.add(scrollPane, 0, rowIndex);
        } else {
            Label newItem = new Label();
            String itemText = "Get ";
            float totalPrice = 0;
            boolean isFirstOffer = true;
            for (Offer offer : discount.getBenefit().getOptionalBenefitItems().getIterable()) {
                if (isFirstOffer == false) {
                    itemText += " and ";
                }
                itemText += offer.getQuantity() + " " + this.logic.getItem(offer.getItemId()).getName();
                isFirstOffer = false;
                totalPrice += offer.getAdditionalPrice() * offer.getQuantity();
            }
            itemText += " for " + totalPrice;
            newItem.setText(itemText);
            scrollPane.setContent(newItem);
            this.storeDealsGrid.add(scrollPaneVBox, 0, rowIndex);
        }

        ++rowIndex;
    }

    public ItemsPerStoreContainer analyzeStoreDeals() {
        ItemsPerStoreContainer itemsPerStoreContainer = new ItemsPerStoreContainer();
        for (Node node : this.storeDealsGrid.getChildren()) {
            if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) != 0) {
                if (((CheckBox) ((VBox) node).getChildren().get(0)).isSelected()) {
                    Discount discount = this.discounts.get(GridPane.getColumnIndex(node) - 1);
                    if (discount.getBenefit().getOperator().equals(eOperator.ALL_OR_NOTHING)) {
                        for (Offer offer : discount.getBenefit().getOptionalBenefitItems().getIterable()) {
                            ItemDetailsContainer itemDetailsContainer = new ItemDetailsContainer();
                            itemDetailsContainer.setId(offer.getItemId());
                            itemDetailsContainer.setName(this.logic.getItem(offer.getItemId()).getName());
                            itemDetailsContainer.setPurchaseCategory(this.logic.getItem(offer.getItemId()).getPurchaseCategory());
                            itemDetailsContainer.setPriceAtStore(offer.getAdditionalPrice());
                            itemDetailsContainer.setAmount((float) offer.getQuantity());
                            itemsPerStoreContainer.addItem(itemDetailsContainer);
                            itemsPerStoreContainer.setItemsPrice(itemsPerStoreContainer.getItemsPrice() +
                                    itemDetailsContainer.getAmount() * itemDetailsContainer.getPriceAtStore());
                        }
                    } else if (discount.getBenefit().getOperator().equals(eOperator.ONE_OF)) {
                        Map<Integer, VBox> rowIndexToOffersVbox = analyzeOfferVbox();
                        VBox currentDeal = rowIndexToOffersVbox.get(GridPane.getRowIndex(node) - 1);
                        int offerIndexInVbox = 0;
                        for (Node deal : currentDeal.getChildren()) {
                            if (((RadioButton) deal).isSelected()) {
                                Offer offer = discounts.get(GridPane.getRowIndex(node) - 1).getBenefit().getOptionalBenefitItems().get(offerIndexInVbox);
                                ItemDetailsContainer itemDetailsContainer = new ItemDetailsContainer();
                                itemDetailsContainer.setId(offer.getItemId());
                                itemDetailsContainer.setName(this.logic.getItem(offer.getItemId()).getName());
                                itemDetailsContainer.setPurchaseCategory(this.logic.getItem(offer.getItemId()).getPurchaseCategory());
                                itemDetailsContainer.setPriceAtStore(offer.getAdditionalPrice());
                                itemDetailsContainer.setAmount((float) offer.getQuantity());
                                itemsPerStoreContainer.addItem(itemDetailsContainer);
                                itemsPerStoreContainer.setItemsPrice(itemDetailsContainer.getAmount() * itemDetailsContainer.getPriceAtStore());
                                break;
                            }
                            ++offerIndexInVbox;
                        }

                    } else {
                        Offer offer = discounts.get(GridPane.getRowIndex(node) - 1).getBenefit().getOptionalBenefitItems().get(0);
                        ItemDetailsContainer itemDetailsContainer = new ItemDetailsContainer();
                        itemDetailsContainer.setId(offer.getItemId());
                        itemDetailsContainer.setName(this.logic.getItem(offer.getItemId()).getName());
                        itemDetailsContainer.setPurchaseCategory(this.logic.getItem(offer.getItemId()).getPurchaseCategory());
                        itemDetailsContainer.setPriceAtStore(offer.getAdditionalPrice());
                        itemDetailsContainer.setAmount((float) offer.getQuantity());
                        itemsPerStoreContainer.addItem(itemDetailsContainer);
                        itemsPerStoreContainer.setItemsPrice(itemDetailsContainer.getAmount() * itemDetailsContainer.getPriceAtStore());
                    }
                }
            }
        }

        return itemsPerStoreContainer;
    }

    private Map<Integer, VBox> analyzeOfferVbox() {
        Map<Integer, VBox> rowIndexToOffersVbox = new HashMap<>();
        for (Node node : this.storeDealsGrid.getChildren()) {
            if (GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) != 0) {
                rowIndexToOffersVbox.put(GridPane.getRowIndex(node) - 1, ((VBox)((ScrollPane)node).getContent()));
            }
        }

        return rowIndexToOffersVbox;
    }
}