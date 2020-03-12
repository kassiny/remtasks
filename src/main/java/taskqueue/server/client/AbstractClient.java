package taskqueue.server.client;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

import org.eclipse.jetty.util.component.LifeCycle;

import taskqueue.server.tasks.AbstractWork;
import taskqueue.server.tasks.WorkDoneListener;

public abstract class AbstractClient implements Runnable, Consumer<WorkDoneListener> {

	
	protected java.util.UUID uuid;

	protected Queue<AbstractWork> works;
	protected List<WorkDoneListener> workDoneListeners;
	protected Throwable clientFailed;
	protected boolean running = false;
	protected AbstractWork currentWork;
	
	public AbstractClient(java.util.UUID uuid) {
		this.uuid = uuid;
		this.works = new LinkedList<AbstractWork>();
		this.workDoneListeners = new LinkedList<WorkDoneListener>();
	}
	
	
	public java.util.UUID getId() {
		return this.uuid;
	}


	public void run() {
//		try {
//			synchronized(this) {
//				while(isRunning()) { 
//					doWorks();
//					wait();
//				}
//			}
//		} catch (InterruptedException e) {
//			this.clientFailed = e;
//			this.running = false;
//		}
	}
	
	public void addWorkDoneListener(WorkDoneListener listener) {
		this.workDoneListeners.add(listener);
	}
	
	public synchronized void addWork(AbstractWork work) {
		
	}
	
	protected void doWorks() {
		
		if(!this.works.isEmpty()) {
			this.currentWork = works.poll();
			currentWork.run();
			this.workDoneListeners.forEach(this);
			return;
		}
		this.currentWork = null;
	}


	public void accept(WorkDoneListener listener) {
		listener.workDone(this.currentWork);
	}	
	
	
}
