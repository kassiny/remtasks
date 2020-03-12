package taskqueue.client.ui.proxy;

import javax.swing.JOptionPane;

import taskqueue.client.ui.AbstractClientFrame;

public class DefaultErrorProxy extends AbstractUIProxy {
	
	protected AbstractClientFrame frame;
	
	public DefaultErrorProxy(AbstractClientFrame frame) {
		this.frame = frame;
	}
	
	public void run() {
		JOptionPane.showMessageDialog(this.frame,
			    this.getResponseMapper().getError(),"Error message",JOptionPane.ERROR_MESSAGE);		
	}

}
