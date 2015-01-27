package bealder.natixis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class TutorialActivity extends SlideActivity {
		/**
		 * The number of pages to show in this tutorial.
		 */
		private final int NUM_PAGES = 3;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_tutorial);

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

						Fragment page = new TutorialSlideFragment();
						page.setArguments(bundle);
						return page;
				}

				@Override
				public int getCount() {
						return NUM_PAGES;
				}
		}
}