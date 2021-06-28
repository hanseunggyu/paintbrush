package transformer;



import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapeTools.GShapeTool;
import transformer.GTransformer;

public class GRotate180 extends GTransformer {

	public GRotate180(GShapeTool selectedShape) {
		super(selectedShape);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void transform(Graphics2D graphics2d, int x, int y) {
	}

	@Override
	public void transforming(Graphics2D graphics2d, int x, int y) {
		AffineTransform affineTransform = new AffineTransform();

		affineTransform.rotate(Math.PI, CenterX, CenterY);
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));
		
	}

}
