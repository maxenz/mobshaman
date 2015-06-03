package com.paramedic.mobshaman.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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
    private int shape_grado;

    public ServiciosAdapter(Context context, ArrayList<Servicio> serviciosArrayList, ServiciosFragment fragment) {

        super(context, R.layout.fragment_servicios, serviciosArrayList);

        this.context = context;
        this.serviciosArrayList = serviciosArrayList;
        this.fragment = fragment;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View rowView = inflater.inflate(R.layout.fragment_servicios, parent, false);

        TextView tvDatosGrales = (TextView) rowView.findViewById(R.id.datosGrales);
        TextView tvDomicilio = (TextView) rowView.findViewById(R.id.domicilio);
        TextView tvHorario = (TextView) rowView.findViewById(R.id.horario);
        ImageView ivGrado = (ImageView) rowView.findViewById(R.id.imgGrado);

        Servicio serv = serviciosArrayList.get(position);

        tvDatosGrales.setText(serv.getDatosGrales());
        tvDomicilio.setText(serv.getDomicilio());
        tvHorario.setText(serv.getHorario());

        shape_grado = R.drawable.shape_grado;

        GradientDrawable dw = (GradientDrawable) context.getResources().getDrawable(R.drawable.shape_grado);

        dw.setColor(serv.getGradoColor());

        ivGrado.setImageDrawable(context.getResources().getDrawable(shape_grado));

        rowView.setId(serv.getIdServicio());

        if (serv.getCurrentViaje() == 1) {
            rowView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.list_item_current_viaje));
        }

        rowView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

             fragment.ID_SERVICIO_SELECCIONADO = v.getId();
             String urlDetalle = fragment.configuration.getUrl() + "/api/services/"
                     + fragment.ID_SERVICIO_SELECCIONADO + "?idMovil=" +
                     fragment.configuration.getMobile() + "&licencia=" +
                     fragment.configuration.getLicense();
             fragment.getDetalleServicio(urlDetalle,"Cargando servicio...",null);

            }
        });

        return rowView;
    }

}
