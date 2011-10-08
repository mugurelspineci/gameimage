package MoveImage.com;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class clsImageCtrl {
	private static final String TAG = clsImage.class.getSimpleName();
	private int code;
	private Bitmap bitmap;
	private int x; //x field to maintain the x coordinate to draw the image
	private int y; //y field to maintain the y coordinate to draw the image
	private int xh; //x field to maintain the x coordinate to draw the image
	private int yh; //y field to maintain the y coordinate to draw the image
	 

	//Function: Construct
	//para:		code
	public clsImageCtrl(int code) {
		this.code = code;
	}

	public clsImageCtrl() {

	}
	
	
	//Function: Draw the specified bitmap
	//para:		canvas
	//return:
	public void draw(Canvas canvas, Paint paint){
		try {
			canvas.drawBitmap(bitmap, x, y, paint);
			//canvas.drawBitmap(bitmap, x+60, y, null);
			//Log.i(TAG, "***** draw(): " + code);
		}catch(Exception ex){
			Log.i(TAG, "***** draw() Error: " + ex.getMessage());
		}
	}
	
	public void draw_h(Canvas canvas, Paint paint){
		try {
			canvas.drawBitmap(bitmap, xh, yh, paint);
		}catch(Exception ex){
			Log.i(TAG, "***** draw_h() Error: " + ex.getMessage());
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getXH() {
		return xh;
	}

	public void setXH(int xh) {
		this.xh = xh;
	}

	public int getYH() {
		return yh;
	}

	public void setYH(int yh) {
		this.yh = yh;
	}
	
	public void setXYH(int xh, int yh){
		this.xh = xh;
		this.yh = yh;
	}
}
