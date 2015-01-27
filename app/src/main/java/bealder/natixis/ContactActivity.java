package bealder.natixis;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ContactActivity extends Activity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_contact);

				new MixinMenuActivity().addMenuListeners(this);

				addSocialLink(R.id.contact_facebook, "https://fr-fr.facebook.com/grandtourmalet");
				addSocialLink(R.id.contact_twitter, "https://twitter.com/GrandTourmalet");
				addSocialLink(R.id.contact_instagram, "http://instagram.com/grandtourmalet/");

				TextView contact = (TextView) findViewById(R.id.contact);
				contact.setText(Html.fromHtml(getString(R.string.contact)));
				contact.setMovementMethod(LinkMovementMethod.getInstance());

				TextView phones = (TextView) findViewById(R.id.phones);
				phones.setText(Html.fromHtml(getString(R.string.telephones)));
		}

		private void addSocialLink(int id, final String url) {
				ImageView socialButton = (ImageView) findViewById(id);
				socialButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								Intent i = new Intent(Intent.ACTION_VIEW);
								i.setData(Uri.parse(url));
								startActivity(i);
						}
				});
		}
}
