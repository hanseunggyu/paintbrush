package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapeTools.GShapeTool;

public class GRotateL90 extends GTransformer {

	public GRotateL90(GShapeTool selectedShape) {
		super(selectedShape);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void transform(Graphics2D graphics2d, int x, int y) {
	}

	@Override
	public void transforming(Graphics2D graphics2d, int x, int y) {
		AffineTransform affineTransform = new AffineTransform();
	
		affineTransform.rotate(-Math.PI/2, CenterX, CenterY);
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));
		
	}

}
