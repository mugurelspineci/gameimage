package MoveImage.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ImageActivity extends Activity {
    /** Called when the activity is first created. */
	public static int scnWidth;
	public static int scnHeight;
	private MainGamePanel gamePanel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
                
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        scnWidth = dm.widthPixels;
        scnHeight= dm.heightPixels;
        
        //setContentView(new MainGamePanel(this));
		gamePanel = new MainGamePanel(this);
		setContentView(gamePanel);
    }
	
	public void dialogMsg(String dg){
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("Congratulation!!!, Are you want to next game?").setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'Yes' Button
						gamePanel.nextImage();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'NO' Button
						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle("You win!!!");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();
	}
}