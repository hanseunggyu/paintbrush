package frame;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import main.GConstants.CFrame;
import menu.GFileMenu;
import menu.GMenuBar;

public class GFrame extends JFrame{
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private GPanel panel;
	private GToolBar toolBar;
	private GMenuBar menuBar;
	private WindowsHandler windowsHandler;

	public GFrame() {
		// initialize attributes
		this.setLocation(CFrame.point);
		this.setSize(CFrame.dimesion);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.windowsHandler = new WindowsHandler();
		this.addWindowListener(this.windowsHandler);
		// initialize components
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);

		BorderLayout layoutManager = new BorderLayout();
		this.getContentPane().setLayout(layoutManager);

		this.toolBar = new GToolBar();
		this.getContentPane().add(this.toolBar, BorderLayout.NORTH);

		this.panel = new GPanel();

		this.getContentPane().add(this.panel, BorderLayout.CENTER);

		// set associations
		this.menuBar.setAssociation(this.panel);
		this.toolBar.setAssociation(this.panel);
	}

	public void window(boolean get) {
		boolean pass;
		pass = get;
		this.menuBar.window(true);
	}

	private class WindowsHandler implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {

		}

		@Override
		public void windowClosing(WindowEvent e) {
			window(true);  
		}

		@Override
		public void windowClosed(WindowEvent e) {

		}

		@Override
		public void windowIconified(WindowEvent e) {

		}

		@Override
		public void windowDeiconified(WindowEvent e) {

		}

		@Override
		public void windowActivated(WindowEvent e) {

		}

		@Override
		public void windowDeactivated(WindowEvent e) {

		}

	}

	public void initialize() {
		this.toolBar.initialize();
		this.panel.initialize();
	}
}
