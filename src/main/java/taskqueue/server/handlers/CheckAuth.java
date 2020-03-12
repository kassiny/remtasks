package taskqueue.server.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import taskqueue.server.Server;
import taskqueue.server.client.Client;

import org.json.simple.JSONObject;;

public class CheckAuth extends AbstractHandler{

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		Client client = this.getClient(request);

		if(!(client instanceof Client)) {
			this.respondHTTP400(response,"Error");
			baseRequest.setHandled(true);
			return;
		}
		
		JSONObject answer = new JSONObject();
		answer.put("ClientID", client.getId().toString());
		answer.put("isAdmin",client.isAdmin());
		this.respondJSON(answer, response);
		baseRequest.setHandled(true);
	}

}
