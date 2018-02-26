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


public class LoginFragment extends Fragment {

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        Button register = v.findViewById(R.id.register);
        Button login = v.findViewById(R.id.login);

        final MyDBHandler dbHandler;
        final EditText usernameInput;
        final EditText passwordInput;

        mContext = this.getActivity();
        dbHandler = new MyDBHandler(mContext);

        usernameInput = v.findViewById(R.id.usernameInputLog);
        passwordInput = v.findViewById(R.id.passwordInputLog);

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment fragment = new RegisterFragment();
                FragmentManager supportFragmentManager = getFragmentManager();
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (dbHandler.checkAccount(username, password)>0){
                    Toast welcome = Toast.makeText(getContext(), "Bienvenido, " + username, Toast.LENGTH_SHORT);
                    welcome.show();
                } else {
                    Toast error = Toast.makeText(getContext(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT);
                    error.show();
                }
            }
        });

        return v;
    }
}
