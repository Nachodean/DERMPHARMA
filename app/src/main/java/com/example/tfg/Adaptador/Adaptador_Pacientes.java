package com.example.tfg.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.tfg.Activities.Farmaceutico.Pacientes_Farmaceutico_Activity;
import com.example.tfg.Modelos.Usuario_Modelo;
import com.example.tfg.R;
import java.util.ArrayList;
import java.util.List;

public class Adaptador_Pacientes extends BaseAdapter {
    private Pacientes_Farmaceutico_Activity activity;
    private List<Usuario_Modelo> listaOriginal;
    private List<Usuario_Modelo> listaMostrada;

    public Adaptador_Pacientes(Pacientes_Farmaceutico_Activity activity) {
        this.activity = activity;
        this.listaOriginal = new ArrayList<>();
        this.listaMostrada = new ArrayList<>();
    }

    @Override
    public int getCount() { return listaMostrada.size(); }
    @Override
    public Object getItem(int i) { return listaMostrada.get(i); }
    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_paciente, null);
            holder = new ViewHolder();
            holder.tvEmail = view.findViewById(R.id.tv_correo_item_paciente);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Usuario_Modelo u = listaMostrada.get(i);
        if (u.getCorreo() != null) {
            holder.tvEmail.setText(u.getCorreo());
        } else {
            holder.tvEmail.setText("Sin correo registrado");
        }
        return view;
    }

    public void cargarDatos(List<Usuario_Modelo> datosNuevos) {
        this.listaOriginal.clear();
        this.listaOriginal.addAll(datosNuevos);
        this.listaMostrada.clear();
        this.listaMostrada.addAll(datosNuevos);
        notifyDataSetChanged();
    }

    public void filtrar(String busqueda) {
        listaMostrada.clear();
        if (busqueda.isEmpty()) {
            listaMostrada.addAll(listaOriginal);
        } else {
            String busquedaMinus = busqueda.toLowerCase();
            for (Usuario_Modelo u : listaOriginal) {
                if (u.getCorreo() != null) {
                    if (u.getCorreo().toLowerCase().contains(busquedaMinus)) {
                        listaMostrada.add(u);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder { TextView tvEmail; }
}