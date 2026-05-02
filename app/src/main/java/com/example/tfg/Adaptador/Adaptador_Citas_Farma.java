package com.example.tfg.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.Activities.Farmaceutico.Citas_Farmaceutico_Activity;
import com.example.tfg.Controlador.Controlador_Citas_Farmaceutico;
import com.example.tfg.Modelos.Cita_Modelo;
import com.example.tfg.R;

import java.util.List;

public class Adaptador_Citas_Farma extends BaseAdapter {

    private Citas_Farmaceutico_Activity activity;
    private int layout;
    private List<Cita_Modelo> lista;
    private Controlador_Citas_Farmaceutico controlador;

    public Adaptador_Citas_Farma(Citas_Farmaceutico_Activity activity, int layout, List<Cita_Modelo> lista, Controlador_Citas_Farmaceutico controlador) {
        this.activity = activity;
        this.layout = layout;
        this.lista = lista;
        this.controlador = controlador;
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
            holder.tvHora = view.findViewById(R.id.tv_item_hora);
            holder.tvFranja = view.findViewById(R.id.tv_item_franja); // Aquí irá la fecha
            holder.tvNombre = view.findViewById(R.id.tv_item_nombre_paciente); // Aquí el correo
            holder.tvMotivo = view.findViewById(R.id.tv_item_motivo);
            holder.btnVerFicha = view.findViewById(R.id.btn_item_ver_ficha);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Cita_Modelo cita = lista.get(i);


        if (cita.getPaciente_nombre() != null) {
            holder.tvNombre.setText(cita.getPaciente_nombre());
        } else {
            holder.tvNombre.setText("Cargando correo...");
        }


        if (cita.getMotivo() != null) {
            holder.tvMotivo.setText(cita.getMotivo());
        } else {
            holder.tvMotivo.setText("Consulta de seguimiento");
        }


        String textoHora;
        if (cita.getHora() != null) {
            textoHora = cita.getHora().replaceAll("[^0-9:]", "");
        } else {
            textoHora = "00:00";
        }
        holder.tvHora.setText(textoHora);


        if (cita.getFecha() != null) {
            holder.tvFranja.setText(cita.getFecha());
        } else {
            holder.tvFranja.setText("Sin fecha");
        }


        holder.btnVerFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlador.alPulsarBotonFicha(cita);
            }
        });

        return view;
    }

    public void actualizar(List<Cita_Modelo> nuevas) {
        this.lista.clear();
        this.lista.addAll(nuevas);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView tvHora, tvFranja, tvNombre, tvMotivo;
        Button btnVerFicha;
    }
}