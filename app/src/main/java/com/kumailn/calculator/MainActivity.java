    package com.kumailn.calculator;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import org.mariuszgromada.math.mxparser.Expression;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;

//Kumail Naqvi June 5th 2017
public class MainActivity extends AppCompatActivity {
    public static String currentCalculation;
    public static String previousCalculation;
    public static String displayCalculation;
    public static String finalCalculation;
    public static String previousAns;
    public static Boolean exponentOn;
    public static Boolean firstExponent;
    public static Boolean pVisible;
    public static Boolean binPressed;
    //False == rad, true == deg
    public static Boolean angle;
    public static final String defaultMethod = "rad";
    public static int LOW_IMPORTANCE_KEY_VIBRATE = 50;
    public static int MED_IMPORTANCE_KEY_VIBRATE = 80;
    public static int HIGH_IMPORTANCE_KEY_VIBRATE = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize stetho
        Stetho.initializeWithDefaults(this);

        //Hide the action bar
        getSupportActionBar().hide();

        //Initialize widgets
        final Button zeroB = (Button)findViewById(R.id.zeroButton);
        final Button oneB = (Button)findViewById(R.id.oneButton);
        final Button twoB = (Button)findViewById(R.id.twoButton);
        final Button threeB = (Button)findViewById(R.id.threeButton);
        final Button fourB = (Button)findViewById(R.id.fourButton);
        final Button fiveB = (Button)findViewById(R.id.fiveButton);
        final Button sixB = (Button)findViewById(R.id.sixButton);
        final Button sevenB = (Button)findViewById(R.id.sevenButton);
        final Button eightB = (Button)findViewById(R.id.eightButton);
        final Button nineB = (Button)findViewById(R.id.nineButton);
        final Button decimalB = (Button)findViewById(R.id.decimalButton);
        final Button ansB = (Button)findViewById(R.id.ansButton);
        final Button multB = (Button)findViewById(R.id.multiplyButton);
        final Button minusB = (Button)findViewById(R.id.minusButton);
        final Button divB = (Button)findViewById(R.id.divideButton);
        final Button equalsB = (Button)findViewById(R.id.equalsButton);
        final Button plusB = (Button)findViewById(R.id.plusButton);
        final Button sinB = (Button)findViewById(R.id.sinButton);
        final Button tanB = (Button)findViewById(R.id.tanButton);
        final Button cosB = (Button)findViewById(R.id.cosButton);
        Button delB = (Button)findViewById(R.id.deleteButton);
        Button clrB = (Button)findViewById(R.id.clearButton);
        final Button bracketB = (Button) findViewById(R.id.bracketButton);
        final Button logB = (Button)findViewById(R.id.logButton);
        final Button lnB = (Button)findViewById(R.id.lnButton);
        final Button binButton = (Button)findViewById(R.id.bbb);
        final Button sqrtB = (Button)findViewById(R.id.sqrtButton);
        final Button popupB = (Button)findViewById(R.id.popupButton);
        final TextView calculationView = (TextView)findViewById(R.id.instantCalcluationView);
        final TextView pView = (TextView)findViewById(R.id.primeView);
        final Button angleB = (Button)findViewById(R.id.angleButton);
        final Button exponentB = (Button) findViewById(R.id.exponentButton);
        final TextView instantCalcView = (TextView)findViewById(R.id.calcView);
        final LinearLayout myLinear = (LinearLayout)findViewById(R.id.linearLayout);
        final ConstraintLayout myLayout = (ConstraintLayout)findViewById(R.id.myBackLayout);
        final RevealFrameLayout myReveal = (RevealFrameLayout)findViewById(R.id.revealFrameLayout);
        final View mvv = findViewById(R.id.awesome_card);

        final String versionName = BuildConfig.VERSION_NAME;
        
        final String versionName2 = BuildConfig.VERSION_NAME;
        
        final String versionName3 = BuildConfig.VERSION_NAME;

        //instantCalcView
        currentCalculation = " ";
        displayCalculation = " ";
        pVisible = false;
        exponentOn = false;
        pView.setVisibility(TextView.INVISIBLE);
        myReveal.setBackgroundColor(Color.TRANSPARENT);
        //android.support.v7.app.ActionBar bar = getSupportActionBar();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));

        try{
            previousAns = loadAnswer();
        }
        catch(Exception ff){

        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String vibrationChoice = sharedPreferences.getString("key_vibration_intensity_setting", "0");
        String themeChoice = sharedPreferences.getString("key_theme_setting", "0");
        Boolean vibrationSwitch = sharedPreferences.getBoolean("key_vibration_switch", true);
        Log.e("vibrate", vibrationChoice);

        //setTheme(Integer.valueOf(themeChoice));

        int numButtonColor = Color.WHITE;
        int cardBackgroundColor = getResources().getColor(R.color.lightRed);
        int backgroundColor = getResources().getColor(R.color.naturalBlack);
        int functionButtonColor = getResources().getColor(R.color.grayC);
        int equalsButtonColor = R.color.equalsButtonOriginal;

        //Change button colors depending on theme
        switch (Integer.valueOf(themeChoice)){
            case 1:
                numButtonColor = Color.BLACK;
                cardBackgroundColor = getResources().getColor(R.color.smallTextColor);
                backgroundColor = Color.WHITE;
                functionButtonColor = getResources().getColor(R.color.grayC);
                equalsButtonColor = R.color.smallTextColor;
                break;
            case 2:
                numButtonColor = getResources().getColor(R.color.fuschia);
                cardBackgroundColor = getResources().getColor(R.color.fuschiaPink);
                backgroundColor = getResources().getColor(R.color.backgroundFuschia);
                functionButtonColor = getResources().getColor(R.color.purple);
                equalsButtonColor = R.color.equalsButtonOriginal;
                break;
            case 3:
                numButtonColor = getResources().getColor(R.color.oceanBlue);
                cardBackgroundColor = getResources().getColor(R.color.lightbluee2);
                backgroundColor = getResources().getColor(R.color.lightbluee);
                functionButtonColor = getResources().getColor(R.color.oxfordBlue);
                equalsButtonColor = R.color.smallTextColor;
                break;
            case 4:
                numButtonColor = getResources().getColor(R.color.clearView3);
                cardBackgroundColor = getResources().getColor(R.color.clearView2);
                backgroundColor = getResources().getColor(R.color.clearView2);
                functionButtonColor = getResources().getColor(R.color.oxfordBlue);
                equalsButtonColor = R.color.equalsButtonOriginal;
        }

        //Set theme to the selected value
        mvv.setBackgroundColor(cardBackgroundColor);
        myLayout.setBackgroundColor(backgroundColor);
        myLinear.setBackgroundColor(backgroundColor);
        zeroB.setTextColor(numButtonColor);
        oneB.setTextColor(numButtonColor);
        twoB.setTextColor(numButtonColor);
        threeB.setTextColor(numButtonColor);
        fourB.setTextColor(numButtonColor);
        fiveB.setTextColor(numButtonColor);
        sixB.setTextColor(numButtonColor);
        sevenB.setTextColor(numButtonColor);
        eightB.setTextColor(numButtonColor);
        nineB.setTextColor(numButtonColor);
        decimalB.setTextColor(numButtonColor);
        ansB.setTextColor(numButtonColor);

        logB.setTextColor(functionButtonColor);
        sinB.setTextColor(functionButtonColor);
        cosB.setTextColor(functionButtonColor);
        tanB.setTextColor(functionButtonColor);
        binButton.setTextColor(functionButtonColor);
        exponentB.setTextColor(functionButtonColor);
        bracketB.setTextColor(functionButtonColor);
        sqrtB.setTextColor(functionButtonColor);
        lnB.setTextColor(functionButtonColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, equalsButtonColor));
        }

        if(vibrationSwitch) {
            switch (Integer.valueOf(vibrationChoice)) {
                case 0:
                    LOW_IMPORTANCE_KEY_VIBRATE = 5;
                    MED_IMPORTANCE_KEY_VIBRATE = 10;
                    HIGH_IMPORTANCE_KEY_VIBRATE = 15;
                case 1:
                    LOW_IMPORTANCE_KEY_VIBRATE = 30;
                    MED_IMPORTANCE_KEY_VIBRATE = 40;
                    HIGH_IMPORTANCE_KEY_VIBRATE = 50;
                case 2:
                    LOW_IMPORTANCE_KEY_VIBRATE = 50;
                    MED_IMPORTANCE_KEY_VIBRATE = 60;
                    HIGH_IMPORTANCE_KEY_VIBRATE = 70;

            }
        }
        else{
            LOW_IMPORTANCE_KEY_VIBRATE = 0;
            MED_IMPORTANCE_KEY_VIBRATE = 0;
            HIGH_IMPORTANCE_KEY_VIBRATE = 0;
        }

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       /* Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);*/


/*        Log.e("A", String.valueOf(myToolbar.getDrawingCacheBackgroundColor()));
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);*/

        if(loadAngle().equals("rad")){
            angleB.setText("RAD");
            angle = false;
        }
        else{
            angleB.setText("DEG");
            //angleB.setTextColor(getResources().getColor(R.color.degree));
            angle = true;
        }

        //Load theme - Not a seperate function because the compilation crashes?
        String themeNumber = loadTheme();
        if(themeNumber.equals("0f")){
            //Stealth Black
            mvv.setBackgroundColor(getResources().getColor(R.color.lightRed));
            myLayout.setBackgroundColor(getResources().getColor(R.color.naturalBlack));
            myLinear.setBackgroundColor(getResources().getColor(R.color.naturalBlack));
            zeroB.setTextColor(Color.WHITE);
            oneB.setTextColor(Color.WHITE);
            twoB.setTextColor(Color.WHITE);
            threeB.setTextColor(Color.WHITE);
            fourB.setTextColor(Color.WHITE);
            fiveB.setTextColor(Color.WHITE);
            sixB.setTextColor(Color.WHITE);
            sevenB.setTextColor(Color.WHITE);
            eightB.setTextColor(Color.WHITE);
            nineB.setTextColor(Color.WHITE);
            decimalB.setTextColor(Color.WHITE);
            ansB.setTextColor(Color.WHITE);
            logB.setTextColor(getResources().getColor(R.color.grayC));
            sinB.setTextColor(getResources().getColor(R.color.grayC));
            cosB.setTextColor(getResources().getColor(R.color.grayC));
            tanB.setTextColor(getResources().getColor(R.color.grayC));
            binButton.setTextColor(getResources().getColor(R.color.grayC));
            exponentB.setTextColor(getResources().getColor(R.color.grayC));
            bracketB.setTextColor(getResources().getColor(R.color.grayC));
            sqrtB.setTextColor(getResources().getColor(R.color.grayC));
            lnB.setTextColor(getResources().getColor(R.color.grayC));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.equalsButtonOriginal));
            }



        }
        else if(themeNumber.equals("f1")){
            //Minimal White
            mvv.setBackgroundColor(getResources().getColor(R.color.smallTextColor));
            myLayout.setBackgroundColor(Color.WHITE);
            myLinear.setBackgroundColor(Color.WHITE);
            zeroB.setTextColor(Color.BLACK);
            oneB.setTextColor(Color.BLACK);
            twoB.setTextColor(Color.BLACK);
            threeB.setTextColor(Color.BLACK);
            fourB.setTextColor(Color.BLACK);
            fiveB.setTextColor(Color.BLACK);
            sixB.setTextColor(Color.BLACK);
            sevenB.setTextColor(Color.BLACK);
            eightB.setTextColor(Color.BLACK);
            nineB.setTextColor(Color.BLACK);
            decimalB.setTextColor(Color.BLACK);
            ansB.setTextColor(Color.BLACK);
            logB.setTextColor(getResources().getColor(R.color.grayC));
            sinB.setTextColor(getResources().getColor(R.color.grayC));
            cosB.setTextColor(getResources().getColor(R.color.grayC));
            tanB.setTextColor(getResources().getColor(R.color.grayC));
            binButton.setTextColor(getResources().getColor(R.color.grayC));
            exponentB.setTextColor(getResources().getColor(R.color.grayC));
            sqrtB.setTextColor(getResources().getColor(R.color.grayC));
            lnB.setTextColor(getResources().getColor(R.color.grayC));
            bracketB.setTextColor(getResources().getColor(R.color.grayC));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.smallTextColor));
            }
        }

        else if(themeNumber.equals("2f")){
            //Fuschia
            mvv.setBackgroundColor(getResources().getColor(R.color.fuschiaPink));
            myLayout.setBackgroundColor(getResources().getColor(R.color.backgroundFuschia));
            myLinear.setBackgroundColor(getResources().getColor(R.color.backgroundFuschia));
            zeroB.setTextColor(getResources().getColor(R.color.fuschia));
            oneB.setTextColor(getResources().getColor(R.color.fuschia));
            twoB.setTextColor(getResources().getColor(R.color.fuschia));
            threeB.setTextColor(getResources().getColor(R.color.fuschia));
            fourB.setTextColor(getResources().getColor(R.color.fuschia));
            fiveB.setTextColor(getResources().getColor(R.color.fuschia));
            sixB.setTextColor(getResources().getColor(R.color.fuschia));
            sevenB.setTextColor(getResources().getColor(R.color.fuschia));
            eightB.setTextColor(getResources().getColor(R.color.fuschia));
            nineB.setTextColor(getResources().getColor(R.color.fuschia));
            decimalB.setTextColor(getResources().getColor(R.color.fuschia));
            ansB.setTextColor(getResources().getColor(R.color.fuschia));
            logB.setTextColor(getResources().getColor(R.color.purple));
            lnB.setTextColor(getResources().getColor(R.color.purple));
            sqrtB.setTextColor(getResources().getColor(R.color.purple));
            sinB.setTextColor(getResources().getColor(R.color.purple));
            cosB.setTextColor(getResources().getColor(R.color.purple));
            tanB.setTextColor(getResources().getColor(R.color.purple));
            binButton.setTextColor(getResources().getColor(R.color.purple));
            exponentB.setTextColor(getResources().getColor(R.color.purple));
            bracketB.setTextColor(getResources().getColor(R.color.purple));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.equalsButtonOriginal));
            }
        }

        else if(themeNumber.equals("3f")){
            //Ocean Blue
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                mvv.setBackgroundColor(getResources().getColor(R.color.lightbluee2));
                myLayout.setBackgroundColor(getResources().getColor(R.color.lightbluee));
                myLinear.setBackgroundColor(getResources().getColor(R.color.lightbluee));
                zeroB.setTextColor(getResources().getColor(R.color.oceanBlue));
                oneB.setTextColor(getResources().getColor(R.color.oceanBlue));
                twoB.setTextColor(getResources().getColor(R.color.oceanBlue));
                threeB.setTextColor(getResources().getColor(R.color.oceanBlue));
                fourB.setTextColor(getResources().getColor(R.color.oceanBlue));
                fiveB.setTextColor(getResources().getColor(R.color.oceanBlue));
                sixB.setTextColor(getResources().getColor(R.color.oceanBlue));
                sevenB.setTextColor(getResources().getColor(R.color.oceanBlue));
                eightB.setTextColor(getResources().getColor(R.color.oceanBlue));
                nineB.setTextColor(getResources().getColor(R.color.oceanBlue));
                decimalB.setTextColor(getResources().getColor(R.color.oceanBlue));
                ansB.setTextColor(getResources().getColor(R.color.oceanBlue));
                logB.setTextColor(getResources().getColor(R.color.purple));
                lnB.setTextColor(getResources().getColor(R.color.purple));
                sqrtB.setTextColor(getResources().getColor(R.color.purple));
                sinB.setTextColor(getResources().getColor(R.color.purple));
                cosB.setTextColor(getResources().getColor(R.color.purple));
                tanB.setTextColor(getResources().getColor(R.color.purple));
                binButton.setTextColor(getResources().getColor(R.color.purple));
                exponentB.setTextColor(getResources().getColor(R.color.purple));
                bracketB.setTextColor(getResources().getColor(R.color.purple));
                equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.smallTextColor));

                saveTheme("3");
            }
            else {
                Toast.makeText(MainActivity.this, "Sorry, this theme requires Android 5.0 Lollipop or greater", Toast.LENGTH_LONG).show();
            }
        }

        else if(themeNumber.equals("4f")){
            //green
            mvv.setBackgroundColor(getResources().getColor(R.color.clearView2));
            myLayout.setBackgroundColor(getResources().getColor(R.color.clearView2));
            myLinear.setBackgroundColor(getResources().getColor(R.color.clearView2));
            zeroB.setTextColor(getResources().getColor(R.color.clearView3));
            oneB.setTextColor(getResources().getColor(R.color.clearView3));
            twoB.setTextColor(getResources().getColor(R.color.clearView3));
            threeB.setTextColor(getResources().getColor(R.color.clearView3));
            fourB.setTextColor(getResources().getColor(R.color.clearView3));
            fiveB.setTextColor(getResources().getColor(R.color.clearView3));
            sixB.setTextColor(getResources().getColor(R.color.clearView3));
            sevenB.setTextColor(getResources().getColor(R.color.clearView3));
            eightB.setTextColor(getResources().getColor(R.color.clearView3));
            nineB.setTextColor(getResources().getColor(R.color.clearView3));
            decimalB.setTextColor(getResources().getColor(R.color.clearView3));
            ansB.setTextColor(getResources().getColor(R.color.clearView3));
            logB.setTextColor(getResources().getColor(R.color.oxfordBlue));
            lnB.setTextColor(getResources().getColor(R.color.oxfordBlue));
            sqrtB.setTextColor(getResources().getColor(R.color.oxfordBlue));
            sinB.setTextColor(getResources().getColor(R.color.oxfordBlue));
            cosB.setTextColor(getResources().getColor(R.color.oxfordBlue));
            tanB.setTextColor(getResources().getColor(R.color.oxfordBlue));
            binButton.setTextColor(getResources().getColor(R.color.oxfordBlue));
            exponentB.setTextColor(getResources().getColor(R.color.oxfordBlue));
            bracketB.setTextColor(getResources().getColor(R.color.oxfordBlue));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.equalsButtonOriginal));
            }
            saveTheme("2");
        }









        sqrtB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("square root", "Button pressed");
                currentCalculation += "sqrt(";
                displayCalculation += "√(";
                calculationView.setText(displayCalculation);
                onAllClicks();
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
            }
        });

        popupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, popupB);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        //DISABLED
                        if(item.getTitle().equals("Customize1")){
                            //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                            builderSingle.setTitle("Set a theme:");

                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_item);
                            arrayAdapter.add("Stealth Black");
                            arrayAdapter.add("Minimal White");
                            arrayAdapter.add("Fuschia");
                            arrayAdapter.add("Tropical Blue");
                            arrayAdapter.add("Forrest Green");


                            builderSingle.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String strName = arrayAdapter.getItem(which);
                                    //Toast.makeText(MainActivity.this, strName, Toast.LENGTH_SHORT).show();

                                    if(which == 0){
                                        //Stealth Black
                                        mvv.setBackgroundColor(getResources().getColor(R.color.lightRed));
                                        myLayout.setBackgroundColor(getResources().getColor(R.color.naturalBlack));
                                        myLinear.setBackgroundColor(getResources().getColor(R.color.naturalBlack));
                                        zeroB.setTextColor(Color.WHITE);
                                        oneB.setTextColor(Color.WHITE);
                                        twoB.setTextColor(Color.WHITE);
                                        threeB.setTextColor(Color.WHITE);
                                        fourB.setTextColor(Color.WHITE);
                                        fiveB.setTextColor(Color.WHITE);
                                        sixB.setTextColor(Color.WHITE);
                                        sevenB.setTextColor(Color.WHITE);
                                        eightB.setTextColor(Color.WHITE);
                                        nineB.setTextColor(Color.WHITE);
                                        decimalB.setTextColor(Color.WHITE);
                                        ansB.setTextColor(Color.WHITE);
                                        logB.setTextColor(getResources().getColor(R.color.grayC));
                                        sinB.setTextColor(getResources().getColor(R.color.grayC));
                                        cosB.setTextColor(getResources().getColor(R.color.grayC));
                                        tanB.setTextColor(getResources().getColor(R.color.grayC));
                                        binButton.setTextColor(getResources().getColor(R.color.grayC));
                                        exponentB.setTextColor(getResources().getColor(R.color.grayC));
                                        bracketB.setTextColor(getResources().getColor(R.color.grayC));
                                        sqrtB.setTextColor(getResources().getColor(R.color.grayC));
                                        lnB.setTextColor(getResources().getColor(R.color.grayC));
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.equalsButtonOriginal));
                                        }
                                        saveTheme("0");




                                    }
                                    else if(which == 1){
                                        //Minimal White
                                        mvv.setBackgroundColor(getResources().getColor(R.color.smallTextColor));
                                        myLayout.setBackgroundColor(Color.WHITE);
                                        myLinear.setBackgroundColor(Color.WHITE);
                                        zeroB.setTextColor(Color.BLACK);
                                        oneB.setTextColor(Color.BLACK);
                                        twoB.setTextColor(Color.BLACK);
                                        threeB.setTextColor(Color.BLACK);
                                        fourB.setTextColor(Color.BLACK);
                                        fiveB.setTextColor(Color.BLACK);
                                        sixB.setTextColor(Color.BLACK);
                                        sevenB.setTextColor(Color.BLACK);
                                        eightB.setTextColor(Color.BLACK);
                                        nineB.setTextColor(Color.BLACK);
                                        decimalB.setTextColor(Color.BLACK);
                                        ansB.setTextColor(Color.BLACK);
                                        logB.setTextColor(getResources().getColor(R.color.grayC));
                                        sinB.setTextColor(getResources().getColor(R.color.grayC));
                                        cosB.setTextColor(getResources().getColor(R.color.grayC));
                                        tanB.setTextColor(getResources().getColor(R.color.grayC));
                                        binButton.setTextColor(getResources().getColor(R.color.grayC));
                                        exponentB.setTextColor(getResources().getColor(R.color.grayC));
                                        sqrtB.setTextColor(getResources().getColor(R.color.grayC));
                                        lnB.setTextColor(getResources().getColor(R.color.grayC));
                                        bracketB.setTextColor(getResources().getColor(R.color.grayC));
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.smallTextColor));
                                        }
                                        saveTheme("1");
                                    }

                                    else if(which == 2){
                                        //Fuschia
                                        mvv.setBackgroundColor(getResources().getColor(R.color.fuschiaPink));
                                        myLayout.setBackgroundColor(getResources().getColor(R.color.backgroundFuschia));
                                        myLinear.setBackgroundColor(getResources().getColor(R.color.backgroundFuschia));
                                        zeroB.setTextColor(getResources().getColor(R.color.fuschia));
                                        oneB.setTextColor(getResources().getColor(R.color.fuschia));
                                        twoB.setTextColor(getResources().getColor(R.color.fuschia));
                                        threeB.setTextColor(getResources().getColor(R.color.fuschia));
                                        fourB.setTextColor(getResources().getColor(R.color.fuschia));
                                        fiveB.setTextColor(getResources().getColor(R.color.fuschia));
                                        sixB.setTextColor(getResources().getColor(R.color.fuschia));
                                        sevenB.setTextColor(getResources().getColor(R.color.fuschia));
                                        eightB.setTextColor(getResources().getColor(R.color.fuschia));
                                        nineB.setTextColor(getResources().getColor(R.color.fuschia));
                                        decimalB.setTextColor(getResources().getColor(R.color.fuschia));
                                        ansB.setTextColor(getResources().getColor(R.color.fuschia));
                                        logB.setTextColor(getResources().getColor(R.color.purple));
                                        lnB.setTextColor(getResources().getColor(R.color.purple));
                                        sqrtB.setTextColor(getResources().getColor(R.color.purple));
                                        sinB.setTextColor(getResources().getColor(R.color.purple));
                                        cosB.setTextColor(getResources().getColor(R.color.purple));
                                        tanB.setTextColor(getResources().getColor(R.color.purple));
                                        binButton.setTextColor(getResources().getColor(R.color.purple));
                                        exponentB.setTextColor(getResources().getColor(R.color.purple));
                                        bracketB.setTextColor(getResources().getColor(R.color.purple));
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.equalsButtonOriginal));
                                        }
                                        saveTheme("2");
                                    }

                                    else if(which == 3){
                                        //Ocean Blue
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                                            mvv.setBackgroundColor(getResources().getColor(R.color.lightbluee2));
                                        myLayout.setBackgroundColor(getResources().getColor(R.color.lightbluee));
                                        myLinear.setBackgroundColor(getResources().getColor(R.color.lightbluee));
                                        zeroB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        oneB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        twoB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        threeB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        fourB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        fiveB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        sixB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        sevenB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        eightB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        nineB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        decimalB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        ansB.setTextColor(getResources().getColor(R.color.oceanBlue));
                                        logB.setTextColor(getResources().getColor(R.color.purple));
                                        lnB.setTextColor(getResources().getColor(R.color.purple));
                                        sqrtB.setTextColor(getResources().getColor(R.color.purple));
                                        sinB.setTextColor(getResources().getColor(R.color.purple));
                                        cosB.setTextColor(getResources().getColor(R.color.purple));
                                        tanB.setTextColor(getResources().getColor(R.color.purple));
                                        binButton.setTextColor(getResources().getColor(R.color.purple));
                                        exponentB.setTextColor(getResources().getColor(R.color.purple));
                                        bracketB.setTextColor(getResources().getColor(R.color.purple));
                                        equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.smallTextColor));

                                        saveTheme("3");
                                        }
                                        else {
                                            Toast.makeText(MainActivity.this, "Sorry, this theme requires Android 5.0 Lollipop or greater", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    else if(which == 4){
                                        //gree
                                        mvv.setBackgroundColor(getResources().getColor(R.color.clearView2));
                                        myLayout.setBackgroundColor(getResources().getColor(R.color.clearView2));
                                        myLinear.setBackgroundColor(getResources().getColor(R.color.clearView2));
                                        zeroB.setTextColor(getResources().getColor(R.color.clearView3));
                                        oneB.setTextColor(getResources().getColor(R.color.clearView3));
                                        twoB.setTextColor(getResources().getColor(R.color.clearView3));
                                        threeB.setTextColor(getResources().getColor(R.color.clearView3));
                                        fourB.setTextColor(getResources().getColor(R.color.clearView3));
                                        fiveB.setTextColor(getResources().getColor(R.color.clearView3));
                                        sixB.setTextColor(getResources().getColor(R.color.clearView3));
                                        sevenB.setTextColor(getResources().getColor(R.color.clearView3));
                                        eightB.setTextColor(getResources().getColor(R.color.clearView3));
                                        nineB.setTextColor(getResources().getColor(R.color.clearView3));
                                        decimalB.setTextColor(getResources().getColor(R.color.clearView3));
                                        ansB.setTextColor(getResources().getColor(R.color.clearView3));
                                        logB.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        lnB.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        sqrtB.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        sinB.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        cosB.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        tanB.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        binButton.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        exponentB.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        bracketB.setTextColor(getResources().getColor(R.color.oxfordBlue));
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, R.color.equalsButtonOriginal));
                                        }
                                        saveTheme("4");
                                    }
                                }
                            });
                            builderSingle.show();
                        }
                        else if(item.getTitle().equals("Settings")){
                            //Toast.makeText(getApplicationContext(), "Coming Soon...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        }
                        else{
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(MainActivity.this);
                            }

                            String nodata="<br/>&#8226; Version " + versionName + "<br/>&#8226; Made by Kumail Naqvi, 2017<br/>&#8226; kumailmn@gmail.com<br/>&#8226; github.com/kumailn<br/>&#8226; powered by mXparser";
                            final SpannableString ss = new SpannableString(Html.fromHtml(nodata));
                            Linkify.addLinks(ss, Linkify.ALL);

                            //added a TextView
                            final TextView tx1=new TextView(MainActivity.this);
                            tx1.setText(ss);
                            tx1.setAutoLinkMask(RESULT_OK);
                            tx1.setMovementMethod(LinkMovementMethod.getInstance());
                            tx1.setTextSize(16);
                            tx1.setTextColor(Color.WHITE);
                            tx1.setPadding(48, 0, 0, 0);

                            builder.setTitle("About the app")
                                    //.setMessage("Made by Kumail Naqvi, 2017, Version 1.5, Contact me at kumailmn@gmail.com, github.com/kumailn, powered by mXparser")
                                    //.setMessage(ss)
                                    .setView(tx1)
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }

                                    })
                                    //.setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }

                        return true;
                    }

                });
                popup.show();
            }
        });

        fiveB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });



        zeroB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "0";
                displayCalculation += "0";
                calculationView.setText(displayCalculation);


                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();


                //calculationView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_right));
            }
        });

        zeroB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "!";
                displayCalculation += "!";
                calculationView.setText(displayCalculation);
                equalsMethod();
                onAllClicks();
                return true;
            }
        });

        oneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "1";
                displayCalculation += "1";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();

            }
        });

        oneB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onVarClicks();
                currentCalculation += "e";
                displayCalculation += "e";
                calculationView.setText(displayCalculation);
                equalsMethod();
                onAllClicks();
                return true;
            }
        });

        twoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "2";
                displayCalculation += "2";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        twoB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onVarClicks();
                currentCalculation += "[phi]";
                displayCalculation += "φ";
                calculationView.setText(displayCalculation);
                equalsMethod();
                onAllClicks();
                return true;
            }
        });

        threeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "3";
                displayCalculation += "3";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        threeB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "#";
                displayCalculation += "%";
                calculationView.setText(displayCalculation);
                equalsMethod();
                onAllClicks();
                return true;
            }
        });



        fourB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "4";
                displayCalculation += "4";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        fiveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "5";
                displayCalculation += "5";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        sixB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "6";
                displayCalculation += "6";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();

            }
        });

        sevenB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "7";
                displayCalculation += "7";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        sevenB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "lcm(";
                displayCalculation += "LCM(";
                calculationView.setText(displayCalculation);
                equalsMethod();
                return true;
            }
        });

        eightB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "8";
                displayCalculation += "8";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        eightB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "gcd(";
                displayCalculation += "GCD(";
                calculationView.setText(displayCalculation);
                equalsMethod();
                return true;
            }
        });

        nineB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(9";
                        displayCalculation += "^(9";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "9";
                        displayCalculation += "9";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "9";
                    displayCalculation += "9";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        logB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVarClicks();
                currentCalculation += "log(10,";
                displayCalculation += "log(";
                calculationView.setText(displayCalculation);
                equalsMethod();
                onAllClicks();
            }
        });

        lnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVarClicks();
                currentCalculation += "ln(";
                displayCalculation += "ln(";
                calculationView.setText(displayCalculation);
                equalsMethod();
                onAllClicks();
            }
        });

        bracketB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCalculation.endsWith("*") || currentCalculation.endsWith("+") || currentCalculation.endsWith("-") || currentCalculation.endsWith("/") || !(currentCalculation.contains("("))){
                    currentCalculation += "(";
                    displayCalculation += "(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    onAllClicks();
                }
                else{
                    currentCalculation += ")";
                    displayCalculation += ")";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    onAllClicks();
                }

            }
        });

        /*bracketB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "(";
                displayCalculation += "(";
                calculationView.setText(displayCalculation);
                equalsMethod();
                return true;
            }
        });*/

        sinB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVarClicks();
                if(angle == true){
                    currentCalculation += "sin([deg]*";
                    displayCalculation += "sin(";
                    calculationView.setText(displayCalculation);

                }
                else{
                    currentCalculation += "sin([rad]*";
                    displayCalculation += "sin(";
                    calculationView.setText(displayCalculation);
                }
                equalsMethod();
                onAllClicks();

            }
        });

        sinB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onVarClicks();
                if(angle == true){
                    currentCalculation += "asin([deg]*";
                    displayCalculation += "arcsin(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    onAllClicks();
                    return true;
                }
                else {
                    currentCalculation += "asin([rad]*";
                    displayCalculation += "arcsin(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    onAllClicks();
                    return true;
                }

            }
        });

        cosB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVarClicks();
                if(angle == true){
                    currentCalculation += "cos([deg]*";
                    displayCalculation += "cos(";
                    calculationView.setText(displayCalculation);
                }
                else{
                    currentCalculation += "cos([rad]*";
                    displayCalculation += "cos(";
                    calculationView.setText(displayCalculation);
                }
                equalsMethod();
                onAllClicks();
            }
        });

        cosB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onVarClicks();
                if(angle == true){
                    currentCalculation += "acos([deg]*";
                    displayCalculation += "arccos(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    onAllClicks();
                    return true;
                }
                else{
                    currentCalculation += "acos([rad]*";
                    displayCalculation += "arccos(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    onAllClicks();
                    return true;
                }

            }
        });

        tanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVarClicks();
                if(angle == true){
                    currentCalculation += "tan([deg]*";
                    displayCalculation += "tan(";
                    calculationView.setText(displayCalculation);
                }
                else {
                    currentCalculation += "tan([rad]*";
                    displayCalculation += "tan(";
                    calculationView.setText(displayCalculation);
                }
                equalsMethod();
                onAllClicks();

            }
        });

        tanB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onVarClicks();
                if(angle == true){
                    currentCalculation += "atan([deg]*";
                    displayCalculation += "arctan(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    onAllClicks();
                    return true;
                }
                else{
                    currentCalculation += "atan([rad]*";
                    displayCalculation += "arctan(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    onAllClicks();
                    return true;
                }
            }
        });

        exponentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "^(";
                displayCalculation += "^(";
                calculationView.setText(displayCalculation);
                equalsMethod();
                /*if(exponentOn == true){
                    exponentOn = false;
                    currentCalculation += ")";
                    exponentB.setTextColor(getResources().getColor(R.color.myGray));
                }
                else{
                    exponentOn = true;
                    firstExponent = true;
                    exponentB.setTextColor(getResources().getColor(R.color.colorAccent));

                }*/
            }
        });

        multB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_around_center_point);
                multB.startAnimation(animation);
                currentCalculation += "*";
                displayCalculation += "×";
                calculationView.setText(displayCalculation);
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(MED_IMPORTANCE_KEY_VIBRATE);
                onAllClicks();
                equalsMethod();
            }
        });

        plusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_around_center_point);
                plusB.startAnimation(animation);

                currentCalculation += "+";
                displayCalculation += "+";
                calculationView.setText(displayCalculation);
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(MED_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        plusB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(pVisible == false){
                    Expression e = new Expression("ispr(" + previousAns + ")");
                    String result = String.valueOf(e.calculate());
                    if(result.equals("0.0")){
                        result = "false";
                    }
                    else{
                        result = "true";
                    }
                    pView.setText(result);
                    pView.setVisibility(TextView.VISIBLE);
                    pVisible = true;
                    return true;
                }
                else{
                    pVisible = false;
                    pView.setVisibility(TextView.INVISIBLE);
                    return true;
                }
            }
        });

        divB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate2);
                divB.startAnimation(animation);
                currentCalculation += "/";
                displayCalculation += "÷";
                calculationView.setText(displayCalculation);
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(MED_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        divB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "C(";
                displayCalculation += "C(";
                calculationView.setText(displayCalculation);
                equalsMethod();
                onAllClicks();
                return true;

            }
        });

        minusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_around_center_point);
                minusB.startAnimation(animation);
                currentCalculation += "-";
                displayCalculation += "-";
                calculationView.setText(displayCalculation);
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(MED_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        minusB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += ",";
                displayCalculation += ",";
                calculationView.setText(displayCalculation);
                equalsMethod();
                onAllClicks();
                return true;
            }
        });

        delB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(MED_IMPORTANCE_KEY_VIBRATE);

                if(displayCalculation.length() < 1){
                    int a = 1;
                    Log.e("limiting", "now");
                }
                else{
                    if(currentCalculation.endsWith("asin([rad]*") || currentCalculation.endsWith("asin([deg]*") || currentCalculation.endsWith("acos([rad]*")
                            || currentCalculation.endsWith("acos([deg]*") || currentCalculation.endsWith("atan([rad]*") || currentCalculation.endsWith("atan([deg]*")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 11);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 7);
                        calculationView.setText(displayCalculation);
                    }
                    else if(currentCalculation.endsWith("sin([rad]*") || currentCalculation.endsWith("cos([rad]*") || currentCalculation.endsWith("tan([rad]*")
                            || currentCalculation.endsWith("sin[deg]*") || currentCalculation.endsWith("cos[deg]*") || currentCalculation.endsWith("tan([deg]*")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 10);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 4);
                        calculationView.setText(displayCalculation);
                    }
                    else if(currentCalculation.endsWith("ln(")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 3);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 3);
                        calculationView.setText(displayCalculation);
                    }
                    else if(currentCalculation.endsWith("log(10,") || currentCalculation.endsWith("lcm(") || currentCalculation.endsWith("gcd(")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 4);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 4);
                        calculationView.setText(displayCalculation);
                    }
                    else if(currentCalculation.endsWith("c(")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 2);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 2);
                        calculationView.setText(displayCalculation);
                    }
                    else if(currentCalculation.endsWith("Error")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 5);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 5);
                        calculationView.setText(displayCalculation);
                    }
                    else if(currentCalculation.endsWith("pi")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 2);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 1);
                        calculationView.setText(displayCalculation);
                    }
                    else if(currentCalculation.endsWith("[phi]")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 5);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 1);
                        calculationView.setText(displayCalculation);
                    }
                    else if(displayCalculation.endsWith("Ans")){
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - previousAns.length());
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 3);
                        calculationView.setText(displayCalculation);
                    }
                    else{
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 1);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 1);
                        calculationView.setText(displayCalculation);
                    }
                }
                equalsMethod();
                onAllClicks();

            }
        });

        clrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(HIGH_IMPORTANCE_KEY_VIBRATE);

                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
                fadeOut.setStartOffset(200);
                fadeOut.setDuration(400);


                // previously invisible view
                final View mv = findViewById(R.id.awesome_card);

                // get the center for the clipping circle
                int centerX = (mv.getRight());
                int centerY = (mv.getBottom());

                int startRadius = 0;
                // get the final radius for the clipping circle
                int endRadius = Math.max(mv.getWidth(), mv.getHeight());

                // create the animator for this view (the start radius is zero)
                Animator anim =
                        ViewAnimationUtils.createCircularReveal(mv, centerX, centerY, startRadius, endRadius);


                AnimationSet animation = new AnimationSet(false); //change to false
                animation.addAnimation(fadeOut);
                mv.setAnimation(animation);

                pView.setVisibility(View.INVISIBLE);
                pVisible = false;


                anim.setDuration(300);
                // make the view visible and start the animation
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mv.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                mv.setVisibility(View.VISIBLE);
                anim.start();

                currentCalculation = " ";
                displayCalculation = " ";
                calculationView.setText(displayCalculation);
                if(exponentOn == true){
                    exponentOn = false;
                    firstExponent = true;
                    //exponentB.setTextColor(getResources().getColor(R.color.black));
                }
                instantCalcView.setText("");
                scrollOnClear();
                //calculationView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_in_left));

                //calculationView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_out_right));


            }
        });

        decimalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(0.";
                        displayCalculation += "0.";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        if (currentCalculation.endsWith("*") || currentCalculation.endsWith("-") || currentCalculation.endsWith("+") || currentCalculation.endsWith("/") || (currentCalculation.trim().length() == 0)){
                            currentCalculation += "0";
                            displayCalculation += "0";
                        }
                        currentCalculation += ".";
                        displayCalculation += ".";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    if (currentCalculation.endsWith("*") || currentCalculation.endsWith("-") || currentCalculation.endsWith("+") || currentCalculation.endsWith("/") || (currentCalculation.trim().length() == 0)){
                        currentCalculation += "0";
                        displayCalculation += "0";
                    }
                    currentCalculation += ".";
                    displayCalculation += ".";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
            }
        });

        decimalB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onVarClicks();
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(pi";
                        displayCalculation += "π";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "pi";
                        displayCalculation += "π";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "pi";
                    displayCalculation += "π";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(LOW_IMPORTANCE_KEY_VIBRATE);
                equalsMethod();
                onAllClicks();
                return true;
            }
        });

        ansB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVarClicks();
                if(previousAns != null){
                    currentCalculation += previousAns;
                    displayCalculation += "Ans";
                    calculationView.setText(displayCalculation);
                }
                equalsMethod();
                onAllClicks();

            }
        });

        ansB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Answer", previousAns);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Answer copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        equalsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                v.playSoundEffect(SoundEffectConstants.CLICK);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(MED_IMPORTANCE_KEY_VIBRATE);


                if(currentCalculation.equals("") || currentCalculation.equals(" ") || currentCalculation.equals("   ")){
                    return;
                }

                if(exponentOn == true){
                    currentCalculation += ")";
                    exponentOn = false;
                    firstExponent = true;
                    //exponentB.setTextColor(getResources().getColor(R.color.black));
                }

                //Log.e("backgroundC", currentCalculation);
                //Log.e("displayC", displayCalculation);

/*                if(currentCalculation.contains("pi")){
                    String xx = currentCalculation;
                    xx = xx.substring(0, 4) + "." + xx.substring(4, xx.length());
                }*/

                Expression e = new Expression(currentCalculation);
                String result = String.valueOf(e.calculate());
                if (result.equals("NaN")){
                    e = new Expression(currentCalculation + ")");
                    result = String.valueOf(e.calculate());
                }
                if(result.endsWith("E-16") || result.endsWith("E-15") || result.endsWith("E-14")){
                    result = "0";
                }
                if (result.endsWith(".0")){
                    result = result.substring(0, result.length() - 2);
                }

                try{
                    double dd = Double.parseDouble(result);
                    if(!result.contains("E")){
                        String df = new DecimalFormat("#######################.############").format(dd);
                        result = df;
                    }


                }
                catch (Exception f){

                }


                if (pVisible == true){
                    //pView.setVisibility(View.INVISIBLE);
                    //pVisible = false;
                }

                if(!(result.equals("NaN") && !(result.equals("∞")))){
                    previousAns = result;
                    saveAnswer(result);
                }
                else if (result.equals("NaN")){
                    result = "Error";
                    //result = "";
                }
                else if (result.equals("∞")){
                    result = "∞";
                }


                //instantCalcView.setText(" " + result);
                if(result.equals("Error")){
                    instantCalcView.setText(" " + result);
                    result = "";
                }
                else{
                    instantCalcView.setText(" " + result);
                    instantCalcView.setTextColor(getResources().getColor(R.color.clearView3));
                    calculationView.setText(" " + "Ans");
                    displayCalculation = (" " + "Ans");
                    currentCalculation = result;
                }
                binPressed = false;
                scrollOnClear();





            }
        });

        binButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String newR = previousAns.replaceAll("\\s+","");;
                if(binPressed == true){
                    return;
                }
                String ccv = String.valueOf(instantCalcView.getText());
                Log.e("INBIN", ccv);
                try{
                    ccv = ccv.replaceAll("\\s+","");;
                    Integer newR2 = Integer.parseInt(ccv);
                    instantCalcView.setText(Integer.toBinaryString(newR2));
                    //equalsMethod();
                }
                catch (Exception e){}
                binPressed = true;

            }

        });

        binButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //String newR = previousAns.replaceAll("\\s+","");
                if(binPressed == true){
                    return true;
                }
                String ccv = String.valueOf(instantCalcView.getText());
                try{
                    ccv = ccv.replaceAll("\\s+","");;
                    Integer newR2 = Integer.parseInt(ccv);
                    instantCalcView.setText(Integer.toHexString(newR2));
                    //equalsMethod();
                }
                catch (Exception e){}
                binPressed = true;
                return true;
            }
        });

        equalsB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    Rational fraction = new Rational(Double.valueOf(String.valueOf(instantCalcView.getText())));
                    instantCalcView.setText(" " + fraction.simple());
                } catch (Exception ea) {
                    ea.printStackTrace();
                }
                return true;
            }
        });


        angleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(angle == true){
                    angleB.setText("RAD");
                    //angleB.setTextColor(Color.RED);
                    saveAngle("rad");
                    angle = false;
                    String ccv = String.valueOf(currentCalculation);
                    Log.e("CCV", ccv);
                    ccv = ccv.replace("deg", "rad");
                    Log.e("CCV2", ccv);
                    currentCalculation = ccv;
                    equalsMethod();
                    //currentCalculation.replaceAll("rad", "deg");
                }
                else{
                    angleB.setText("DEG");
                    //angleB.setTextColor(getResources().getColor(R.color.degree));
                    saveAngle("deg");
                    angle = true;
                    String ccv = String.valueOf(currentCalculation);
                    ccv = ccv.replace("rad", "deg");
                    currentCalculation = ccv;
                    equalsMethod();
                    //currentCalculation.replaceAll("deg", "rad");
                }
            }
        });





    }



    public void onAllClicks(){
        TextView instantC = (TextView)findViewById(R.id.instantCalcluationView);
        TextView calculationView = (TextView)findViewById(R.id.calcView);
        HorizontalScrollView mSV = (HorizontalScrollView)findViewById(R.id.myScrollView);
        Layout layout = instantC.getLayout();
        mSV.smoothScrollTo(mSV.getRight(), 0);
        calculationView.setTextColor(getResources().getColor(R.color.basic_text));

    }

    public void onVarClicks(){
        if (!(currentCalculation.endsWith("*") || currentCalculation.endsWith("-") || currentCalculation.endsWith("+") || currentCalculation.endsWith("/") || currentCalculation.endsWith("(") || currentCalculation.endsWith("#") || (currentCalculation.trim().length() == 0))){
            currentCalculation += "*";
            displayCalculation += "×";
        }


    }


    public void scrollOnClear(){
        TextView instantC = (TextView)findViewById(R.id.instantCalcluationView);
        HorizontalScrollView mSV = (HorizontalScrollView)findViewById(R.id.myScrollView);
        Layout layout = instantC.getLayout();
        mSV.smoothScrollTo(-1000, 0);

    }


    public void equalsMethod(){
        TextView calculationView = (TextView)findViewById(R.id.calcView);
        TextView instantCalcView = (TextView)findViewById(R.id.instantCalcluationView);
        TextView pView = (TextView)findViewById(R.id.primeView);
        instantCalcView.setTextColor(getResources().getColor(R.color.basic_text));

        //Catch for international users - turn commas into decimals
        if(currentCalculation.contains(",") && (!(currentCalculation.contains("lcm")) && !(currentCalculation.contains("gcd"))) && (!(currentCalculation.contains("C")))){
            currentCalculation = currentCalculation.replace(",", ".");
        }

        try {
            //currentCalculation = currentCalculation.replace("Ans", previousAns);
            //displayCalculation = displayCalculation.replace("Ans", previousAns);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(currentCalculation.equals("") || currentCalculation.equals(" ") || currentCalculation.equals("   ")){
            return;
        }

        if(exponentOn == true){
            currentCalculation += ")";
            exponentOn = false;
            firstExponent = true;
            //exponentB.setTextColor(getResources().getColor(R.color.black));
        }
        //abc
        Log.e("backgroundC", currentCalculation);
        Log.e("displayC", displayCalculation);

        if(currentCalculation.contains("+.")) {
            Log.e("DECIMAL","DETECTED");
            for(int i = 0; i < currentCalculation.length(); i++){
                if(currentCalculation.substring(i, i+1).equals("+.")){
                    currentCalculation = currentCalculation.substring(0, i) + "0" + currentCalculation.substring(i, currentCalculation.length());
                    Log.e(currentCalculation, "DECIMAL");

                }
            }

        }
        Expression e = new Expression(currentCalculation);
        String result = String.valueOf(e.calculate());
        if (result.equals("NaN")){
            e = new Expression(currentCalculation + ")");
            result = String.valueOf(e.calculate());

        }
        if(result.endsWith("E-16") || result.endsWith("E-15") || result.endsWith("E-14")){
            result = "0";
        }
        if (result.endsWith(".0")){
            result = result.substring(0, result.length() - 2);
        }

        try{
            double dd = Double.parseDouble(result);
            if(!result.contains("E")){
                String df = new DecimalFormat("#######################.############").format(dd);
                result = df;
            }


        }
        catch (Exception f){

        }

        if(!(result.equals("NaN"))){
            //previousAns = result;
        }
        else{
            result = "Error";
        }
        if(!result.equals("Error")){
            //currentCalculation = currentCalculation.replace(previousAns, "Ans");
            //displayCalculation = displayCalculation.replace(previousAns, "Ans");
            calculationView.setText(" " + result);
        }
        //
        if(pVisible == true){
            Expression ex = new Expression("ispr(" + calculationView.getText()  + ")");
            String result2 = String.valueOf(ex.calculate());
            if(result2.equals("0.0")){
                result2 = "false";
            }
            else{
                result2 = "true";
            }
            pView.setText(result2);
            pView.setVisibility(TextView.VISIBLE);
        }

        if(calculationView.getText().length() > 17){
            calculationView.setTextSize(30);
        }
        else{
            calculationView.setTextSize(34);
        }
        binPressed = false;
        //displayCalculation = " " + result;
        //currentCalculation = result;
    }



    public void saveAnswer(String meth){
        //Local data storage
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("answer", meth);
        editor.commit();
    }

    public String loadAnswer(){
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String myMethod = sharedPreferences.getString("answer", defaultMethod);
        return (myMethod);
    }

    public void saveTheme(String meth){
        //Local data storage
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("theme", meth);
        editor.commit();
    }

    public String loadTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String myMethod = sharedPreferences.getString("theme", defaultMethod);
        return (myMethod);
    }

    public void saveAngle(String meth){
        //Local data storage
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("angle", meth);
        editor.commit();
    }

    public String loadAngle(){
        SharedPreferences sharedPreferences = getSharedPreferences("myData", Context.MODE_PRIVATE);
        String myMethod = sharedPreferences.getString("angle", defaultMethod);
        return (myMethod);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Initialize widgets
        final Button zeroB = (Button)findViewById(R.id.zeroButton);
        final Button oneB = (Button)findViewById(R.id.oneButton);
        final Button twoB = (Button)findViewById(R.id.twoButton);
        final Button threeB = (Button)findViewById(R.id.threeButton);
        final Button fourB = (Button)findViewById(R.id.fourButton);
        final Button fiveB = (Button)findViewById(R.id.fiveButton);
        final Button sixB = (Button)findViewById(R.id.sixButton);
        final Button sevenB = (Button)findViewById(R.id.sevenButton);
        final Button eightB = (Button)findViewById(R.id.eightButton);
        final Button nineB = (Button)findViewById(R.id.nineButton);
        final Button decimalB = (Button)findViewById(R.id.decimalButton);
        final Button ansB = (Button)findViewById(R.id.ansButton);
        final Button multB = (Button)findViewById(R.id.multiplyButton);
        final Button minusB = (Button)findViewById(R.id.minusButton);
        final Button divB = (Button)findViewById(R.id.divideButton);
        final Button equalsB = (Button)findViewById(R.id.equalsButton);
        final Button plusB = (Button)findViewById(R.id.plusButton);
        final Button sinB = (Button)findViewById(R.id.sinButton);
        final Button tanB = (Button)findViewById(R.id.tanButton);
        final Button cosB = (Button)findViewById(R.id.cosButton);
        Button delB = (Button)findViewById(R.id.deleteButton);
        Button clrB = (Button)findViewById(R.id.clearButton);
        final Button bracketB = (Button) findViewById(R.id.bracketButton);
        final Button logB = (Button)findViewById(R.id.logButton);
        final Button lnB = (Button)findViewById(R.id.lnButton);
        final Button binButton = (Button)findViewById(R.id.bbb);
        final Button sqrtB = (Button)findViewById(R.id.sqrtButton);
        final Button popupB = (Button)findViewById(R.id.popupButton);
        final TextView calculationView = (TextView)findViewById(R.id.instantCalcluationView);
        final TextView pView = (TextView)findViewById(R.id.primeView);
        final Button angleB = (Button)findViewById(R.id.angleButton);
        final Button exponentB = (Button) findViewById(R.id.exponentButton);
        final TextView instantCalcView = (TextView)findViewById(R.id.calcView);
        final LinearLayout myLinear = (LinearLayout)findViewById(R.id.linearLayout);
        final ConstraintLayout myLayout = (ConstraintLayout)findViewById(R.id.myBackLayout);
        final RevealFrameLayout myReveal = (RevealFrameLayout)findViewById(R.id.revealFrameLayout);
        final View mvv = findViewById(R.id.awesome_card);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String vibrationChoice = sharedPreferences.getString("key_vibration_intensity_setting", "0");
        Boolean vibrationSwitch = sharedPreferences.getBoolean("key_vibration_switch", true);
        Log.e("vibrate", vibrationChoice);

        if(vibrationSwitch) {
            switch (Integer.valueOf(vibrationChoice)) {
                case 0:
                    LOW_IMPORTANCE_KEY_VIBRATE = 5;
                    MED_IMPORTANCE_KEY_VIBRATE = 10;
                    HIGH_IMPORTANCE_KEY_VIBRATE = 15;
                case 1:
                    LOW_IMPORTANCE_KEY_VIBRATE = 40;
                    MED_IMPORTANCE_KEY_VIBRATE = 50;
                    HIGH_IMPORTANCE_KEY_VIBRATE = 60;
                case 2:
                    LOW_IMPORTANCE_KEY_VIBRATE = 70;
                    MED_IMPORTANCE_KEY_VIBRATE = 80;
                    HIGH_IMPORTANCE_KEY_VIBRATE = 90;

            }
        }
        else{
            LOW_IMPORTANCE_KEY_VIBRATE = 0;
            MED_IMPORTANCE_KEY_VIBRATE = 0;
            HIGH_IMPORTANCE_KEY_VIBRATE = 0;
        }

        String themeChoice = sharedPreferences.getString("key_theme_setting", "0");
        //setTheme(Integer.valueOf(themeChoice));

        int numButtonColor = Color.WHITE;
        int cardBackgroundColor = getResources().getColor(R.color.lightRed);
        int backgroundColor = getResources().getColor(R.color.naturalBlack);
        int functionButtonColor = getResources().getColor(R.color.grayC);
        int equalsButtonColor = R.color.equalsButtonOriginal;

        switch (Integer.valueOf(themeChoice)){
            case 1:
                numButtonColor = Color.BLACK;
                cardBackgroundColor = getResources().getColor(R.color.smallTextColor);
                backgroundColor = Color.WHITE;
                functionButtonColor = getResources().getColor(R.color.grayC);
                equalsButtonColor = R.color.smallTextColor;
                break;
            case 2:
                numButtonColor = getResources().getColor(R.color.fuschia);
                cardBackgroundColor = getResources().getColor(R.color.fuschiaPink);
                backgroundColor = getResources().getColor(R.color.backgroundFuschia);
                functionButtonColor = getResources().getColor(R.color.purple);
                equalsButtonColor = R.color.equalsButtonOriginal;
                break;
            case 3:
                numButtonColor = getResources().getColor(R.color.oceanBlue);
                cardBackgroundColor = getResources().getColor(R.color.lightbluee2);
                backgroundColor = getResources().getColor(R.color.lightbluee);
                functionButtonColor = getResources().getColor(R.color.oxfordBlue);
                equalsButtonColor = R.color.smallTextColor;
                break;
            case 4:
                numButtonColor = getResources().getColor(R.color.clearView3);
                cardBackgroundColor = getResources().getColor(R.color.clearView2);
                backgroundColor = getResources().getColor(R.color.clearView2);
                functionButtonColor = getResources().getColor(R.color.oxfordBlue);
                equalsButtonColor = R.color.equalsButtonOriginal;
        }

        //Stealth Black
        mvv.setBackgroundColor(cardBackgroundColor);
        myLayout.setBackgroundColor(backgroundColor);
        myLinear.setBackgroundColor(backgroundColor);
        zeroB.setTextColor(numButtonColor);
        oneB.setTextColor(numButtonColor);
        twoB.setTextColor(numButtonColor);
        threeB.setTextColor(numButtonColor);
        fourB.setTextColor(numButtonColor);
        fiveB.setTextColor(numButtonColor);
        sixB.setTextColor(numButtonColor);
        sevenB.setTextColor(numButtonColor);
        eightB.setTextColor(numButtonColor);
        nineB.setTextColor(numButtonColor);
        decimalB.setTextColor(numButtonColor);
        ansB.setTextColor(numButtonColor);

        logB.setTextColor(functionButtonColor);
        sinB.setTextColor(functionButtonColor);
        cosB.setTextColor(functionButtonColor);
        tanB.setTextColor(functionButtonColor);
        binButton.setTextColor(functionButtonColor);
        exponentB.setTextColor(functionButtonColor);
        bracketB.setTextColor(functionButtonColor);
        sqrtB.setTextColor(functionButtonColor);
        lnB.setTextColor(functionButtonColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, equalsButtonColor));
        }

    }

/*    public void setTheme(int number){
        Button zeroB = (Button)findViewById(R.id.zeroButton);
        Button oneB = (Button)findViewById(R.id.oneButton);
        Button twoB = (Button)findViewById(R.id.twoButton);
        Button threeB = (Button)findViewById(R.id.threeButton);
        Button fourB = (Button)findViewById(R.id.fourButton);
        Button fiveB = (Button)findViewById(R.id.fiveButton);
        Button sixB = (Button)findViewById(R.id.sixButton);
        Button sevenB = (Button)findViewById(R.id.sevenButton);
        Button eightB = (Button)findViewById(R.id.eightButton);
        Button nineB = (Button)findViewById(R.id.nineButton);
        Button decimalB = (Button)findViewById(R.id.decimalButton);
        Button ansB = (Button)findViewById(R.id.ansButton);
        Button multB = (Button)findViewById(R.id.multiplyButton);
        Button minusB = (Button)findViewById(R.id.minusButton);
        Button divB = (Button)findViewById(R.id.divideButton);
        Button equalsB = (Button)findViewById(R.id.equalsButton);
        Button plusB = (Button)findViewById(R.id.plusButton);
        Button sinB = (Button)findViewById(R.id.sinButton);
        Button tanB = (Button)findViewById(R.id.tanButton);
        Button cosB = (Button)findViewById(R.id.cosButton);
        Button delB = (Button)findViewById(R.id.deleteButton);
        Button clrB = (Button)findViewById(R.id.clearButton);
        Button bracketB = (Button) findViewById(R.id.bracketButton);
        Button logB = (Button)findViewById(R.id.logButton);
        Button lnB = (Button)findViewById(R.id.lnButton);
        Button binButton = (Button)findViewById(R.id.bbb);
        Button sqrtB = (Button)findViewById(R.id.sqrtButton);
        Button popupB = (Button)findViewById(R.id.popupButton);
        TextView calculationView = (TextView)findViewById(R.id.instantCalcluationView);
        TextView pView = (TextView)findViewById(R.id.primeView);
        Button angleB = (Button)findViewById(R.id.angleButton);
        Button exponentB = (Button) findViewById(R.id.exponentButton);
        TextView instantCalcView = (TextView)findViewById(R.id.calcView);
        LinearLayout myLinear = (LinearLayout)findViewById(R.id.linearLayout);
        ConstraintLayout myLayout = (ConstraintLayout)findViewById(R.id.myBackLayout);
        RevealFrameLayout myReveal = (RevealFrameLayout)findViewById(R.id.revealFrameLayout);
        View mvv = findViewById(R.id.awesome_card);

        int numButtonColor = Color.WHITE;
        int cardBackgroundColor = getResources().getColor(R.color.lightRed);
        int backgroundColor = getResources().getColor(R.color.naturalBlack);
        int functionButtonColor = getResources().getColor(R.color.grayC);
        int equalsButtonColor = R.color.equalsButtonOriginal;

        switch (number){
            case 1:
                numButtonColor = Color.BLACK;
                cardBackgroundColor = getResources().getColor(R.color.smallTextColor);
                backgroundColor = Color.WHITE;
                functionButtonColor = getResources().getColor(R.color.grayC);
                equalsButtonColor = R.color.smallTextColor;
                break;
            case 2:
                numButtonColor = getResources().getColor(R.color.fuschia);
                cardBackgroundColor = getResources().getColor(R.color.fuschiaPink);
                backgroundColor = getResources().getColor(R.color.backgroundFuschia);
                functionButtonColor = getResources().getColor(R.color.purple);
                equalsButtonColor = R.color.equalsButtonOriginal;
                break;
            case 3:
                numButtonColor = getResources().getColor(R.color.oceanBlue);
                cardBackgroundColor = getResources().getColor(R.color.lightbluee2);
                backgroundColor = getResources().getColor(R.color.lightbluee);
                functionButtonColor = getResources().getColor(R.color.oxfordBlue);
                equalsButtonColor = R.color.smallTextColor;
                break;
            case 4:
                numButtonColor = getResources().getColor(R.color.clearView3);
                cardBackgroundColor = getResources().getColor(R.color.clearView2);
                backgroundColor = getResources().getColor(R.color.clearView2);
                functionButtonColor = getResources().getColor(R.color.oxfordBlue);
                equalsButtonColor = R.color.equalsButtonOriginal;
        }

        //Stealth Black
        mvv.setBackgroundColor(cardBackgroundColor);
        myLayout.setBackgroundColor(backgroundColor);
        myLinear.setBackgroundColor(backgroundColor);
        zeroB.setTextColor(numButtonColor);
        oneB.setTextColor(numButtonColor);
        twoB.setTextColor(numButtonColor);
        threeB.setTextColor(numButtonColor);
        fourB.setTextColor(numButtonColor);
        fiveB.setTextColor(numButtonColor);
        sixB.setTextColor(numButtonColor);
        sevenB.setTextColor(numButtonColor);
        eightB.setTextColor(numButtonColor);
        nineB.setTextColor(numButtonColor);
        decimalB.setTextColor(numButtonColor);
        ansB.setTextColor(numButtonColor);

        logB.setTextColor(functionButtonColor);
        sinB.setTextColor(functionButtonColor);
        cosB.setTextColor(functionButtonColor);
        tanB.setTextColor(functionButtonColor);
        binButton.setTextColor(functionButtonColor);
        exponentB.setTextColor(functionButtonColor);
        bracketB.setTextColor(functionButtonColor);
        sqrtB.setTextColor(functionButtonColor);
        lnB.setTextColor(functionButtonColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            equalsB.setBackgroundTintList(ContextCompat.getColorStateList(MainActivity.this, equalsButtonColor));
        }
    }

    */
}

/**
 * This app uses the mXParser library from http://mathparser.org.
 *
 * Copyright 2010-2017 MARIUSZ GROMADA. All rights reserved. You may use this software under the condition of Simplified BSD License. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *THIS SOFTWARE IS PROVIDED BY MARIUSZ GROMADA ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL MARIUSZ GROMADA OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of MARIUSZ GROMADA.
 *
 */
