package net.sf.taverna.t2.activities.stilts.process;

import java.util.List;
import net.sf.taverna.t2.activities.stilts.configuration.StiltsConfiguration;
import net.sf.taverna.t2.activities.stilts.input.TwoInputsBean;
import net.sf.taverna.t2.activities.stilts.configuration.ConfigurationUtils;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFind;
import net.sf.taverna.t2.activities.stilts.utils.StiltsFixcols;
import net.sf.taverna.t2.activities.stilts.utils.StiltsJoin;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;

/**
 * Base class for applying a Stilts Stilts Match/ join of two tables.
 * <p>
 * Based on {$link http://www.star.bris.ac.uk/~mbt/stilts/sun256/sun256.html#tmatch2}
 * <p>
 * Allows Workflow designers to specify match/join criteria such as find, fixcols and join.
 * <p>
 * Super classes will define how the matching is done.
 * @author christian
 */
public abstract class TMatch2Bean extends StiltsProcessBean {
 
    /**
     * Determines what happens when a row in one table can be matched by more than one row in the other table
     */
    private StiltsFind findValue;
    private final String FIND_NAME = "How to handle multiple matches";
            
    /**
     * Determines which rows are included in the output table. 
     */
    private StiltsJoin joinValue;
    private final String JOIN_NAME = "Which rows to include";
    
    /**
     * Determines how input columns are renamed before use in the output table
     */
    private StiltsFixcols fixcolsValue;
    private final String FIX_COLS_NAME = "How to rename columns";

    /**
     * Serialization constructor
     */
    TMatch2Bean(){
    }
    
    /**
     * Parameterized constructor to help ensure the right information is passed in.
     * <p>
     * This method allows a single call to create the Bean and populate all the required fields.
     * This has the same effect as calling the unparameterized constructor and then all the setters.
     * None of the parameters should be null.
     * 
     * @param findEnum
     * @param fixcolsEnum
     * @param joinEnum
     * @param inputBean 
     */
    TMatch2Bean(StiltsFind findEnum, StiltsFixcols fixcolsEnum, StiltsJoin joinEnum, TwoInputsBean inputBean){
        setInputs(inputBean);
        this.findValue = findEnum;
        this.fixcolsValue = fixcolsEnum;
        this.joinValue = joinEnum;
    }
    
    public void checkValid() throws ActivityConfigurationException{
        super.checkValid();
        if (findValue == null){
             throw new ActivityConfigurationException("Find parameter not set.");
        }
        if (joinValue == null){
             throw new ActivityConfigurationException("Join Paramter not set.");
        }
        if (fixcolsValue == null){
             throw new ActivityConfigurationException("Fixcols parameter not set.");
        }
    }

    /**
     * @return the findValue
     */
    public StiltsFind getFindValue() {
        return findValue;
    }

    /**
     * @param findValue the findValue to set
     */
    public void setFindValue(StiltsFind findValue) {
        this.findValue = findValue;
    }

    /**
     * @return the joinValue
     */
    public StiltsJoin getJoinValue() {
        return joinValue;
    }

    /**
     * @param joinValue the joinValue to set
     */
    public void setJoinValue(StiltsJoin joinValue) {
        this.joinValue = joinValue;
    }

    /**
     * @return the fixcolsValue
     */
    public StiltsFixcols getFixcolsValue() {
        return fixcolsValue;
    }

    /**
     * @param fixcolsValue the fixcolsValue to set
     */
    public void setFixcolsValue(StiltsFixcols fixcolsValue) {
        this.fixcolsValue = fixcolsValue;
    }

    @Override
    public String retrieveStilsCommand() {
        //Currently the remaining parameters are applied in StiltsActivity.createProcessParameters
        //this should idealy be moved here.
        return "tmatch2";
    }

        @Override
    public List<StiltsConfiguration> configurations() {
        List<StiltsConfiguration> configurations = super.configurations();
        configurations.add(new StiltsConfiguration (FIND_NAME,  findValue, true));
        configurations.add(new StiltsConfiguration (JOIN_NAME,  joinValue, true));
        configurations.add(new StiltsConfiguration (FIX_COLS_NAME,  fixcolsValue, true));
        return configurations;        
    }

    public void checkConfiguration(List<StiltsConfiguration> newConfigurations) throws ActivityConfigurationException{ 
        super.checkConfiguration(newConfigurations);
        ConfigurationUtils.checkClass(newConfigurations, FIND_NAME, StiltsFind.class);    
        ConfigurationUtils.checkClass(newConfigurations, JOIN_NAME, StiltsJoin.class);
        ConfigurationUtils.checkClass(newConfigurations, FIX_COLS_NAME, StiltsFixcols.class);
    }    
}
   