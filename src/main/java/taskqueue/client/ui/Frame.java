package taskqueue.client.ui;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.File;


import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.*;
import javax.swing.JTabbedPane;
import javax.swing.DefaultListModel;


import taskqueue.client.RunClient;



public class Frame extends AbstractClientFrame {
	int x = 0;
	int y = 0;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panelFiles;
	JPanel panelTasks;
	JPanel panelWorks;
	JList<String> taskList;
	
	
	JTabbedPane tabbedPane;
	public void initFrame() {
		
		
		
		
		
		JPanel panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				// setBackground(Color.YELLOW);
				// g.setColor( Color.yellow);
				//g.fillRoundRect(x, y, 5, 5, 5, 5);
			}
		};
		panelFiles = new JPanel();
		panelTasks = new JPanel();
		panelWorks = new JPanel();
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 300, 500, 60);
		tabbedPane.add("Init", panel);
		tabbedPane.add("Files", panelFiles);
		tabbedPane.add("Tasks", panelTasks);
		tabbedPane.add("Works", panelWorks);
		
		add(tabbedPane);
		fpicker = new JFilePicker("Pick a task", "Load");
		fpicker.setMode(JFilePicker.MODE_OPEN);
		uploadButton = new JButton("Unpload");
		uploadButton.setLocation(10, 10);
		uploadButton.setVisible(true);
		uploadButton.setEnabled(true);
		uploadButton.setMinimumSize(new Dimension(40, 20));
		
		signinTextField = new JTextField();
		signinTextField.setText("Enter id here");
		signinTextField.setColumns(28);
		signinTextField.setMinimumSize(new Dimension(1300,signinTextField.getMinimumSize().height));
		
		signInButton = new JButton();
		signInButton.setText("Log in");
		signUpButton = new JButton();
		signUpButton.setText("Register");
		
		upDateButton = new JButton();
		upDateButton.setText("Update");
		panelFiles.add(upDateButton);
		
		panel.add(signinTextField);
		panel.add(signInButton);
		panel.add(signUpButton);
		
		
	
		
		/*final JFileChooser fchooser = new JFileChooser();

		JButton startTask2 = new JButton("Start task 2");
		startTask2.setLocation(10, 40);
		startTask2.setVisible(true);
		startTask2.setEnabled(true);
		startTask2.setMinimumSize(new Dimension(40, 20));*/
		
		final DefaultListModel<String> listM = new DefaultListModel<String>();
		taskList = new JList<String>(listM);
		panelFiles.add(taskList);
				
		JLabel exQueue = new JLabel();
		exQueue.setBackground(Color.CYAN);
		JLabel waitQueue = new JLabel();
		exQueue.setVisible(true);

		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setVisible(true);
		panel.setEnabled(true);

		setMinimumSize(new Dimension(500, 500));
		setPreferredSize(new Dimension(1000, 800));
		panel.setMinimumSize(new Dimension(500, 500));
		panel.add(fpicker);
		panel.add(uploadButton);
		//panel.add(startTask2);
		

		panel.add(exQueue);
		panel.add(waitQueue);
		
		panel.setBackground(Color.YELLOW);
		 x = 50;
		 y = 50;
		 
		uploadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				JFileChooser fchooser = fpicker.getFileChooser();
				final File f = fchooser.getSelectedFile();
				listM.addElement(f.getAbsolutePath());
				repaint();
			}
		});
		
		
		//panelFiles.add(list);
		/*startTask2.addActionListener(new ActionListener() {
		*/

		panel.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				/*
				 * try { dos.writeInt(x); dos.writeInt(y); } catch (java.io.IOException ex) {
				 * ex.printStackTrace(); }
				 */

				repaint();
			}

			public void mouseReleased(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}
		});

		//add(panel);
		
	}

//	public void dispose() {
//		Window[] windows = this.getWindows();
//		for(int i=0;i<windows.length;i++) {
//			windows[i].dispose();
//		}
//	}
}
