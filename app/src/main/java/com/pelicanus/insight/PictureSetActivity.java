package com.pelicanus.insight;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

public class PictureSetActivity extends AppCompatActivity {
    Picture picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_set);
        picture = (Picture) DataHolder.getInstance().retrieve("PICTURE_SET");
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent ReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, ReturnedIntent);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    int x = 16, y = 9;
                    if (picture.getType() == Picture.Type.Trip_avatar) {
                        x = 64;
                        y = 35;
                    } else  if (picture.getType() == Picture.Type.User_avatar) {
                        x = 1;
                        y = 1;
                    }
                    UCrop.of( ReturnedIntent.getData(), genUri())
                            .withAspectRatio(x, y)
                            .withMaxResultSize(1024, 1024)
                            .start(PictureSetActivity.this);
                }
                break;
            case UCrop.REQUEST_CROP:
                 if (resultCode == RESULT_OK) {
                     try {
                        picture.Set(UCrop.getOutput(ReturnedIntent), this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == UCrop.RESULT_ERROR) {
                    final Throwable cropError = UCrop.getError(ReturnedIntent);
                    Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            default:
                PictureSetActivity.this.finish();
        }
    }
    //Да, картинка в итоге сохраняется на телефоне
    //Но она хранится в кеше, который очищается при первой же необходимости
    private Uri genUri() {
        File file = new File(getCacheDir(), "PIC_TEMP");
        return  Uri.parse(file.getAbsolutePath());
    }
    protected void onDestroy() {
        DataHolder.getInstance().remove("PICTURE_SET");
        super.onDestroy();
        finish();
    }
}
