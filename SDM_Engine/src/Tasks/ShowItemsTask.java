package Tasks;

import Interfaces.ItemsAdder;
import ItemsDetailsContainer.ItemsDetailsContainer;
import ItemsDetailsContainer.ItemDetailsContainer;
import javafx.concurrent.Task;
import javafx.scene.control.TextField;

public class ShowItemsTask extends Task<Boolean> {
    private ItemsAdder uiAdapter;
    private ItemsDetailsContainer itemsDetailsContainer;

    public ShowItemsTask(ItemsAdder uiAdapter, ItemsDetailsContainer allItemsDetails) {
        this.uiAdapter = uiAdapter;
        this.itemsDetailsContainer = allItemsDetails;
    }

    @Override
    protected Boolean call() throws Exception {
        if (this.itemsDetailsContainer.getIteratable() != null) {
            for (ItemDetailsContainer itemDetailsContainer : this.itemsDetailsContainer.getIteratable()) {
                this.uiAdapter.addItemToShow(itemDetailsContainer);
            }
        }

        return Boolean.TRUE;
    }
}