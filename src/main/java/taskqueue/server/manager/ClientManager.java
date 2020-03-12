package taskqueue.server.manager;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;

import taskqueue.server.client.Client;
import taskqueue.server.events.ClientRegisteredEvent;

public class ClientManager extends AbstractManagerWithEvents {
	
	protected Dictionary<UUID,Client> clients;
	
	public ClientManager() {
		this.clients = new Hashtable<UUID,Client>();
	}
	
	public String registerClient() {
		UUID uuid = UUID.randomUUID();
		Client client = new Client(uuid);
		this.clients.put(uuid, client);
		this.fireEvent(new ClientRegisteredEvent(client));
		return uuid.toString();
	}
	
	
	public Client getClient(String strUUID) {
		UUID uuid = UUID.fromString(strUUID);
		Client client = this.clients.get(uuid);
		return client;
	}
	
	public Enumeration<UUID> getAllClients(){
		return this.clients.keys();
	}

	@Override
	public void doStart()  throws Exception {

	}

	@Override
	public void doStop() throws Exception {
		return;
	}

	
	
	
}
