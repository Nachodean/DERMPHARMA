package com.example.tfg.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfg.Activities.Farmaceutico.Historial_Paciente_Activity;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adaptador_Historial_Paciente extends BaseAdapter {

    private Historial_Paciente_Activity activity;
    private int layout;
    private List<Consulta_Modelo> lista;

    public Adaptador_Historial_Paciente(Historial_Paciente_Activity activity, int layout, List<Consulta_Modelo> lista) {
        this.activity = activity;
        this.layout = layout;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;


        if (view == null) {
            view = LayoutInflater.from(activity).inflate(layout, null);

            holder = new ViewHolder();
            holder.tvFecha = view.findViewById(R.id.tv_item_fecha_consulta);
            holder.tvEstado = view.findViewById(R.id.tv_item_estado_consulta);
            holder.tvResumen = view.findViewById(R.id.tv_item_resumen_consulta);

            view.setTag(holder);
        } else {

            holder = (ViewHolder) view.getTag();
        }


        Consulta_Modelo consulta = lista.get(i);


        if (consulta.getTimestamp() > 0) {

            Date fechaReal = new Date(consulta.getTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String fechaTexto = sdf.format(fechaReal);

            holder.tvFecha.setText(fechaTexto);
        } else {
            holder.tvFecha.setText("Fecha no disponible");
        }


        if (consulta.getEstado() != null) {
            holder.tvEstado.setText(consulta.getEstado());
        } else {
            holder.tvEstado.setText("PENDIENTE");
        }


        if (consulta.getSintomas() != null) {
            holder.tvResumen.setText(consulta.getSintomas());
        } else {
            holder.tvResumen.setText("Consulta sobre el estado de la piel");
        }

        return view;
    }


    public void actualizar(List<Consulta_Modelo> nuevasConsultas) {
        this.lista.clear();
        this.lista.addAll(nuevasConsultas);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView tvFecha;
        TextView tvEstado;
        TextView tvResumen;
    }
}