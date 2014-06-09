package net.sf.taverna.t2.activities.stilts.ui.config.preprocess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.taverna.t2.activities.stilts.preprocess.RowCountPreProcessorBean;

/**
 * Base class of all the All PreProcess using a row count Configuration Panels
 * <p>
 * Handles the details of the number of rows shared by these PreProcessors.
 * 
 * @author Christian Brenninkmeijer
 * @version 1.0
 * @param <BoundedBean> a specific RowCountPreProcessorBean
 */
@SuppressWarnings("serial")
public abstract class RowCountPreProcessorConfigurationPanel<BoundedBean extends RowCountPreProcessorBean>  extends StiltsPreProcessConfigurationPanel<BoundedBean>{
 
    private JTextField rowCountField;
        
    public RowCountPreProcessorConfigurationPanel(BoundedBean preprocessBean){
        super(preprocessBean);
        JLabel label1 = new JLabel ("Please specify the Rows(s) to " + getAction() + ".");
        addNextRow(label1, 2);
            
        JLabel commandLabel = new JLabel ("Column(s) to " + getAction() + ": ");
        addNextRow(commandLabel, 1);
        rowCountField = newTextField(preprocessBean.getNumberOfRows()+"");
        addNextCol(rowCountField, 1);
    }
   
    /**
      * Check that user values in UI are valid
     * @return 
      */
    @Override
    public boolean checkValues() {
         // All valid, return true
        if (rowCountField.getText().trim().isEmpty()){
            String message = "Rows(s) to " + getAction() + " can not be empty";
            JOptionPane.showMessageDialog(this, message, "Empty " + "Rows(s) to " + getAction(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer check = Integer.parseInt(rowCountField.getText());
            if (check <= 0){
                String message = "Rows(s) to " + getAction() + " must be positive. Found " + check;
                JOptionPane.showMessageDialog(this, message, "None Postitive " + "Rows(s) to " + getAction(), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception ex){
            String message = "Rows(s) to " + getAction() + " must be an integer. Readed cause an Exception " + ex.getMessage();
            JOptionPane.showMessageDialog(this, message, "Non Integer " + "Rows(s) to " + getAction(), JOptionPane.ERROR_MESSAGE);
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
        return (!rowCountField.getText().equals(preprocessBean.getNumberOfRows()+""));
    }

    /**
      * Prepare a new configuration bean from the UI, to be returned with
      * getConfiguration()
      */
    @Override
    public void noteConfiguration() {
        Integer check = Integer.parseInt(rowCountField.getText());
        System.out.println("noteConfiguration " + check);
        preprocessBean.setNumberOfRows(check);
    }

    /**
      * Update GUI from a changed configuration bean (perhaps by undo/redo).
      * 
      */
    @Override
    public void refreshConfiguration(BoundedBean preprocessBean) {
        super.refreshConfiguration(preprocessBean);
        System.out.println("refreshConfigurationn " + preprocessBean.getNumberOfRows());        
        rowCountField.setText(preprocessBean.getNumberOfRows()+"");
    }

    abstract String getAction();
}
