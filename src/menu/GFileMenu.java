package menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frame.GPanel;
import main.GConstants.EFileMenuItem1;
import shapeTools.GShapeTool;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components
	private File Directory;
	private File file;
	double paperWidth = 8.26;
	double paperHeight = 11.69;
	double leftMargin = 1.50;
	double rightMargin = 0.50;
	double topMargin = 0.50;
	double bottomMargin = 1.00;
	private Image img = null;
	// associations
	private GPanel panel;

	public GFileMenu(String text) {

		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenuItem1 eFileMenuItem : EFileMenuItem1.values()) {
			JMenuItem menuItem = new JMenuItem(eFileMenuItem.getText());
			menuItem.setActionCommand(eFileMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}

		this.file = null;
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;

	}

	private void openFile() {
		JFileChooser fileChooser = new JFileChooser(Directory);
		fileChooser.showOpenDialog(panel);

		this.Directory = fileChooser.getCurrentDirectory();
		this.file = fileChooser.getSelectedFile();

		try {
			if (file != null) {
				ObjectInputStream objectInputStream = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(file)));

				Vector<GShapeTool> shapes;
				try {

					shapes = (Vector<GShapeTool>) objectInputStream.readObject();
					panel.setShapes(shapes);
					this.opencheck();
					objectInputStream.close();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void opencheck() {

		try {
			if (file != null) {
				ObjectInputStream objectInputStream = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(file)));
				Vector<GShapeTool> data;
				panel.repaint();
				try {
					data = (Vector<GShapeTool>) objectInputStream.readObject();
					panel.setShapes(data);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				objectInputStream.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveFile() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(this.file)));
			objectOutputStream.writeObject(this.panel.getShapes());
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeFile() {

		try {
			if (file != null) {
				// file에 null일때 조건
				ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(file)));

				objectOutputStream1.writeObject(panel.getShapes());
				objectOutputStream1.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean checkSaveOrNot() {
		boolean bCancel = true;

		if (this.panel.isModified()) {
			int reply = JOptionPane.showConfirmDialog(this.panel, "변경내용을 저장 할까요?");
			if (reply == JOptionPane.OK_OPTION) {
				this.save();
				bCancel = false;
			} else if (reply == JOptionPane.NO_OPTION) {
				this.panel.setModified(false);
				bCancel = false;
			} else if (reply == JOptionPane.CANCEL_OPTION) {

			} else {
				bCancel = false;
			}
		}
		return bCancel;
	}

	private void nnew() {
		if (!checkSaveOrNot()) {
			this.panel.clearScreen();
			this.file = null;
		}
	}

	public void save() {
		if (this.panel.isModified()) {
			if (this.file == null) {
				this.saveAs();
			} else {
				this.saveFile();
			}
		}
	}

	private void saveAs() {
		// save
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(this.panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
			this.saveFile();
		}
	}

	public void exitProgram() {
		if (!checkSaveOrNot()) {// false면
			System.exit(0);
		} else {

		}
	}

	private void printPanel() {

		PrinterJob printjob = PrinterJob.getPrinterJob();
		Paper paper = new Paper();
		printjob.setPrintable(new Printable() {

			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				Graphics2D g2d;
				if (pageIndex == 0) {
					g2d = (Graphics2D) graphics;
					g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

					g2d.scale(0.75, 0.75);
					panel.paint(g2d);

					return (PAGE_EXISTS);
				} else {
					return (NO_SUCH_PAGE);
				}
			}
		});
		paper.setSize(paperWidth * 72.0, paperHeight * 72.0);
		paper.setImageableArea(leftMargin * 72.0, topMargin * 72.0, (paperWidth - leftMargin - rightMargin) * 72.0,
				(paperHeight - topMargin - bottomMargin) * 72.0);

		PageFormat pageformat = new PageFormat();
		pageformat.setPaper(paper);
		pageformat.setOrientation(PageFormat.LANDSCAPE);

		PrintRequestAttributeSet prtRequestAttrSet = new HashPrintRequestAttributeSet();
		if (printjob.printDialog(prtRequestAttrSet)) { 
			try {
				printjob.print();
			} catch (Exception PrintException) {
				PrintException.printStackTrace();
			}
		}
	}

	
	
// Mainframe 에서 close button 으로 종료 시 저장 확인 구현
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenuItem1 eMenuItem = EFileMenuItem1.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eNew:
				nnew();
				break;
			case eOpen:
				openFile();
				break;
			case eSave:
				save();
				break;
			case eSaveAs:
				saveAs();
				break;
			case ePrint:
				printPanel();
				break;
			case eScreen:
				panel.ScreenShot();
			
				break;
			case eExit:
				exitProgram();
				break;
			default:
				break;
			}
		}

	}
}
