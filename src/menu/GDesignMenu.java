package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frame.GPanel;
import main.GConstants;
import main.GConstants.EDesignItem1;

public class GDesignMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components
	// associations
	private GPanel panel;
    private GConstants constansts;
	public GDesignMenu(String text) {

		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EDesignItem1 eEditMenuItem : EDesignItem1.values()) {
			JMenuItem menuItem = new JMenuItem(eEditMenuItem.getText());
			menuItem.setActionCommand(eEditMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;

	}

	@SuppressWarnings("static-access")
	public void anchors() {

		Scanner sc = new Scanner(System.in);
		int i = sc.nextInt();
		constansts.hAnchor = i;
		constansts.wAnchor = i;
		panel.repaint();
		sc.close();
	}
	public void BackColor() {
		JColorChooser jColorChooser = new JColorChooser();
		@SuppressWarnings("static-access")
		Color c = jColorChooser.showDialog(this.panel, "Background Color", Color.BLACK);
		if (c != null) {
			this.panel.setBackground(c);
		}
	}

	public void ShapeColor() {
		JColorChooser jColorChooser = new JColorChooser();
		@SuppressWarnings("static-access")
		Color c = jColorChooser.showDialog(this.panel, "Shape Color", Color.BLACK);
		if (c != null) {
			this.panel.ShapeColor(c);
		}
	}

	public void AnchorColor() {
		JColorChooser jColorChooser = new JColorChooser();
		@SuppressWarnings("static-access")
		Color c = jColorChooser.showDialog(this.panel, "Anchor Color", Color.BLACK);
		if (c != null) {
			this.panel.AnchorColor(c);
		}
	}

	public void LineColor() {
		JColorChooser jColorChooser = new JColorChooser();
		@SuppressWarnings("static-access")

		Color c = jColorChooser.showDialog(this.panel, "Line Color", Color.BLACK);
		if (c != null) {
			this.panel.LineColor(c);
		}
	}

	private class ActionHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			EDesignItem1 eMenuItem = EDesignItem1.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eAnchor:
				JOptionPane input =new JOptionPane("앵커두께 입력");
				String numberS = input.showInputDialog("앵커 두께 입력");
				if(numberS != null) {
				int number= Integer.parseInt(numberS);
				constansts.hAnchor = number;
				constansts.wAnchor = number;
				panel.repaint();
				}
				break;
			case eLine:
				JOptionPane in =new JOptionPane("선두께 입력");
				String numberL = in.showInputDialog("선 두께 입력");
				if(numberL != null) {
				int number1= Integer.parseInt(numberL);
				panel.lineW(number1);
				}
				break;
			case ebackgroundC:
				BackColor();
				panel.repaint();
				break;
			case eAnchorC:
				AnchorColor();
				panel.repaint();
				break;
			case eShapeC:
				ShapeColor();
				panel.repaint();
				break;
			case eLineC:
				LineColor();
				panel.repaint();
				break;
			default:
				break;
			}
		}
	}

}
