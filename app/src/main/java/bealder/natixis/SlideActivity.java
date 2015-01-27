package bealder.natixis;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by neamar on 12/13/14.
 */
public class SlideActivity extends FragmentActivity {


		/**
		 * The pager widget, which handles animation and allows swiping horizontally to access previous
		 * and next wizard steps.
		 */
		private ViewPager mPager;

		/**
		 * The pager adapter, which provides the pages to the view pager widget.
		 */
		private PagerAdapter mPagerAdapter;


		protected void initPager(FragmentStatePagerAdapter adapter) {
				// Instantiate a ViewPager and a PagerAdapter.
				mPager = (ViewPager) findViewById(R.id.pager);
				mPagerAdapter = adapter;
				mPager.setAdapter(mPagerAdapter);

				mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
						@Override
						public void onPageScrolled(int i, float v, int i2) {

						}

						@Override
						public void onPageSelected(int i) {
								Log.i("PAGER-CHANGE", "Now displaying page " + i);
								ImageView dot;
								int[] dots = new int[]{R.id.dot_1, R.id.dot_2, R.id.dot_3};
								for (int dotResource : dots) {
										dot = (ImageView) findViewById(dotResource);
										dot.setImageResource(R.drawable.dot);
								}

								dot = (ImageView) findViewById(dots[i]);
								dot.setImageResource(R.drawable.dot_on);
						}

						@Override
						public void onPageScrollStateChanged(int i) {

						}
				});
		}

		@Override
		public void onBackPressed() {
				if (mPager.getCurrentItem() == 0) {
						// If the user is currently looking at the first step, allow the system to handle the
						// Back button. This calls finish() on this activity and pops the back stack.
						super.onBackPressed();
				} else {
						// Otherwise, select the previous step.
						mPager.setCurrentItem(mPager.getCurrentItem() - 1);
				}
		}
}
