package Tasks;


import ItemsDetailsContainer.ItemDetailsContainer;
import SDMAddItemToStorePage.UIAdapter;
import SDMSystem.SDMSystemItem;
import SDMSystem.SDMSystemItems;
import javafx.concurrent.Task;


public class FillItemsNamesTask extends Task<Boolean> {
    private UIAdapter uiAdapter;
    private SDMSystemItems itemsToFill;

    public FillItemsNamesTask( UIAdapter uiAdapter,SDMSystemItems itemsToFill ){
        this.uiAdapter = uiAdapter;
        this.itemsToFill = itemsToFill;
    }

    @Override
    protected Boolean call() throws Exception {
        if(itemsToFill.getIterable() != null){
            for(SDMSystemItem item : itemsToFill.getIterable()){
                uiAdapter.addItemToShow(new ItemDetailsContainer(item));
            }
        }

        return Boolean.TRUE;
    }
}
