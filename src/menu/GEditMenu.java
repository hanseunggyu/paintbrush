package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.EEditMenuItem1;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components
	// associations
	private GPanel panel;

	@SuppressWarnings("deprecation")
	public GEditMenu(String text) {
	
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EEditMenuItem1 eEditMenuItem : EEditMenuItem1.values()) {
			JMenuItem menuItem = new JMenuItem(eEditMenuItem.getText());
			if(eEditMenuItem.getText() == "redo")
			menuItem.setAccelerator(KeyStroke.getKeyStroke('Y', InputEvent.CTRL_MASK));
			if(eEditMenuItem.getText() == "undo")
			menuItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK));
			if(eEditMenuItem.getText() == "잘라내기")
				menuItem.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
			if(eEditMenuItem.getText() == "복사")
				menuItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
			if(eEditMenuItem.getText() == "붙여넣기")
				menuItem.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
			if(eEditMenuItem.getText() == "삭제")
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
			menuItem.setActionCommand(eEditMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}	
	}
	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}


	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EEditMenuItem1 eMenuItem = EEditMenuItem1.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eRedo:
        	panel.Redo();
        	panel.repaint();
				break;
			case eUndo:
			panel.Undo();
			panel.repaint();
				break;
			case eFront:
				panel.Front();
				panel.repaint();
					break;
			case eBack:
				panel.Back();
				panel.repaint();
					break;
			case eCut:
				panel.Cut();
				panel.repaint();
					break;
			case ePaste:
				panel.Paste();
				panel.repaint();
					break;
			case eCopy:
				panel.Copy();
				panel.repaint();
					break;
			case eDelete:
				panel.Delete();
				panel.repaint();
					break;
			default:
				break;
			}
		}
	}

}
