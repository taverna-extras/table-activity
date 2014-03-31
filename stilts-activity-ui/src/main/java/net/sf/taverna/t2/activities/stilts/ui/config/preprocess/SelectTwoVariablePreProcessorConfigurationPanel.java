package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import net.sf.taverna.t2.activities.stilts.preprocess.SelectTwoVariablesPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.ui.config.DescriptionRenderer;
import net.sf.taverna.t2.activities.stilts.utils.DescribableInterface;
import net.sf.taverna.t2.activities.stilts.operator.StiltsTwoVariableOperator;

@SuppressWarnings("serial")
public class SelectTwoVariablePreProcessorConfigurationPanel extends StiltsPreProcessConfigurationPanel<SelectTwoVariablesPreProcessorBean>{
 
    private JTextField variable1Field;
    private JTextField variable2Field;
    private JComboBox<StiltsTwoVariableOperator> operatorSelector;
    
    private static final String VARIABLE1_LABEL = "First Variable for expression";
    private static final String VARIABLE2_LABEL = "Second Variable for expression";
    private static final String OPERATOR_LABEL = "Operator";
    private static final String STILS_HELP_PAGE = "http://www.star.bris.ac.uk/~mbt/stilts/sun256/addcol.html";
    
    protected static ListCellRenderer<DescribableInterface> listCellRenderer = new DescriptionRenderer();

    public SelectTwoVariablePreProcessorConfigurationPanel(SelectTwoVariablesPreProcessorBean preprocessBean, boolean editable){
        super(preprocessBean, editable);        
    }
    
    @Override
    void addEditable(SelectTwoVariablesPreProcessorBean preprocessBean){   
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        JLabel operatorLabel = new JLabel (OPERATOR_LABEL);
        add(operatorLabel, c);
        operatorSelector = new JComboBox<StiltsTwoVariableOperator>(StiltsTwoVariableOperator.booleanValues());
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
        preprocessBean = new SelectTwoVariablesPreProcessorBean();
        preprocessBean.setVariable1(variable1Field.getText());
        preprocessBean.setVariable2(variable2Field.getText());
        preprocessBean.setOperator((StiltsTwoVariableOperator)operatorSelector.getSelectedItem());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    public void refreshConfiguration(SelectTwoVariablesPreProcessorBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        variable1Field.setText(preprocessBean.getVariable1());
        variable2Field.setText(preprocessBean.getVariable2());
        operatorSelector.setSelectedItem(preprocessBean.getOperator());
    }
}
