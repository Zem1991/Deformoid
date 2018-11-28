package deformoid.mecanica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread splashTimer =  new Thread(){
			public void run(){
				try{
					sleep(3000);
					Intent it = new Intent("com.deformoid.mecanica.MAINACTIVITY");
					startActivity(it);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					finish();
				}
			}
		};
		splashTimer.start();
	}
}
