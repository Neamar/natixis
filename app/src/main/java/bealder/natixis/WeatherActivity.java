package bealder.natixis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;


public class WeatherActivity extends SlideActivity {
		/**
		 * The number of weather station to show
		 */
		private final int NUM_PAGES = DownloadWeatherService.STATIONS.length;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_weather);

				new MixinMenuActivity().addMenuListeners(this);

				TextView dateText = (TextView) findViewById(R.id.date);

				String dateString = DownloadWeatherService.getCurrentDateFormatted();

				dateString = dateString.substring(0, 1).toUpperCase() + dateString.substring(1).toLowerCase();
				dateText.setText(dateString);

				initPager(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
		}


		protected class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
				public ScreenSlidePagerAdapter(FragmentManager fm) {
						super(fm);
				}

				@Override
				public Fragment getItem(int position) {
						Bundle bundle = new Bundle();
						bundle.putInt("page", position);

						Fragment page = new WeatherSlideFragment();
						page.setArguments(bundle);
						return page;
				}

				@Override
				public int getCount() {
						return NUM_PAGES;
				}
		}
}
