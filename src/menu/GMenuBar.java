package menu;

import javax.swing.JMenuBar;

import frame.GPanel;
import main.GConstants.EMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GDesignMenu designMenu;
	private GRotatingMenu rotatingMenu;
	private GTextImage GtextImage;
	public GMenuBar() {
		this.fileMenu = new GFileMenu(EMenu.eFile.getText());
		this.add(this.fileMenu);
		this.editMenu = new GEditMenu(EMenu.eEdit.getText());
		this.add(this.editMenu);
		this.designMenu = new GDesignMenu(EMenu.eDesign.getText());
		this.add(this.designMenu);
		this.rotatingMenu = new GRotatingMenu(EMenu.eRotating.getText());
		this.add(this.rotatingMenu);
		this.GtextImage = new GTextImage(EMenu.eTextImg.getText());
		this.add(this.GtextImage);
	}

	public void setAssociation(GPanel panel) {
		this.fileMenu.setAssociation(panel);	
		this.editMenu.setAssociation(panel);	
		this.designMenu.setAssociation(panel);	
		this.rotatingMenu.setAssociation(panel);	
		this.GtextImage.setAssociation(panel);	
	}

	public void window(boolean b) {
		boolean pass;
		pass = b;
		this.fileMenu.exitProgram();
		
	}
	

}
