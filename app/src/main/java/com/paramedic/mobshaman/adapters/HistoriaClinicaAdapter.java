package com.paramedic.mobshaman.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.fragments.HistoriaClinicaFragment;
import com.paramedic.mobshaman.models.HistoriaClinica;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by soporte on 11/08/2014.
 */
public class HistoriaClinicaAdapter extends ArrayAdapter<HistoriaClinica> {

    private final Context context;
    private final ArrayList<HistoriaClinica> hcArrayList;
    private final HistoriaClinicaFragment fragment;
    private int shape_grado;

    public HistoriaClinicaAdapter(Context context, ArrayList<HistoriaClinica> hcArrayList,
                                  HistoriaClinicaFragment fragment) {

        super(context, R.layout.fragment_historia_clinica, hcArrayList);

        this.context = context;
        this.hcArrayList = hcArrayList;
        this.fragment = fragment;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.fragment_historia_clinica, parent, false);

        TextView tvDatosGenerales = (TextView) rowView.findViewById(R.id.hc_datos_generales);
        TextView tvDiagnostico = (TextView) rowView.findViewById(R.id.hc_diagnostico);
        TextView tvSintomas = (TextView) rowView.findViewById(R.id.hc_sintomas);

        HistoriaClinica hc = hcArrayList.get(position);

        shape_grado = R.drawable.shape_grado;
        GradientDrawable dw = (GradientDrawable) context.getResources().getDrawable(R.drawable.shape_grado);
        dw.setColor(hc.getGradoColor());

        String datosGrales = MessageFormat.format("{0} - {1} - Mov. {2}",hc.getFecIncidente(),
                hc.getPaciente(),hc.getMovil());
        tvDatosGenerales.setCompoundDrawablesWithIntrinsicBounds(shape_grado, 0, 0, 0);
        tvDatosGenerales.setText(datosGrales);
        tvDiagnostico.setText(hc.getDiagnostico());
        tvSintomas.setText(hc.getSintomas());

        return rowView;
    }

}
