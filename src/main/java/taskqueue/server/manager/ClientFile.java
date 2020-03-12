package taskqueue.server.manager;

import java.util.UUID;

public class ClientFile {
	
	 protected FileManager fm;
	 protected UUID uuid;
	 protected UUID clientID;
	 protected String fileName;
	 protected String fileType;
	 
	 ClientFile(FileManager fm){
		 this.fm = fm;
	 }

	public FileManager getFm() {
		return fm;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public UUID getClientID() {
		return clientID;
	}

	public void setClientID(UUID clientID) {
		this.clientID = clientID;
	}

}
