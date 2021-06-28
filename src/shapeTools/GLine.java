package shapeTools;

import java.awt.geom.Line2D;

import main.GConstants.EDrawingStyle;

public class GLine extends GShapeTool {
	// attributes
	private static final long serialVersionUID = 1L;

	public GLine() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Line2D.Double();
		
	}
	@Override
	public GShapeTool newInstance() {
		return new GLine();
	}
	
	// methods
	@Override
	public void setInitPoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x, y, x, y);
	}
	@Override
	public void setFinalPoint(int x, int y) {
	}	
	
	@Override
	public void movePoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(line.getX1(), line.getY1(), x, y);
	}

}
