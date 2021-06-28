package menu;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.ETextImage;

public class GTextImage extends JMenu {

	// components
	// associations
	private GPanel panel;
	private File Directory;
	private Image img = null;
	private File file;
	public GTextImage(String text) {

		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (ETextImage eEditMenuItem : ETextImage.values()) {
			JMenuItem menuItem = new JMenuItem(eEditMenuItem.getText());
			if(eEditMenuItem.getText() == "이미지 Undo")
				menuItem.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_MASK));
			if(eEditMenuItem.getText() == "이미지 Redo")
				menuItem.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.CTRL_MASK));
			menuItem.setActionCommand(eEditMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;

	}
	private void Imageinsert() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(panel);

		this.Directory = fileChooser.getCurrentDirectory();
		this.file = fileChooser.getSelectedFile();
		try {
			if (file != null) {
			InputStream objectInputStream = new BufferedInputStream(new FileInputStream(file));
		
			try {
				img = ImageIO.read(file);
				panel.Imageinsert(img);
				
			} catch (IOException e) {
			}
			}
		} catch (FileNotFoundException e) {
		}
	}

	private class ActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ETextImage eMenuItem = ETextImage.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eImage:
				Imageinsert();
				break;
			case eImageRedo:
				panel.ImageRedo();
				break;
			case eText:
				panel.Textinsert();
				break;
			case eTextRemove:
				JOptionPane input =new JOptionPane("삭제할 텍스트 입력");
				String numberS = input.showInputDialog("삭제할 텍스트 입력");
				if(numberS != null) {
				panel.TextRemove(numberS);
				panel.repaint();
				}
				break;
			case eImageUndo:
				panel.ImageUndo();
				break;
			default:
				break;
			}
		}


	}
}
