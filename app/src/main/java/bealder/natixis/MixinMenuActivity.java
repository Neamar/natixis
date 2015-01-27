package bealder.natixis;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by neamar on 12/13/14.
 */
public class MixinMenuActivity {
		public void addMenuListeners(Activity currentActivity) {
				if (!(currentActivity instanceof WelcomeActivity)) {
						addMenuListener(currentActivity, R.id.menu_welcome, WelcomeActivity.class);
				}
				if (!(currentActivity instanceof AboutActivity)) {
						addMenuListener(currentActivity, R.id.menu_about, AboutActivity.class);
				}
				if (!(currentActivity instanceof MentionActivity)) {
						addMenuListener(currentActivity, R.id.menu_mentions, MentionActivity.class);
				}
		}

		private void addMenuListener(final Activity currentActivity, int id, final Class activityToOpen) {
				ImageView contactButton = (ImageView) currentActivity.findViewById(id);
				contactButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								Intent i = new Intent(currentActivity, activityToOpen);
								i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
								currentActivity.startActivity(i);
						}
				});
		}
}
