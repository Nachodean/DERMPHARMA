package com.example.tfg.Adaptador;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfg.Activities.Cliente.Historial_Cliente_Activity;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;

import java.util.List;

public class Adaptador_Historial extends BaseAdapter {

    private Historial_Cliente_Activity actividad;
    private int layoutTarjeta;
    private List<Consulta_Modelo> consultas;

    public Adaptador_Historial(Historial_Cliente_Activity actividad, int layoutTarjeta, List<Consulta_Modelo> consultas) {
        this.actividad = actividad;
        this.layoutTarjeta = layoutTarjeta;
        this.consultas = consultas;
    }

    @Override
    public int getCount() {
        return consultas.size();
    }

    @Override
    public Object getItem(int position) {
        return consultas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(actividad);
            v = layoutInflater.inflate(layoutTarjeta, null);

            holder = new ViewHolder();
            holder.tv_tarjeta_titulo = v.findViewById(R.id.tv_tarjeta_titulo);
            holder.tv_tarjeta_descripcion = v.findViewById(R.id.tv_tarjeta_descripcion);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        Consulta_Modelo consulta = consultas.get(position);

        if (consulta != null) {
            String estadoConsulta = consulta.getEstado();


            if (estadoConsulta != null && (estadoConsulta.equalsIgnoreCase("Respondida") || estadoConsulta.equalsIgnoreCase("Resuelta"))) {


                holder.tv_tarjeta_titulo.setText("RESPONDIDA");
                holder.tv_tarjeta_titulo.setTextColor(Color.parseColor("#388E3C"));

                String valoracion = consulta.getValoracion_clinica();
                if (valoracion != null && !valoracion.isEmpty()) {
                    holder.tv_tarjeta_descripcion.setText(valoracion);
                } else {
                    holder.tv_tarjeta_descripcion.setText("Diagnóstico y receta disponibles.");
                }

            } else {


                holder.tv_tarjeta_titulo.setText("PENDIENTE");
                holder.tv_tarjeta_titulo.setTextColor(Color.parseColor("#F57C00"));
                holder.tv_tarjeta_descripcion.setText("Esperando diagnóstico del farmacéutico.");

            }
        }

        return v;
    }

    public void actualizarDatos(List<Consulta_Modelo> nuevasConsultas) {
        this.consultas.clear();
        this.consultas.addAll(nuevasConsultas);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView tv_tarjeta_titulo, tv_tarjeta_descripcion;
    }
}