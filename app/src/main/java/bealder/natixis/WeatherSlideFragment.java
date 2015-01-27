package bealder.natixis;

/**
 * Created by neamar on 12/13/14.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by neamar on 12/13/14.
 */
public class WeatherSlideFragment extends Fragment {
		public int[] ICONS = new int[]{
						R.drawable.weather_0,
						R.drawable.weather_1,
						R.drawable.weather_2,
						R.drawable.weather_3,
						R.drawable.weather_4,
						R.drawable.weather_5,
						R.drawable.weather_6,
						R.drawable.weather_7,
						R.drawable.weather_8,
						R.drawable.weather_9,
						R.drawable.weather_10,
						R.drawable.weather_11,
						R.drawable.weather_12,
						R.drawable.weather_13,
						R.drawable.weather_14,
						R.drawable.weather_15,
						R.drawable.weather_16,
						R.drawable.weather_17,
						R.drawable.weather_18,
						R.drawable.weather_19,
						R.drawable.weather_20,
						R.drawable.weather_21,
						R.drawable.weather_22,
						R.drawable.weather_23,
						R.drawable.weather_24,
						R.drawable.weather_25,
						R.drawable.weather_26,
						R.drawable.weather_27,
						R.drawable.weather_28,
						R.drawable.weather_29,
						R.drawable.weather_30,
						R.drawable.weather_31,
						R.drawable.weather_32,
						R.drawable.weather_33,
						R.drawable.weather_34,
						R.drawable.weather_35,
						R.drawable.weather_36,
						R.drawable.weather_37,
						R.drawable.weather_38,
						R.drawable.weather_39,
						R.drawable.weather_40,
						R.drawable.weather_41,
						R.drawable.weather_42,
						R.drawable.weather_43,
						R.drawable.weather_44,
						R.drawable.weather_45,
						R.drawable.weather_46,
						R.drawable.weather_47
		};

		// See https://developer.yahoo.com/weather/documentation.html#codes
		public String[] WEATHER_CODES = new String[]{
						"Tornade",
						"Tempête tropicale",
						"Typhon",
						"Gros orages",
						"Tempêtes",
						"Pluie et neige",
						"Pluie et grésil",
						"Neige et grésil",
						"Bruine verglaçante",
						"Bruine",
						"Pluie verglaçante",
						"Averses",
						"Averses",
						"Tempête de neige",
						"Neige légère",
						"Poudreuse",
						"Neige",
						"Grêle",
						"Neige fondue",
						"Poussière",
						"Brumeux",
						"Brume",
						"Enfumé",
						"Nuageux",
						"Venteux",
						"Froid",
						"Très nuageux",
						"Partiellement nuageux",
						"Partiellement nuageux",
						"Partiellement nuageux",
						"Partiellement nuageux",
						"Temps clair",
						"Ensoleillé",
						"Beau temps",
						"Beau temps",
						"Pluie et grêle",
						"Chaud",
						"Orages isolés",
						"Orages",
						"Orages",
						"Averses éparses",
						"Forte chute de neige",
						"Averses de neige",
						"Forte chute de neige",
						"Partiellement nuageux",
						"Averses orageuses",
						"Averses de neige",
						"Orages isolés"
		};

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {
				int currentPage = getArguments().getInt("page", 1);

				final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weather, container, false);

				// Set the station name
				TextView stationName = (TextView) rootView.findViewById(R.id.station);
				stationName.setText(DownloadWeatherService.STATIONS[currentPage]);

				// Retrieve the weather
				final int woeid = DownloadWeatherService.WOEIDS[currentPage];
				final SharedPreferences weatherData = getActivity().getSharedPreferences("weather", Context.MODE_PRIVATE);

				// Can we display the data right now?
				if (cacheIsUpToDate(weatherData, woeid)) {
						displayData(rootView, weatherData, woeid);
				} else {
						// If we can't, wait for signaling
						displayPlaceholder(rootView);

						weatherData.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
								@Override
								public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
										if (s.equals(woeid + "-update") && cacheIsUpToDate(sharedPreferences, woeid)) {
												displayData(rootView, sharedPreferences, woeid);
										}
								}
						});
				}

				return rootView;
		}

		public boolean cacheIsUpToDate(SharedPreferences weatherData, int woeid) {
				return weatherData.getString(woeid + "-update", "").equals(DownloadWeatherService.getCurrentDateFormatted());
		}

		public void displayData(View rootView, SharedPreferences weatherData, int woeid) {
				// Information in the cache is up to date: display it
				JSONObject todayWeather = null;
				JSONObject tomorrowWeather = null;
				try {
						todayWeather = new JSONObject(weatherData.getString(woeid + "-today", "{}"));
						tomorrowWeather = new JSONObject(weatherData.getString(woeid + "-tomorrow", "{}"));
				} catch (JSONException e) {
						e.printStackTrace();
				}

				displayIcons(rootView, todayWeather, tomorrowWeather);
		}

		public void displayIcons(View rootView, JSONObject todayWeather, JSONObject tomorrowWeather) {
				try {
						int todayCode = Integer.parseInt(todayWeather.getString("code"));

						ImageView todayWeatherIcon = (ImageView) rootView.findViewById(R.id.today_weather_image);
						todayWeatherIcon.setImageResource(ICONS[todayCode]);

						TextView todayWeatherText = (TextView) rootView.findViewById(R.id.today_weather_text);
						todayWeatherText.setText("min. " + todayWeather.getString("low") + "°C\nmax. " + todayWeather.getString("high") + "°C\n" + WEATHER_CODES[todayCode]);

						int tomorrowCode = Integer.parseInt(todayWeather.getString("code"));

						ImageView tomorrowWeatherIcon = (ImageView) rootView.findViewById(R.id.tomorrow_weather_image);
						tomorrowWeatherIcon.setImageResource(ICONS[tomorrowCode]);

						TextView tomorrowWeatherText = (TextView) rootView.findViewById(R.id.tomorrow_weather_text);
						tomorrowWeatherText.setText("min. " + tomorrowWeather.getString("low") + "°C\nmax. " + tomorrowWeather.getString("high") + "°C\n" + WEATHER_CODES[tomorrowCode]);

				} catch (JSONException e) {
						e.printStackTrace();
				}
		}

		public void displayPlaceholder(View rootView) {
				ImageView todayWeatherIcon = (ImageView) rootView.findViewById(R.id.today_weather_image);
				todayWeatherIcon.setImageResource(R.drawable.weather_unknown);

				ImageView tomorrowWeatherIcon = (ImageView) rootView.findViewById(R.id.tomorrow_weather_image);
				tomorrowWeatherIcon.setImageResource(R.drawable.weather_unknown);

				TextView todayWeatherText = (TextView) rootView.findViewById(R.id.today_weather_text);
				todayWeatherText.setText("Non disponible");

				TextView tomorrowWeatherText = (TextView) rootView.findViewById(R.id.tomorrow_weather_text);
				tomorrowWeatherText.setText("Non disponible");
		}
}