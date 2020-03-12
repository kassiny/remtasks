package taskqueue.server.events;

import taskqueue.server.client.AbstractClient;

public class ClientRegisteredEvent implements Event {
	protected AbstractClient client;
	
	public ClientRegisteredEvent(AbstractClient client) {
		this.client = client;
	}
	
	public AbstractClient getClient() {
		return this.client;
	}
}
