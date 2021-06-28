package main;

import java.awt.Dimension;
import java.awt.Point;

import shapeTools.GArc;
import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GPolygon;
import shapeTools.GRectFloat;
import shapeTools.GRectangle;
import shapeTools.GShapeTool;

public class GConstants {
	public static class CFrame {
		public final static Point point = new Point(200, 300);
		public final static Dimension dimesion = new Dimension(400, 600);
	}

	public enum EDrawingStyle {
		e1PointDrawing, e2PointDrawing, eNPointDrawing
	};

	public static int wAnchor = 10;
	public static int hAnchor = 10;

	public enum EAction {
		eDraw, eMove, eResize, eRotate, eCRotate
	}

	public enum EShapeTool {

		eRectangle(new GRectangle(), "Rectangle"), eRecF(new GRectFloat(), "GRectFloat"),
		eOval(new GOval(), "Oval"), eLine(new GLine(), "Line"), eArc(new GArc(), "Arc"), ePolygon(new GPolygon(), "Polygon");

		private GShapeTool shapeTool;
		private String text;

		private EShapeTool(GShapeTool shapeTool, String text) {
			this.shapeTool = shapeTool;
			this.text = text;
		}


		public GShapeTool getShapeTool() {
			return this.shapeTool;
		}

		public String getText() {
			return this.text;
		}
	}

	public enum EMenu {
		eFile("����"), eEdit("����"), eColor("��"), eDesign("������"), eRotating("���� ���� ȸ��"), eTextImg("�ؽ�Ʈ, �̹���");

		private String text;

		private EMenu(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}

	public enum EFileMenuItem1 {
		eNew("���θ����"), eOpen("����"), eSave("����"), eSaveAs("�ٸ��̸�����"), ePrint("����Ʈ"), eScreen("��ũ����"), eExit("������");

		private String text;

		private EFileMenuItem1(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}

	}

	public enum EEditMenuItem1 {
		eRedo("redo"), eUndo("undo"), eFront("���� ������"), eBack("���� �ڷ�"), eCut("�߶󳻱�"), ePaste("�ٿ��ֱ�"), eCopy("����"), eDelete("����");

		private String text;

		private EEditMenuItem1(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;

		}
	}

	public enum EDesignItem1 {
		eAnchor("��Ŀ ũ�� ����"), eLine("���� �β�����"), ebackgroundC("���� ����"), eShapeC("���� �� ����"), eAnchorC("��Ŀ�� ����"), eLineC("���� ���� �� ����");

		private String text;

		private EDesignItem1(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;

		}
	}
	public enum ERotatingItem {
		eRotateR90("��� 90�� ȸ��"), eRotateL90("�·� 90�� ȸ��"),eRotate180("180�� ȸ��");

		private String text;

		private ERotatingItem(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}

	}
	public enum ETextImage {
		eImage("�̹��� ����"), eImageRedo("�̹��� Redo"), eImageUndo("�̹��� Undo"), eText("�ؽ�Ʈ ����"), eTextRemove("�ؽ�Ʈ �����");

		private String text;

		private ETextImage(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}

	}
	public enum EAnchors {
		NW, WW, SW, NN, SS, NE, EE, SE, RR, CC;

	}
}
