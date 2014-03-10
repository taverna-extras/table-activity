package net.sf.taverna.t2.activities.stilts.ui.config.process;

import net.sf.taverna.t2.activities.stilts.ui.config.input.SingleFormatMultipleInputsConfigurationPanel;
import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.process.TCatBean;

@SuppressWarnings("serial")
public class TCatConfigurationPanel extends StiltsProcessConfigurationPanel <TCatBean>{
     
    public TCatConfigurationPanel(TCatBean processBean, SingleFormatMultipleInputsConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Multiple File Catenatiom Tool"));
    }

}