package net.sf.taverna.t2.activities.stilts;

/**
 * Stilts activity configuration bean.
 * 
 */
public interface InputTypeInterface extends OutputTypeInterface {
       
   /**
     * @return the formatOfInput
     */
    public String getFormatOfInput();
    
    /**
     * @param formatOfInput the formatOfInput to set
     */
    public void setFormatOfInput(String formatOfInput);
    
    /**
     * @return the typeOfInput
     */
    public String getTypeOfInput();

    /**
     * @param typeOfInput the typeOfInput to set
     */
    public void setTypeOfInput(String typeOfInput);

}
