package taskqueue.server.handlers;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import taskqueue.server.client.Client;
import taskqueue.server.manager.ClientFile;
import taskqueue.server.manager.ClientFolder;
import taskqueue.server.manager.FileManager;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class UploadHandler extends AbstractHandler {
	
	protected Logger logger;
	
	public UploadHandler() {
		super();
		logger = Log.getLogger(this.getClass());
	}
	
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Client client = this.getClient(request);

		if(!(client instanceof Client)) {
			this.respondHTTP400(response,"Unauthorized");
			baseRequest.setHandled(true);
			return;
		}
			
		try {
			
			ClientFolder clientfolder = this.getServer().getBean(FileManager.class).getClientFolder(client);
			JSONArray answer = new JSONArray();
			JSONObject fileanswer;
			if(request.getMethod()=="PUT") {
				
				
				String newFileName = request.getHeader("Filename");
				
				if(org.eclipse.jetty.util.StringUtil.isEmpty(newFileName)) {
					throw new Exception("No file name");
				}
				
				
				ClientFile clientfile = clientfolder.putFile(request.getInputStream(), newFileName);
				
				fileanswer = new JSONObject();
				
				fileanswer.put("FileID", clientfile.getUuid().toString());
				fileanswer.put("FileName",clientfile.getFileName());
				answer.add(fileanswer);
				

			}
			
			if(request.getMethod()=="GET") {
				
				Enumeration<String> files = clientfolder.getClientFiles();
				
				while(files.hasMoreElements()) {
					ClientFile clientfile = clientfolder.getClientFile(files.nextElement());
					fileanswer = new JSONObject();
					fileanswer.put("FileID", clientfile.getUuid().toString());
					fileanswer.put("FileName",clientfile.getFileName());
					answer.add(fileanswer);
				}
				
			}
			
			this.respondJSON(answer, response);
			baseRequest.setHandled(true);
			return;			
			
		} catch (Exception e) {
			this.respondHTTP400(response, "Upload error");
			baseRequest.setHandled(true);
			logger.info(String.format(" File uploading error (%s) type %s", e.getMessage(),e.getClass().toString()));
			return;
		}
	}

}
