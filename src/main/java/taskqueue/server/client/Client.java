package taskqueue.server.client;

import java.util.LinkedList;
import java.util.Queue;

import taskqueue.server.TaskCollection;
import taskqueue.server.tasks.AbstractWork;

public class Client extends AbstractClient{ 


	protected boolean admin = false;
	
	
	
	public Client(java.util.UUID uuid) {
		super(uuid);
	}
	
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void flyUpToGod() {
		this.admin = true;
	}
	
}
