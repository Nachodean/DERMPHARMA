package com.example.tfg.Controlador;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.tfg.Activities.Farmaceutico.Detalle_Consulta;
import com.example.tfg.Modelos.Producto_Modelo;
import com.example.tfg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Controlador_Detalle_Consulta implements View.OnClickListener {

    private Detalle_Consulta vista;
    private String idDocumento;
    private FirebaseFirestore db;

    public Controlador_Detalle_Consulta(Detalle_Consulta vista, String idDocumento) {
        this.vista = vista;
        this.idDocumento = idDocumento;
        this.db = FirebaseFirestore.getInstance();

        cargarProductos();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_volver) {
            vista.finish();
        } else if (v.getId() == R.id.btn_enviar_respuesta) {
        enviarDiagnostico();
        }
    }

    private void cargarProductos() {
        // 1. CAMBIO AQUÍ: La colección empieza con mayúscula "Productos"
        db.collection("Productos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> limpiadores = new ArrayList<>();
                    List<String> hidratantes = new ArrayList<>();
                    List<String> tratamientos = new ArrayList<>();
                    List<String> protectores = new ArrayList<>();

                    limpiadores.add("Seleccionar Limpiador");
                    hidratantes.add("Seleccionar Hidratante");
                    tratamientos.add("Seleccionar Tratamiento");
                    protectores.add("Seleccionar Protección");

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Usamos tu modelo directamente para sacar los datos
                        Producto_Modelo producto = document.toObject(Producto_Modelo.class);

                        String nombre = producto.getNombre();
                        String categoria = producto.getCategoria();

                        if (categoria != null && nombre != null) {
                            
                            switch (categoria.toLowerCase()) {
                                case "limpiador":
                                case "limpieza": // Añado ambas por si en tu BD usas "limpieza"
                                    limpiadores.add(nombre);
                                    break;
                                case "hidratacion": // 2. CAMBIO AQUÍ: Coincide con tu BD
                                    hidratantes.add(nombre);
                                    break;
                                case "tratamiento":
                                    tratamientos.add(nombre);
                                    break;
                                case "proteccion":
                                    protectores.add(nombre);
                                    break;
                            }
                        }
                    }

                    vista.setListaLimpieza(limpiadores);
                    vista.setListaHidratacion(hidratantes);
                    vista.setListaTratamiento(tratamientos);
                    vista.setListaProteccion(protectores);
                } else {
                    vista.mostrarMensaje("Error al cargar productos de la base de datos");
                }
            }
        });
    }

    private void enviarDiagnostico() {
        String valoracion = vista.getValoracion();
        if (valoracion.isEmpty()) {
            vista.mostrarMensaje("La valoración es obligatoria");
            return;
        }

        vista.bloquearBoton(true);

        db.collection("consultas_cutaneas").document(idDocumento)
                .update(
                        "valoracion", valoracion,
                        "limpieza", vista.getLimpieza(),
                        "hidratacion", vista.getHidratacion(),
                        "tratamiento", vista.getTratamiento(),
                        "proteccion", vista.getProteccion(),
                        "estado", "Respondida"
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        vista.mostrarMensaje("Respuesta enviada con éxito");
                        vista.finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        vista.mostrarMensaje("Error al enviar");
                        vista.bloquearBoton(false);
                    }
                });
    }
}