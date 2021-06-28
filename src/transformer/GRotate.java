package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapeTools.GShapeTool;

public class GRotate extends GTransformer {
	
	public GRotate(GShapeTool selectedShape) {
		super(selectedShape);
	}

	@Override
	public void transform(Graphics2D graphics2d, int x, int y) {
		
		AffineTransform affineTransform = new AffineTransform();
		double degree = Math.atan2(CenterX-x,CenterY-y);
		
		affineTransform.rotate(memory-degree, CenterX, CenterY);
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));
	   
	}

	@Override
	public void transforming(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
