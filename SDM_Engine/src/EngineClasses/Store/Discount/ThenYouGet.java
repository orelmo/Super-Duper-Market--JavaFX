package EngineClasses.Store.Discount;

import jaxbClasses.SDMOffer;

public class ThenYouGet {
    private OptionalBenefitItems optionalBenefitItems;
    private eOperator operator;

    public ThenYouGet(){
        this.optionalBenefitItems = new OptionalBenefitItems();
    }

    public ThenYouGet(jaxbClasses.ThenYouGet thenYouGet) throws Exception {
        optionalBenefitItems = new OptionalBenefitItems();
        for (SDMOffer offer : thenYouGet.getSDMOffer()) {
            this.optionalBenefitItems.add(offer.getItemId(), new Offer(offer));
        }
        this.operator = eOperator.stringToEoperator(thenYouGet.getOperator());
    }

    public OptionalBenefitItems getOptionalBenefitItems() {
        return this.optionalBenefitItems;
    }

    public eOperator getOperator() {
        return this.operator;
    }

    public void setOptionalBenefitItems(OptionalBenefitItems optionalBenefitItems) {
        this.optionalBenefitItems = optionalBenefitItems;
    }

    public void setOperator(eOperator operator) {
        this.operator = operator;
    }
}