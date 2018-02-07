package service;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import model.FBUser;

/**
 * Created by alkempl on 07.02.18.
 */

public class FirebasePelicanusService {

    /**
     * Private constructor to prevent instantiation
     */
    public FirebasePelicanusService() {
    }

    public FBUser getFBUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
            return new FBUser(uid, name, email, emailVerified, photoUrl);
        } else {
            Log.e("ERR", "userInit called without real auth user");
            return new FBUser("no-id", "No user", "no-email@dot.com", false, null);
        }
    }

    public boolean checkFBUserExistanceInDB() {

        return true;
    }

}
