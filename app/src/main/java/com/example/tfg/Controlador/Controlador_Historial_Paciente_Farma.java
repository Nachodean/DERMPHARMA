package com.example.tfg.Controlador;

import android.view.View;

import androidx.annotation.Nullable;

import com.example.tfg.Activities.Farmaceutico.Historial_Paciente_Activity;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Controlador_Historial_Paciente_Farma implements View.OnClickListener {

    private Historial_Paciente_Activity vista;
    private FirebaseFirestore db;

    public Controlador_Historial_Paciente_Farma(Historial_Paciente_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
    }

    public void cargarConsultasDelPaciente(String uid) {
        if (uid == null) {
            return;
        }


        db.collection("consultas_cutaneas")
                .whereEqualTo("uid_Cliente", uid)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                        if (error != null) {
                            return;
                        }

                        if (value != null) {
                            List<Consulta_Modelo> lista = new ArrayList<>();

                            for (DocumentSnapshot doc : value.getDocuments()) {
                                try {
                                    Consulta_Modelo con = doc.toObject(Consulta_Modelo.class);
                                    if (con != null) {
                                        lista.add(con);
                                    }
                                } catch (Exception e) {
                                    System.out.println("Documento ignorado: " + doc.getId());
                                }
                            }


                            vista.pintarConsultas(lista);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_volver_historial) {
            vista.finish();
        }
    }
}