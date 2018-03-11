package com.pelicanus.insight.model;

import android.app.Activity;
import android.content.Intent;
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
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        this.imageView = imageView;
        this.type = type;
    }

    public Picture(ImageView imageView, Type type, String name) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        this.imageView = imageView;
        this.type = type;
        this.name = name;
    }

    public void SetDefault() {
        switch (type) {
            case Trip_avatar:
                imageView.setImageResource(R.drawable.city_zaglushka);
                break;
            case User_avatar:
                imageView.setImageResource(R.mipmap.ic_launcher_round);
                break;
        }
    }

    private byte[] ExtractData() {
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public boolean Upload() {
        boolean check = false;
        if (name != null) {
            storage.child(type.toString()+"/"+name).putBytes(ExtractData());
            check = true;
        }
        return check;
    }
    public boolean Upload(String pic_name) {
        name = pic_name;
        return Upload();
    }

    public boolean Download() {
        if (name == null) {
            SetDefault();
            return false;
        }
        final boolean[] check = {true};
        imageView.setImageDrawable(null);
        storage.child(type.toString()+"/"+name).getBytes(maxSize).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] image_b) {
                imageView.setImageBitmap(BitmapFactory.decodeByteArray(image_b, 0, image_b.length));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                SetDefault();
                check[0] = false;
            }
        });
        return check[0];
    }
    public boolean Download(String pic_name) {
        name = pic_name;
        return Download();
    }

    public void Set(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        activity.startActivityForResult(intent, 1);
    }
    public void Set(Uri uri) {
        imageView.setImageURI(uri);
    }
    /* Не удалять! Этот метод нужно скопировать в активити, чтобы работала загрузка из галереи
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    <объект класса Picture>.Set(imageReturnedIntent.getData());
                }
        }
    }
     */
}
