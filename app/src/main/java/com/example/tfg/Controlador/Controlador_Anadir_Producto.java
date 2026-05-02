package com.example.tfg.Controlador;

import android.view.View;
import androidx.annotation.NonNull;
import com.example.tfg.Activities.Farmaceutico.Anadir_Producto_Activity;
import com.example.tfg.Modelos.Producto_Modelo;
import com.example.tfg.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class Controlador_Anadir_Producto implements View.OnClickListener {
    private static final String COLECCION = "Productos";

    public static final String CAT_LIMPIEZA = "Limpieza";
    public static final String CAT_HIDRATACION = "Hidratacion";
    public static final String CAT_TRATAMIENTO = "Tratamiento";
    public static final String CAT_PROTECCION = "Proteccion";

    private Anadir_Producto_Activity vista;
    private FirebaseFirestore db;

    public Controlador_Anadir_Producto(Anadir_Producto_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_guardar_nuevo_prod) {
            String nombre = vista.getNombreIngresado();
            String categoria = vista.getCategoriaSeleccionada();
            registrarProducto(nombre, categoria);
        }
        else if (id == R.id.btn_volver_add) {
            vista.cerrarPantalla();
        }
    }

    public List<String> obtenerCategorias() {
        List<String> lista = new ArrayList<>();
        lista.add(CAT_LIMPIEZA);
        lista.add(CAT_HIDRATACION);
        lista.add(CAT_TRATAMIENTO);
        lista.add(CAT_PROTECCION);
        return lista;
    }

    public void registrarProducto(String nombre, String categoria) {
        if (nombre.isEmpty()) {
            vista.mostrarMensaje("El nombre no puede estar vacío");
            return;
        }

        Producto_Modelo p = new Producto_Modelo();
        p.setNombre(nombre);
        p.setCategoria(categoria);

        db.collection(COLECCION).add(p)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        vista.notificarExito();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        vista.mostrarMensaje("Error al guardar: " + e.getMessage());
                    }
                });
    }
}