package net.sf.taverna.t2.activities.stilts.ui.config.test;

import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.test.TCatNBean;
import net.sf.taverna.t2.activities.stilts.test.MultipleFormatsBean;

@SuppressWarnings("serial")
public class TCatNConfigurationPanel extends StiltsProcessConfigurationPanel <TCatNBean>{
     
    TCatNConfigurationPanel(TCatNBean processBean, MultipleFormatsConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Multiple File Catenatiom Tool"));
    }

}
