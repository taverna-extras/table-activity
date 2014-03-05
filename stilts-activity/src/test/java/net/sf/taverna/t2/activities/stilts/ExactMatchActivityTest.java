package net.sf.taverna.t2.activities.stilts;

import java.util.ArrayList;
import net.sf.taverna.t2.activities.stilts.utils.StiltsConfigurationConstants;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityConfigurationException;
import org.junit.After;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ExactMatchActivityTest {

    private ExactMatchBean configBean;

    private final ExactMatchActivity activity = new ExactMatchActivity();

    @Before
    public void makeConfigBean() throws Exception {
        configBean = new ExactMatchBean();
        ArrayList<String> formatsOfInputs = new ArrayList<String>();
        formatsOfInputs.add("csv");
        formatsOfInputs.add("csv");
        configBean.setFormatsOfInputs(formatsOfInputs);
        configBean.setFormatOfOutput("csv");
        ArrayList<String> typesOfInputs = new ArrayList<String>();
        typesOfInputs.add(StiltsConfigurationConstants.STRING_TYPE);
        typesOfInputs.add(StiltsConfigurationConstants.STRING_TYPE);
        configBean.setTypesOfInputs(typesOfInputs);
        configBean.setTypeOfOutput(StiltsConfigurationConstants.STRING_TYPE);
        configBean.setFindValue("all");
        configBean.setJoinValue("1and2");
        configBean.setFixcolsValue("dups");
        configBean.setNumbertOfColumnsToMatch(1);
    }
    
    @Test
    public void executeAsynch() throws Exception {
        System.out.println("executeAsynch");
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+1, 
                "code,Last,Email,Job" + System.lineSeparator() + 
                "1,Smith,test@example.com,Programmer" + System.lineSeparator() + 
                "2,Brown,test@example.com,Boss");
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+2, 
                "id,name,number" + System.lineSeparator() + 
                "1,Peter,1433" + System.lineSeparator() + 
                "2,Jack,456");
        inputs.put(ExactMatchActivity.getMatchColumnName(1, 1),"code"); 
        inputs.put(ExactMatchActivity.getMatchColumnName(2, 1),"id"); 

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(AbstractStilsActivity.RESULT_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(AbstractStilsActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("code,Last,Email,Job,id,name,number"));
        assertTrue("Wrong output : Smith line missing. ", result.contains("1,Smith,test@example.com,Programmer,1,Peter,1433"));
        assertTrue("Wrong output : Brown line missing. ", result.contains("2,Brown,test@example.com,Boss,2,Jack,456"));
    }

    @Test
    public void doubleMatch() throws Exception {
        System.out.println("doubleMatch");
        configBean.setNumbertOfColumnsToMatch(2);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+1, 
                "code,first, Last,Email,Job" + System.lineSeparator() + 
                "1,Peter,Smith,test@example.com,Programmer" + System.lineSeparator() + 
                "2,Jack,Brown,test@example.com,Boss");
        inputs.put(MultipleFormatsActivity.INPUT_PARAMETER_NAME+2, 
                "id,name,number" + System.lineSeparator() + 
                "1,Peter,1433" + System.lineSeparator() + 
                "2,Peter,1433" + System.lineSeparator() + 
                "2,Jack,456");
        inputs.put(ExactMatchActivity.getMatchColumnName(1, 1),"code"); 
        inputs.put(ExactMatchActivity.getMatchColumnName(1, 2),"first"); 
        inputs.put(ExactMatchActivity.getMatchColumnName(2, 1),"id"); 
        inputs.put(ExactMatchActivity.getMatchColumnName(2, 2),"name"); 

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(AbstractStilsActivity.RESULT_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);
                
        System.out.println("Run done");
        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(AbstractStilsActivity.RESULT_PARAMETER_NAME).toString();
        System.out.println(result);
        assertTrue("Wrong output : Header line missing. ", result.contains("code,first,Last,Email,Job,id,name,number"));
        assertTrue("Wrong output : Smith line missing. ", result.contains("1,Peter,Smith,test@example.com,Programmer,1,Peter,1433"));
        assertTrue("Wrong output : Brown line missing. ", result.contains("2,Jack,Brown,test@example.com,Boss,2,Jack,456"));
        assertTrue("Wrong output : BAd Peter Line found. ", !result.contains("2,Peter"));        
    }

    @Test
    public void reConfiguredActivity() throws Exception {
        assertEquals("Unexpected inputs", 0, activity.getInputPorts().size());
        assertEquals("Unexpected outputs", 0, activity.getOutputPorts().size());

        activity.configure(configBean);
        assertEquals("Unexpected inputs", 4, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
        configBean.setNumbertOfColumnsToMatch(2);
        activity.configure(configBean);
        assertEquals("Unexpected inputs", 6, activity.getInputPorts().size());
        if (configBean.isDebugMode()){
            assertEquals("Unexpected outputs", 3, activity.getOutputPorts().size());            
        } else {
            assertEquals("Unexpected outputs", 1, activity.getOutputPorts().size());
        }
    }

    @Test(expected = ActivityConfigurationException.class)
    public void reConfiguredActivityLengthError() throws Exception {
        ArrayList<String> typesOfInputs = new ArrayList<String>();
        configBean.setNumberOfInputs(3);
        activity.configure(configBean);
    }
}