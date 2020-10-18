package SDMShowItemsPage;

import Interfaces.ItemsAdder;
import ItemsDetailsContainer.ItemDetailsContainer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class UIAdapter implements ItemsAdder {
    private Accordion accordionToFill;

    public UIAdapter(Accordion accordionToFill){
        this.accordionToFill = accordionToFill;
    }

    public void addItemToShow(ItemDetailsContainer item) {
        Platform.runLater(() -> {
            TitledPane titledPane = new TitledPane();
            titledPane.animatedProperty().setValue(true);
            titledPane.setText(item.getName());

            VBox vBox = new VBox();
            vBox.prefHeight(200);
            vBox.prefWidth(100);
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20, 0, 0, 20));

            vBox.getChildren().add(new Label("Id: " + item.getId()));
            vBox.getChildren().add(new Label("Name: " + item.getName()));
            vBox.getChildren().add(new Label("Purchase category: " + item.getPurchaseCategory()));
            vBox.getChildren().add(new Label("Number of sellers: " + item.getStoresSellingTheItem()));
            vBox.getChildren().add(new Label("Average price: " + item.getAvgPrice()));
            vBox.getChildren().add(new Label("Sold amount: " + item.getSoldCounter()));

            titledPane.setContent(vBox);

            this.accordionToFill.getPanes().add(titledPane);
        });
    }
}