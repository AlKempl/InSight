package com.pelicanus.insight;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Dialog1 extends DialogFragment implements OnClickListener {

    final String LOG_TAG = "myLogs";
    private EditText inputOldPassword, inputNew1Password, inputNew2Password;
    private TextInputLayout inputLayoutOldPassword, inputLayoutNew1Password, inputLayoutNew2Password;
    private Button btnChangePass, btnCancelPass;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.change_pass_layout, null);
        inputLayoutOldPassword = v.findViewById(R.id.input_layout_password);
        inputLayoutNew1Password = v.findViewById(R.id.input_layout_new_password1);
        inputLayoutNew2Password = v.findViewById(R.id.input_layout_new_password2);

        inputOldPassword = v.findViewById(R.id.input_password);
        inputNew1Password = v.findViewById(R.id.input_new_password1);
        inputNew2Password = v.findViewById(R.id.input_new_password2);

        inputOldPassword.addTextChangedListener(new MyTextWatcher(inputOldPassword));
        inputNew1Password.addTextChangedListener(new MyTextWatcher(inputNew1Password));
        inputNew2Password.addTextChangedListener(new MyTextWatcher(inputNew2Password));

        btnChangePass = v.findViewById(R.id.confirm_pass_change);
        btnCancelPass = v.findViewById(R.id.cancel_pass_change);

        inputOldPassword.setText("");
        inputNew1Password.setText("");
        inputNew2Password.setText("");

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass(view);
                dismiss();
            }
        });

        btnCancelPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }

    public void onClick(View v) {

    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
//        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
//        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    private boolean validatePassword() {
        if (inputOldPassword.getText().toString().trim().isEmpty()) {
            //inputLayoutOldPassword.setError(getString(R.string.err_msg_password));
            inputLayoutOldPassword.setError("Empty password");
            return false;
        } else {
            inputLayoutOldPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTwoPasswords() {
        if (!inputNew1Password.getText().toString().trim().equals(inputNew2Password.getText().toString().trim())) {
            //inputLayoutNew2Password.setError(getString(R.string.err_msg_password));
            inputLayoutNew2Password.setError("Passwords don't match");
            return false;
        }

        if (inputNew1Password.getText().toString().trim().equals("")) {
                inputLayoutNew2Password.setError("Empty password");
                return false;
        }

        if (inputNew1Password.getText().toString().trim().length() <= 6) {
                inputLayoutNew2Password.setError("Password must be longer than 6 symbols");
                return false;
        }

        inputLayoutNew1Password.setErrorEnabled(false);
        inputLayoutNew2Password.setErrorEnabled(false);
        return true;
    }

    public void changePass(View view) {
        if (validatePassword() && validateTwoPasswords()) {
            inputLayoutNew2Password.setErrorEnabled(false);
            String pass = inputNew1Password.getText().toString().trim();
            FirebaseAuth.getInstance().getCurrentUser().updatePassword(pass);
            Toast.makeText(view.getContext(), "Password successfully updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(view.getContext(), "Password wasn't updated", Toast.LENGTH_LONG).show();
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (view.getId()) {
                case R.id.input_password:
                    validatePassword();
                    break;

                case R.id.input_new_password1:
                case R.id.input_layout_new_password2:
                    validateTwoPasswords();
                    break;
            }
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_password:
                    validatePassword();
                    break;

                case R.id.input_new_password1:
                case R.id.input_layout_new_password2:
                    validateTwoPasswords();
                    break;
            }
        }
    }


}