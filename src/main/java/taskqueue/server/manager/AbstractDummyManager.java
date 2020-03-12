package taskqueue.server.manager;

import org.eclipse.jetty.util.component.LifeCycle;

public abstract class AbstractDummyManager implements LifeCycle {

	protected int status =0;
 
	
	public abstract void doStart() throws Exception;
	
	
	public void start() throws Exception {
		this.status = 1;
		doStart();
	}
	
	public void stop() throws Exception {
		status |= 4;
	}

	public boolean isRunning() {
		return (status & 3) == 3;
	}

	public boolean isStarted() {
		// TODO Auto-generated method stub
		return (status & 2) == 2;
	}

	public boolean isStarting() {
		// TODO Auto-generated method stub
		return (status & 1) == 1;
	}

	public boolean isStopping() {
		// TODO Auto-generated method stub
		return (status & 7) > 4;
	}

	public boolean isStopped() {
		// TODO Auto-generated method stub
		return (status & 7) == 4 || (status & 7) == 0;
	}

	public boolean isFailed() {
		// TODO Auto-generated method stub
		return (status & 8) == 8;
	}

	public void addLifeCycleListener(Listener listener) {
	
		
	}

	public void removeLifeCycleListener(Listener listener) {
		
		
	}

}
