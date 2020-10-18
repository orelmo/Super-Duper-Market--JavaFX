package EngineClasses.Item;

import com.sun.xml.internal.ws.util.StringUtils;
import jaxbClasses.SDMItem;

public class Item {
    private String name;
    private ePurchaseCategory purchaseCategory;
    private int id;

     public Item(SDMItem SDMitem) {
         this.id = SDMitem.getId();
         this.purchaseCategory = ePurchaseCategory.valueOf(SDMitem.getPurchaseCategory());
         this.name = toTitleCase(SDMitem.getName());
     }

     public Item(Item otherItem){
         this.name = otherItem.name;
         this.id = otherItem.id;
         this.purchaseCategory = otherItem.purchaseCategory;
     }

    public String getName() {
        return this.name;
    }

    public ePurchaseCategory getPurchaseCategory() {
        return this.purchaseCategory;
    }

    public int getId() {
        return this.id;
    }

    private String toTitleCase(String str) {
        StringBuilder sb = new StringBuilder();
        for (String word : str.split(" ")) {
            sb.append(StringUtils.capitalize(word.toLowerCase()));
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /*@Override
    public String toString() {
        char index = 'a';
        StringBuilder sb = new StringBuilder();
        sb.append('\t').append(index++).append(". ").append("Item ID: ").append(this.id).append('\n');
        sb.append('\t').append(index++).append(". ").append("Item Name: ").append(this.name).append('\n');
        sb.append('\t').append(index).append(". ").append("Item Purchase Category: ").append(this.purchaseCategory.toString()).append('\n');

        return sb.toString();
    }*/

    @Override
    public String toString(){
        return this.getName() + ", id: " + this.getId() + ", Category: " + this.getPurchaseCategory();
    }
}
