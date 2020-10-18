package SDMShowMapPage;

import EngineClasses.Customer.Customer;
import EngineClasses.Interfaces.Locationable;
import EngineClasses.Store.Store;
import SDMSystem.SDMSystemCustomer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class LocationableNode extends Button {

    private Locationable locationable;

    public LocationableNode(Locationable locationable, URL iconUrl) {
        ImageView imageView = new ImageView(iconUrl.toString());

        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        this.setGraphic(imageView);
        this.setBackground(null);
        this.locationable = locationable;

        this.getStyleClass().remove("button");
    }

    public void fillTooltipText(Tooltip tooltip) {
        StringBuilder sb = new StringBuilder();
        if(this.locationable instanceof Store){
            sb.append("Id: ").append(((Store)this.locationable).getId()).append("\n");
            sb.append("Name: ").append(((Store)this.locationable).getName()).append("\n");
            sb.append("PPK: ").append(((Store)this.locationable).getPPK()).append("\n");
            sb.append("Number of orders: ").append(((Store)this.locationable).getOrdersHistory().size());
        } else {
            sb.append("Id: ").append(((SDMSystemCustomer)this.locationable).getId()).append("\n");
            sb.append("Name: ").append(((SDMSystemCustomer)this.locationable).getName()).append("\n");
            sb.append("Number of orders: ").append(((SDMSystemCustomer)this.locationable).getNumberOfOrders());
        }

        tooltip.setText(sb.toString());
    }
}
