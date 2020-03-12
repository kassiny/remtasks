package taskqueue.server.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

import org.eclipse.jetty.server.Request;
import taskqueue.server.Server;
import taskqueue.server.manager.ClientManager;

public class RegisterClient extends AbstractHandler{

	@Override
	public void handle(String target,
			Request baseRequest,
			HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
		
		//String clientId = server.registerClient();
		String clientId = ((ClientManager) this.getServer().getBean(ClientManager.class)).registerClient();
	
		response.setContentType("application/json");
		JSONArray answer = new JSONArray();
		answer.add(clientId);
		this.respondJSON(answer, response);
		
		baseRequest.setHandled(true);
	}
	

	
	
}
