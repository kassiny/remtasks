package taskqueue.server.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.json.simple.JSONAware;

import taskqueue.server.client.Client;
import taskqueue.server.manager.ClientManager;

public abstract class AbstractHandler extends org.eclipse.jetty.server.handler.AbstractHandler {

	
	protected taskqueue.server.Server getTaskQServer(){
		Server server = this.getServer();
		if(server instanceof taskqueue.server.Server) {
			return (taskqueue.server.Server) server;
		}
		return null;
	}
	
	
	protected Client getClient(HttpServletRequest request) {
		String clientID = request.getHeader("ClientID");
		if(org.eclipse.jetty.util.StringUtil.isEmpty(clientID)) {
			return null;
		}
		Client client = ((ClientManager) this.getServer().getBean(ClientManager.class)).getClient(clientID);
		return client;
	}
	
	public void respondHTTP400(HttpServletResponse response, String message) throws IOException {
        // Declare response status code
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.getWriter().println(message);
	}
	
	public void respondJSON(JSONAware answer, HttpServletResponse response) throws IOException {
	    // Declare response encoding and types
        response.setContentType("application/json; charset=utf-8");

        // Declare response status code
        response.setStatus(HttpServletResponse.SC_OK);
        
        response.getWriter().print(answer);
	}

}
