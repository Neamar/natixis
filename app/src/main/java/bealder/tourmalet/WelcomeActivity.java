package bealder.tourmalet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


public class WelcomeActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_welcome);
		}


		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
				// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.menu_welcome, menu);
				return true;
		}
}
