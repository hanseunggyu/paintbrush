package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frame.GPanel;
import main.GConstants;
import main.GConstants.EDesignItem1;
import main.GConstants.ERotatingItem;

public class GRotatingMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components
	// associations
	private GPanel panel;
	public GRotatingMenu(String text) {

		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (ERotatingItem eRotatingItem : ERotatingItem.values()) {
			JMenuItem menuItem = new JMenuItem(eRotatingItem.getText());
			menuItem.setActionCommand(eRotatingItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;

	}


	private class ActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ERotatingItem rotatingItem = ERotatingItem.valueOf(e.getActionCommand());
			switch (rotatingItem) {
		
			case eRotateR90:
				panel.R90();
				panel.repaint();
				break;
			case eRotateL90:
				panel.L90();
				panel.repaint();
				break;
			case eRotate180:
				panel.R180();
				panel.repaint();
				break;
			default:
				break;
			}
		}
	}

}
