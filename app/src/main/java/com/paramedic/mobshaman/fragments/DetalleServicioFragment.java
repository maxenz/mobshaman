package com.paramedic.mobshaman.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.MapaServicioActivity;
import com.paramedic.mobshaman.models.Servicio;

/**
 * Created by soporte on 23/07/2014.
 */
public class DetalleServicioFragment extends Fragment {

    TextView tvLocalidad, tvDomicilio, tvEntreCalle1, tvEntreCalle2,
    tvReferencias, tvSintomas, tvGrado, tvAviso, tvPaciente, tvSexo,
    tvEdad, tvEntidad, tvNroAfiliado, tvNroInterno, tvCoPago, tvObservaciones,
    tvNroServicio, tvTelefono;

    Servicio serv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /** Redefino este metodo para poder actualizar desde el fragment y no desde la activity **/

        boolean handled = false;

        int id = item.getItemId();
        switch (id) {
            case R.id.action_ubicacion_servicio:
                /** Voy a la actividad donde me muestra la ubicación del servicio **/
                Intent intent = new Intent(getActivity(), MapaServicioActivity.class);
                intent.putExtra("Servicio", serv);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_detalle_servicio,container,false);

        displayValues(myView);

        ExpandableListView elv = (ExpandableListView) myView.findViewById(R.id.service_components_list);
        elv.setAdapter(new ExpandableListAdapter());

        return myView;
    }

    private void displayValues(View myView) {

        /** Obtengo via intent los datos del servicio y los muestro en la UI **/

        Intent intent = this.getActivity().getIntent();

        serv = (Servicio) intent.getSerializableExtra("Servicio");



//       tvSintomas.setText(serv.getSintomas());
//        tvGrado.setText(serv.getGrado());
//        tvGrado.setTextColor(serv.getGradoColor());



    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private String[] groups = { "Servicio", "Síntomas", "Domicilio", "Derivación" };

        //Grupo servicio
        Spannable nroServicio = getDescriptionFormatted("Nro. Servicio", serv.getNroServicio());
        Spannable telefono = getDescriptionFormatted("Teléfono", serv.getTelefono());
        Spannable grado = getDescriptionFormatted("Grado", serv.getGrado());
        Spannable gradoFormatted = getGradeColor(grado, serv.getGrado());
        Spannable paciente = getDescriptionFormatted("Paciente", serv.getPaciente());
        Spannable entidad = getDescriptionFormatted("Entidad", serv.getCliente());
        Spannable nroAfiliado = getDescriptionFormatted("Nro. Afiliado", serv.getNroAfiliado());
        Spannable sexo = getDescriptionFormatted("Sexo", serv.getSexo());
        Spannable edad = getDescriptionFormatted("Edad", serv.getEdad());
        Spannable aviso = getDescriptionFormatted("Aviso", serv.getAviso());
        Spannable referencias = getDescriptionFormatted("Referencias", serv.getReferencia());
        Spannable copago = getDescriptionFormatted("CoPago", serv.getCoPago().toString());
        Spannable observaciones = getDescriptionFormatted("Observaciones", serv.getObservaciones());

        // Grupo domicilio
        Spannable localidad = getDescriptionFormatted("Localidad", serv.getLocalidad());
        Spannable domicilio = getDescriptionFormatted("Domicilio", serv.getDomicilio());
        Spannable entreCalle1 = getDescriptionFormatted("Entre Calle 1", serv.getEntreCalle1());
        Spannable entreCalle2 = getDescriptionFormatted("Entre Calle 2", serv.getEntreCalle2());
        Spannable partido = getDescriptionFormatted("Partido", serv.getPartido());
        Spannable institucion = getDescriptionFormatted("Institución", serv.getInstitucion());

        //Grupo Sintomas


        // Grupo Derivacion
        Spannable derDomicilio = getDescriptionFormatted("Domicilio", serv.getDerDomicilio());
        Spannable derLocalidad = getDescriptionFormatted("Localidad", serv.getDerLocalidad());
        Spannable derEntreCalle1 = getDescriptionFormatted("Entre Calle 1", serv.getDerEntreCalle1());
        Spannable derEntreCalle2 = getDescriptionFormatted("Entre Calle 2", serv.getDerEntreCalle2());
        Spannable derInstitucion = getDescriptionFormatted("Institución", serv.getDerInstitucion());
        Spannable derPartido = getDescriptionFormatted("Partido", serv.getDerPartido());
        Spannable derReferencia = getDescriptionFormatted("Referencia", serv.getDerReferencia());


        private Spannable[][] children = {
                { nroServicio, telefono, paciente,gradoFormatted, entidad, nroAfiliado, sexo,
                        edad, aviso, referencias, copago, observaciones },
                {
                //SintomasItems, no se como me llegan
                },
                { localidad, partido, domicilio, entreCalle1, entreCalle2, institucion },
                {derDomicilio, derLocalidad, derEntreCalle1, derEntreCalle2, derInstitucion,
                derPartido, derReferencia}
        };

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return children[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return groups[i];
        }

        @Override
        public Spannable getChild(int i, int i1) {
            return children[i][i1];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(DetalleServicioFragment.this.getActivity());
            textView.setText(getGroup(i).toString());
            textView.setPadding(80, 20, 20, 20);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

            return textView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

            TextView textView = new TextView(DetalleServicioFragment.this.getActivity());
            textView.setText(getChild(i, i1));
            textView.setPadding(20,20,20,20);
            return textView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        private Spannable getDescriptionFormatted(String label, String description) {
            String fullWord = label + ": " + description;
            Spannable wordToSpan = new SpannableString(fullWord);
            wordToSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, label.length() + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return wordToSpan;
        }

        private Spannable getGradeColor(Spannable word, String description) {
            word.setSpan(new ForegroundColorSpan(serv.getGradoColor()), 6, 6 + description.length() + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return word;
        }

    }
}
