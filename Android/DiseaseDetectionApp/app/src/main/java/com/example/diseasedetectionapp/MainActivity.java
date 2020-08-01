package com.example.diseasedetectionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity extends AppCompatActivity {
    ImageView imageView1, imageView2, imageView3, imageView4;
    Button button;
    ProgressBar progressBar;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    int selectIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!Python.isStarted()){
            Python.start(new AndroidPlatform(MainActivity.this));
        }

        button = (Button) findViewById(R.id.chooseImgBtn);
        imageView1 = (ImageView) findViewById(R.id.selectImg1);
        imageView2 = (ImageView) findViewById(R.id.selectImg2);
        imageView3 = (ImageView) findViewById(R.id.selectImg3);
        imageView4 = (ImageView) findViewById(R.id.selectImg4);
        progressBar=findViewById(R.id.progressBar);
        executePythonScript();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectIndex < 4) {
                    selectIndex++;

                }
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    public void executePythonScript(){
        Python python=Python.getInstance();
        PyObject test=python.getModule("test");
        Log.i("PYTHON:",test.callAttr("test").toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            switch (selectIndex) {
                case 1:
                    imageView1.setImageURI(imageUri);
                    break;
                case 2:
                    imageView2.setImageURI(imageUri);
                    break;
                case 3:
                    imageView3.setImageURI(imageUri);
                    break;
                case 4:
                    imageView4.setImageURI(imageUri);
                    break;
            }
            progressBar.incrementProgressBy(1);
        }
    }
}