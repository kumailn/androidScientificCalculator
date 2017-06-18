package com.kumailn.calculator;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.Expression;
import org.w3c.dom.Text;

import java.text.DecimalFormat;

import io.codetail.animation.ViewAnimationUtils;

//Kumail Naqvi June 5th 2017
//test 1
public class MainActivity extends AppCompatActivity {
    public static String currentCalculation;
    public static String previousCalculation;
    public static String displayCalculation;
    public static String finalCalculation;
    public static String previousAns;
    public static Boolean exponentOn;
    public static Boolean firstExponent;
    public static Boolean pVisible;
    //False == rad, true == deg
    public static Boolean angle;
    public static final String defaultMethod = "rad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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
        Button equalsB = (Button)findViewById(R.id.equalsButton);
        final Button plusB = (Button)findViewById(R.id.plusButton);
        Button sinB = (Button)findViewById(R.id.sinButton);
        final Button tanB = (Button)findViewById(R.id.tanButton);
        Button cosB = (Button)findViewById(R.id.cosButton);
        Button delB = (Button)findViewById(R.id.deleteButton);
        Button clrB = (Button)findViewById(R.id.clearButton);
        Button bracketB = (Button) findViewById(R.id.bracketButton);
        Button logB = (Button)findViewById(R.id.logButton);
        Button lnB = (Button)findViewById(R.id.lnButton);
        Button binButton = (Button)findViewById(R.id.bbb);
        Button sqrtB = (Button)findViewById(R.id.sqrtButton);
        final Button popupB = (Button)findViewById(R.id.popupButton);
        final TextView calculationView = (TextView)findViewById(R.id.instantCalcluationView);
        final TextView pView = (TextView)findViewById(R.id.primeView);
        final Button angleB = (Button)findViewById(R.id.angleButton);
        final Button exponentB = (Button) findViewById(R.id.exponentButton);
        final TextView instantCalcView = (TextView)findViewById(R.id.calcView);
        final LinearLayout myLinear = (LinearLayout)findViewById(R.id.linearLayout);
        final ConstraintLayout myLayout = (ConstraintLayout)findViewById(R.id.myBackLayout);

        //instantCalcView




        currentCalculation = " ";
        displayCalculation = " ";
        pVisible = false;
        exponentOn = false;
        pView.setVisibility(TextView.INVISIBLE);
        //android.support.v7.app.ActionBar bar = getSupportActionBar();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));

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

        sqrtB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("square root", "Button pressed");
                currentCalculation += "sqrt(";
                displayCalculation += "√(";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
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
                        if(item.getTitle().equals("Settings")){
                            //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                            builderSingle.setTitle("Set a theme:");

                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
                            arrayAdapter.add("Stealth Black");
                            arrayAdapter.add("Minimal White");


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
                                    Toast.makeText(MainActivity.this, strName, Toast.LENGTH_SHORT).show();

                                    if(which == 0){
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




                                    }
                                    else if(which == 1){
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
                                    }
                                }
                            });
                            builderSingle.show();
                        }
                        else{
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(MainActivity.this);
                            }

                            String nodata="<br/>&#8226; Version 1.5<br/>&#8226; Made by Kumail Naqvi, 2017<br/>&#8226; kumailmn@gmail.com<br/>&#8226; github.com/kumailn<br/>&#8226; powered by mXparser (mathparser.org)";
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



        zeroB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "0";
                displayCalculation += "0";
                calculationView.setText(displayCalculation);


                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
                equalsMethod();


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
                vv.vibrate(50);
                equalsMethod();

            }
        });

        oneB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "e";
                displayCalculation += "e";
                calculationView.setText(displayCalculation);
                equalsMethod();
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
                vv.vibrate(50);
                equalsMethod();
            }
        });

        twoB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "[phi]";
                displayCalculation += "φ";
                calculationView.setText(displayCalculation);
                equalsMethod();
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
                vv.vibrate(50);
                equalsMethod();
            }
        });

        threeB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "#";
                displayCalculation += "%";
                calculationView.setText(displayCalculation);
                equalsMethod();
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
                vv.vibrate(50);
                equalsMethod();
            }
        });

        fiveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "5";
                displayCalculation += "5";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
                equalsMethod();
            }
        });

        sixB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "6";
                displayCalculation += "6";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
                equalsMethod();

            }
        });

        sevenB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentCalculation += "7";
                displayCalculation += "7";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
                equalsMethod();
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
                vv.vibrate(50);
                equalsMethod();
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
                vv.vibrate(50);
                equalsMethod();
            }
        });

        logB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "log(10,";
                displayCalculation += "log(";
                calculationView.setText(displayCalculation);
                equalsMethod();
            }
        });

        lnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "ln(";
                displayCalculation += "ln(";
                calculationView.setText(displayCalculation);
                equalsMethod();
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
                }
                else{
                    currentCalculation += ")";
                    displayCalculation += ")";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
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

            }
        });

        sinB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(angle == true){
                    currentCalculation += "asin([deg]*";
                    displayCalculation += "arcsin(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    return true;
                }
                else {
                    currentCalculation += "asin([rad]*";
                    displayCalculation += "arcsin(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    return true;
                }

            }
        });

        cosB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        cosB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(angle == true){
                    currentCalculation += "acos([deg]*";
                    displayCalculation += "arccos(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    return true;
                }
                else{
                    currentCalculation += "acos([rad]*";
                    displayCalculation += "arccos(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    return true;
                }

            }
        });

        tanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });

        tanB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(angle == true){
                    currentCalculation += "atan([deg]*";
                    displayCalculation += "arctan(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
                    return true;
                }
                else{
                    currentCalculation += "atan([rad]*";
                    displayCalculation += "arctan(";
                    calculationView.setText(displayCalculation);
                    equalsMethod();
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
                vv.vibrate(80);
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
                vv.vibrate(80);
                equalsMethod();
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
                vv.vibrate(80);
                equalsMethod();
            }
        });

        divB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "C(";
                displayCalculation += "C(";
                calculationView.setText(displayCalculation);
                equalsMethod();
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
                vv.vibrate(80);
                equalsMethod();
            }
        });

        minusB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += ",";
                displayCalculation += ",";
                calculationView.setText(displayCalculation);
                equalsMethod();
                return true;
            }
        });

        delB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);

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

            }
        });

        clrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(120);

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
                        currentCalculation += ".";
                        displayCalculation += ".";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += ".";
                    displayCalculation += ".";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
                equalsMethod();
            }
        });

        decimalB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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
                vv.vibrate(50);
                equalsMethod();
                return true;
            }
        });

        ansB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(previousAns != null){
                    currentCalculation += previousAns;
                    displayCalculation += "Ans";
                    calculationView.setText(displayCalculation);
                }
                equalsMethod();

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
                vv.vibrate(110);


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

                if(currentCalculation.contains("pi")){
                    String xx = currentCalculation;
                    xx = xx.substring(0, 4) + "." + xx.substring(4, xx.length());
                }

                Expression e = new Expression(currentCalculation);
                String result = String.valueOf(e.calculate());
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
                    pView.setVisibility(View.INVISIBLE);
                    pVisible = false;
                }

                if(!(result.equals("NaN"))){
                    previousAns = result;
                }
                else{
                    result = "Error";
                    //result = "";


                }
                //instantCalcView.setText(" " + result);
                if(result.equals("Error")){
                    instantCalcView.setText(" " + result);
                    result = "";
                }
                else{
                    instantCalcView.setText(" " + result);
                    calculationView.setText(" " + "Ans");
                    displayCalculation = (" " + "Ans");
                    currentCalculation = result;
                }



            }
        });

        binButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String newR = previousAns.replaceAll("\\s+","");;
                String ccv = String.valueOf(instantCalcView.getText());
                Log.e("INBIN", ccv);
                try{
                    ccv = ccv.replaceAll("\\s+","");;
                    Integer newR2 = Integer.parseInt(ccv);
                    instantCalcView.setText(Integer.toBinaryString(newR2));
                    //equalsMethod();
                }
                catch (Exception e){}
            }

        });

        binButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //String newR = previousAns.replaceAll("\\s+","");
                String ccv = String.valueOf(instantCalcView.getText());
                try{
                    ccv = ccv.replaceAll("\\s+","");;
                    Integer newR2 = Integer.parseInt(ccv);
                    instantCalcView.setText(Integer.toHexString(newR2));
                    //equalsMethod();
                }
                catch (Exception e){}
                return true;
            }
        });

        equalsB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Rational fraction = new Rational(Double.valueOf(previousAns));
                calculationView.setText(" " + fraction.simple());
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

    public void equalsMethod(){
        TextView calculationView = (TextView)findViewById(R.id.calcView);
        TextView instantCalcView = (TextView)findViewById(R.id.instantCalcluationView);
        TextView pView = (TextView)findViewById(R.id.primeView);

        if(currentCalculation.contains(",") && (!(currentCalculation.contains("lcm")) && !(currentCalculation.contains("gcd")))){
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

        if(currentCalculation.contains("pi")){
            String xx = currentCalculation;
            xx = xx.substring(0, 4) + "." + xx.substring(4, xx.length());
        }

        Expression e = new Expression(currentCalculation);
        String result = String.valueOf(e.calculate());
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
            Expression ex = new Expression("ispr(" + instantCalcView.getText()  + ")");
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
        //displayCalculation = " " + result;
        //currentCalculation = result;
    }



/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflates menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about){
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(MainActivity.this);
            }
            builder.setTitle("About the app")
                    .setMessage("Made by Kumail Naqvi, 2017, Version 1.0, Contact me at kumailmn@gmail.com, github.com/kumailn")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }

                    })
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        if(item.getItemId() == R.id.action_settings){
            //Intent i = new Intent(this, SettingsActivity.class);
            //startActivity(i);
            Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);    }*/


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
