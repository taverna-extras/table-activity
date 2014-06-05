package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.AddColumnTwoVariablesPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;

/**
 * add Column based on a two variable PreProcess Configuration Panels
 * <p>
 * Allows the specification of the operator and the two variables, while location and name of that column are handled by the super class.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 */@SuppressWarnings("serial")
public class AddColumnTwoVariablePreProcessorConfigurationPanel extends AddColumnPreProcessConfigurationPanel<AddColumnTwoVariablesPreProcessorBean>{
 
    private JTextField variable1Field;
    private JTextField variable2Field;
    private JComboBox<StiltsTwoVariableOperator> operatorSelector;
    
    private static final String VARIABLE1_LABEL = "First Variable for expression";
    private static final String VARIABLE2_LABEL = "Second Variable for expression";
    private static final String OPERATOR_LABEL = "Operator";
    private static final String STILS_HELP_PAGE = "http://www.star.bris.ac.uk/~mbt/stilts/sun256/addcol.html";
    
    public AddColumnTwoVariablePreProcessorConfigurationPanel(AddColumnTwoVariablesPreProcessorBean preprocessBean){
        super(preprocessBean);        
    }
    
    @Override
    void addEditable(AddColumnTwoVariablesPreProcessorBean preprocessBean){   
        super.addEditable(preprocessBean);
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = nextRow();
        JLabel operatorLabel = new JLabel (OPERATOR_LABEL);
        add(operatorLabel, c);
        operatorSelector = new JComboBox<StiltsTwoVariableOperator>(StiltsTwoVariableOperator.values());
        operatorSelector.setSelectedItem(preprocessBean.getOperator());
        operatorSelector.setRenderer(listCellRenderer);
        c.gridx = 1;
        add(operatorSelector, c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy+=1;
        JLabel nameLabel = new JLabel ("Variables can be column names, column Number (prefixed with $)");
        add(nameLabel, c);
        
        c.gridy+=1;
        JLabel exprLabel = new JLabel ("Variables can also be any valid Stilts Expression:");
        add(exprLabel, c);
        
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy+=1;
        JLabel variable1Label = new JLabel (VARIABLE1_LABEL);
        add(variable1Label, c);
        c.gridx = 1;
        variable1Field = new JTextField(preprocessBean.getVariable1(), 20);
        add(variable1Field, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy+=1;
        JLabel variable2Label = new JLabel (VARIABLE2_LABEL);
        add(variable2Label, c);
        c.gridx = 1;
        variable2Field = new JTextField(preprocessBean.getVariable2(), 20);
        add(variable2Field, c);
    }
   
    /**
      * Check that user values in UI are valid
     * @return 
      */
    public boolean checkValues() {
         // All valid, return true
        if (!super.checkValues()){
            return false;
        }
        if (variable1Field.getText().trim().isEmpty()){
            String message = VARIABLE1_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + VARIABLE1_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (variable2Field.getText().trim().isEmpty()){
            String message = VARIABLE2_LABEL + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + VARIABLE2_LABEL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
     * @return 
      */
    public boolean isConfigurationChanged() {
        if (super.isConfigurationChanged()){
            return true;
        }
        if (!variable1Field.getText().equals(preprocessBean.getVariable1())){
            return true;
        }
        if (!variable2Field.getText().equals(preprocessBean.getVariable2())){
            return true;
        }
        if (!operatorSelector.getSelectedItem().equals(preprocessBean.getOperator())){
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
        preprocessBean = new AddColumnTwoVariablesPreProcessorBean();
        super.noteConfiguration();
        preprocessBean.setVariable1(variable1Field.getText());
        preprocessBean.setVariable2(variable2Field.getText());
        preprocessBean.setOperator((StiltsTwoVariableOperator)operatorSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(AddColumnTwoVariablesPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        variable1Field.setText(preprocessBean.getVariable1());
        variable2Field.setText(preprocessBean.getVariable2());
        operatorSelector.setSelectedItem(preprocessBean.getOperator());
    }
}
