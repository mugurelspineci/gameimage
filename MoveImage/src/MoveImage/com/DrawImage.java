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
	int arrRandNum[];
	int nPos; // vi tri lay hình (tu mang arrBitmap)
	int nImage; // so hinh trong ung dung
	boolean bMove;

	Bitmap arrBitmap[]; // Luu cac hinh
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

		// so hinh
		nImage = 4;

		// duoc di chuyen
		bMove = true;
		// int i;
		try {

			//chieu rong cua hinh cat nhỏ
			width = ImageActivity.scnWidth / 6;
			height = width;

			//chieu rong cua hinh lớn
			scaleWidth = width * 4;
			scaleHeight = height * 6;

			beginX = width;
			// beginY = ImageActivity.scnHeight / 20;

			// load bitmap
			// bitmap = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img3);

			// random 0->4
			nPos = randInt(nImage - 1);
			// Load random image
			bitmap = loadImage(nPos);

			if (bitmap == null)
				return;

			lstImgs = new ArrayList<clsImage>();
			lstImgCtrl = new ArrayList<clsImageCtrl>();

			// Luu 24 hinh da cat
			arrBmp = splitImage(bitmap);
			if (arrBmp == null) {
				Log.i(TAG, "*****init() Khong the load hinh");
				return;
			}

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

			// Luu day so ngau nhien tu 0->23
			arrRandNum = randCoord();

			// tạo hinh lộn xộn
			mixImage();
			// Tạo các hinh de dieu khien (xem hinh hien tai, trộn hinh, xem hinh ke)
			loadImageCtrl();
		} catch (Exception ex) {
			Log.i(TAG, "*****init() Error: " + ex.getMessage());
		}
	}

	// Function: ve hinh goc va cac button image
	public void drawImage(Bitmap picture) {
		Canvas canvas = null;
		Bitmap bitmapT;
		try {
			canvas = this.surfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			bitmapT = Bitmap.createScaledBitmap(picture, scaleWidth, scaleHeight, true);
			canvas.drawBitmap(bitmapT, beginX, beginY, null);

			// Ve button image
			drawControl(canvas);

		} catch (Exception ex) {
			Log.i(TAG, "***** drawImage() Error: " + ex.getMessage());
		} finally {
			if (canvas != null)
				surfaceHolder.unlockCanvasAndPost(canvas);
		}
	}

	// Function: draw all image
	public void drawAll() {
		clsImage clsImg;
		Canvas canvas = null;
		Paint paint;

		try {
			canvas = this.surfaceHolder.lockCanvas();
			canvas.drawColor(Color.BLACK);
			paint = new Paint();
			paint.setAlpha(55);
			if (bMove == true) {
				Log.i(TAG, "***** drawAll() ");
				for (int i = 0; i < lstImgs.size(); i++) {
					clsImg = (clsImage) lstImgs.get(i);
					if (clsImg.isShow() == true)
						clsImg.draw(canvas, null);
					else
						clsImg.draw(canvas, paint);
				}				 
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
	public void drawControl(Canvas canvas) {
		clsImageCtrl clsCtrl;
		int i = 0;
		try {
			// canvas.drawColor(Color.BLACK);
			for (i = 0; i < lstImgCtrl.size(); i++) {
				clsCtrl = (clsImageCtrl) lstImgCtrl.get(i);
				// clsCtrl = (clsImageCtrl) lstImgCtrl.get(0);
				clsCtrl.draw(canvas, null);
				// Log.i(TAG, "***** drawControl(): " + i + ",xy: " +
				// clsCtrl.getX() + "," + clsCtrl.getY());
			}

		} catch (Exception ex) {
			Log.i(TAG, "***** drawControl() Error: " + ex.getMessage() + ", " + i);
		}
	}

	// Function: tao hinh de dieu khien (xem hinh hien tai, trộn hinh, xem hinh
	// ke)
	private void loadImageCtrl() {
		clsImageCtrl clsCtrl;
		int i = 0;
		int x, y;
		Bitmap bitmapT;
		int widthCtrl, heightCtrl;

		widthCtrl = 10;
		heightCtrl = 10;

		try {
			lstImgCtrl = new ArrayList<clsImageCtrl>();

			// image View
			x = 40;
			y = 70;
			bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.view);
			clsCtrl = new clsImageCtrl(i);
			clsCtrl.setBitmap(Bitmap.createScaledBitmap(bitmapT, widthCtrl, heightCtrl, true));
			clsCtrl.setXY(x, ImageActivity.scnHeight - y);
			// clsCtrl.setXY(x, y);
			lstImgCtrl.add(clsCtrl);

			// image mix
			x += bitmapT.getWidth() + 15;
			i++;
			bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.mix);
			clsCtrl = new clsImageCtrl(i);
			clsCtrl.setBitmap(Bitmap.createScaledBitmap(bitmapT, widthCtrl, heightCtrl, true));
			clsCtrl.setXY(x, ImageActivity.scnHeight - y);
			lstImgCtrl.add(clsCtrl);

			// image change
			x += bitmapT.getWidth() + 5;
			i++;
			bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.change);
			clsCtrl = new clsImageCtrl(i);
			clsCtrl.setBitmap(Bitmap.createScaledBitmap(bitmapT, widthCtrl, heightCtrl, true));
			clsCtrl.setXY(x, ImageActivity.scnHeight - y);
			lstImgCtrl.add(clsCtrl);

			// image Close
			i++;
			bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.close);
			clsCtrl = new clsImageCtrl(i);
			clsCtrl.setBitmap(Bitmap.createScaledBitmap(bitmapT, widthCtrl, heightCtrl, true));
			clsCtrl.setXY(scaleWidth, beginY);
			lstImgCtrl.add(clsCtrl);

		} catch (Exception ex) {
			Log.i(TAG, "***** loadImageCtrl() Error: " + ex.getMessage());
		}
	}

	// function: cat thanh nhieu hinh nho, tạo tọa độ cho hinh
	// return: array
	public Bitmap[] splitImage(Bitmap picture) {
		int l = 0;
		int x = 0; // The x coordinates of the first pixel in source
		int y = 0; // The y coordinates of the first pixel in source
		int cX = beginX;
		int cY = beginY;

		if (picture == null)
			return null;

		nX = scaleWidth / width;
		nY = scaleHeight / height;

		arrCoords = new int[nY * nX][2];

		// the new scaled bitmap.
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(picture, scaleWidth, scaleHeight, true);
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

	// Function: load hinh
	// Para: num: so hinh can load
	// Return: bitmap
	public Bitmap loadImage(int num) {
		Bitmap bitmapT = null;
		try {
			switch (num) {
			case 0:
				bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img1);
			case 1:
				bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img2);
			case 2:
				bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img3);
			case 3:
				bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img4);
				// default: return null;
			}
			return Bitmap.createScaledBitmap(bitmapT, scaleWidth, scaleHeight, true);
		} catch (Exception ex) {
			Log.i(TAG, "***** loadImages() Error: " + ex.getMessage());
			return null;
		}

	}

	// Function: Tìm hình có tọa độ tương ứng
	// para: truyen vao tọa độ x,y
	// return: tra ve lớp hình có toa do gan đúng với tọa độ truyền vào
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
	// para: truyen vao tọa độ x,y
	// return: tra ve lớp hình có toa do gan đúng với tọa độ truyền vào
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
			// Log.i(TAG, "***** searchCoordCtrl() called ");
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
	public void mixImage() {
		int l = nX * nY;
		clsImage clsImgA, clsImgB;
		try {
			for (int i = 0; i < l; i++) {
				clsImgA = (clsImage) lstImgs.get(i);
				clsImgB = (clsImage) lstImgs.get(arrRandNum[i]);
				changeImage(clsImgA, clsImgB);
			}
		} catch (Exception ex) {
			Log.i(TAG, "***** mixImage() Error: " + ex.getMessage());
		}
	}

	// Function: tao mang đã trộn các hình
	public int[] randCoord() {
		int rnd;
		int num = 5;
		// Random r = new Random();

		int[][] arr = new int[num][24];
		arr[0] = new int[] { 18, 15, 10, 2, 5, 4, 14, 0, 23, 17, 9, 1, 21, 16, 19, 13, 8, 6, 22, 3, 11, 7, 12, 20 }; // ok
		arr[1] = new int[] { 14, 4, 22, 17, 8, 21, 2, 20, 13, 9, 12, 7, 1, 10, 11, 6, 0, 15, 18, 19, 23, 3, 5, 16 }; // ok
		arr[2] = new int[] { 7, 11, 16, 3, 22, 10, 23, 19, 9, 21, 20, 0, 6, 14, 2, 17, 5, 13, 18, 15, 12, 8, 1, 4 };
		arr[3] = new int[] { 8, 13, 22, 15, 2, 20, 6, 9, 16, 0, 18, 4, 23, 11, 19, 5, 21, 1, 14, 17, 10, 12, 3, 7 };
		arr[4] = new int[] { 2, 21, 12, 0, 11, 17, 7, 22, 5, 20, 13, 16, 8, 19, 4, 3, 15, 23, 9, 14, 18, 10, 6, 1 };

		// rnd = randInt(num);
		rnd = 1;
		Log.i(TAG, "*****randCoord() rand=: " + rnd);
		return arr[rnd];
	}

	// Function:Random
	// Para: pham vi phat sinh
	// Return: tra va so ngau nhien
	public int randInt(int num) {
		Random r = new Random();
		return r.nextInt(num);
	}

	public void handleActionDown(int x, int y) {

		clsImage clsT;
		clsImageCtrl clsCtrl;
		try {

			clsCtrl = searchCoordCtrl(x, y);
			if (clsCtrl != null) {
				switch (clsCtrl.getCode()) {
				case 0:
					// ve hinh goc
					drawImage(bitmap);
					// khong ve hinh da cat
					bMove = false;
				case 1:
					// khong ve hinh da cat
					bMove = true
					// trộn hình
					mixImage();
					// ve lai hinh sau khi trộn
					drawAll();
				case 2:
					// xem hinh kế
					nPos++;
					if (nPos >= nImage)
						nPos = 0;
					bitmap = loadImage(nPos);
					drawAll();
				case 3:
					// duoc di chuyen hinh
					bMove = true;
					drawAll();
				}
				return;
			}

			// khong di chuyen hinh
			if (bMove == false)
				return;

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
