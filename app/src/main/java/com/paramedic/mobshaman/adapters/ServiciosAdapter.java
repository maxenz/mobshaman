package com.paramedic.mobshaman.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.models.Servicio;

import java.util.ArrayList;

/**
 * Created by maxo on 22/07/14.
 */
public class ServiciosAdapter extends ArrayAdapter<Servicio> {


    private final Context context;
    private final ArrayList<Servicio> serviciosArrayList;

    public ServiciosAdapter(Context context, ArrayList<Servicio> serviciosArrayList) {

        super(context, R.layout.rowlayout, serviciosArrayList);

        this.context = context;
        this.serviciosArrayList = serviciosArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        // 3. Get the two text view from the rowView
        TextView tvDatosGrales = (TextView) rowView.findViewById(R.id.datosGrales);
        TextView tvDomicilio = (TextView) rowView.findViewById(R.id.domicilio);
        TextView tvHorario = (TextView) rowView.findViewById(R.id.horario);

        ImageView ivGrado = (ImageView) rowView.findViewById(R.id.imgGrado);

        // 4. Set the text for textView
        tvDatosGrales.setText(formatDatosGrales(serviciosArrayList.get(position)));
        tvDomicilio.setText(serviciosArrayList.get(position).getDomicilio());
        tvHorario.setText(serviciosArrayList.get(position).getHorario());

        ivGrado.setImageDrawable(context.getResources().getDrawable(R.drawable.shapes));


        // 5. retrn rowView
        return rowView;
    }

    private String formatDatosGrales(Servicio serv) {

        String cliente = serv.getCliente();
        String nroIncidente = serv.getNroIncidente();
        String sexoEdad = serv.getSexoEdad();

        return cliente + " / " + nroIncidente + " / " + sexoEdad;

    }

}
