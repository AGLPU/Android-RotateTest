package com.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends Activity {
    private Button btnRotate;
    private Button btnReset;
    private Button btnCapture;
    private Button btnBrowse;
    private Button btnZoomOut;
    private Button btnZoomIn;
    private Button btnMoveRight;
    private Button btnMoveLeft;

    private ImageView imageView;
    private Bitmap image;
    private TextView msg;

    private LinearLayout captureLayout;
    private List<Bitmap> captured = new ArrayList<Bitmap>();

    private float x = 0, y = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViews();
        addListeners();

        this.image = BitmapFactory.decodeResource(getResources(), R.drawable.m);
        this.imageView.setImageBitmap(image);
//        this.oriWidth = image.getWidth();
//        this.oriHeight = image.getHeight();
    }

//    private void setNewImage(Bitmap newImage) {
//        imageInfo(newImage);
//        this.imageView.setImageBitmap(newImage);
//        if (image != null) {
//            image.recycle();
//        }
//        image = newImage;
//    }

    private void imageInfo(Bitmap newImage) {
        String s = "w: " + newImage.getWidth() + ", h: " + newImage.getHeight();
        this.msg.setText(s);
    }


    private void findViews() {
        this.btnRotate = (Button) findViewById(R.id.BtnRotate);
        this.btnBrowse = (Button) findViewById(R.id.BtnBrowse);
        this.btnReset = (Button) findViewById(R.id.BtnReset);
        this.btnCapture = (Button) findViewById(R.id.BtnCapture);
        this.btnZoomOut = (Button) findViewById(R.id.BtnZoomOut);
        this.btnZoomIn = (Button) findViewById(R.id.BtnZoomIn);
        this.btnMoveLeft = (Button) findViewById(R.id.BtnMoveLeft);
        this.btnMoveRight = (Button) findViewById(R.id.BtnMoveRight);

        this.captureLayout = (LinearLayout) findViewById(R.id.captures);
        this.imageView = (ImageView) findViewById(R.id.Image);
        this.msg = (TextView) findViewById(R.id.Msg);
    }

    private void addListeners() {
        this.btnRotate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Matrix matrix = new Matrix();
//                matrix.setRotate(10);
//                Bitmap newImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
//                setNewImage(newImage);

                Matrix matrix = new Matrix();

                //copying the image matrix(source) to this matrix
                matrix.set(imageView.getImageMatrix());

                matrix.postRotate(10, imageView.getWidth() / 2, imageView.getHeight() / 2);
                imageView.setImageMatrix(matrix);

                //checking the size of the image
                Drawable d = imageView.getDrawable();
                Bitmap bmp = ((BitmapDrawable) d).getBitmap();
                imageInfo(bmp);
            }
        });
        this.btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();

                //copying the image matrix(source) to this matrix
                matrix.set(imageView.getImageMatrix());
                matrix.postScale(1.2f, 1.2f);
                // matrix.postRotate(10, imageView.getWidth() / 2, imageView.getHeight() / 2);
                imageView.setImageMatrix(matrix);

                //checking the size of the image
                Drawable d = imageView.getDrawable();
                Bitmap bmp = ((BitmapDrawable) d).getBitmap();
                imageInfo(bmp);
            }
        });
        this.btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();

                //copying the image matrix(source) to this matrix
                matrix.set(imageView.getImageMatrix());
                matrix.postScale(0.8f, 0.8f);
                // matrix.postRotate(10, imageView.getWidth() / 2, imageView.getHeight() / 2);
                imageView.setImageMatrix(matrix);

                //checking the size of the image
                Drawable d = imageView.getDrawable();
                Bitmap bmp = ((BitmapDrawable) d).getBitmap();
                imageInfo(bmp);
            }
        });
        this.btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bmp = getBitmapFromView();
                captured.add(bmp);
                btnBrowse.setText("Browse " + captured.size());
            }
        });
        this.btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captureLayout.getVisibility() == View.VISIBLE) {
                    imageView.setVisibility(View.VISIBLE);
                    captureLayout.setVisibility(View.GONE);
                    captureLayout.removeAllViews();
                } else {
                    captureLayout.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    for (Bitmap m : captured) {
                        ImageView view = new ImageView(MyActivity.this);
                        view.setLayoutParams(new AbsListView.LayoutParams(100, 100));
                        view.setImageBitmap(m);
                        captureLayout.addView(view);
                    }
                }
            }
        });
        this.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageMatrix(new Matrix());
            }
        });
        this.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        move(event.getX() - x, event.getY() - y);
                        x = event.getX();
                        y = event.getY();
                        break;
                }
                return true;
            }
        });
        this.btnMoveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                matrix.set(imageView.getImageMatrix());
                matrix.postTranslate(-10, 0);
                imageView.setImageMatrix(matrix);
            }
        });
        this.btnMoveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matrix matrix = new Matrix();
                matrix.set(imageView.getImageMatrix());
                matrix.postTranslate(10, 0);
                imageView.setImageMatrix(matrix);
            }
        });
    }

    private void move(float offsetX, float offsetY) {
        Matrix matrix = new Matrix();
        matrix.set(imageView.getImageMatrix());
        matrix.postTranslate(offsetX, offsetY);
        imageView.setImageMatrix(matrix);
    }

    private Bitmap getBitmapFromView() {
        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null
        imageView.setDrawingCacheEnabled(true);
        imageView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        imageView.layout(0, 0, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
        imageView.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(imageView.getDrawingCache());
        imageView.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }

}
