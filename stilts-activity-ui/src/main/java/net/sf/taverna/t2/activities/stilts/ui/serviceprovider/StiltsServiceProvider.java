package net.sf.taverna.t2.activities.stilts.ui.serviceprovider;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import net.sf.taverna.t2.activities.stilts.input.*;
import net.sf.taverna.t2.activities.stilts.preprocess.*;
import net.sf.taverna.t2.activities.stilts.process.*;
import net.sf.taverna.t2.activities.stilts.utils.*;

import net.sf.taverna.t2.servicedescriptions.ServiceDescription;
import net.sf.taverna.t2.servicedescriptions.ServiceDescriptionProvider;

public class StiltsServiceProvider implements ServiceDescriptionProvider {
	
    /**
      * Do the actual search for services. Return using the callBack parameter.
      */
    @SuppressWarnings("unchecked")
    public void findServiceDescriptionsAsync(FindServiceDescriptionsCallBack callBack) {
        // Use callback.status() for long-running searches
        // callBack.status("Resolving example services");

        List<StiltsInputType> typesOfInputsEnums = new ArrayList<StiltsInputType>();
        typesOfInputsEnums.add(StiltsInputType.FILE);
        typesOfInputsEnums.add(StiltsInputType.STRING);
        List<StiltsInputFormat> formatsOfInputsEnums = new ArrayList<StiltsInputFormat>();
        formatsOfInputsEnums.add(StiltsInputFormat.TST);
        formatsOfInputsEnums.add(StiltsInputFormat.CSV);

        List<ServiceDescription> results = new ArrayList<ServiceDescription>();

        SingleInputBean singleInputBean = new SingleInputBean(StiltsInputFormat.TST, StiltsInputType.FILE);
        TPipeBean tPipeBean = new TPipeBean(singleInputBean);
        StiltsServiceDesc tPipeDescription = new StiltsServiceDesc(tPipeBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Copy to new Format");
 	// Optional: set description
	//tPipeDescription.setDescription("Copy to new Format");
	results.add(tPipeDescription);

        MultipleFormatsBean flexibleInputBean = new FlexibleInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        TCatNBean tCatNBean = new TCatNBean(flexibleInputBean);
        StiltsServiceDesc tCatNDescription = new StiltsServiceDesc(tCatNBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Concatenate various Files diffferent formats");
	// Optional: set description
	//tCatNDescription.setDescription("Concatenate various Files");
	results.add(tCatNDescription);

        SingleFormatMultipleInputsBean singleFormatMultipleInputsBean = 
                new SingleFormatMultipleInputsBean(typesOfInputsEnums, StiltsInputFormat.CSV);
        TCatBean tCatBean = new TCatBean(singleFormatMultipleInputsBean);
        StiltsServiceDesc tCatDescription = new StiltsServiceDesc(tCatBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Concatenate various Files same format");
        results.add(tCatDescription);
        
        TJoinBean tJoinBean = new TJoinBean(flexibleInputBean);
        StiltsServiceDesc tJoinDescription = new StiltsServiceDesc(tJoinBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Join various files different formats");
        results.add(tJoinDescription);
        
        TwoInputsBean twoInputBean = new TwoInputsBean(typesOfInputsEnums, formatsOfInputsEnums);
        ExactMatchBean exactMatchBean = new ExactMatchBean(1, StiltsFind.ALL, StiltsFixcols.DUPS, StiltsJoin.ONE_AND_TWO, twoInputBean);
        StiltsServiceDesc exactMatchDescription = new StiltsServiceDesc(exactMatchBean, StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Exact Macth two or more files");
        results.add(exactMatchDescription);

        UserSpecifiedPreProcessorBean userSpecifiedPreProcessorBean = new UserSpecifiedPreProcessorBean("delcols 1");
        StiltsServiceDesc userSpecifiedPreProcessorDescription =
                new StiltsServiceDesc(userSpecifiedPreProcessorBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Generic PreProcessor");
 	results.add(userSpecifiedPreProcessorDescription);

        DeleteColumnPreProcessorBean deleteColumnPreProcessorBean = new DeleteColumnPreProcessorBean("1");
        StiltsServiceDesc deleteColumnPreProcessorDescription =
                new StiltsServiceDesc(deleteColumnPreProcessorBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Delete Column");
 	results.add(deleteColumnPreProcessorDescription);

        AddColumnByCommandPreProcessorBean  addColumnByCommandBean = 
                new AddColumnByCommandPreProcessorBean("$1 + $3", "newCol", StiltsLocationType.AFTER, "$2");
        StiltsServiceDesc addColumnByCommandDescription =
                new StiltsServiceDesc(addColumnByCommandBean, tPipeBean, 
                        StiltsOutputFormat.CSV, StiltsOutputType.STRING, false, "Configurable Add Column");
 	results.add(addColumnByCommandDescription);

        // partialResults() can also be called several times from inside
        // for-loop if the full search takes a long time
        callBack.partialResults(results);

        // No more results will be coming
        callBack.finished();
    }

    /**
      * Icon for service provider
      */
    public Icon getIcon() {
        return null;
    }

    /**
      * Name of service provider, appears in right click for 'Remove service provider'
     */
    public String getName() {
        return "MGrid based on AstroTaverna";
    }
	
    @Override
    public String toString() {
        return getName();
    }

}
