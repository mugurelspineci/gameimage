package MoveImage.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class ImageActivity extends Activity {
    /** Called when the activity is first created. */
	public static int scnWidth;
	public static int scnHeight;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
                
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        scnWidth = dm.widthPixels;
        scnHeight= dm.heightPixels;
        
        setContentView(new MainGamePanel(this));
    }
}