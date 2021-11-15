package com.verNANDo57.rulebook_educational.rules.dictionaries;

import static com.verNANDo57.rulebook_educational.AppUtils.LOG_TAG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.verNANDo57.rulebook_educational.BottomNavAmongActivitiesFragment;
import com.verNANDo57.rulebook_educational.customthemeengine.app.CustomThemeEngineAppCompatActivity;
import com.verNANDo57.rulebook_educational.extradata.R;
import com.verNANDo57.rulebook_educational.rules.AppExtraBooleans;

public class AppDictionaries extends CustomThemeEngineAppCompatActivity {
    private AppExtraBooleans booleansInMainRules;

    public void onCreate(Bundle savedInstanceState) {
        booleansInMainRules = new AppExtraBooleans(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_dictionaries);

        Toolbar toolbar = findViewById(R.id.toolbar_in_dictionaries);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new NavigationView.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavAmongActivitiesFragment BottomNavBetweenActivities = new BottomNavAmongActivitiesFragment();
                BottomNavBetweenActivities.show(getSupportFragmentManager(), LOG_TAG);
            }
        });

        Intent AppDictionariesScrollableActivity = new Intent(this, AppDictionariesScrollableActivity.class);

        Button vocabulary_words = findViewById(R.id.vocabulary_words);
        vocabulary_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleansInMainRules.setRulebookMainRulesFragmentOpenBoolean("vocabulary_words");
                startActivity(AppDictionariesScrollableActivity);
            }
        });

        Button phrasebook = findViewById(R.id.phrasebook);
        phrasebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleansInMainRules.setRulebookMainRulesFragmentOpenBoolean("phrasebook");
                startActivity(AppDictionariesScrollableActivity);
            }
        });

        Button orthoepical_dictionary = findViewById(R.id.orthoepical_dictionary);
        orthoepical_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleansInMainRules.setRulebookMainRulesFragmentOpenBoolean("orthoepical_dictionary");
                startActivity(AppDictionariesScrollableActivity);
            }
        });
    }
}
