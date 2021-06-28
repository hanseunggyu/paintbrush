package transformer;

import java.awt.Graphics2D;

import shapeTools.GShapeTool;

public abstract class GTransformer {

	protected GShapeTool selectedShape;
	protected int px, py;
	protected int oneJumX, oneJumY;
	protected double memory, CenterX, CenterY;

	public GTransformer(GShapeTool selectedShape) {
		this.selectedShape = selectedShape;
	}

	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		/* Move ó���� ��� ���� */
		this.px = x;
		this.py = y;

		/* Resize ó���� ��� ���� */
		this.oneJumX = this.selectedShape.getShape().getBounds().x;
		this.oneJumY = this.selectedShape.getShape().getBounds().y;

		/* Rotate ������ ���� �� ó�� ���� �� ��� */
		this.CenterX = selectedShape.getShape().getBounds().getCenterX();
		this.CenterY = selectedShape.getShape().getBounds().getCenterY();
		this.memory = Math.atan2(CenterX - x, CenterY - y);
		this.transforming(graphics2d, x, y);
	}

	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.transform(graphics2d, x, y);

		/* Move ������ ��� ���� */
		this.px = x;
		this.py = y;

		/* Rotate ���� �� ��� */
		this.memory = Math.atan2(CenterX - x, CenterY - y);

	}

	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
	}

	public abstract void transform(Graphics2D graphics2d, int x, int y);

	public abstract void transforming(Graphics2D graphics2d, int x, int y);

}
