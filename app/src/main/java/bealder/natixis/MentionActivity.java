package bealder.natixis;

import android.app.Activity;
import android.os.Bundle;


public class MentionActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_webview);

				new MixinMenuActivity().addMenuListeners(this);
		}
}
