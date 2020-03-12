package taskqueue.server;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Enumeration;
import java.util.UUID;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.json.simple.JSONAware;

import simple.embedding.jetty.HelloWorld;
import taskqueue.server.client.Client;
import taskqueue.server.events.CreateClientFolderOnRegister;
import taskqueue.server.events.Listener;
import taskqueue.server.manager.ClientManager;
import taskqueue.server.manager.FileManager;

import org.eclipse.jetty.util.log.Log;

public class Server extends org.eclipse.jetty.server.Server {
	
	protected ClientCollection clientCollection;
	protected 
	
	Server(){
		super();
		this.clientCollection = new ClientCollection();
		
	}
	 
	public void initializeContexts() {
		
		ClientManager cm = new ClientManager();
		FileManager fm = new FileManager("clientData");
		
		taskqueue.server.events.Listener clientRegisteredListener = new CreateClientFolderOnRegister(fm);
		cm.addUserEventListener(clientRegisteredListener);	
		
		
		this.addBean(cm);
		this.addBean(fm);
		
		Client admin = cm.getClient(cm.registerClient());
		admin.flyUpToGod();
		Log.getLog().info("Admin id is "+admin.getId().toString());
		
		
		ContextHandlerCollection collection = new ContextHandlerCollection();
		
		this.createContext(collection,
				new HelloWorld("<h1> Hello world </h1>"),
				"/hello");
		
		this.createContext(collection,
				new taskqueue.server.handlers.RegisterClient(),
				"/register");
		
		this.createContext(collection,
				new taskqueue.server.handlers.ListClients(),
				"/listclients");
		
		this.createContext(collection,
				new taskqueue.server.handlers.CheckAuth(),
				"/checkauth");
		
		this.createContext(collection,
				new taskqueue.server.handlers.UploadHandler(),
				"/upload");
		
		this.setHandler(collection);
	}
	
	public ContextHandler createContext(ContextHandlerCollection collection, Handler handler, String uri) {
		ContextHandler context;
		context = new ContextHandler(uri);
		context.setHandler(handler);
		collection.addHandler(context);
		return context;
	}

}
