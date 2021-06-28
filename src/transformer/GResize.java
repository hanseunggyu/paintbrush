package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import shapeTools.GShapeTool;

public class GResize extends GTransformer {

	public GResize(GShapeTool selectedShape) {
		super(selectedShape);
	}

	@SuppressWarnings("static-access")
	public void transform(Graphics2D graphics2d, int x, int y) {

		AffineTransform affineTransform = new AffineTransform();
		Point location = new Point();
		location.x = -oneJumX;
		location.y = -oneJumY;

		// x, y 움직인점
		double multiplex = 0;
		double multipley = 0;
		double width = selectedShape.getShape().getBounds().width;
		double height = selectedShape.getShape().getBounds().height;

		affineTransform.setToTranslation(location.x, location.y);
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));

		if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().NE) {
			multiplex = (x - oneJumX) / (width);
			multipley = (oneJumY - y + height) / (height);
			location.x = oneJumX;
			location.y = y;
			oneJumY = y;
		} else if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().EE) {
			multiplex = (x - oneJumX) / width;
			multipley = 1;
			location.x = oneJumX;
			location.y = oneJumY;
		} else if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().SE) {
			multiplex = (x - oneJumX) / width;
			multipley = (y - oneJumY) / height;
			location.x = oneJumX;
			location.y = oneJumY;
		} else if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().SS) {

			multiplex = 1;
			multipley = (y - oneJumY) / height;
			location.x = oneJumX;
			location.y = oneJumY;
		} else if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().SW) {
			multiplex = (oneJumX - x + width) / width;
			multipley = (y - oneJumY) / height;
			location.x = x;
			location.y = oneJumY;
			oneJumX = x;
		} else if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().WW) {
			multiplex = (oneJumX - x + width) / width;
			multipley = 1;
			location.x = x;
			location.y = oneJumY;
			oneJumX = x;
		} else if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().SW) {
			multiplex = (oneJumX - x + width) / width;
			multipley = (y - oneJumY) / height;
			location.x = x;
			location.y = oneJumY;
			oneJumX = x;
		}
		else if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().NW) {
			multiplex = (oneJumX - x + width) / width;
			multipley = (oneJumY - y + height) / height;
			location.x = x;
			location.y = y;
			oneJumX = x;
			oneJumY = y;
		} else if (this.selectedShape.getAnchors() == this.selectedShape.getAnchors().NN) {
			multiplex = 1;
			multipley = (oneJumY - y + height) / height;
			location.x = oneJumX;
			location.y = y;
			oneJumY = y;
		}
	
		affineTransform.setToScale(multiplex, multipley); // 배수로 늘려줌
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));// 설정
		affineTransform.setToTranslation(location.x, location.y); // 도형이동
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));// 설정

		
	}

	@Override
	public void transforming(Graphics2D graphics2d, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}