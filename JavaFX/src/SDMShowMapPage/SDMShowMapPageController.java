package SDMShowMapPage;

import EngineClasses.Interfaces.Locationable;
import EngineClasses.Store.Store;
import SDMSystem.SDMSystem;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.awt.*;
import java.net.URL;
import java.util.Random;

public class SDMShowMapPageController {

    @FXML
    private GridPane mapGrid;

    @FXML
    private BorderPane mapBorderPane;

    @FXML
    private ScrollPane mapScrollPane;

    private SDMSystem logic;

    public void buildMap() {
        Point mapSize = this.logic.getMapSize();
        generateMapBySize(mapSize);
        String backgroundImageUrl = "/SDMShowMapPage/Resources/MapBackground.jpg";
        this.mapGrid.setBackground(new Background(new BackgroundImage(new Image(backgroundImageUrl), null, null, null, null)));
        this.mapScrollPane.setMaxWidth(70 * mapSize.x);
        this.mapScrollPane.setMaxHeight(70 * mapSize.y);
    }

    private void generateMapBySize(Point mapSize) {
        fillEmptyCells(mapSize);
        fillObjects();
    }

    private void fillObjects() {
        LocationableNode locationableNode;
        Random rand = new Random();

        for (Locationable locationable : this.logic.getLocationableObjects()) {
            if (locationable instanceof Store) {
                locationableNode = new LocationableNode(locationable, getClass().getResource("/SDMShowMapPage/Resources/ShopIcon.png"));
            } else {
                int randCustomerImage = rand.nextInt(4) + 1;
                URL imageUrl = getClass().getResource("/SDMShowMapPage/Resources/CustomerIcon" + randCustomerImage + ".png");
                locationableNode = new LocationableNode(locationable, imageUrl);
            }

            Tooltip tooltip = new Tooltip();
            tooltip.setFont(new Font(18));
            locationableNode.fillTooltipText(tooltip);
            locationableNode.setTooltip(tooltip);

            this.mapGrid.add(locationableNode, locationable.getLocation().getX() - 1, locationable.getLocation().getY() - 1);
        }
    }

    private void fillEmptyCells(Point mapSize) {
        for (int i = 0; i < mapSize.x; i++) {
            for (int j = 0; j < mapSize.y; j++) {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(60);
                imageView.setFitWidth(60);
                this.mapGrid.add(imageView, i, j);
            }
        }
    }

    public void setLogic(SDMSystem logic) {
        this.logic = logic;
    }
}