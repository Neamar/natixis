package bealder.natixis;

/**
 * Created by neamar on 12/13/14.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by neamar on 12/13/14.
 */
public class TutorialSlideFragment extends Fragment {
		public int[] PAGES = new int[]{R.layout.fragment_tutorial_slide_1, R.layout.fragment_tutorial_slide_2, R.layout.fragment_tutorial_slide_3};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
				int currentPage = getArguments().getInt("page", 1);

				ViewGroup rootView = (ViewGroup) inflater.inflate(
								PAGES[currentPage], container, false);

				ImageView closeTutorial = (ImageView) rootView.findViewById(R.id.close_tutorial);
				if (closeTutorial != null) {
						closeTutorial.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
										getActivity().finish();
								}
						});
				}
				return rootView;
		}
}