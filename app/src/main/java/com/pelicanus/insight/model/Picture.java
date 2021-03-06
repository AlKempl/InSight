package com.pelicanus.insight.model;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pelicanus.insight.PictureSetActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


/**
 * Created by Slavik on 09.03.2018.
 */
public class Picture {
    long maxSize = 1024 * 1024;
    @NonNull
    private ImageView imageView;
    @Getter
    private Type type;
    @Setter
    private String name;
    private StorageReference storage = FirebaseStorage.getInstance().getReference();
    private Bitmap bitmap;
    private int quality = 100;
    public Picture(ImageView imageView, Type type) {
        this.setImageView(imageView);
        this.type = type;
    }

    public Picture(ImageView imageView, Type type, String name) {
        this.setImageView(imageView);
        this.type = type;
        this.name = name;
    }

    public Picture(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public Picture(Type type) {
        this.type = type;
    }
    public void setImageView(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        this.imageView = imageView;
        if (name != null)
            LoadToImageView();
    }

    public void SetDefault() {
        DownloadDefault();
    }

    private byte[] ExtractData() {
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return baos.toByteArray();
    }

    public void LoadToImageView() {
        if (imageView != null && bitmap != null) {
             imageView.setImageBitmap(bitmap);
        } else if (bitmap == null)
            Download();
    }

    public void Upload() {
        if (name != null)
            storage.child(type.toString() + "/" + name).putBytes(ExtractData());
    }

    public void Download() {
        Download(false);
    }

    public void Download(boolean forcibly) {
        if (name == null) {
            SetDefault();
            return;
        }
        if (bitmap == null || forcibly) {
            OnSuccessListener<byte[]> SucList = new OnSuccessListener<byte[]>() {
                @Override
                public synchronized void onSuccess(byte[] image_b) {
                    bitmap = BitmapFactory.decodeByteArray(image_b, 0, image_b.length);
                    LoadToImageView();
                }
            };
            OnFailureListener fail = new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    SetDefault();
                }
            };
            Task<byte[]> task = storage.child(type.toString() + "/" + name).getBytes(maxSize);
            task.addOnSuccessListener(SucList).addOnFailureListener(fail);
        }else {
            LoadToImageView();
        }
    }

    public void DownloadDefault() {
        OnSuccessListener<byte[]> SucList = new OnSuccessListener<byte[]>() {
                @Override
                public synchronized void onSuccess(byte[] image_b) {
                    bitmap = BitmapFactory.decodeByteArray(image_b, 0, image_b.length);
                    LoadToImageView();
                }
            };
            OnFailureListener fail = new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            };
            Task<byte[]> task = storage.child(type.toString() + "/" + "default.jpg").getBytes(maxSize);
            task.addOnSuccessListener(SucList).addOnFailureListener(fail);
    }

    public void Set(Activity activity) {
        DataHolder.getInstance().save("PICTURE_SET", this);
        activity.startActivity(new Intent(activity, PictureSetActivity.class));
    }

    public void Set(Uri uri, Activity activity) throws IOException {
        bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver() , uri);
        LoadToImageView();
        DataHolder.getInstance().remove("PICTURE_SET");
    }

    public enum Type {Trip_avatar, User_avatar, Test}
}
