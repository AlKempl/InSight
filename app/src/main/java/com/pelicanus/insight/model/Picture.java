package com.pelicanus.insight.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pelicanus.insight.CreateTrip;

import java.io.ByteArrayOutputStream;

import lombok.NonNull;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Slavik on 09.03.2018.
 */

public class Picture {
    public enum PictureType {Trip_avatar, User_avatar}

    @NonNull
    ImageView imageView;
    PictureType type;
    String name;
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    long maxSize = 1024*1024;
    public Picture(ImageView imageView, PictureType type) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        this.imageView = imageView;
        this.type = type;
    }
    public Picture(ImageView imageView, PictureType type, String name) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        this.imageView = imageView;
        this.type = type;
        this.name = name;
    }


    private byte[] ExtractData() {
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // делаем компрессию данных для экономии
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
    //логический тип - чтобы реализовать проверку на успешность загрузки
    public boolean Upload(String pic_name) {
        String path = type.toString();
        /*StorageReference storage = */
        storage.child(path+"/"+pic_name).putBytes(ExtractData());
        //UploadTask uploadTask = storage.putBytes(ExtractData());
        boolean check = true;
        return check;
    }
    public boolean Upload() {
        String path = type.toString();
        storage.child(path+"/"+name).putBytes(ExtractData());
        boolean check = true;
        return check;
    }
    public boolean Load() {
        if (name == null) {
            name = "default.jpeg";
        }
        storage.child(type.toString()+"/"+name).getBytes(maxSize).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] image_b) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(image_b, 0, image_b.length));
            }
        });
        return true;
    }
   /* private void callPickedImageActivity() {
        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE_REQUEST_CODE);
    }*/
    public void Set() {
        Intent imageReturnedIntent = new Intent(Intent.ACTION_PICK);
        Uri selectedImage = imageReturnedIntent.getData();
        imageView.setImageURI(selectedImage);
    }

}
