package frame;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.GConstants.EAction;
import main.GConstants.EAnchors;
import main.GConstants.EDrawingStyle;
import shapeTools.GShapeTool;
import transformer.GMover;
import transformer.GResize;
import transformer.GRotate;
import transformer.GRotate180;
import transformer.GRotateL90;
import transformer.GRotateR90;
import transformer.GTransformer;

public class GPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private Vector<GShapeTool> shapes;
	private Vector<Image> img;
	private Vector<Image> imgre;
	private Vector<Image> imgun;
	private Vector<JLabel> labelbox;
	private GMouseHandler mouseHandler;
	private Vector<Vector<GShapeTool>> undo;
	private Vector<Vector<GShapeTool>> redo;
	private Vector<GShapeTool> memo;
	private Vector<Integer> imagelocationX;
	private Vector<Integer> imagelocationY;
	private int x, y;
	private int insertx, inserty;
	private int insertX, insertY;
	private int Check = 0;

	private int set = 0;
	private int setImg = 0;
	// associations

	// working objects
	GShapeTool cut;

	private GShapeTool shapeTool;
	private GShapeTool selectedShape;
	private GTransformer transformer;
	private boolean bModified;

	///////////////////////////////////////////////////////
	// getters and setters

	public Vector<GShapeTool> getShapes() {
		return this.shapes;
	}

	public void setShapes(Vector<GShapeTool> shapes) {
		this.shapes = shapes;
		this.repaint();
	}

	public void setSelection(GShapeTool shapeTool) {
		this.shapeTool = shapeTool;
	}

	public boolean isModified() {
		return this.bModified;
	}

	public boolean setModified(boolean bModified) {
		return this.bModified = bModified;
	}

	// constructors
	public GPanel() {
		this.shapes = new Vector<GShapeTool>();
		this.imagelocationX = new Vector<Integer>();
		this.imagelocationY = new Vector<Integer>();

		this.img = new Vector<Image>();
		this.imgre = new Vector<Image>();
		this.imgun = new Vector<Image>();
		this.labelbox = new Vector<JLabel>();
		this.mouseHandler = new GMouseHandler();
		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.addMouseWheelListener(this.mouseHandler);

		this.redo = new Vector<Vector<GShapeTool>>();
		this.undo = new Vector<Vector<GShapeTool>>();
		this.memo = new Vector<GShapeTool>();
		this.bModified = false;
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);
		for (GShapeTool shape : this.shapes) { // GshapeTool벡터에 담긴 도형 그리는...
			shape.draw((Graphics2D) graphics);
		}
		for (int i = 0; i < this.img.size(); i++) {
			graphics.drawImage(this.img.get(i), imagelocationX.get(i), imagelocationY.get(i), null); // Image벡터에 담긴 도형
																										// 그리는...
		}

	}

	private void setSelected(GShapeTool selectedShape) {// 도형 선택 관리 메소드
		for (GShapeTool shape : this.shapes) {
			shape.setSelected(false);
		}
		if (selectedShape == null) {
			this.repaint();
		} else {
			this.selectedShape = selectedShape;
			this.selectedShape.setSelected(true);
			this.repaint();
		}
	}

	private GShapeTool onShape(int x, int y) { // on shape

		for (GShapeTool shape : this.shapes) {
			EAction eAction = shape.containes(x, y);
			if (eAction != null) {
				return shape;
			}
		}
		return null;
	}

	private void initDrawing(int x, int y) { // 도형그릴때 시작 메소드
		repaint();
		this.selectedShape = this.shapeTool.newInstance();
		this.selectedShape.setInitPoint(x, y);
	}

	private void setIntermediatePoint(int x, int y) { // PolyGon 점찍는 과정 처리 메소드
		this.selectedShape.setIntermediatePoint(x, y);
	}

	private void keepDrawing(int x, int y) {// e2Point 그릴때 keepDrawing 메소드

		// exclusive or mode
		Graphics2D graphics2D = (Graphics2D) getGraphics();
		graphics2D.setXORMode(getBackground());

		float dash4[] = { 10, 4f };
		graphics2D.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, dash4, 0));
		// erase
		selectedShape.animate(graphics2D, x, y);

	}

	private void finishDrawing(int x, int y) {// 도형 그리기 끝낼때 메소드

		Graphics2D graphics2D = (Graphics2D) getGraphics();
		graphics2D.setXORMode(getBackground());
		float dash4[] = { 10, 4f };
		graphics2D.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1, dash4, 0));
		this.selectedShape.draw(graphics2D);
		// 점선 지우고

		// 실선 그리기
		graphics2D.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, 0));
		this.selectedShape.draw(graphics2D);

		Vector<GShapeTool> cloning = new Vector<GShapeTool>();
		for (GShapeTool shape : this.shapes) {
			cloning.add(shape.clone());
		}
		// undo는 화면(vector안에 vector) 클로닝 저장
		this.undo.add(cloning);

		this.selectedShape.setFinalPoint(x, y);
		this.shapes.add(this.selectedShape);

		this.bModified = true;
		repaint();
	}

	/*
	 * 간편 Rotating 메소드
	 */
	public void R90() { // 우측 90도
		String var = "R90";
		initTransforming1(var);
	}

	public void L90() { // 좌측 90도
		String var = "L90";
		initTransforming1(var);
	}

	public void R180() { // 180도
		String var = "180";
		initTransforming1(var);
	}

	private void initTransforming1(String var) { // 간편 Rotating용 initTransform

		if (this.selectedShape != null) {
			if (var == "R90") {
				this.transformer = new GRotateR90(this.selectedShape);
			} else if (var == "L90") {
				this.transformer = new GRotateL90(this.selectedShape);
			} else if (var == "180") {
				this.transformer = new GRotate180(this.selectedShape);
			}

			Graphics2D graphics2d = (Graphics2D) this.getGraphics();
			graphics2d.setXORMode(this.getBackground());
			this.transformer.initTransforming(graphics2d, x, y);
			Vector<GShapeTool> cloning = new Vector<GShapeTool>();
			for (GShapeTool shape : this.shapes) {
				cloning.add(shape.clone());
			}

			// undo는 화면(vector안에 vector) 클로닝 저장
			this.undo.add(cloning);

		}
	}

	private void initTransforming(GShapeTool selectedShape, int x, int y) { // 도형변화 시작 메소드 initTransform
		this.selectedShape = selectedShape;
		EAction eAction = this.selectedShape.getAction();
		switch (eAction) {
		case eMove:
			this.transformer = new GMover(this.selectedShape);
			break;
		case eResize:
			this.transformer = new GResize(this.selectedShape);
			break;
		case eRotate:
			this.transformer = new GRotate(this.selectedShape);

			break;
		default:
			break;
		}
		this.set = 0;
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.initTransforming(graphics2d, x, y);
		Vector<GShapeTool> cloning = new Vector<GShapeTool>();
		for (GShapeTool shape : this.shapes) {
			cloning.add(shape.clone());
		}

		// undo는 화면(vector안에 vector) 클로닝 저장
		this.undo.add(cloning);
	}

	private void keepTransforming(int x, int y) { // 도형 변화중인 것 처리 메소드
		repaint();
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.keepTransforming(graphics2d, x, y);

	}

	private void finishTransforming(int x, int y) { // 도형변화 마무리 메소드
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.finishTransforming(graphics2d, x, y);

		this.bModified = true;
	}

	public void Imageinsert(Image img) { // FileMenu에서 이미지 받아오는 메소드

		this.insertX = insertx;
		this.insertY = inserty;
		this.imagelocationX.addElement(this.insertX);
		this.imagelocationY.addElement(this.insertY);
		this.img.add(img);
		Graphics graphics = (Graphics2D) this.getGraphics();
		// paint(graphics);
		for (int i = 0; i < this.img.size(); i++) {
			graphics.drawImage(this.img.get(i), imagelocationX.get(i), imagelocationY.get(i), null); // Image벡터에 담긴 도형

		}

	}
	public void ImageRedo() {
		if (!imgre.isEmpty() && setImg > 0) {
			setImg--;
		this.img.add(this.imgre.lastElement());
		this.imgre.remove(this.imgre.size()-1);
		Graphics graphics = (Graphics2D) this.getGraphics();
		// paint(graphics);
		for (int i = 0; i < this.img.size(); i++) {
			graphics.drawImage(this.img.get(i), imagelocationX.get(i), imagelocationY.get(i), null); // Image벡터에 담긴 도형

		}
		repaint();
		}
	}
	public void ImageUndo() {
		if (!img.isEmpty()) {
			setImg++;
		this.imgre.add(this.img.lastElement());
		
		this.img.removeElementAt(this.img.size()-1);
		repaint();
	}
	}

	public void Textinsert() {

		this.insertX = insertx;
		this.insertY = inserty;

		JOptionPane input = new JOptionPane("텍스트 입력");
		String numberS = input.showInputDialog("텍스트 입력");

		JLabel label = new JLabel(numberS);
		label.setBounds(insertX, insertY, 200, 30);
		labelbox.addElement(label);// 박스에 넣어줌

		///////////////////////////////////////////////////////////////////////////////

		for (int i = 0; i < this.labelbox.size(); i++) {
			this.remove(this.labelbox.get(i)); // 패널에 존재하는 라벨 다 지워줌
		}
		for (int i = 0; i < this.labelbox.size(); i++) {
			this.add(this.labelbox.get(i));
		}
		repaint();
	}

	public void TextRemove(String numberS) {
		for (int i = 0; i < this.labelbox.size(); i++) {
			this.remove(this.labelbox.get(i)); // 패널에 존재하는 라벨 다 지워줌
		}

		for (int i = 0; i < this.labelbox.size(); i++) {
			this.add(this.labelbox.get(i));
		}
		for (int i = 0; i < this.labelbox.size(); i++) {
			if (this.labelbox.get(i).getText().equals(numberS)) {
				this.remove(this.labelbox.get(i));
				this.labelbox.remove(i);
			}
		}

		repaint();
	}

	public void Redo() { // Redo Vector로 구현 // 교수님은 Stack 사용하심
		if (!redo.isEmpty() && set > 0) {
			set--;

			Vector<GShapeTool> cloning = new Vector<GShapeTool>();
			for (GShapeTool shape : this.shapes) {
				cloning.add((GShapeTool) shape.clone());
			}
			this.undo.add(cloning);

			Vector<GShapeTool> memo = new Vector<GShapeTool>();

			memo = redo.lastElement();
			this.redo.remove(redo.lastElement());
			this.shapes = memo;

		}
	}

	public void Undo() { // Undo Vector로 구현 // 교수님은 Stack 사용하심

		if (!undo.isEmpty()) {
			set++;
			redo.add(this.shapes);

			Vector<GShapeTool> memo1 = new Vector<GShapeTool>();
			memo1 = this.undo.lastElement();
			this.undo.remove(this.undo.lastElement());
			this.shapes = memo1;
		}
	}

	public void Front() { // 선택도형이 화면의 가장 앞에 튀어나와있게 해주는 메소드

		if (this.selectedShape != null) { // 선택 도형 있으면

			GShapeTool shape = this.selectedShape; // selectedShape 임시저장
			this.shapes.remove(this.selectedShape); // shapes벡터에서 지워줌
			this.shapes.add(shape); // shapes벡터의 가장 마지막에 추가해줌으로써 도형 앞에 나오게해줌
			// this.selectedShape = shape;

		}
	}

	public void Back() { // 선택도형이 화면의 가장 뒤에 들어가있게 해주는 메소드
		if (this.selectedShape != null) {
			GShapeTool memory = this.selectedShape; // 선택도형

			this.shapes.removeElement(this.selectedShape);
			Vector<GShapeTool> change = new Vector<GShapeTool>();
			for (int i = 0; i < this.shapes.size(); i++) { // 선택한 도형 제외 기존 벡터값들 모두 저장
				change.add(i, this.shapes.get(i));
			}
			this.shapes.removeAllElements();// 현재 화면의 벡터내부 값들 다 삭제

////////////////////////////////////////////////////////////////////////////////////////////////////////

			this.shapes.add(memory); // 벡터 처음에 맨 선택도형 저장

			for (int i = 0; i < change.size(); i++) { // 기존 벡터값들 모두 저장
				this.shapes.add(i + 1, change.get(i));
			}

//         this.selectedShape = this.shapes.get(0);

		}
	}

	public void Cut() { // 잘라내기 메소드
		if (this.selectedShape != null) {
			memo.add(selectedShape);
			this.shapes.removeElement(this.selectedShape);

		}
	}

	public void Paste() { // 붙여넣기 메소드
		if (memo.size() != 0) {
			if (memo.get(0) != null) {
				
				this.shapes.add(memo.lastElement());
			}
		}
	}

	public void Copy() { // 복사 메소드
		if (this.selectedShape != null) {

			for (GShapeTool shape : this.shapes) {
				if (this.selectedShape.getShape() == shape.clone().getShape()) {
					memo.add(shape.clone());
				}
			}
		}
	}

	public void Delete() { // 삭제 메소드
		if (this.selectedShape != null) {
			this.shapes.removeElement(this.selectedShape);
		}
	}

	public void AnchorColor(Color c) {// 앵커 색
		if (selectedShape != null) {
			Graphics2D graphics2D = (Graphics2D) getGraphics();
			selectedShape.AnchorColor(c);
			selectedShape.draw(graphics2D);
		}
	}

	public void ShapeColor(Color c) {// 도형 색
		if (selectedShape != null) {
			Graphics2D graphics2D = (Graphics2D) getGraphics();
			selectedShape.ShapeColor(c);
			selectedShape.draw(graphics2D);
		}
	}

	public void LineColor(Color c) {// 도형 테두리 색
		if (selectedShape != null) {
			Graphics2D graphics2D = (Graphics2D) getGraphics();
			selectedShape.LineColor(c);
			selectedShape.draw(graphics2D);
		}
	}

	public void lineW(int num) {// 도형 테두리 두께
		if (selectedShape != null) {
			Graphics2D graphics2D = (Graphics2D) getGraphics();
			selectedShape.LineWidth(num);
			selectedShape.draw(graphics2D);
		}
	}

	public void initialize() {
		this.setBackground(Color.WHITE);
	}

	public void clearScreen() { // 화면 전체지우기
		this.shapes.clear();
		this.img.clear();
		this.setBackground(Color.WHITE);
		this.repaint();
	}

	public void ScreenShot() {
		Check++;
		try {
			File filepath = new File("캡쳐본/" + " " + this.Check + " " + ".png");

			Robot robot = new Robot();
			Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage Buf = robot.createScreenCapture(rect);

			try {
				JOptionPane.showMessageDialog(null, "캡쳐완료", "캡쳐", JOptionPane.INFORMATION_MESSAGE);

				ImageIO.write(Buf, "png", filepath);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} catch (HeadlessException e1) {
			e1.printStackTrace();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	private void Cursor(int x, int y) { // 커서변경 메소드

		EAnchors i = Cursorjoin(x, y);
		// int q = shape.Corsur1(x, y);
		if (i != null) {

			if (i.toString() == "NW") {
				setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
			} else if (i.toString() == "WW") {
				setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
			} else if (i.toString() == "SW") {
				setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
			} else if (i.toString() == "NN") {
				setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			} else if (i.toString() == "SS") {
				setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
			} else if (i.toString() == "NE") {
				setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
			} else if (i.toString() == "EE") {
				setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			} else if (i.toString() == "SE") {
				setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
			} else if (i.toString() == "RR") {
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			} else if (i.toString() == "CC") {
				setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		} else {
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	public EAnchors Cursorjoin(int x, int y) { // 마우스 커서 Move할때마다 0.1초단위로 변경되어서 메소드 추가함
		EAnchors anchor = null;
		for (GShapeTool shape : shapes) {

			anchor = shape.Corsur(x, y);
			if (anchor != null) {
				return anchor;
			}
		}
		return anchor;
	}

	///////////////////////////////////////////////////////
	// inner classes
	private class GMouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

		private boolean isDrawing;
		private boolean isTransforming;

		public GMouseHandler() {
			this.isDrawing = false;
			this.isTransforming = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			insertx = e.getX();
			inserty = e.getY();

			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
					setSelected(selectedShape);
				} else {
					initTransforming(selectedShape, e.getX(), e.getY());
					this.isTransforming = true;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					keepDrawing(e.getX(), e.getY());
				}
			} else if (this.isTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				}
			} else if (this.isTransforming) {
				finishTransforming(e.getX(), e.getY());
				this.isTransforming = false;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {

			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					keepDrawing(e.getX(), e.getY());
				}
			} else {
				Cursor(e.getX(), e.getY());
			}

		}

		private void mouseLButton1Clicked(MouseEvent e) {
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
				} else {
					setSelected(selectedShape);
				}
			} else {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					setIntermediatePoint(e.getX(), e.getY());
				}
			}
		}

		private void mouseLButton2Clicked(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				}
			}
		}

		private void mouseRButton1Clicked(MouseEvent e) {
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.mouseLButton1Clicked(e);
				} else if (e.getClickCount() == 2) {
					this.mouseLButton2Clicked(e);
				}
			} else if (e.getButton() == MouseEvent.BUTTON2) {
				if (e.getClickCount() == 1) {
					this.mouseRButton1Clicked(e);
				}
			}
		}
	}

}