package taskqueue.server.tasks;

public interface WorkDoneListener {

	public void workDone(AbstractWork work);
	
}
