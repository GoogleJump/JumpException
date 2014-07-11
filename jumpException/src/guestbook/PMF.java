package guestbook;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.annotations.FetchPlan;

public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
    	//The statement below allows for any depth of classes with objects in it (maxfetchdepth_
    	// setFetchSize allows for any sized recursive object (lists) to get all of there elements
    	
    	pmfInstance.getPersistenceManager().getFetchPlan().setMaxFetchDepth(-1).setFetchSize(javax.jdo.FetchPlan.FETCH_SIZE_GREEDY);
        return pmfInstance;
    }
}
