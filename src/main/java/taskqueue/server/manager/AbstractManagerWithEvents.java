package taskqueue.server.manager;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.jetty.util.component.LifeCycle;

import taskqueue.server.events.AbstractEventDispatcher;

public abstract class AbstractManagerWithEvents extends AbstractEventDispatcher implements LifeCycle {

	protected int status =0;
	protected Throwable lastException;
	protected Thread eventThread;
	protected Dictionary<Class<?>,LifeCycle.Listener> lifeCycleListeners = new Hashtable<Class<?>,LifeCycle.Listener>();
	
	public enum LifeCycleEvent {STARTING, STARTED, STOPPING, STOPPED, FAILURE}	
	
	public abstract void doStart() throws Exception;
	public abstract void doStop() throws Exception;
	
	@Override
	public void setFailed(Throwable t) {
		this.lastException = t;
		this.status |= 8; // Setting fail bit 
		this.status &= ~2;  // Switch off running bit
		this.notifyListeners(LifeCycleEvent.FAILURE, t);
	}
	
	public void run() {
		this.status |= 2;
		super.run();
	}
	
	public void start() throws Exception {
		this.addLifeCycleListener(new ManagerLifeCycleListener(this.getClass().getName()));
		eventThread = new Thread(this);
		eventThread.setName("Events-"+this.getClass().toString());
		this.status = 1;
		this.notifyListeners(AbstractManagerWithEvents.LifeCycleEvent.STARTING,null);
		this.eventThread.start();
		doStart();
		if(this.eventThread.isAlive()) {
			this.notifyListeners(AbstractManagerWithEvents.LifeCycleEvent.STARTED, null);
		}
	}
	
	
	protected void notifyListeners(AbstractManagerWithEvents.LifeCycleEvent e, Throwable t) {
		// notify listeners
		Enumeration<LifeCycle.Listener> listeners = this.lifeCycleListeners.elements(); 
		while(listeners.hasMoreElements()) {
			switch(e) {
			case STARTING:
				listeners.nextElement().lifeCycleStarting(this);
				break;
			case STARTED:
				listeners.nextElement().lifeCycleStarted(this);
				break;
			case STOPPING:
				listeners.nextElement().lifeCycleStopping(this);
				break;
			case STOPPED:
				listeners.nextElement().lifeCycleStopped(this);
				break;				
			case FAILURE:
				listeners.nextElement().lifeCycleFailure(this,t);
				break;								
			}
		}
		
	}
	
	public void stop() throws Exception {
		status |= 4;
		this.notifyListeners(LifeCycleEvent.STOPPING, null);
		doStop();
		synchronized (this) {
			this.status &= ~3; // Setting started and running bits  
			this.notifyAll(); // Runs all events by their listener's if exists  
			Thread.sleep(3000);
			
			// Kill thread if it had not ended
			if(this.eventThread.isAlive()) {
				this.eventThread.interrupt();
			}
			
		}
		this.notifyListeners(LifeCycleEvent.STOPPED, null);
	}

	public boolean isRunning() {
		return (status & 3) == 3;
	}

	public boolean isStarted() {
		return (status & 2) == 2;
	}

	public boolean isStarting() {
		return (status & 1) == 1;
	}

	public boolean isStopping() {
		return (status & 7) > 4;
	}

	public boolean isStopped() {
		return (status & 7) == 4 || (status & 7) == 0;
	}

	public boolean isFailed() {
		return (status & 8) == 8;
	}
	
	public void addLifeCycleListener(Listener listener) {
		this.lifeCycleListeners.put(listener.getClass(), listener);
	}

	public void removeLifeCycleListener(Listener listener) {
		this.lifeCycleListeners.remove(listener.getClass());
	}

}
