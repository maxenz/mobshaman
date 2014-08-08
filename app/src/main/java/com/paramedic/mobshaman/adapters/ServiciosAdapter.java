package com.paramedic.mobshaman.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.fragments.ServiciosFragment;
import com.paramedic.mobshaman.models.Servicio;
import java.util.ArrayList;

/**
 * Created by maxo on 22/07/14.
 */
public class ServiciosAdapter extends ArrayAdapter<Servicio> {


    private final Context context;
    private final ArrayList<Servicio> serviciosArrayList;
    private final ServiciosFragment fragment;

    public ServiciosAdapter(Context context, ArrayList<Servicio> serviciosArrayList, ServiciosFragment fragment) {

        super(context, R.layout.rowlayout, serviciosArrayList);

        this.context = context;
        this.serviciosArrayList = serviciosArrayList;
        this.fragment = fragment;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView tvDatosGrales = (TextView) rowView.findViewById(R.id.datosGrales);
        TextView tvDomicilio = (TextView) rowView.findViewById(R.id.domicilio);
        TextView tvHorario = (TextView) rowView.findViewById(R.id.horario);
        ImageView ivGrado = (ImageView) rowView.findViewById(R.id.imgGrado);

        Servicio serv = serviciosArrayList.get(position);

        tvDatosGrales.setText(serv.getDatosGrales());
        tvDomicilio.setText(serv.getDomicilio());
        tvHorario.setText(serv.getHorario());

        int shapeGrado = serv.getGradoDrawable();

        ivGrado.setImageDrawable(context.getResources().getDrawable(shapeGrado));

        rowView.setId(serv.getIdServicio());

        rowView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

             fragment.ID_SERVICIO_SELECCIONADO = v.getId();
             String urlDetalle = fragment.URL_REST + "/api/servicios/"
                     + fragment.ID_SERVICIO_SELECCIONADO + "?idMovil=" +
                     fragment.NRO_MOVIL;
             fragment.getDetalleServicio(urlDetalle,"Cargando servicio...",null);

            }
        });

        return rowView;
    }

}
