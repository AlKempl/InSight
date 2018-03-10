package com.pelicanus.insight.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pelicanus.insight.R;

import java.io.ByteArrayOutputStream;

import lombok.NonNull;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Slavik on 09.03.2018.
 */

public class Picture {
    public enum Type {Trip_avatar, User_avatar}

    @NonNull
    ImageView imageView;
    Type type;
    String name;
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    long maxSize = 1024*1024;

    public Picture(ImageView imageView, Type type) {
        imageView.setImageResource(R.drawable.city_zaglushka);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        this.imageView = imageView;
        this.type = type;
    }

    public Picture(ImageView imageView, Type type, String name) {
        imageView.setImageResource(R.drawable.city_zaglushka);
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

    /** Отправка на сервер
     *
     * @return Успешность отправки
     */
    public boolean Upload() {
        String path = type.toString();
        boolean check = false;
        if (name != null) {
            storage.child(path+"/"+name).putBytes(ExtractData());
            check = true;
        }
        return check;
    }
    public boolean Upload(String pic_name) {
        name = pic_name;
        return Upload();
    }
    public boolean Load() {
        if (name == null) {
            name = "default.jpeg";
        }
        imageView.setImageDrawable(null);
        storage.child(type.toString()+"/"+name).getBytes(maxSize).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] image_b) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(image_b, 0, image_b.length));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                switch (type) {
                    case Trip_avatar:
                        imageView.setImageResource(R.drawable.facebook_login_logo);
                        break;
                    case User_avatar:
                        imageView.setImageResource(R.drawable.city_zaglushka);
                        break;
                }
            }
        });
        return true;
    }
    public boolean Load(String pic_name) {
        name = pic_name;
        return Load();
    }

    public void Set(Uri selectedImage) {
        imageView.setImageDrawable(null);
        imageView.setImageURI(selectedImage);
    }



}
