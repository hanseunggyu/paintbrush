package shapeTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;
import java.awt.image.renderable.RenderableImage;
import java.io.Serializable;

import main.GConstants;
import main.GConstants.EAction;
import main.GConstants.EAnchors;
import main.GConstants.EDrawingStyle;

abstract public class GShapeTool implements Serializable, Cloneable {
	// attributes
	private static final long serialVersionUID = 1L;

	private EDrawingStyle eDrawingStyle;
	protected Shape shape;
	protected Image image;
	private Ellipse2D[] anchors;
	private boolean isSelected;
	private EAnchors selectedAnchor;
	private Color Acolor = Color.WHITE, Scolor = null, Lcolor = null;
	private int LineW;
	private Color Check;
	private EAction eAction;

	public AffineTransform affineTransform;
	// working variables

	// constructors
	public GShapeTool(EDrawingStyle eDrawingState) {
		this.anchors = new Ellipse2D.Double[EAnchors.values().length];
		for (EAnchors eAnchor : EAnchors.values()) {
			this.anchors[eAnchor.ordinal()] = new Ellipse2D.Double();
		}
		this.isSelected = false;
		this.eDrawingStyle = eDrawingState;
		this.selectedAnchor = null;
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity(); // 초기화 0 시키고 더해갈려고
	}

	// getters & setters
	public EDrawingStyle getDrawingStyle() {
		return this.eDrawingStyle;
	}
	
	public void AnchorColor(Color c) {
		this.Acolor = c;
		
	}

	public void ShapeColor(Color c) {
		this.Scolor = c;
	}

	public void LineColor(Color c) {
		this.Lcolor = c;
	}
	public void LineWidth(int num) {
		this.LineW = num;
		
	}
	public EAction getAction() {
		return this.eAction;
	}

	public EAnchors getAnchors() {
		return this.selectedAnchor;
	}

	public EAnchors Corsur(int x, int y) {

		for (int i = 0; i < this.anchors.length - 1; i++) {
			if ((this.anchors[0]).contains(x, y)) {
				return EAnchors.NW;
			} else if ((this.anchors[1]).contains(x, y)) {
				return EAnchors.WW;
			} else if ((this.anchors[2]).contains(x, y)) {
				return EAnchors.SW;
			} else if ((this.anchors[3]).contains(x, y)) {
				return EAnchors.NN;
			} else if ((this.anchors[4]).contains(x, y)) {
				return EAnchors.SS;
			} else if ((this.anchors[5]).contains(x, y)) {
				return EAnchors.NE;
			} else if ((this.anchors[6]).contains(x, y)) {
				return EAnchors.EE;
			} else if ((this.anchors[7]).contains(x, y)) {
				return EAnchors.SE;
			} else if ((this.anchors[8]).contains(x, y)) {
				return EAnchors.RR;
			} else if (this.shape.contains(x, y)) {
				return EAnchors.CC;
			}

		}
		return null;
	}

//	public int Corsur1(int x, int y) {
//		if(this.affineTransform.createTransformedShape(this.shape).contains(x, y)) {
//			return 1;
//		}else {
//		return 0;
//		}
//	}
	// methods
	public EAction containes(int x, int y) {
		this.eAction = null;
		if (this.isSelected) {
			for (int i = 0; i < this.anchors.length - 1; i++) {
				if (this.affineTransform.createTransformedShape(this.anchors[i]).contains(x, y)) {
					this.selectedAnchor = EAnchors.values()[i];
					this.eAction = EAction.eResize;
					// if(EAction.eResize.)
				}
			}
			if (this.anchors[EAnchors.RR.ordinal()].contains(x, y)) {
				this.eAction = EAction.eRotate;
			}
		}
		// shapeanchor 움직이는 상태에서 contain
		if (this.affineTransform.createTransformedShape(this.shape).contains(x, y)) {
			this.eAction = EAction.eMove;

		}
		return this.eAction;
	}

	public Shape getShape() {
		
		return shape;
	}
	/*
	 * public Image getShape() {
	 * 
	 * return shape; }
	 */

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	private void drawAnchors(Graphics2D graphics) {
		int wAnchor = GConstants.wAnchor;
		int hAnchor = GConstants.hAnchor;

		Rectangle rectangle = this.shape.getBounds();
		int x0 = rectangle.x - wAnchor / 2;
		int x1 = rectangle.x - wAnchor / 2 + (rectangle.width) / 2;
		int x2 = rectangle.x - wAnchor / 2 + rectangle.width;
		int y0 = rectangle.y - hAnchor / 2;
		int y1 = rectangle.y - hAnchor / 2 + (rectangle.height) / 2;
		int y2 = rectangle.y - hAnchor / 2 + rectangle.height;

		this.anchors[EAnchors.NW.ordinal()].setFrame(x0, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.WW.ordinal()].setFrame(x0, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.SW.ordinal()].setFrame(x0, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.NN.ordinal()].setFrame(x1, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.SS.ordinal()].setFrame(x1, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.NE.ordinal()].setFrame(x2, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.EE.ordinal()].setFrame(x2, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.SE.ordinal()].setFrame(x2, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.RR.ordinal()].setFrame(x1, y0 - 40, wAnchor, hAnchor);

		for (EAnchors eAnchor : EAnchors.values()) {
			Color color = graphics.getColor();
			graphics.setColor(Acolor);
			graphics.fill(this.affineTransform.createTransformedShape(this.anchors[eAnchor.ordinal()]));
			graphics.setColor(color);
			graphics.draw(this.affineTransform.createTransformedShape(this.anchors[eAnchor.ordinal()]));

		}
	}

	public GShapeTool clone() {
		try {
			return (GShapeTool) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void draw(Graphics2D graphics) {
		
		
		if(this.LineW != 0) {
			graphics.setStroke(new BasicStroke(LineW,BasicStroke.CAP_ROUND,0));
		}else {
			graphics.setStroke(new BasicStroke(0,BasicStroke.CAP_ROUND,0));
		}
		if (this.Scolor != null) {
			graphics.setColor(Scolor);
			graphics.fill(this.shape);
		}else {
			graphics.setColor(Color.WHITE);
			graphics.fill(this.shape);
		}
		if (this.Lcolor != null) {
			graphics.setColor(Lcolor);
		}else {
			graphics.setColor(Color.BLACK);
		}
		graphics.draw(this.affineTransform.createTransformedShape(this.shape));
		if (isSelected) {
			this.drawAnchors(graphics);
		}
		
	}

	public void animate(Graphics2D graphics2d, int x, int y) {

		this.draw(graphics2d);
		this.movePoint(x, y);
		this.draw(graphics2d);
		
	}

	// interface
	public abstract GShapeTool newInstance();

	public abstract void setInitPoint(int x, int y);

	public void setIntermediatePoint(int x, int y) {
	}

	public abstract void setFinalPoint(int x, int y);

	public abstract void movePoint(int x, int y);





}
