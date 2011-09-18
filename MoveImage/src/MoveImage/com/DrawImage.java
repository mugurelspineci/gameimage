package MoveImage.com;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.util.Log;

public class DrawImage extends Activity {

	private static final String TAG = DrawImage.class.getSimpleName();
	ArrayList<clsImage> lstImgs;
	ArrayList<clsImageCtrl> lstImgCtrl;
	int[][] arrCoords;
	int width = 80; // The width of the bitmap
	int height = 80;// The height of the bitmap

	int nX; // The number of rows
	int nY; // The number of columns

	int beginX = 10; // The X-coordinates of image start
	int beginY = 2; // The Y-coordinates of image start

	int scaleWidth = 320; // The new bitmap's desired width.
	int scaleHeight = 480; // The new bitmap's desired height

	int optImage; // opacity image
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
		// so hinh
		nImage = 4;

		// duoc di chuyen
		bMove = true;
		// int i;
		try {

			// chieu rong cua hinh cat nhỏ
			width = ImageActivity.scnWidth / 6;
			// width = 30;
			height = width;

			// chieu rong cua hinh lớn
			scaleWidth = width * 4;
			scaleHeight = height * 6;

			beginX = width / 2 + 10;
			// beginY = ImageActivity.scnHeight / 20;

			// random 0->4
			nPos = randInt(nImage - 1);
			// Load random image
			bitmap = loadImage(nPos);
			// bitmap = loadImage(0);

			if (bitmap == null)
				return;

			lstImgs = new ArrayList<clsImage>();
			lstImgCtrl = new ArrayList<clsImageCtrl>();

			// Luu cac hinh cat (nhau nhien)
			saveImage();

			// Tạo các hinh de dieu khien (xem hinh hien tai, trộn hinh, xem hinh ke)
			loadImageCtrl();
		} catch (Exception ex) {
			Log.i(TAG, "*****init() Error: " + ex.getMessage());
		}
	}

	// Function: Luu hinh da tron
	private boolean saveImage() {
		int l = 0;
		int arrRandNum[]; // Luu day so ngau nhien tu 0->23
		clsImage clsImg;
		int codeT;
		try {
			lstImgs.clear();
			// lstImgs= null;
			// Luu day so ngau nhien tu 0->23
			arrRandNum = randCoord();
			// Luu 24 hinh da cat
			arrBitmap = splitImage(bitmap);
			if (arrBitmap == null) {
				Log.i(TAG, "*****saveImage() khong co hinh da cat trong mang");
				return false;
			}

			for (int r = 0; r < nY; r++) {
				for (int c = 0; c < nX; c++) {
					// lay ma so ngau nhien trong mang
					// clsImg = new clsImage(l);
					clsImg = new clsImage(l, arrRandNum[l]);
					// luu hinh ngau nhien
					clsImg.setBitmap(arrBitmap[arrRandNum[l]]);
					// luu toa do hien thi
					clsImg.setX(arrCoords[l][0]);
					clsImg.setY(arrCoords[l][1]);
					clsImg.setShow(true);
					// save code
					if (l >= nX)
						clsImg.setImgT(l - 4); // luu code cua hinh o tren
					if (l < (nY * nX) - 4)
						clsImg.setImgB(l + 4); // luu code cua hinh o duoi
					if (c > 0)
						clsImg.setImgL(l - 1); // luu code cua hinh o trai
					if (c < nX - 1)
						clsImg.setImgR(l + 1); // luu code cua hinh o phai

					lstImgs.add(clsImg);
					l++;
				}
			}

			// codeT = (nY - 1) * nX;
			codeT = nY * nX - 1;
			clsImg = (clsImage) lstImgs.get(codeT);
			// set opacity
			clsImg.setShow(false);
			lstImgs.set(codeT, clsImg);
			Log.i(TAG, "***** saveImage() called: " + ImageActivity.scnWidth + ", " + ImageActivity.scnHeight);
			// Log.i(TAG, "***** init() called ");
			return true;
		} catch (Exception ex) {
			Log.i(TAG, "***** saveImage() Error: " + ex.getMessage());
		}
		return false;
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
				// Log.i(TAG, "***** drawAll() ");
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
			Log.i(TAG, "***** drawAll() Error: " + ex.getMessage());
		} finally {
			if (canvas != null)
				surfaceHolder.unlockCanvasAndPost(canvas);
		}
		// Log.i(TAG, "***** drawAll() called ");
	}

	// Function: draw line
	private void drawLine(Canvas canvas) {
		// Rect rect;
		Paint paint;
		int x, y;
		try {
			// canvas.drawColor(Color.BLACK);
			// rect = new Rect(beginX, beginY, scaleWidth, scaleHeight);
			paint = new Paint();
			paint.setColor(Color.WHITE);
			// ve duong doc
			x = beginX;
			y = beginY;
			for (int i = 0; i <= 4; i++) {
				canvas.drawLine(x, y, x, height * 6, paint);
				x += width;
			}

			x = beginX;
			// ve duong ngang
			for (int i = 0; i <= 6; i++) {
				canvas.drawLine(x, y, (width * 5) - 5, y, paint);
				y += height;
			}

		} catch (Exception ex) {
			Log.i(TAG, "***** drawBackground() Error: " + ex.getMessage());
		} finally {

		}
	}

	// Function: draw control
	private void drawControl(Canvas canvas) {
		clsImageCtrl clsCtrl;
		int i = 0;
		try {
			// canvas.drawColor(Color.BLACK);
			for (i = 0; i < lstImgCtrl.size() - 1; i++) {

				clsCtrl = (clsImageCtrl) lstImgCtrl.get(i);
				// clsCtrl = (clsImageCtrl) lstImgCtrl.get(0);
				clsCtrl.draw(canvas, null);
				// Log.i(TAG, "***** drawControl(): " + i + ",xy: " +
				// clsCtrl.getX() + "," + clsCtrl.getY());
			}
			if (i == lstImgCtrl.size() - 1 && bMove == false) {
				clsCtrl = (clsImageCtrl) lstImgCtrl.get(i);
				clsCtrl.draw(canvas, null);
			}

		} catch (Exception ex) {
			Log.i(TAG, "***** drawControl() Error: " + ex.getMessage() + ", " + i);
		}
	}

	// Function: tao hinh de dieu khien (xem hinh hien tai, trộn hinh, xem hinh ke)
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
			x = 3;
			// y = 70;
			y = beginY + height;
			bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.view);
			clsCtrl = new clsImageCtrl(i);
			clsCtrl.setBitmap(Bitmap.createScaledBitmap(bitmapT, widthCtrl, heightCtrl, true));
			// clsCtrl.setXY(x, ImageActivity.scnHeight - y);
			clsCtrl.setXY(x, y);
			// clsCtrl.setXY(x, y);
			lstImgCtrl.add(clsCtrl);

			// image mix
			// x += bitmapT.getWidth() + 15;
			y += beginY + height / 2;
			i++;
			bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.mix);
			clsCtrl = new clsImageCtrl(i);
			clsCtrl.setBitmap(Bitmap.createScaledBitmap(bitmapT, widthCtrl, heightCtrl, true));
			// clsCtrl.setXY(x, ImageActivity.scnHeight - y);
			clsCtrl.setXY(x, y);
			lstImgCtrl.add(clsCtrl);

			// image change
			// x += bitmapT.getWidth() + 5;
			y += beginY + height / 2;
			i++;
			bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.change);
			clsCtrl = new clsImageCtrl(i);
			clsCtrl.setBitmap(Bitmap.createScaledBitmap(bitmapT, widthCtrl, heightCtrl, true));
			// clsCtrl.setXY(x, ImageActivity.scnHeight - y);
			clsCtrl.setXY(x, y);
			lstImgCtrl.add(clsCtrl);

			// image Close
			i++;
			bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.close);
			clsCtrl = new clsImageCtrl(i);
			clsCtrl.setBitmap(Bitmap.createScaledBitmap(bitmapT, widthCtrl, heightCtrl, true));
			clsCtrl.setXY(scaleWidth + width - 10, beginY);
			lstImgCtrl.add(clsCtrl);

		} catch (Exception ex) {
			Log.i(TAG, "***** loadImageCtrl() Error: " + ex.getMessage());
		}
	}

	// function: cat thanh nhieu hinh nho, tạo tọa độ cho hinh
	// return: array
	private Bitmap[] splitImage(Bitmap picture) {
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
			// Log.i(TAG, "***** splitImage() called ");
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
	private Bitmap loadImage(int num) {
		Bitmap bitmapT = null;
		try {
			switch (num) {
			case 0:
				bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img1);
				break;
			case 1:
				bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img2);
				break;
			case 2:
				bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img3);
				break;
			case 3:
				bitmapT = BitmapFactory.decodeResource(gamepanel.getResources(), R.drawable.img4);
				break;
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
	private clsImage searchCoord(int x, int y) {
		clsImage clsImgT;
		try {
			for (int i = 0; i < lstImgs.size(); i++) {
				clsImgT = (clsImage) lstImgs.get(i);
				if ((clsImgT.getX() <= x && clsImgT.getX() + width >= x)
						&& (clsImgT.getY() <= y && clsImgT.getY() + height >= y)) {
					return clsImgT;
				}
			}
			// Log.i(TAG, "***** searchCoord() called ");
		} catch (Exception ex) {
			Log.i(TAG, "***** searchCoord() Error: " + ex.getMessage());
		}
		return null;
	}

	// Function: Tìm hình có tọa độ tương ứng
	// para: truyen vao tọa độ x,y
	// return: tra ve lớp hình có toa do gan đúng với tọa độ truyền vào
	private clsImageCtrl searchCoordCtrl(int x, int y) {
		clsImageCtrl clsImgT;
		try {
			for (int i = 0; i < lstImgCtrl.size(); i++) {
				clsImgT = lstImgCtrl.get(i);

				if ((clsImgT.getX() <= x && clsImgT.getX() + clsImgT.getBitmap().getWidth() >= x)
						&& (clsImgT.getY() <= y && clsImgT.getY() + clsImgT.getBitmap().getHeight() >= y)) {
					return clsImgT;
				}
			}

		} catch (Exception ex) {
			Log.i(TAG, "***** searchCoordCtrl() Error: " + ex.getMessage());
		}
		return null;
	}

	// Function: Di chuyen hinh
	// Para: clsChange
	private boolean moveImage(clsImage clsChange) {
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
	private boolean changeImage(clsImage clsA, clsImage clsB) {
		Bitmap bitmap;
		boolean show;
		int num;
		try {

			bitmap = clsB.getBitmap();
			show = clsB.isShow();
			num = clsB.getNum();

			clsB.setBitmap(clsA.getBitmap());
			clsB.setShow(clsA.isShow());
			clsB.setNum(clsA.getNum());

			clsA.setBitmap(bitmap);
			clsA.setShow(show);
			clsA.setNum(num);

			lstImgs.set(clsA.getCode(), clsA);
			lstImgs.set(clsB.getCode(), clsB);

			return true;
		} catch (Exception ex) {
			Log.i(TAG, "***** changeImage() Error: " + ex.getMessage());
			return false;
		}
	}

	// Function: Kiem tra game da hoan thanh chua
	private void checkFinish() {
		clsImage clsImgT;
		try {
			for (int i = 0; i < lstImgs.size(); i++) {
				clsImgT = (clsImage) lstImgs.get(i);
				if (clsImgT.getNum() != i)
					return;
			}
			//dialogMsgImage
			gamepanel.dialogMsgImg("Congratulation!!!, Are you want to next game?");
			
			//gamepanel.nextImage();
			//Log.i(TAG, "***** checkFinish(): Next game" );

			
		} catch (Exception ex) {
			Log.i(TAG, "***** checkFinish() Error: " + ex.getMessage());
		}
	}

	// Function: tao mang đã trộn các hình
	private int[] randCoord() {
		int rnd;
		int num = 5;
		// Random r = new Random();

		int[][] arr = new int[num][16];
		arr[0] = new int[] { 18, 15, 10, 2, 5, 4, 14, 0, 17, 9, 1, 21, 16, 19, 13, 8, 6, 22, 3, 11, 7, 12, 20, 23 }; // ok
		arr[1] = new int[] { 14, 4, 22, 17, 8, 21, 2, 20, 13, 9, 12, 7, 1, 10, 11, 6, 0, 15, 18, 19, 3, 5, 16, 23 }; // ok
		arr[2] = new int[] { 7, 11, 16, 3, 22, 19, 10, 21, 20, 9, 0, 6, 14, 2, 17, 5, 13, 18, 15, 12, 8, 1, 4, 23 }; // ok
		arr[3] = new int[] { 8, 13, 22, 15, 2, 20, 6, 9, 16, 0, 18, 4, 11, 19, 5, 21, 1, 14, 17, 10, 12, 3, 7, 23 }; // ok
		arr[4] = new int[] { 17, 21, 12, 0, 11, 2, 7, 22, 5, 20, 13, 16, 8, 19, 4, 3, 15, 9, 14, 18, 10, 6, 1, 23 }; // ok

		rnd = randInt(num);
		// rnd = 2;
		Log.i(TAG, "*****randCoord() rand=: " + rnd);
		return arr[rnd];
	}

	// Function:Random
	// Para: pham vi phat sinh
	// Return: tra va so ngau nhien
	private int randInt(int num) {
		Random r = new Random();
		return r.nextInt(num);
	}

	// Function: Hien thi hinh ke
	public void nextImage() {
		nPos++;
		if (nPos >= nImage)
			nPos = 0;
		bitmap = loadImage(nPos);
		bMove = true;
		// Luu hinh da tron
		saveImage();
		// ve lai hinh
		drawAll();
	}

	public void handleActionDown(int x, int y) {

		clsImage clsT;
		clsImageCtrl clsCtrl;
		try {

			clsCtrl = searchCoordCtrl(x, y);
			if (clsCtrl != null) {
				switch (clsCtrl.getCode()) {
				case 0:
					// 0:xem hinh goc
					if (bMove == true) {
						// khong di chuyen
						bMove = false;
						// xem hinh goc
						drawImage(bitmap);
					} else {
						bMove = true;
						drawAll();
					}
					// Log.i(TAG, "***** handleActionDown() hinh goc ");
					break;

				case 1:
					// 1: tron hinh
					// khong ve hinh da cat
					bMove = true;
					// trộn hình
					// mixImage();
					saveImage();
					// ve lai hinh sau khi trộn
					drawAll();
					// Log.i(TAG, "***** handleActionDown() tron hinh");
					break;
				case 2:
					// 2: xem hinh kế
					nextImage();
					// Log.i(TAG, "***** handleActionDown() hinh ke " + nPos);
					break;
				case 3:
					// 3: chon hinh close
					// duoc di chuyen hinh
					bMove = true;
					drawAll();
					// Log.i(TAG, "***** handleActionDown() hinh ke ");
					break;
				}
				return;
			}

			// khong di chuyen hinh
			if (bMove == false)
				return;
			// Log.i(TAG, "***** handleActionDown() di chuyen ");
			clsT = searchCoord(x, y);
			// search not found
			if (clsT == null) {
				//Log.i(TAG, "***** handleActionDown() khong co toa do: " + x + "," + y);
				return;
			}

			// di chuyen hinh
			if (moveImage(clsT) == false) {
				//Log.i(TAG, "***** handleActionDown() khong co hinh: " + clsT.getCode() + "L" + clsT.getImgL() + ", R"
				//		+ clsT.getImgR() + ", T" + clsT.getImgT() + ", B" + clsT.getImgB());
				return;
			}

			drawAll();

			// Kiem tra hinh da sap dung thu tu chua
			checkFinish();

			// Log.i(TAG, "***** handleActionDown() called ");
		} catch (Exception ex) {
			Log.i(TAG, "***** handleActionDown() Error: " + ex.getMessage());
		}
	}
}
