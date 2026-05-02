package com.example.tfg.Controlador;

import android.view.View;

import androidx.annotation.Nullable;

import com.example.tfg.Activities.Cliente.Detalle_Receta_Activity;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Controlador_Detalle_Consulta_Cliente implements View.OnClickListener {

    private Detalle_Receta_Activity vista;
    private String idDocumento;
    private FirebaseFirestore db;

    public Controlador_Detalle_Consulta_Cliente(Detalle_Receta_Activity vista, String idDocumento) {
        this.vista = vista;
        this.idDocumento = idDocumento;
        this.db = FirebaseFirestore.getInstance();


        iniciarEscuchaRealTime();
    }

    private void iniciarEscuchaRealTime() {
        db.collection("consultas_cutaneas").document(idDocumento)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            vista.mostrarMensaje("Error al sincronizar datos.");
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {

                            Consulta_Modelo consulta = new Consulta_Modelo();

                            consulta.setSintomas(snapshot.getString("sintomas"));
                            consulta.setValoracion_clinica(snapshot.getString("valoracion"));
                            consulta.setRutina_limpieza(snapshot.getString("limpieza"));
                            consulta.setRutina_hidratacion(snapshot.getString("hidratacion"));
                            consulta.setRutina_tratamiento(snapshot.getString("tratamiento"));
                            consulta.setRutina_proteccion(snapshot.getString("proteccion"));


                            vista.mostrarDatos(consulta);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == com.example.tfg.R.id.btn_volver_historial) {
            vista.finish();
        } else if (id == com.example.tfg.R.id.btn_entendido) {
            vista.finish();
        }
    }
}