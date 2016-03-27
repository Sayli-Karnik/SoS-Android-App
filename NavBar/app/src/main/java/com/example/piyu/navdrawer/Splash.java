package com.example.piyu.navdrawer;
/*
* This is the initiator activity showing the app logo with animation.
* It detects gestures for ringing the siren and sending messages.
* */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.GestureDetector;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;


public class Splash extends AppCompatActivity {
    private ImageView imageView;
    private Animation animation;
    private Animation animation2;
    private GestureDetector mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"Long press to send sms",Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"Double tap to ring siren",Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_splash);
        Android_Gesture_Detector  android_gesture_detector  =  new Android_Gesture_Detector();
        // Create a GestureDetector
        mGestureDetector = new GestureDetector(this, android_gesture_detector);
        imageView= (ImageView)findViewById(R.id.imageView);
        animation= AnimationUtils.loadAnimation(getBaseContext(),R.anim.splash_anim);
        animation2= AnimationUtils.loadAnimation(getBaseContext(),R.anim.splashfadeout);
        animation.setDuration(4000);
        imageView.startAnimation(animation);
        // Create an object of the Android_Gesture_Detector  Class

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.startAnimation(animation2);
                finish();
                Intent intent = new Intent(getBaseContext(), SlidingClass.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
        // Return true if you have consumed the event, false if you haven't.
        // The default implementation always returns false.
    }
    class Android_Gesture_Detector implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("Gesture ", " onDown");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapConfirmed");
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapUp");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("Gesture ", " onShowPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("Gesture ", " onDoubleTap");
            Intent in = new Intent(Splash.this,SirenActivity.class);
            startActivity(in);

            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("Gesture ", " onDoubleTapEvent");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("Gesture ", " onLongPress");
            SharedPreferences sharedpreferences = getSharedPreferences("Emergency_Numbers", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =  sharedpreferences.edit();
            int size_of_emergency_contacts = sharedpreferences.getAll().size();
            int j=0;
            try {
                SmsManager smsManager = SmsManager.getDefault();
                while (size_of_emergency_contacts > 0) {
                    String phoneNo =sharedpreferences.getString("phno"+j,"9869105188");
                    j++;
                    smsManager.sendTextMessage(phoneNo, null, "Help Me! I am in danger!", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                    size_of_emergency_contacts--;
                }
            }
            catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Log.d("Gesture ", " onScroll");
            if (e1.getY() < e2.getY()){
                Log.d("Gesture ", " Scroll Down");
//                SharedPreferences sharedpreferences = getSharedPreferences("Emergency_Numbers", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor =  sharedpreferences.edit();
//                int size_of_emergency_contacts = sharedpreferences.getAll().size();
//                int j=0;
//                try {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    while (size_of_emergency_contacts > 0) {
//                        String phoneNo =sharedpreferences.getString("phno"+j,"9869105188");
//                        j++;
//                        smsManager.sendTextMessage(phoneNo, null, "Help Me! I am in danger!", null, null);
//                        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
//                        size_of_emergency_contacts--;
//                    }
//                }
//                catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
//                    e.printStackTrace();
//                }

            }
            if(e1.getY() > e2.getY()){
                Log.d("Gesture ", " Scroll Up");
                }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() < e2.getX()) {
                Log.d("Gesture ", "Left to Right swipe: "+ e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
            }
            if (e1.getX() > e2.getX()) {
                Log.d("Gesture ", "Right to Left swipe: "+ e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
            }
            if (e1.getY() < e2.getY()) {
                Log.d("Gesture ", "Up to Down swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            if (e1.getY() > e2.getY()) {
                Log.d("Gesture ", "Down to Up swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            return false;

        }
    }

}
