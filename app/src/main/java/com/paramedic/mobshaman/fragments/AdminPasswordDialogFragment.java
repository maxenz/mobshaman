package com.paramedic.mobshaman.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View myView = inflater.inflate(R.layout.dialog_admin_password,null);

        ipt_user = (EditText) myView.findViewById(R.id.username);
        ipt_password = (EditText) myView.findViewById(R.id.password);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(myView)
                // Add action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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
        return builder.create();
    }
}