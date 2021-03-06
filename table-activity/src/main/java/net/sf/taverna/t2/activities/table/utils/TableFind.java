package net.sf.taverna.t2.activities.table.utils;

/**
 * Determines what happens when a row in one table can be matched by more than one row in the other table
 * @see http://www.star.bristol.ac.uk/~mbt/stilts/sun256/tmatch2-usage.html
 * @author christian
 */
public enum TableFind implements DescribableInterface {

    ALL("all","All matches (repeat rows)",
            "All matches. Every match between the two tables is included in the result. "
            + "Rows from both of the input tables may appear multiple times in the result."),
    BEST("best","Best Match for any row only",
            "Best match, symmetric. The best pairs are selected in a way which treats the two tables symmetrically. "
            + "Any input row which appears in one result pair is disqualified from appearing in any other result pair, "
            + "so each row from both input tables will appear in at most one row in the result."),
    BEST1("best1","Best match for each Table 1 row",
            "Best match for each Table 1 row. For each row in table 1, "
            + "only the best match from table 2 will appear in the result. "
            + "Each row from table 1 will appear a maximum of once in the result, "
            + "but rows from table 2 may appear multiple times."),
    BEST2("best2","Best match for each Table 2 row",
            "Best match for each Table 2 row. For each row in table 2, "
            + "only the best match from table 1 will appear in the result. "
            + "Each row from table 2 will appear a maximum of once in the result, "
            + "but rows from table 1 may appear multiple times.");

    private final String stiltsName;
    private final String userName;
    private final String description;

    TableFind(String stiltsName, String userName, String description){
        this.stiltsName = stiltsName;  
        this.userName = userName;
        this.description = description;
    }

    /**
     * @return the stiltsName
     */
    public String getStiltsName() {
        return stiltsName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    public static TableFind byStiltsName(String stiltsName){
        for (TableFind format:TableFind.values()){
            if (format.stiltsName.equals(stiltsName)){
                return format;
            }
        }
        throw new UnsupportedOperationException("No TableInputFormat known for " + stiltsName);
    }
    
    @Override
    public String toString(){
        return userName;
    }

}
