package net.sf.taverna.t2.activities.stilts.ui.config;

import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsActivity;
import net.sf.taverna.t2.activities.stilts.SingleFormatMultipleInputsBean;
import net.sf.taverna.t2.activities.stilts.SingleInputActivity;
import net.sf.taverna.t2.activities.stilts.SingleInputBean;

import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;

@SuppressWarnings("serial")
public class SingleFormatMultipleInputsConfigurationPanel 
        <InputActivityType extends SingleFormatMultipleInputsActivity, 
        InputType extends SingleFormatMultipleInputsBean>  extends
        MultipleInputsConfigurationPanel<InputActivityType, InputType> {
	
    private JComboBox inputsFormatSelector;
            
    private static final String INPUT_FORMAT_LABEL = "Input Format";
            
    public SingleFormatMultipleInputsConfigurationPanel(InputActivityType activity) {
        super(activity);
        configBean = (InputType)activity.getConfiguration();
        initGui();
    }

    protected void initGui() {
        super.initGui();
  
        JLabel labelInputFormatType = new JLabel(INPUT_FORMAT_LABEL + ": ");
        add(labelInputFormatType);
        inputsFormatSelector = new JComboBox(StiltsConfigurationConstants.VALID_INPUT_FORMATS_ARRAY);
        add(inputsFormatSelector);
        labelInputFormatType.setLabelFor(inputsFormatSelector);
    }

    /**
      * Check that user values in UI are valid
      */
    @Override
    public boolean checkValues() {
        if (!super.checkValues()){
            return false;
        }
        if (!StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST.contains(inputsFormatSelector.getSelectedItem())){
            String message = inputsFormatSelector.getSelectedItem() + 
                    " Used for " + INPUT_FORMAT_LABEL + 
                    " Is not a valid Input format. Valid formats are: " + StiltsConfigurationConstants.VALID_INPUT_FORMATS_LIST;
            JOptionPane.showMessageDialog(this, "test", "Illegal format", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // All valid, return true
        return true;
    }

    /**
      * Return configuration bean generated from user interface last time
      * noteConfiguration() was called.
      */
    @Override
    public InputType getConfiguration() {
        // Should already have been made by noteConfiguration()
        return configBean;
    }

    /**
      * Check if the user has changed the configuration from the original
      */
    @Override
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        if (!configBean.getFormatOfInputs().equals(inputsFormatSelector.getSelectedItem())){
            return true;
        }
        return false;
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        noteConfiguration(new SingleFormatMultipleInputsBean());
    }

    protected void noteConfiguration(InputType bean) {
    	super.noteConfiguration(configBean);
        configBean.setFormatOfInputs(inputsFormatSelector.getSelectedItem().toString());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration() {
        super.refreshConfiguration();
        configBean = (InputType)activity.getConfiguration();
        
        inputsFormatSelector.setSelectedItem(configBean.getFormatOfInputs());
    }
}