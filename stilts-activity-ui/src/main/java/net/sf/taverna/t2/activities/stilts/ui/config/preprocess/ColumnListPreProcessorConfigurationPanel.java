package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.ColumnListPreProcessorBean;

@SuppressWarnings("serial")
public abstract class ColumnListPreProcessorConfigurationPanel<BoundedBean extends ColumnListPreProcessorBean>  extends StiltsPreProcessConfigurationPanel<BoundedBean>{
 
    private JTextField columnsListField;
        
    public ColumnListPreProcessorConfigurationPanel(BoundedBean preprocessBean, boolean editable){
        super(preprocessBean, editable);
    }
    
    @Override
    void addEditable(BoundedBean preprocessBean){ 
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        JLabel label1 = new JLabel ("Please specify the Column(s) to " + getAction() + ".");
        add(label1, c);
            
        c.gridy = 1;
        JLabel label2 = new JLabel ("Can be the column name as specified in the file.");
        add(label2, c);

        c.gridy = 2;
        JLabel label3 = new JLabel ("Can be the column number (starting at 1)(No $).");
        add(label3, c);

        c.gridy = 3;
        JLabel label4 = new JLabel ("Multiple columns can be specified. Seperaated by a space.");
        add(label4, c);

        c.gridy = 4;            
        c.gridwidth = 1;
        JLabel commandLabel = new JLabel ("Column(s) to " + getAction() + ": ");
        add(commandLabel, c);
        c.gridx = 1;
        columnsListField = new JTextField(preprocessBean.getColumnList(), 20);
        add(columnsListField, c);
    }
   
    final int nextY(){
        return 5;
    }
    
    /**
      * Check that user values in UI are valid
     * @return 
      */
    @Override
    public boolean checkValues() {
         // All valid, return true
        if (columnsListField.getText().trim().isEmpty()){
            String message = "Column(s) to " + getAction() + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + "Column(s) to " + getAction(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
      * Check if the user has changed the configuration from the original
     * @return 
      */
    @Override
    public boolean isConfigurationChanged() {
        return (!columnsListField.getText().equals(preprocessBean.getColumnList()));
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        preprocessBean.setColumnList(columnsListField.getText());
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(BoundedBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        columnsListField = new JTextField(preprocessBean.getColumnList());
    }

    abstract String getAction();
}
