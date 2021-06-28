package shapeTools;

import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import main.GConstants.EDrawingStyle;

public class GArc extends GShapeTool {

	public GArc() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Arc2D.Float(0, 0, 0, 0, 0, 180 ,0);
	}
	@Override
	public GShapeTool newInstance() {
		return new GArc();
	}
	
	// methods
	@Override
	public void setInitPoint(int x, int y) {
		Arc2D arc2D = (Arc2D) this.shape;
		arc2D.setFrame(x, y, 0, 0);
	}
	@Override
	public void setFinalPoint(int x, int y) {
		
	}	
	
	@Override
	public void movePoint(int x, int y) {
		Arc2D arc2D = (Arc2D) this.shape;
		arc2D.setFrame(
				arc2D.getX(), arc2D.getY(), x-arc2D.getX(), y-arc2D.getY());
	}
}
