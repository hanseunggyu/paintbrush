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
		eFile("파일"), eEdit("편집"), eColor("색"), eDesign("디자인"), eRotating("간편 도형 회전"), eTextImg("텍스트, 이미지");

		private String text;

		private EMenu(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}

	public enum EFileMenuItem1 {
		eNew("새로만들기"), eOpen("열기"), eSave("저장"), eSaveAs("다른이름으로"), ePrint("프린트"), eScreen("스크린샷"), eExit("나가기");

		private String text;

		private EFileMenuItem1(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}

	}

	public enum EEditMenuItem1 {
		eRedo("redo"), eUndo("undo"), eFront("도형 앞으로"), eBack("도형 뒤로"), eCut("잘라내기"), ePaste("붙여넣기"), eCopy("복사"), eDelete("삭제");

		private String text;

		private EEditMenuItem1(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;

		}
	}

	public enum EDesignItem1 {
		eAnchor("앵커 크기 변경"), eLine("라인 두께변경"), ebackgroundC("배경색 변경"), eShapeC("도형 색 변경"), eAnchorC("앵커색 변경"), eLineC("도형 라인 색 변경");

		private String text;

		private EDesignItem1(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;

		}
	}
	public enum ERotatingItem {
		eRotateR90("우로 90도 회전"), eRotateL90("좌로 90도 회전"),eRotate180("180도 회전");

		private String text;

		private ERotatingItem(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}

	}
	public enum ETextImage {
		eImage("이미지 삽입"), eImageRedo("이미지 Redo"), eImageUndo("이미지 Undo"), eText("텍스트 삽입"), eTextRemove("텍스트 지우기");

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
