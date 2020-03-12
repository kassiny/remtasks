package taskqueue.server.manager;

import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;


public class ManagerLifeCycleListener implements LifeCycle.Listener {

	protected Logger logger;
	
	public ManagerLifeCycleListener(String loggerName) {
		this.logger = Log.getLogger(loggerName);
	}
	
	public void lifeCycleStarting(LifeCycle event) {
		this.logger.info("Starting...");
	}

	public void lifeCycleStarted(LifeCycle event) {
		this.logger.info("Started");	
		
	}

	public void lifeCycleFailure(LifeCycle event, Throwable cause) {
		this.logger.info("Failure");
		
	}

	public void lifeCycleStopping(LifeCycle event) {
		this.logger.info("Stopping...");	
		
	}

	public void lifeCycleStopped(LifeCycle event) {
		// TODO Auto-generated method stub
		
	}

}
