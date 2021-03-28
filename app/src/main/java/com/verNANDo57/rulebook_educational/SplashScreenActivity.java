package com.verNANDo57.rulebook_educational;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.verNANDo57.rulebook_educational.customthemeengine.app.CustomThemeEngineAppCompatActivity;
import com.verNANDo57.rulebook_educational.customthemeengine.utils.ColorUtils;
import com.verNANDo57.rulebook_educational.for_pills.R;
import com.verNANDo57.rulebook_educational.preferences.RulebookApplicationSharedPreferences;

public class SplashScreenActivity extends CustomThemeEngineAppCompatActivity
{
	RulebookApplicationSharedPreferences preferences;

	private ProgressBar Indicator1;
	private ProgressBar Indicator2;
	private ProgressBar Indicator3;

	private final int totalProgressTime = 10;

	public void onCreate(Bundle savedInstanceState)
    {
    	preferences =  new RulebookApplicationSharedPreferences(this);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.app_strangescreen);

		int BackgroundTintColor = ColorUtils.lighter(getResources().getColor(R.color.coloraccent), 0.01f);

		ImageView strangescreen_image = findViewById(R.id.strangescreen_image);

		Indicator1 = findViewById(R.id.appSplashScreenProgressBar1); //Indicator1(ProgressBar1)
		Indicator2 = findViewById(R.id.appSplashScreenProgressBar2); //Indicator2(ProgressBar2)
		Indicator3 = findViewById(R.id.appSplashScreenProgressBar3); //Indicator3(ProgressBar3)

		Indicator1.setIndeterminateTintList(ColorStateList.valueOf(BackgroundTintColor));
		Indicator2.setIndeterminateTintList(ColorStateList.valueOf(BackgroundTintColor));
		Indicator3.setIndeterminateTintList(ColorStateList.valueOf(BackgroundTintColor));

		Indicator1.setProgress(0); //Progress of Indicator1(ProgressBar1) at startup
		Indicator2.setProgress(0); //Progress of Indicator2(ProgressBar2) at startup
		Indicator3.setProgress(0); //Progress of Indicator3(ProgressBar3) at startup

		Indicator1.setMax(totalProgressTime);
		Indicator2.setMax(totalProgressTime);
		Indicator3.setMax(totalProgressTime);

		//Intent to MainActivity after splashscreen process ends
		final Intent StrangeActivityEnd = new Intent(this, MainActivity.class);

		//splashscreen process start
		new Thread(new Runnable() {
			public void run() {
				int counter = 0;
				while (counter < totalProgressTime) {
					try {
						Thread.sleep(50);
						counter++;
						Indicator1.setProgress(counter);
						Indicator2.setProgress(counter);
						Indicator3.setProgress(counter);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				startActivity(StrangeActivityEnd);
				finish();
			}
		}).start();
    }


}
