package com.paramedic.mobshaman.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.ConfigGeneralActivity;

/**
 * Created by maxo on 29/07/14.
 */

public class AdminPasswordDialogFragment extends DialogFragment {

    EditText ipt_user, ipt_password;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        /** Obtengo variables para usar de la UI **/

        View myView = inflater.inflate(R.layout.dialog_admin_password,null);

        ipt_user = (EditText) myView.findViewById(R.id.username);
        ipt_password = (EditText) myView.findViewById(R.id.password);
        ipt_password.setTypeface(Typeface.DEFAULT );

        /** Armo el dialog para ingresar usuario / password **/
        builder.setView(myView)
                // Add action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        /** Si ingreso los datos correctos del administrador, puedo configurar
                         * la aplicación
                         */

                        String user_ingresado = ipt_user.getText().toString();
                        String pass_ingresado = ipt_password.getText().toString();

                        if (user_ingresado.equals("administrador") && pass_ingresado.equals("yeike")) {
                            startActivity(new Intent(getActivity(), ConfigGeneralActivity.class));
                        } else {
                            Toast.makeText(getActivity(),"No está autorizado para ingresar",Toast.LENGTH_LONG).show();
                            AdminPasswordDialogFragment.this.getDialog().cancel();
                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AdminPasswordDialogFragment.this.getDialog().cancel();
                    }
                });

        builder.setInverseBackgroundForced(true);
        return builder.create();
    }
}