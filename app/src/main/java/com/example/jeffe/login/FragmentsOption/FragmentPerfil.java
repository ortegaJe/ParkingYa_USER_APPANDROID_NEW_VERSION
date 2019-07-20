package com.example.jeffe.login.FragmentsOption;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeffe.login.R;

public class FragmentPerfil extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_perfil_fragment, container, false);

        TextView textView_NombreRegis = (TextView) v.findViewById(R.id.textView_NombreRegis);
        TextView textView_UsuarioRegis = (TextView) v.findViewById(R.id.txtV_email_user);
        TextView textView_PasswordRegis = (TextView) v.findViewById(R.id.textView_PasswordRegis);
        TextView textView_EdadRegis = (TextView) v.findViewById(R.id.textView_EdadRegis);
        TextView textView_addressRegis = (TextView) v.findViewById(R.id.textView_addressRegis);
        TextView txtV_tel_reg_user = (TextView) v.findViewById(R.id.txtV_tel_reg_user);

        Intent intent = getActivity().getIntent();
        String name = intent.getStringExtra("name");
        String usuario = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        //cambiar a string - agregar ciudad??
        //int age= intent.getIntExtra("age", -1);
        String age = intent.getStringExtra("age");
        String address = intent.getStringExtra("address");
        String phone = intent.getStringExtra("phone");


        textView_NombreRegis.setText(name);
        textView_UsuarioRegis.setText(usuario);
        textView_PasswordRegis.setText(password);
        //cambiar a string
        textView_EdadRegis.setText(age);
        textView_addressRegis.setText(address);
        txtV_tel_reg_user.setText(phone);

        return v;
    }
}
