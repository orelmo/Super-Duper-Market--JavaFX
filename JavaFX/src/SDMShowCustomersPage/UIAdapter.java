package SDMShowCustomersPage;

import SDMSystem.SDMSystemCustomer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class UIAdapter {
    private Accordion accordionToFill;

    public UIAdapter(Accordion accordionToFill) {
        this.accordionToFill = accordionToFill;
    }

    public void addCustomerToShow(SDMSystemCustomer customer) {
        Platform.runLater(() -> {
            TitledPane titledPane = new TitledPane();
            titledPane.animatedProperty().setValue(true);
            titledPane.setText(customer.getName());

            VBox vBox = new VBox();
            vBox.prefHeight(200);
            vBox.prefWidth(100);
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20, 0, 0, 20));

            vBox.getChildren().add(new Label("Serial number: " + customer.getId()));
            vBox.getChildren().add(new Label("Name: " + customer.getName()));
            vBox.getChildren().add(new Label("Location : " + customer.getLocation().toString()));
            vBox.getChildren().add(new Label("Number of orders: " + customer.getNumberOfOrders()));
            vBox.getChildren().add(new Label("Average bought items price: " + customer.getAvgOrdersItemsPrice()));
            vBox.getChildren().add(new Label("Average delivery price: " + customer.getAvgOrdersDeliveryPrice()));

            titledPane.setContent(vBox);

            this.accordionToFill.getPanes().add(titledPane);
        });
    }
}