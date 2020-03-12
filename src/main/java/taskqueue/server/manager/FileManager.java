package taskqueue.server.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.UUID;

import taskqueue.server.client.AbstractClient;
import taskqueue.server.tasks.ManagedFile;

public class FileManager extends AbstractDummyManager {
	
	protected Dictionary<UUID,ClientFile> clientFile;
	protected String folderName;
	protected Throwable error;
	protected Dictionary<UUID,ClientFolder> clientFolders;
	
	
	public FileManager(String folderName) {
		this.folderName = folderName;
		this.clientFolders = new Hashtable<UUID,ClientFolder>();
	}
	
	@Override
	public void doStart() throws Exception {
		clientFile = new Hashtable<UUID, ClientFile>();
		try {
				File rootFolder = new File(folderName);
				if(!rootFolder.exists()) {
					rootFolder.mkdir();
				}
				this.folderName =  rootFolder.getAbsolutePath();
		} catch(Exception t) {
			this.setFailed(t);
			return;
		}
		status |= 2;
	}
	
	public void createClientPersonalFolder(AbstractClient client) throws Exception {
		
		UUID clientID = client.getId();
		
		File fc = new File(folderName + 
				File.separatorChar + 
				clientID.toString());
		if(fc.exists()) {
			fc.delete();
		}
		fc.mkdir();
		
		ClientFolder cf = new ClientFolder(this,fc.getAbsolutePath());
		cf.setClientID(clientID.toString());
		this.clientFolders.put(clientID,cf);
	}
	
	public ClientFolder getClientFolder(AbstractClient client) {
		return this.clientFolders.get(client.getId());
	}
	
	public void setFailed(Throwable t) {
		status |= 8;
		status &= ~(7);
		this.error = t;
	}
	
	
	public ClientFile putFile(InputStream is, String filename) throws Exception {
		File target = new File(filename);
		
		if(target.isDirectory()) {
			throw new Exception("Client folder should not contains any folders");
		}
		if(target.exists()) {
			target.delete();
		}
		
		
		OutputStream os = new FileOutputStream(target);
		org.eclipse.jetty.util.IO.copy(is, os);
		os.close();
		
		UUID fileUUID = UUID.randomUUID();
		ClientFile newfile = new ClientFile(this);
		newfile.setUuid(fileUUID);
		this.clientFile.put(fileUUID, newfile);
		return newfile;
	}
	
	
	
}