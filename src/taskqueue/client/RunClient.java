/**
 * 
 */
package taskqueue.client;


import javax.swing.JOptionPane;
import java.util.Deque;
import java.util.ArrayDeque;

import taskqueue.client.ui.Frame;
/**
 * @author BingoUser
 *
 */
public class RunClient {
	
	protected Frame frame;
	
	protected Deque<Runnable> runnables = new ArrayDeque<Runnable>();
	
	public RunClient(Frame frame) {
		this.frame = frame;
	
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		new RunClient(new Frame()).start();
	}

	private void start() throws InterruptedException {
		
		Frame localframe = this.frame;
		RunClient runner = this;
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() { 
        	 
            public void run() { 
  
            	localframe.initFrame();
            	localframe.setVisible(true);
            	localframe.setRunner(runner);
            	
             } 
  
         }); 
        
        synchronized(this) {
        	while(true) {
        		this.wait();
        		this.runRunner();
        	}
        }
	}
	
	protected void runRunner() {
		if(!runnables.isEmpty()) {
			runnables.poll().run();
		}
	}
	
	public void addRunner(Runnable runner) {
		runnables.add(runner);
		this.notify();
	}

}