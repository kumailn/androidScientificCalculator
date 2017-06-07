package com.example.kumail.scientificcalculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.mariuszgromada.math.mxparser.Expression;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import org.mariuszgromada.math.mxparser.*;

import android.widget.EditText;
import android.widget.TextView;
import android.os.Vibrator;
import android.widget.Toast;

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
    //False == rad, true == deg
    public static Boolean angle;
    public static final String defaultMethod = "rad";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        final Button tanB = (Button)findViewById(R.id.tanButton);
        Button cosB = (Button)findViewById(R.id.cosButton);
        Button delB = (Button)findViewById(R.id.deleteButton);
        Button clrB = (Button)findViewById(R.id.clearButton);
        Button bracketB = (Button) findViewById(R.id.bracketButton);
        Button logB = (Button)findViewById(R.id.logButton);
        Button lnB = (Button)findViewById(R.id.lnButton);
        Button sqrtB = (Button)findViewById(R.id.sqrtButton);
        final EditText editT = (EditText)findViewById(R.id.editText1);
        final TextView pView = (TextView)findViewById(R.id.primeView);
        final Button angleB = (Button)findViewById(R.id.angleButton);
        final Button exponentB = (Button) findViewById(R.id.exponentButton);
        final TextView calculationView = (TextView)findViewById(R.id.calcView);


        currentCalculation = " ";
        displayCalculation = " ";
        pVisible = false;
        exponentOn = false;
        pView.setVisibility(TextView.INVISIBLE);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        editT.setInputType(InputType.TYPE_NULL);
        editT.setTextIsSelectable(true);

        if(loadAngle().equals("rad")){
            angleB.setText("R");
            angle = false;
        }
        else{
            angleB.setText("D");
            angleB.setTextColor(getResources().getColor(R.color.degree));
            angle = true;
        }

        sqrtB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "sqrt(";
                displayCalculation += "√(";
                calculationView.setText(displayCalculation);

                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });



        zeroB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(0";
                        displayCalculation += "⁰";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "0";
                        displayCalculation += "⁰";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "0";
                    displayCalculation += "0";
                    calculationView.setText(displayCalculation);
                    editT.setText(displayCalculation);
                    editT.setSelection(displayCalculation.length());
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });

        zeroB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "!";
                displayCalculation += "!";
                calculationView.setText(displayCalculation);
                return true;
            }
        });

        oneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(1";
                        displayCalculation += "¹";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "1";
                        displayCalculation += "¹";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "1";
                    displayCalculation += "1";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);

            }
        });

        oneB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "e";
                displayCalculation += "e";
                calculationView.setText(displayCalculation);
                return true;
            }
        });

        twoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(2";
                        displayCalculation += "²";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "2";
                        displayCalculation += "²";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "2";
                    displayCalculation += "2";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });

        twoB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "[phi]";
                displayCalculation += "φ";
                calculationView.setText(displayCalculation);
                return true;
            }
        });

        threeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(3";
                        displayCalculation += "³";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "3";
                        displayCalculation += "³";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "3";
                    displayCalculation += "3";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });

        threeB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "#";
                displayCalculation += "%";
                calculationView.setText(displayCalculation);
                return true;
            }
        });



        fourB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(4";
                        displayCalculation += "⁴";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "4";
                        displayCalculation += "⁴";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "4";
                    displayCalculation += "4";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });

        fiveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(5";
                        displayCalculation += "⁵";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "5";
                        displayCalculation += "⁵";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "5";
                    displayCalculation += "5";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });

        sixB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(6";
                        displayCalculation += "⁶";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "6";
                        displayCalculation += "⁶";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "6";
                    displayCalculation += "6";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });

        sevenB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(7";
                        displayCalculation += "⁷";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "7";
                        displayCalculation += "⁷";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "7";
                    displayCalculation += "7";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });

        sevenB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "lcm(";
                displayCalculation += "LCM(";
                calculationView.setText(displayCalculation);
                return true;
            }
        });

        eightB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(8";
                        displayCalculation += "⁸";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "8";
                        displayCalculation += "⁸";
                        calculationView.setText(displayCalculation);
                    }

                }
                else{
                    currentCalculation += "8";
                    displayCalculation += "8";
                    calculationView.setText(displayCalculation);
                }
                Vibrator vv = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vv.vibrate(50);
            }
        });

        eightB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "gcd(";
                displayCalculation += "GCD(";
                calculationView.setText(displayCalculation);
                return true;
            }
        });

        nineB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    if (firstExponent == true){
                        currentCalculation += "^(9";
                        displayCalculation += "⁹";
                        calculationView.setText(displayCalculation);
                        firstExponent = false;
                    }
                    else {
                        currentCalculation += "9";
                        displayCalculation += "⁹";
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
            }
        });

        logB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "log(10,";
                displayCalculation += "log(";
                calculationView.setText(displayCalculation);
            }
        });

        lnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "ln(";
                displayCalculation += "ln(";
                calculationView.setText(displayCalculation);
            }
        });

        bracketB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += ")";
                displayCalculation += ")";
                calculationView.setText(displayCalculation);
            }
        });

        bracketB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "(";
                displayCalculation += "(";
                calculationView.setText(displayCalculation);
                return true;
            }
        });

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

            }
        });

        sinB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(angle == true){
                    currentCalculation += "asin([deg]*";
                    displayCalculation += "arcsin(";
                    calculationView.setText(displayCalculation);
                    return true;
                }
                else {
                    currentCalculation += "asin([rad]*";
                    displayCalculation += "arcsin(";
                    calculationView.setText(displayCalculation);
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
            }
        });

        cosB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(angle == true){
                    currentCalculation += "acos([deg]*";
                    displayCalculation += "arccos(";
                    calculationView.setText(displayCalculation);
                    return true;
                }
                else{
                    currentCalculation += "acos([rad]*";
                    displayCalculation += "arccos(";
                    calculationView.setText(displayCalculation);
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

            }
        });

        tanB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(angle == true){
                    currentCalculation += "atan([deg]*";
                    displayCalculation += "arctan(";
                    calculationView.setText(displayCalculation);
                    return true;
                }
                else{
                    currentCalculation += "atan([rad]*";
                    displayCalculation += "arctan(";
                    calculationView.setText(displayCalculation);
                    return true;
                }
            }
        });

        exponentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exponentOn == true){
                    exponentOn = false;
                    currentCalculation += ")";
                    exponentB.setTextColor(getResources().getColor(R.color.black));
                }
                else{
                    exponentOn = true;
                    firstExponent = true;
                    exponentB.setTextColor(getResources().getColor(R.color.colorAccent));

                }
            }
        });

        multB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "*";
                displayCalculation += "×";
                calculationView.setText(displayCalculation);
            }
        });

        plusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "+";
                displayCalculation += "+";
                calculationView.setText(displayCalculation);
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
                currentCalculation += "/";
                displayCalculation += "÷";
                calculationView.setText(displayCalculation);
            }
        });

        divB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += "C(";
                displayCalculation += "C(";
                calculationView.setText(displayCalculation);
                return true;
            }
        });

        minusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation += "-";
                displayCalculation += "-";
                calculationView.setText(displayCalculation);
            }
        });

        minusB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                currentCalculation += ",";
                displayCalculation += ",";
                calculationView.setText(displayCalculation);
                return true;
            }
        });

        delB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCalculation.length() < 1){
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
                    else{
                        currentCalculation = currentCalculation.substring(0, currentCalculation.length() - 1);
                        displayCalculation = displayCalculation.substring(0, displayCalculation.length() - 1);
                        calculationView.setText(displayCalculation);
                    }
                }

            }
        });

        clrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCalculation = " ";
                displayCalculation = " ";
                calculationView.setText(displayCalculation);
                if(exponentOn == true){
                    exponentOn = false;
                    firstExponent = true;
                    exponentB.setTextColor(getResources().getColor(R.color.black));
                }

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
                return true;
            }
        });

        ansB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(previousAns != null){
                    currentCalculation += previousAns;
                    displayCalculation += previousAns;
                    calculationView.setText(displayCalculation);
                }

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
                if(currentCalculation.equals("") || currentCalculation.equals(" ") || currentCalculation.equals("   ")){
                    return;
                }

                if(exponentOn == true){
                    currentCalculation += ")";
                    exponentOn = false;
                    firstExponent = true;
                    exponentB.setTextColor(getResources().getColor(R.color.black));
                }

                Log.e("currentC", currentCalculation);

                Expression e = new Expression(currentCalculation);
                String result = String.valueOf(e.calculate());
                if(result.endsWith("E-16") || result.endsWith("E-15") || result.endsWith("E-14")){
                    result = "0";
                }
                if (result.endsWith(".0")){
                    result = result.substring(0, result.length() - 2);
                }
                if(result.length() > 12){
                    result = result.substring(0,12);
                }

                if (pVisible == true){
                    pView.setVisibility(View.INVISIBLE);
                    pVisible = false;
                }
                calculationView.setText(" " + result);
                displayCalculation = " " + result;
                if(!(result.equals("NaN"))){
                    previousAns = result;
                }
                currentCalculation = result;


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
                    angleB.setText("R");
                    angleB.setTextColor(Color.RED);
                    saveAngle("rad");
                    angle = false;
                    //currentCalculation.replaceAll("rad", "deg");
                }
                else{
                    angleB.setText("D");
                    angleB.setTextColor(getResources().getColor(R.color.degree));
                    saveAngle("deg");
                    angle = true;
                    //currentCalculation.replaceAll("deg", "rad");
                }
            }
        });


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