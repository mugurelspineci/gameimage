package MoveImage.com;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class clsImage {
	private static final String TAG = clsImage.class.getSimpleName();
	private int code;
	private Bitmap bitmap;
	private int x; //x field to maintain the x coordinate to draw the image
	private int y; //y field to maintain the y coordinate to draw the image
	private boolean show;
	private int imgL;
	private int imgR;
	private int imgT;
	private int imgB;

	//Function: Construct
	//para:		code
	public clsImage(int code) {
		this.code = code;
		this.show = true;
		imgL = -1;
		imgR = -1;
		imgT = -1;
		imgB = -1;
	}

	public clsImage() {

	}
	
	
	//Function: Draw the specified bitmap, set opacity of Bitmap image
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

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public int getImgL() {
		return imgL;
	}

	public void setImgL(int imgL) {
		this.imgL = imgL;
	}

	public int getImgR() {
		return imgR;
	}

	public void setImgR(int imgR) {
		this.imgR = imgR;
	}

	public int getImgT() {
		return imgT;
	}

	public void setImgT(int imgT) {
		this.imgT = imgT;
	}

	public int getImgB() {
		return imgB;
	}

	public void setImgB(int imgB) {
		this.imgB = imgB;
	}

}
