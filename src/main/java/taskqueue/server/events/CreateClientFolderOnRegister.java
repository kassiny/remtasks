package taskqueue.server.events;

import taskqueue.server.events.Listener;

import taskqueue.server.manager.FileManager;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class CreateClientFolderOnRegister implements Listener {

	protected FileManager fm;
	protected Logger logger;
	
	public CreateClientFolderOnRegister(FileManager fm) {
		this.fm = fm;
		this.logger = Log.getLogger(this.getClass());
	}

	public void processEvent(ClientRegisteredEvent e) throws Exception {
		this.logger.info(String.format("Creating client folder %s", e.getClient().getId().toString()));
		fm.createClientPersonalFolder(e.getClient());
	}

}
