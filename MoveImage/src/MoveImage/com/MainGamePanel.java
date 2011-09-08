package MoveImage.com;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//
//This is the main surface that handles the ontouch events and draws the image to the screen.
//
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	private DrawImage drawImage;
	public MainGamePanel(Context context) {		
		super(context);
		//Bitmap bitmap;
		// TODO Auto-generated constructor stub
		try{
			getHolder().addCallback(this);
			//bitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.img);
			//bitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.droid_1);
			drawImage = new DrawImage(getHolder(), this);
			
			//Log.d(TAG, "Contructor created");
		}catch(Exception e){
			Log.d(TAG, "Contructor Error");
		}
		
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		drawImage.drawAll();
		//drawImage.drawAlpha(20);
		//drawImage.draw(0);
		//drawImage.draw(4);
		//drawImage.draw(8);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.i(TAG, "_____onTouchEvent.ACTION_DOWN");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the DrawImage
			//Log.i(TAG, "_____onTouchEvent.ACTION_DOWN");
			drawImage.handleActionDown((int)event.getX(), (int)event.getY());
			
			// check if in the lower part of the screen we exit
			//if (event.getY() > getHeight() - 50) {
			//	thread.setRunning(false);
			//	((Activity)getContext()).finish();
			//} else {
			//	Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			//}
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			//if (droid.isTouched()) {
			//	// the droid was picked up and is being dragged
			//	droid.setX((int)event.getX());
			//	droid.setY((int)event.getY());
			//}
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			//if (droid.isTouched()) {
			//	droid.setTouched(false);
			//}
		}
		
		return true;
	}

}
