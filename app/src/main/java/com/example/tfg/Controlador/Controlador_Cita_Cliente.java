package com.example.tfg.Controlador;

import android.view.View;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

import com.example.tfg.Activities.Cliente.Citas_Cliente_Activity;
import com.example.tfg.Modelos.Cita_Modelo;
import com.example.tfg.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Controlador_Cita_Cliente implements View.OnClickListener {

    private Citas_Cliente_Activity vista;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public Controlador_Cita_Cliente(Citas_Cliente_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }


    public void configurarCalendario(CalendarView cv) {
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
                vista.setFechaSeleccionada(fecha);

                comprobarDisponibilidad(fecha);
            }
        });
    }

    private void comprobarDisponibilidad(String fecha) {
        db.collection("citas")
                .whereEqualTo("fecha", fecha)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<String> ocupadas = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            String h = doc.getString("hora");
                            if (h != null) ocupadas.add(h);
                        }
                        // Mandamos la lista a la vista para bloquear botones
                        vista.actualizarEstadoHorarios(ocupadas);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_confirmar_cita) {
            String fecha = vista.getFechaSeleccionada();
            String hora = vista.getHoraSeleccionada();

            if (fecha.isEmpty() || hora.isEmpty()) {
                vista.mostrarMensaje("Por favor, elige fecha y hora.");
                return;
            }

            prepararGuardado(fecha, hora);

        } else if (id == R.id.btn_volver_citas) {
            vista.cerrarPantalla();
        }
    }

    private void prepararGuardado(final String fecha, final String hora) {
        if (mAuth.getCurrentUser() == null) return;
        final String uid = mAuth.getCurrentUser().getUid();


        db.collection("Usuarios").document(uid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String nombreUsuario = "Usuario";
                        if (documentSnapshot.exists()) {
                        
                            nombreUsuario = documentSnapshot.getString("correo");
                        }
                        enviarCitaAFirebase(nombreUsuario, uid, fecha, hora);
                    }
                });
    }

    private void enviarCitaAFirebase(String nombre, String uid, String fecha, String hora) {

        Cita_Modelo nuevaCita = new Cita_Modelo(nombre, uid, fecha, hora, "Consulta presencial");

        db.collection("citas").add(nuevaCita)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        vista.mostrarMensaje("¡Cita reservada!");
                        vista.cerrarPantalla();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        vista.mostrarMensaje("Error al reservar: " + e.getMessage());
                    }
                });
    }
}