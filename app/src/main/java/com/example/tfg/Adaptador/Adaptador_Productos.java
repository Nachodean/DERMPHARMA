package com.example.tfg.Adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.tfg.Activities.Farmaceutico.Catalogo_Productos_Activity;
import com.example.tfg.Modelos.Producto_Modelo;
import com.example.tfg.R;
import java.util.ArrayList;
import java.util.List;

public class Adaptador_Productos extends BaseAdapter {
    private Catalogo_Productos_Activity activity;
    private List<Producto_Modelo> listaOriginal;
    private List<Producto_Modelo> listaFiltrada;

    public Adaptador_Productos(Catalogo_Productos_Activity activity) {
        this.activity = activity;
        this.listaOriginal = new ArrayList<>();
        this.listaFiltrada = new ArrayList<>();
    }

    @Override public int getCount() { return listaFiltrada.size(); }
    @Override public Object getItem(int i) { return listaFiltrada.get(i); }
    @Override public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_producto, null);
            holder = new ViewHolder();
            holder.tvNombre = view.findViewById(R.id.tv_item_nombre_producto);
            holder.tvCategoria = view.findViewById(R.id.tv_item_categoria_producto);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Producto_Modelo p = listaFiltrada.get(i);

        if (p.getNombre() != null) { holder.tvNombre.setText(p.getNombre()); }
        else { holder.tvNombre.setText("Sin nombre"); }

        if (p.getCategoria() != null) { holder.tvCategoria.setText(p.getCategoria()); }
        else { holder.tvCategoria.setText("Sin categoría"); }

        return view;
    }

    public void cargarDatos(List<Producto_Modelo> nuevos) {
        this.listaOriginal.clear();
        this.listaOriginal.addAll(nuevos);
        this.listaFiltrada.clear();
        this.listaFiltrada.addAll(nuevos);
        notifyDataSetChanged();
    }

    public void filtrarBusqueda(String texto) {
        listaFiltrada.clear();
        if (texto.isEmpty()) {
            listaFiltrada.addAll(listaOriginal);
        } else {
            String txtMinus = texto.toLowerCase();
            for (Producto_Modelo p : listaOriginal) {
                if (p.getNombre() != null && p.getNombre().toLowerCase().contains(txtMinus)) {
                    listaFiltrada.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder { TextView tvNombre, tvCategoria; }
}