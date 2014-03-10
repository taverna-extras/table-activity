package net.sf.taverna.t2.activities.stilts.ui.config.test;

import javax.swing.JLabel;
import net.sf.taverna.t2.activities.stilts.test.SingleInputBean;
import net.sf.taverna.t2.activities.stilts.test.TPipeBean;

@SuppressWarnings("serial")
public class TPipeConfigurationPanel extends StiltsProcessConfigurationPanel <TPipeBean>{
     
    TPipeConfigurationPanel(TPipeBean processBean, SingleInputConfigurationPanel inputPanel){
        super(processBean, inputPanel);
        processPanel.add(new JLabel("Simple Piping Tool"));
    }

}
