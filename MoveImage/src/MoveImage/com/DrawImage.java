package MoveImage.com;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.util.Log;

public class DrawImage {

	private static final String TAG = DrawImage.class.getSimpleName();
	ArrayList<clsImage> lstImgs;
	ArrayList<clsImageCtrl> lstImgCtrl;
	int[][] arrCoords;
	int width = 80; // The width of the bitmap
	int height = 80;// The height of the bitmap

	int nX; // The number of rows
	int nY; // The number of columns

	int beginX = 10; // The X-coordinates of image start
	int beginY = 10; // The Y-coordinates of image start

	int scaleWidth = 320; // The new bitmap's desired width.
	int scaleHeight = 480; // The new bitmap's desired height

	int optImage; // opacity image
	int arrImg[];
	Bitmap bitmap;
	MainGamePanel gamepanel;

	private SurfaceHolder surfaceHolder;

	public DrawImage(SurfaceHolder surfaceHolder, MainGamePanel gamepanel) {
		this.surfaceHolder = surfaceHolder;
		this.gamepanel = gamepanel;
		init();
	}

	public void init() {
		Bitmap[] arrBmp;
		int l = 0;
		clsImage clsImg;
		int codeT;

		// int i;
		try {

			width = ImageActivity.scnWidth / 6;
			height = width;

			scaleWidth = width * 4;
			scaleHeight = height * 6;

			beginX = width;
			// beginY = ImageActivity.scnHeight / 20;

			// load bitmap
			bitmap = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img3);

			if (bitmap == null)
				return;

			lstImgs = new ArrayList<clsImage>();
			lstImgCtrl = new ArrayList<clsImageCtrl>();

			arrBmp = splitImage(bitmap);
			if (arrBmp == null) {
				Log.i(TAG, "*****init() Khong the load hinh");
				return;
			}
			arrImg = randImage();

			for (int r = 0; r < nY; r++) {
				for (int c = 0; c < nX; c++) {
					clsImg = new clsImage(l);
					clsImg.setBitmap(arrBmp[l]);
					clsImg.setX(arrCoords[l][0]);
					clsImg.setY(arrCoords[l][1]);
					clsImg.setShow(true);
					// save code
					if (l >= nX)
						clsImg.setImgT(l - 4);
					if (l < nY * nX - 4)
						clsImg.setImgB(l + 4);
					if (c > 0)
						clsImg.setImgL(l - 1);
					if (c < nX - 1)
						clsImg.setImgR(l + 1);

					lstImgs.add(clsImg);
					l++;
				}
			}

			// codeT = (nY - 1) * nX;
			codeT = l - 1;
			clsImg = (clsImage) lstImgs.get(codeT);
			// set opacity
			clsImg.setShow(false);
			lstImgs.set(codeT, clsImg);
			Log.i(TAG, "***** init() called: " + ImageActivity.scnWidth + ", " + ImageActivity.scnHeight);
			// Log.i(TAG, "***** init() called ");

			// arrImg = randImage();
			// tạo hinh lộn xộn
			mixImage();
			//Tạo các hinh de dieu khien (xem hinh hien tai, trộn hinh, xem hinh ke)
			loadImageCtrl();
		} catch (Exception ex) {
			Log.i(TAG, "*****init() Error: " + ex.getMessage());
		}
	}

	// Function: ve hinh goc va cac button image
	public void drawImage() {
		clsImage clsImg;
		Canvas canvas = null;
		Paint paint;
		Bitmap bitmapT;
		try {
			canvas = this.surfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			bitmapT = Bitmap.createScaledBitmap(bitmap, scaleWidth, scaleHeight, true);
			canvas.drawBitmap(bitmap, beginX, beginY, null);

			// Ve button image
			drawControl(canvas);

		} catch (Exception ex) {
			Log.i(TAG, "***** draw() Error: " + ex.getMessage());
		} finally {
			if (canvas != null)
				surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}

	// Function: draw all image
	//
	//
	public void drawAll() {
		// clsImage clsImg;
		clsImage clsImg;
		Canvas canvas = null;
		Paint paint;

		try {
			canvas = this.surfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			paint = new Paint();
			paint.setAlpha(55);
			for (int i = 0; i < lstImgs.size(); i++) {
				clsImg = (clsImage) lstImgs.get(i);
				if (clsImg.isShow() == true)
					clsImg.draw(canvas, null);
				else
					clsImg.draw(canvas, paint);
				// Log.i(TAG, "***** draw() : " + i);
			}

			// Ve duong ke
			drawLine(canvas);
			// ve button image
			drawControl(canvas);

		} catch (Exception ex) {
			Log.i(TAG, "***** draw() Error: " + ex.getMessage());
		} finally {
			if (canvas != null)
				surfaceHolder.unlockCanvasAndPost(canvas);
		}
		// Log.i(TAG, "***** drawAll() called ");
	}

	// Function: draw line
	//
	//
	public void drawLine(Canvas canvas) {
		// Rect rect;
		Paint paint;
		int x, y;
		try {
			// canvas.drawColor(Color.BLACK);
			// rect = new Rect(beginX, beginY, scaleWidth, scaleHeight);
			paint = new Paint();
			paint.setColor(Color.BLACK);
			// ve duong doc
			x = beginX;
			y = beginY;
			for (int i = 0; i <= 4; i++) {
				canvas.drawLine(x, y, x, height * 6 + 20, paint);
				x += width;
			}

			x = beginX;
			// ve duong ngang
			for (int i = 0; i <= 6; i++) {
				canvas.drawLine(x, y, width * 5, y, paint);
				y += height;
			}

		} catch (Exception ex) {
			Log.i(TAG, "***** drawBackground() Error: " + ex.getMessage());
		} finally {

		}
	}

	// Function: draw control
	//
	//
	public void drawControl(Canvas canvas) {
		clsImageCtrl clsCtrl;
		try {
			// canvas.drawColor(Color.BLACK);
			for (int i = 0; i < lstImgCtrl.size(); i++) {
				clsCtrl = (clsImageCtrl) lstImgCtrl.get(i);
				clsCtrl.draw(canvas, null);
			}

		} catch (Exception ex) {
			Log.i(TAG, "***** drawControl() Error: " + ex.getMessage());
		}
	}

	// Function: tao hinh de dieu khien (xem hinh hien tai, trộn hinh, xem hinh ke)
	//
	private void loadImageCtrl() {
		clsImageCtrl clsCtrl;
		int i = 0;
		int x, y;
		Bitmap bitmapT;
		lstImgCtrl = new ArrayList<clsImageCtrl>();

		x = 20;
		y = 20;
		bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.btn_next);
		clsCtrl = new clsImageCtrl(i);
		clsCtrl.setXY(x, ImageActivity.scnHeight - y);
		lstImgCtrl.add(clsCtrl);

		x += bitmapT.getWidth() + 5;
		i++;
		bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.btn_next);
		clsCtrl = new clsImageCtrl(i);
		clsCtrl.setXY(x, ImageActivity.scnHeight - y);
		lstImgCtrl.add(clsCtrl);

		x += bitmapT.getWidth() + 5;
		i++;
		bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.btn_next);
		clsCtrl = new clsImageCtrl(i);
		clsCtrl.setXY(x, ImageActivity.scnHeight - y);
		lstImgCtrl.add(clsCtrl);
	}

	// //////////////////////////
	// function: split Image and set coordinate
	// return: array
	// //////////////////////////
	public Bitmap[] splitImage(Bitmap picture) {
		int l = 0;
		int x = 0; // The x coordinates of the first pixel in source
		int y = 0; // The y coordinates of the first pixel in source
		int dstWidth; // The new bitmap's desired width.
		int dstHeight; // The new bitmap's desired height
		int cX = beginX;
		int cY = beginY;

		if (picture == null)
			return null;

		dstWidth = scaleWidth;
		dstHeight = scaleHeight;
		nX = scaleWidth / width;
		nY = scaleHeight / height;

		arrCoords = new int[nY * nX][2];

		// the new scaled bitmap.
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(picture, dstWidth, dstHeight, true);
		Bitmap[] imgs = new Bitmap[nY * nX];
		try {
			for (int r = 0; r < nY; r++) {
				x = 0;
				cX = beginX;
				for (int c = 0; c < nX; c++) {
					// A bitmap that represents the specified subset of source
					imgs[l] = Bitmap.createBitmap(scaledBitmap, x, y, width, height);
					arrCoords[l][0] = cX;
					arrCoords[l][1] = cY;
					// The X coordinates of image
					cX += width;
					x += width;
					l++;
				}
				// The Y coordinate of image
				cY += height;
				y += height;
			}
			Log.i(TAG, "***** splitImage() called ");
			return imgs;
		} catch (IllegalArgumentException ex) {
			Log.i(TAG, "***** splitImage() Error: " + ex.getMessage());
			return null;
		} catch (Exception ex) {
			Log.i(TAG, "***** splitImage() Error: " + ex.getMessage());
			return null;
		}
	}

	// Function: Tìm hình có tọa độ tương ứng
	// para1: x coordinate x
	// para2: y coordinate y
	// return: clsImage
	public clsImage searchCoord(int x, int y) {
		clsImage clsImgT;
		try {
			for (int i = 0; i < lstImgs.size(); i++) {
				clsImgT = (clsImage) lstImgs.get(i);
				if ((clsImgT.getX() <= x && clsImgT.getX() + width >= x)
						&& (clsImgT.getY() <= y && clsImgT.getY() + height >= y)) {
					return clsImgT;
				}
			}
			Log.i(TAG, "***** searchCoord() called ");
		} catch (Exception ex) {
			Log.i(TAG, "***** searchCoord() Error: " + ex.getMessage());
		}
		return null;
	}

	// Function: Tìm hình có tọa độ tương ứng
	// para1: x coordinate x
	// para2: y coordinate y
	// return: clsImageCtrl
	public clsImageCtrl searchCoordCtrl(int x, int y) {
		clsImageCtrl clsImgT;
		try {
			for (int i = 0; i < lstImgCtrl.size(); i++) {
				clsImgT = (clsImageCtrl) lstImgCtrl.get(i);
				if ((clsImgT.getX() <= x && clsImgT.getX() + width >= x)
						&& (clsImgT.getY() <= y && clsImgT.getY() + height >= y)) {
					return clsImgT;
				}
			}
			Log.i(TAG, "***** searchCoordCtrl() called ");
		} catch (Exception ex) {
			Log.i(TAG, "***** searchCoordCtrl() Error: " + ex.getMessage());
		}
		return null;
	}

	// Function: Di chuyen hinh
	// Para: clsChange
	public boolean moveImage(clsImage clsChange) {
		clsImage clsT;

		try {
			if (clsChange == null)
				return false;

			if (clsChange.getImgL() > -1) {
				clsT = (clsImage) lstImgs.get(clsChange.getImgL());
				if (clsT.isShow() == false) {
					return changeImage(clsChange, clsT);
				}
			}
			if (clsChange.getImgR() > -1) {
				clsT = (clsImage) lstImgs.get(clsChange.getImgR());
				if (clsT.isShow() == false)
					return changeImage(clsChange, clsT);
			}

			if (clsChange.getImgT() > -1) {
				clsT = (clsImage) lstImgs.get(clsChange.getImgT());
				if (clsT.isShow() == false)
					return changeImage(clsChange, clsT);
			}

			if (clsChange.getImgB() > -1) {
				clsT = (clsImage) lstImgs.get(clsChange.getImgB());
				if (clsT.isShow() == false)
					return changeImage(clsChange, clsT);
			}
		} catch (Exception ex) {
			Log.i(TAG, "***** moveImage() Error: " + ex.getMessage());
		}
		return false;
	}

	// Function : change image
	//
	public boolean changeImage(clsImage clsA, clsImage clsB) {
		Bitmap bitmap;
		boolean show;
		try {

			bitmap = clsB.getBitmap();
			show = clsB.isShow();

			clsB.setBitmap(clsA.getBitmap());
			clsB.setShow(clsA.isShow());

			clsA.setBitmap(bitmap);
			clsA.setShow(show);

			lstImgs.set(clsA.getCode(), clsA);
			lstImgs.set(clsB.getCode(), clsB);

			return true;
		} catch (Exception ex) {
			Log.i(TAG, "***** changeImage() Error: " + ex.getMessage());
			return false;
		}
	}

	// Function: tron hinh
	//
	public void mixImage() {
		int l = nX * nY;
		clsImage clsImgA, clsImgB;
		for (int i = 0; i < l; i++) {
			clsImgA = (clsImage) lstImgs.get(i);
			clsImgB = (clsImage) lstImgs.get(arrImg[i]);
			changeImage(clsImgA, clsImgB);
		}
	}

	// Function: tao mang đã trộn các hình
	public int[] randImage() {
		int rnd;
		Random r = new Random();

		int[][] arr = new int[6][24];
		arr[0] = new int[] { 18, 15, 10, 2, 5, 4, 14, 0, 23, 17, 9, 1, 21, 16, 19, 13, 8, 6, 22, 3, 11, 7, 12, 20 };
		arr[1] = new int[] { 14, 4, 22, 17, 8, 21, 2, 20, 13, 9, 12, 7, 1, 10, 11, 6, 0, 15, 18, 19, 23, 3, 5, 16 };
		arr[2] = new int[] { 7, 11, 16, 3, 22, 10, 23, 19, 9, 21, 20, 0, 6, 14, 2, 17, 5, 13, 18, 15, 12, 8, 1, 4 };
		arr[3] = new int[] { 8, 13, 22, 15, 2, 20, 6, 9, 16, 0, 18, 4, 23, 11, 19, 5, 21, 1, 14, 17, 10, 12, 3, 7 };
		arr[4] = new int[] { 2, 21, 12, 0, 11, 17, 7, 22, 5, 20, 13, 16, 8, 19, 4, 3, 15, 23, 9, 14, 18, 10, 6, 1 };

		// rnd = r.nextInt(5);
		rnd = 1;
		Log.i(TAG, "*****randImage() rand=: " + rnd);
		return arr[rnd];
	}

	public void handleActionDown(int x, int y) {

		clsImage clsT;
		clsImageCtrl clsCtrl;
		try {

			clsCtrl = searchCoordCtrl(x, y);
			if (clsCtrl != null) {
				if (clsCtrl.getCode() == 0) {
					drawImage();
				} else if (clsCtrl.getCode() == 1) {

				} else if (clsCtrl.getCode() == 2) {

				}
				return;
			}
			clsT = searchCoord(x, y);
			// search not found
			if (clsT == null) {
				Log.i(TAG, "***** handleActionDown() khong co toa do: " + x + "," + y);
				return;
			}

			// di chuyen hinh
			if (moveImage(clsT) == false) {
				Log.i(TAG, "***** handleActionDown() khong co hinh: " + clsT.getCode());
				return;
			}

			drawAll();
			// Log.i(TAG, "***** handleActionDown() called ");
		} catch (Exception ex) {
			Log.i(TAG, "***** handleActionDown() Error: " + ex.getMessage());
		}
	}
}
