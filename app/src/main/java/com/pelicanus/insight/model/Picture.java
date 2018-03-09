package com.pelicanus.insight.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import lombok.NonNull;

/**
 * Created by Slavik on 09.03.2018.
 */

public class Picture {

    @NonNull
    ImageView imageView;
    public enum PictureType {Trip_avatar, User_avatar}
    PictureType type;
    public Picture(ImageView imageView, PictureType type) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        this.imageView = imageView;
        this.type = type;
    }



    private byte[] ExtractData() {
        Bitmap bitmap = imageView.getDrawingCache();

        // создаем ByteArrayOutputStream, необходимый для создания массива байтов для метод putBytes()
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // делаем компрессию данных для экономии
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // создаем массив байтов
        return baos.toByteArray();
    }
    public boolean Upload(String pic_name) {
        String path = type.toString();
        /*StorageReference storage = */
        FirebaseStorage.getInstance().getReference().child(path+"/"+pic_name).putBytes(ExtractData());
        //UploadTask uploadTask = storage.putBytes(ExtractData());
        boolean check = true;
        /*uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@android.support.annotation.NonNull Exception exception) {
                che
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Успешно
            }
        });*/
        return check;
    }

}
