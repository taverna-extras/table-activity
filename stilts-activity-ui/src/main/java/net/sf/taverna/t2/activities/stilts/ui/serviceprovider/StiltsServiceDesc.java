package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;
import net.sf.taverna.t2.activities.stilts.process.StiltsProcessBean;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.workflowmodel.processor.activity.Activity;

import net.sf.taverna.t2.activities.stilts.StiltsActivity;
import net.sf.taverna.t2.activities.stilts.StiltsBean;
import net.sf.taverna.t2.activities.stilts.StiltsInterface;
import net.sf.taverna.t2.activities.stilts.preprocess.StiltsPreProcessBean;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputFormat;
import net.sf.taverna.t2.activities.stilts.utils.StiltsOutputType;

public class StiltsServiceDesc extends ServiceDescription<StiltsBean> 
        implements StiltsInterface{

    private StiltsProcessBean process;
    private StiltsPreProcessBean preprocess;
    private StiltsOutputFormat outputFormatEnum;
    private StiltsOutputType outputTypeEnum;    
    private boolean debugMode;
    private String name;
    
    public StiltsServiceDesc(StiltsPreProcessBean prepocessBean, StiltsProcessBean process, StiltsOutputFormat outputFormatEnum, 
            StiltsOutputType outputTypeEnum, boolean debugMode, String name){
       this.process =  process;
       this.preprocess = prepocessBean;
       this.outputFormatEnum = outputFormatEnum;
       this.outputTypeEnum = outputTypeEnum;
       this.debugMode = debugMode;
       this.name = name;
    }
    
    public StiltsServiceDesc(StiltsProcessBean process, StiltsOutputFormat outputFormatEnum, 
            StiltsOutputType outputTypeEnum, boolean debugMode, String name){
        this(null, process, outputFormatEnum, outputTypeEnum, debugMode, name);
    }
    
    @Override
    public StiltsProcessBean getProcess() {
        return process;
    }

    @Override
    public StiltsPreProcessBean getPreprocess() {
        return preprocess;
    }

    @Override
    public StiltsOutputFormat getOutputFormat() {
        return outputFormatEnum;
    }

    @Override
    public StiltsOutputType getOutputType() {
        return outputTypeEnum;
    }

    @Override
    public boolean isDebugMode() {
       return debugMode;
    }

    @Override
    public Class<? extends Activity<StiltsBean>> getActivityClass() {
        return StiltsActivity.class;
    }

    @Override
    public StiltsBean getActivityConfiguration() {
        return new StiltsBean(this);
    }

    @Override
    public Icon getIcon() {
       // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<? extends Comparable> getPath() {
        return Arrays.asList("Stilts Tools");
    }

    @Override
    protected List<? extends Object> getIdentifyingData() {
        return Arrays.<Object>asList(name, process.toString());
    }

}
