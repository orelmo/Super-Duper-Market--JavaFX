package EngineClasses.Store.Discount;

import javax.naming.directory.NoSuchAttributeException;

public enum eOperator {
    IRRELEVANT , ONE_OF, ALL_OR_NOTHING;

    public static eOperator stringToEoperator(String operator) throws Exception {
        switch (operator){
            case "IRRELEVANT": {
                return IRRELEVANT;
            }
            case "ONE-OF": {
                return ONE_OF;
            }
            case "ALL-OR-NOTHING" :{
                return ALL_OR_NOTHING;
            }
            default:{
                throw new NoSuchAttributeException("No Operator Such" + operator);
            }
        }
    }
}
