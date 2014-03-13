package net.sf.taverna.t2.activities.stilts.process;

import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.input.SingleInputBean;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.preprocess.UserSpecifiedPreProcessorBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsInputType;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

import net.sf.taverna.t2.activities.testutils.ActivityInvoker;
import net.sf.taverna.t2.workflowmodel.OutputPort;
import net.sf.taverna.t2.workflowmodel.processor.activity.ActivityInputPort;

import org.junit.Before;
import org.junit.Test;

public class UserSpecifiedPreProcessorActivityTest {

    private StiltsBean configBean;

    private final StiltsActivity activity = new StiltsActivity();

    private static final String CSV_STRING = "id,name,number" + System.lineSeparator() +
            "1,John,1234" + System.lineSeparator() +
            "2,Christian,4567" + System.lineSeparator();

    @Before
    public void makeConfigBean() throws Exception {
        SingleInputBean inputBean = new SingleInputBean(StiltsInputFormat.TST, StiltsInputType.FILE);
        UserSpecifiedPreProcessorBean preProcessBean = new UserSpecifiedPreProcessorBean("delcols 1");
        TPipeBean processBean = new TPipeBean(inputBean);
        configBean = new StiltsBean(preProcessBean, processBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false);
    }

    //@Test(expected = ActivityConfigurationException.class)
    //public void invalidConfiguration() throws ActivityConfigurationException {
    //    configBean.setFormatOfOutput("invalidExample");
    //    // Should throw ActivityConfigurationException
    //    activity.configure(configBean);
    //}

    @Test
    public void addCol1() throws Exception {
        System.out.println("addCol");
        UserSpecifiedPreProcessorBean preProcessorBean = (UserSpecifiedPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setPreProcessCommand("addcol -after name newcol \"$1 + $3\"");
        String expected = "id,name,newcol,number" + System.lineSeparator() +
            "1,John,1235,1234" + System.lineSeparator() +
            "2,Christian,4569,4567" + System.lineSeparator();

        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

    @Test
    public void addColName() throws Exception {
        System.out.println("addColName");
        UserSpecifiedPreProcessorBean preProcessorBean = (UserSpecifiedPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setPreProcessCommand("addcol -before name newcol \"id - number\"");
        String expected =  "id,newcol,name,number" + System.lineSeparator() +
            "1,-1233,John,1234" + System.lineSeparator() +
            "2,-4565,Christian,4567" + System.lineSeparator();
        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

    @Test
    public void addColGreaterThan() throws Exception {
        System.out.println("addColGreaterThan");
        UserSpecifiedPreProcessorBean preProcessorBean = (UserSpecifiedPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setPreProcessCommand("addcol newcol \"id < number\"");
        String expected =  "id,name,number,newcol" + System.lineSeparator() +
            "1,John,1234,true" + System.lineSeparator() +
            "2,Christian,4567,true" + System.lineSeparator();
        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

    @Test
    public void addColFloat() throws Exception {
        System.out.println("addColFloat");
        UserSpecifiedPreProcessorBean preProcessorBean = (UserSpecifiedPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setPreProcessCommand("addcol newcol \"(float)number\"");
        String expected =  "id,name,number,newcol" + System.lineSeparator() +
            "1,John,1234,1234.0" + System.lineSeparator() +
            "2,Christian,4567,4567.0" + System.lineSeparator();
        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

    @Test
    public void addColRoundUp() throws Exception {
        System.out.println("addColRoundUp");
        UserSpecifiedPreProcessorBean preProcessorBean = (UserSpecifiedPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setPreProcessCommand("addcol newcol \"roundUp(number/2)\"");
        String expected =  "id,name,number,newcol" + System.lineSeparator() +
            "1,John,1234,617" + System.lineSeparator() +
            "2,Christian,4567,2283" + System.lineSeparator();
        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }

   @Test
    public void addColMax() throws Exception {
        System.out.println("addColMax");
        UserSpecifiedPreProcessorBean preProcessorBean = (UserSpecifiedPreProcessorBean)configBean.getPreprocess();
        preProcessorBean.setPreProcessCommand("addcol newcol \"max(id,number)\"");
        String expected =  "id,name,number,newcol" + System.lineSeparator() +
            "1,John,1234,1234" + System.lineSeparator() +
            "2,Christian,4567,4567" + System.lineSeparator();
        configBean.setDebugMode(false);
        activity.configure(configBean);

        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put(StiltsActivity.INPUT_TABLE_PARAMETER_NAME, "src/test/resources/test.tst");

        Map<String, Class<?>> expectedOutputTypes = new HashMap<String, Class<?>>();
        expectedOutputTypes.put(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME, String.class);
        //expectedOutputTypes.put("moreOutputs", String.class);

        Map<String, Object> outputs = ActivityInvoker.invokeAsyncActivity(
                activity, inputs, expectedOutputTypes);

        assertEquals("Unexpected outputs", 1, outputs.size());
        //assertEquals("simple", outputs.get("simpleOutput"));
        String result = outputs.get(StiltsActivity.OUTPUT_TABLE_PARAMETER_NAME).toString();
        System.out.println(result);
        assertEquals("Unexpected outputs", expected, result);
    }
}
