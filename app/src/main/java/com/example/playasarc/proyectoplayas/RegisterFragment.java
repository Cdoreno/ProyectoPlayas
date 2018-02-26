package com.example.playasarc.proyectoplayas;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterFragment extends Fragment {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        Button cancel = v.findViewById(R.id.cancel);
        Button send = v.findViewById(R.id.send);

        final MyDBHandler dbHandler;
        final EditText usernameInput;
        final EditText passwordInput;
        final EditText passwordInputConfirm;

        mContext = this.getActivity();
        dbHandler = new MyDBHandler(mContext);

        usernameInput = v.findViewById(R.id.usernameInputReg);
        passwordInput = v.findViewById(R.id.passwordInputReg);
        passwordInputConfirm = v.findViewById(R.id.passwordInputConfirmReg);

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new LoginFragment();
                FragmentManager supportFragmentManager = getFragmentManager();
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();
                String passwordConfirm = passwordInputConfirm.getText().toString();

                if (password.equals(passwordConfirm)) {

                    Accounts account = new Accounts(mContext, username, password);

                    if (dbHandler.checkUsername(username) > 0) {
                        Toast error = Toast.makeText(getContext(), "El usuario ya existe", Toast.LENGTH_SHORT);
                        error.show();
                    } else {

                        dbHandler.addAccount(account);

                        Toast success = Toast.makeText(getContext(), "Usuario creado correctamente", Toast.LENGTH_SHORT);
                        success.show();
                    }

                } else {
                    Toast error = Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT);
                    error.show();
                }

            }
        });
        return v;
    }

}
