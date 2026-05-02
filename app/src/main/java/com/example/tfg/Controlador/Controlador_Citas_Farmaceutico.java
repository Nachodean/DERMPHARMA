package com.example.tfg.Controlador;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tfg.Activities.Farmaceutico.Citas_Farmaceutico_Activity;
import com.example.tfg.Modelos.Cita_Modelo;
import com.example.tfg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Controlador_Citas_Farmaceutico implements View.OnClickListener {

    private Citas_Farmaceutico_Activity vista;
    private FirebaseFirestore db;

    public Controlador_Citas_Farmaceutico(Citas_Farmaceutico_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
    }

    public void escucharCitas() {
        db.collection("citas")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }

                        if (value != null) {
                            final List<Cita_Modelo> listaTemp = new ArrayList<>();
                            final int total = value.getDocuments().size();

                            if (total == 0) {
                                vista.pintarCitas(listaTemp);
                                return;
                            }

                            for (DocumentSnapshot doc : value.getDocuments()) {
                                final Cita_Modelo cita = doc.toObject(Cita_Modelo.class);
                                if (cita != null) {
                                    if (cita.getPaciente_nombre() == null) {
                                        buscarCorreoYNotificar(cita, listaTemp, total);
                                    } else {
                                        listaTemp.add(cita);
                                        if (listaTemp.size() == total) {
                                            vista.pintarCitas(listaTemp);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }

    private void buscarCorreoYNotificar(final Cita_Modelo cita, final List<Cita_Modelo> lista, final int total) {
        if (cita.getPaciente_id() != null) {
            db.collection("Usuarios").document(cita.getPaciente_id()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot resultado = task.getResult();
                                if (resultado != null) {
                                    String email = resultado.getString("correo");
                                    if (email != null) {
                                        cita.setPaciente_nombre(email);
                                    } else {
                                        cita.setPaciente_nombre("Correo no registrado");
                                    }
                                }
                            }
                            lista.add(cita);
                            if (lista.size() == total) {
                                vista.pintarCitas(lista);
                            }
                        }
                    });
        }
    }

    public void alPulsarBotonFicha(Cita_Modelo citaSeleccionada) {
        String idPaciente = citaSeleccionada.getPaciente_id();
        String correo;

        if (citaSeleccionada.getPaciente_nombre() != null) {
            correo = citaSeleccionada.getPaciente_nombre();
        } else {
            correo = "Sin correo";
        }

        vista.navegarAHistorialPaciente(idPaciente, correo);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_volver_inicio_farma) {
            vista.finalizarPantalla();
        }
    }
}