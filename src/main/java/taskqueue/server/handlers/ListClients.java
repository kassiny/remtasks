package taskqueue.server.handlers;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import org.json.simple.JSONArray;

import taskqueue.server.Server;
import taskqueue.server.client.Client;
import taskqueue.server.manager.ClientManager;


public class ListClients extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		
		
		Client client = this.getClient(request);
		
		if(!(client instanceof Client) || !client.isAdmin()) {
			this.respondHTTP400(response,"Admin only");
			baseRequest.setHandled(true);
			return;			
		}
		
		JSONArray answer = new JSONArray();
		
		answer.addAll(Collections.list(
				((ClientManager) this.getServer().getBean(ClientManager.class)).getAllClients())
			);
		
		this.respondJSON(answer, response);
		
		baseRequest.setHandled(true);
		
	}
	

}
