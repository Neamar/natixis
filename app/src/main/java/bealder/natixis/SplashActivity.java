package bealder.natixis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends Activity {

		// Splash screen timer
		private static int SPLASH_TIME_OUT = 1500;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_splash);

				// Start in the background the service to download the Weather
				startService(new Intent(this, DownloadWeatherService.class));

				new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer.
             */

						@Override
						public void run() {
								// Start main activty
								Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
								startActivity(i);

								// close this activity
								finish();
						}
				}, SPLASH_TIME_OUT);
		}
}
