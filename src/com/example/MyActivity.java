package com.example;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActivity extends Activity {
    private Button button;
    private ImageView imageView;
    private Bitmap image;
    private TextView msg;

    private int oriWidth;
    private int oriHeight;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViews();
        addListeners();

        setNewImage(BitmapFactory.decodeResource(getResources(), R.drawable.m));
        this.oriWidth = image.getWidth();
        this.oriHeight = image.getHeight();
    }

    private void setNewImage(Bitmap newImage) {
        imageInfo(newImage);
        this.imageView.setImageBitmap(newImage);
        if (image != null) {
            image.recycle();
        }
        image = newImage;
    }

    private void imageInfo(Bitmap newImage) {
        String s = "w: " + newImage.getWidth() + ", h: " + newImage.getHeight();
        this.msg.setText(s);
    }

    private void addListeners() {
        this.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Matrix matrix = new Matrix();
                matrix.setRotate(10);
                Bitmap newImage = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
                setNewImage(newImage);
            }
        });
    }

    private void findViews() {
        this.button = (Button) findViewById(R.id.Btn);
        this.imageView = (ImageView) findViewById(R.id.Image);
        this.msg = (TextView) findViewById(R.id.Msg);
    }

}
