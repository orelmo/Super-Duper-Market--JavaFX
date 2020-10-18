package SDMNewOrderPage;

import EngineClasses.Item.ePurchaseCategory;
import ItemsDetailsContainer.ItemDetailsContainer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TableViewItem {
    private String name;
    private int id;
    private ePurchaseCategory ePurchaseCategory;
    private IntegerProperty price = new SimpleIntegerProperty();
    private TextField amount = new TextField("0");
    private FloatProperty amountProperty = new SimpleFloatProperty();
    private FloatProperty totalPrice = new SimpleFloatProperty();
    private SDMNewOrderPageController controller;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ePurchaseCategory getPurchaseCategory() {
        return ePurchaseCategory;
    }

    public int getPrice() {
        return price.get();
    }

    public TextField getAmount() {
        return amount;
    }

    public Number getAmountValue() {
        if (this.ePurchaseCategory.equals(ePurchaseCategory.Quantity)) {
            return Integer.parseInt(this.amount.getText());
        } else {
            return Float.parseFloat(this.amount.getText());
        }
    }

    public float getTotalPrice() {
        return totalPrice.get();
    }

    public FloatProperty totalPriceProperty() {
        return totalPrice;
    }

    public TableViewItem(ItemDetailsContainer itemDetailsContainer, SDMNewOrderPageController controller) {
        this.name = itemDetailsContainer.getName();
        this.id = itemDetailsContainer.getId();
        this.ePurchaseCategory = itemDetailsContainer.getPurchaseCategory();
        this.price.setValue(itemDetailsContainer.getPriceAtStore());
        this.controller = controller;

        totalPrice.bind(Bindings.multiply(this.price, this.amountProperty));
        setAmountTextFieldOnKeyTyped();
        setAmountTextFieldOnKeyReleased();

        this.amount.focusedProperty().addListener((obj, oldV, newV)->{
            if(newV == false && amount.getText().equals("0") == false){
                controller.showAnimation();
            }
        });
    }

    private void setAmountTextFieldOnKeyReleased() {
        this.amount.setOnKeyReleased(this::onAmountKeyReleased);
    }

    private void onAmountKeyReleased(KeyEvent keyEvent) {
        this.controller.onAmountChanged(this);
        this.amountProperty.set(Float.parseFloat(this.amount.getText()));
    }

    private void setAmountTextFieldOnKeyTyped() {
        if (this.ePurchaseCategory.equals(ePurchaseCategory.Quantity)) {
            this.amount.setOnKeyTyped(this::onQuantityAmountKeyTyped);
        } else {
            this.amount.setOnKeyTyped(this::onWeightAmountKeyTyped);
        }
    }

    private void onWeightAmountKeyTyped(KeyEvent keyEvent) {
        if (((TextField) keyEvent.getSource()).getText().isEmpty()) {
            ((TextField) keyEvent.getSource()).setText("0");
            ((TextField) keyEvent.getSource()).positionCaret(((TextField) keyEvent.getSource()).getText().length());
        }

        if (keyEvent.getCharacter().charAt(0) != 8 && keyEvent.getCharacter().charAt(0) != 127) {
            if ((Character.isDigit(keyEvent.getCharacter().charAt(0)) == false && keyEvent.getCharacter().charAt(0) != '.') ||
                    (keyEvent.getCharacter().charAt(0) == '.' && ((TextField) keyEvent.getSource()).getText().contains("."))) {
                keyEvent.consume();
            } else {
                if (((TextField) keyEvent.getSource()).getText().equals("0")) {
                    if(keyEvent.getCharacter().charAt(0) == '.'){
                        ((TextField) keyEvent.getSource()).setText("0.");
                    } else {
                        ((TextField) keyEvent.getSource()).setText(String.valueOf(keyEvent.getCharacter().charAt(0)));
                    }
                    ((TextField) keyEvent.getSource()).positionCaret(((TextField) keyEvent.getSource()).getText().length());
                    keyEvent.consume();
                }
            }
        }

        this.controller.updateIsEmptyOrder();
    }

    private void onQuantityAmountKeyTyped(KeyEvent keyEvent) {
        if (((TextField) keyEvent.getSource()).getText().isEmpty()) {
            ((TextField) keyEvent.getSource()).setText("0");
            ((TextField) keyEvent.getSource()).positionCaret(((TextField) keyEvent.getSource()).getText().length());
        }

        if (keyEvent.getCharacter().charAt(0) != 8 && keyEvent.getCharacter().charAt(0) != 127) {
            if (Character.isDigit(keyEvent.getCharacter().charAt(0)) == false) {
                keyEvent.consume();
            } else {
                if (((TextField) keyEvent.getSource()).getText().equals("0")) {
                    ((TextField) keyEvent.getSource()).setText(String.valueOf(keyEvent.getCharacter().charAt(0)));
                    ((TextField) keyEvent.getSource()).positionCaret(((TextField) keyEvent.getSource()).getText().length());
                    keyEvent.consume();
                }
            }
        }

        this.controller.updateIsEmptyOrder();
    }
}