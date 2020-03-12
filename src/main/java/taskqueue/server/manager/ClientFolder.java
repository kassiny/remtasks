package taskqueue.server.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;
import org.eclipse.jetty.util.IO;

public class ClientFolder {

	protected String clientID;
	protected String folder;
	protected FileManager filemanager;
	protected Dictionary<String,ClientFile> clientFiles;
	
	public ClientFolder(FileManager filemanager,String folder){
		this.folder = folder;
		this.filemanager = filemanager;
		this.clientFiles = new Hashtable<String,ClientFile>();
	}
	
	public String getClientID() {
		return clientID;
	}
	
	public String getFolder() {
		return folder;
	}
	
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	public ClientFile putFile(InputStream is, String filename) throws Exception {
		ClientFile file = this.filemanager.putFile(is, this.folder + File.separatorChar + filename);
		file.setFileName(filename);
		String filetype = filename.substring(filename.length() - (new StringBuilder(filename)).reverse().indexOf("."));
		file.setFileType(filetype);
		file.setClientID(UUID.fromString(clientID));
		this.clientFiles.put(filename, file);
		return file;
	}
	
	public  Enumeration<String> getClientFiles(){
		return this.clientFiles.keys();
	}
	
	public ClientFile getClientFile(String filename) {
		return this.clientFiles.get(filename);
	}
	
}
