package shapeTools;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import main.GConstants.EDrawingStyle;

public class GRectFloat extends GShapeTool {

	public GRectFloat() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new RoundRectangle2D.Float(1, 1, 1, 1, 70, 70);
	}
	@Override
	public GShapeTool newInstance() {
		return new GRectFloat();
	}
	
	// methods
	@Override
	public void setInitPoint(int x, int y) {
		RoundRectangle2D rectangle2 = (RoundRectangle2D) this.shape;
		rectangle2.setFrame(x, y, 0, 0);
	}
	@Override
	public void setFinalPoint(int x, int y) {
		
	}	
	
	@Override
	public void movePoint(int x, int y) {
		RoundRectangle2D rectangle2 = (RoundRectangle2D) this.shape;
		rectangle2.setFrame(
				rectangle2.getX(), rectangle2.getY(), x-rectangle2.getX(), y-rectangle2.getY());
	}
}
