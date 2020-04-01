package com.obiangetfils.kermashop.Intro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.obiangetfils.kermashop.CommonActivity.AddStoryActivity;
import com.obiangetfils.kermashop.CommonActivity.DisplayStoryActivity;
import com.obiangetfils.kermashop.CommonActivity.RegisterActivity;
import com.obiangetfils.kermashop.R;
import com.obiangetfils.kermashop.utills.Ecom01ThemesDialog;


public class IntroScreen extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Ecom01ThemesDialog.getcolorList().get(Ecom01ThemesDialog.selectedTheme).getThemeID());
        super.onCreate(savedInstanceState);

        addSlide(new Intro_Slide_1());
        addSlide(new Intro_Slide_2());
        addSlide(new Intro_Slide_3());
        addSlide(new Intro_Slide_4());
        addSlide(new Intro_Slide_5());

        // Hide StatusBar
        showStatusBar(false);
        showSkipButton(true);
        setProgressButtonEnabled(true);

        setBarColor(ContextCompat.getColor(IntroScreen.this, R.color.white));
        setSeparatorColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimaryLight));

        setColorDoneText(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));
        setColorSkipButton(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));
        setNextArrowColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary));

        setIndicatorColor(ContextCompat.getColor(IntroScreen.this, R.color.colorPrimary),
                ContextCompat.getColor(IntroScreen.this, R.color.iconsLight));
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(IntroScreen.this, DisplayStoryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }


    //*********** Called when the Activity has detected the User pressed the Back key ********//

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Finish this Activity
        finish();


    }
}
