package com.example.jeffe.login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DialogReservMain extends AppCompatDialogFragment {

    private EditText editTextUsername;
    private EditText editTextPassword;

    public DialogReservMain frag_activity;

    //private TextView horas_a_reservar;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        //horas_a_reservar = view.findViewById(R.id.horas_a_reservar);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("RESERVAS PARKING YA!")
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .setPositiveButton("reservar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reservar();
                        //final String hours_reserv = horas_a_reservar.getText().toString();
                    }

    private void reservar() {

        final String name_park = editTextUsername.getText().toString();
        final String license_user = editTextPassword.getText().toString();

        Response.Listener<String> respoListerner = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success) {

                        //Arreglar mensaje!!! no sal
                        Toast.makeText(getContext(), "Registro exitoso...!!!", Toast.LENGTH_SHORT).show();
                        //sendTestEmail();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                        builder.setMessage("Error al reservar")
                                .setNegativeButton("Reintentar", null)
                                .create().show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        ReservationsRequest reservationsRequest = new ReservationsRequest(name_park, license_user, respoListerner);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(reservationsRequest);
    }
                });

        return builder.create();
    }


    /**private void sendTestEmail() {

        BackgroundMail.newBuilder(getActivity())
                .withUsername("noreplyparkingya@gmail.com")
                .withPassword("park100891")
                .withMailto("jeffersonj-o@hotmail.com")
                .withSubject("CONFIRMACION DE RESERVA")
                .withBody("Reserva para el vehiculo con numero de placa "
                        +editTextUsername.getText().toString()
                        + " su codigo de verficacion es #215")

                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();
    }*/

}
