package com.example.tfg.Controlador;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tfg.Activities.Cliente.FormularioPiel_Activity;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Controlador_Formulario implements View.OnClickListener {

    private FormularioPiel_Activity vista;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    public Controlador_Formulario(FormularioPiel_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_enviar_formulario) {
            validarYEnviar();
        }
        if (v.getId()==R.id.btn_volver_test){
            vista.finish();
        }
    }

    private void validarYEnviar() {

        for (int i = 0; i < 10; i++) {
            if (vista.getGruposPreguntas()[i].getCheckedRadioButtonId() == -1) {

                Toast.makeText(vista, "Falta responder la pregunta " + (i + 1), Toast.LENGTH_SHORT).show();
                return;
            }
        }


        recopilarYEnviarTest();
    }

    private void recopilarYEnviarTest() {
        StringBuilder informeCompleto = new StringBuilder();

        informeCompleto.append("--- RESULTADOS DEL TEST ---\n\n");
        informeCompleto.append("1. Tacto: ").append(vista.obtenerRespuesta(0)).append("\n");
        informeCompleto.append("2. Brillo: ").append(vista.obtenerRespuesta(1)).append("\n");
        informeCompleto.append("3. Tono: ").append(vista.obtenerRespuesta(2)).append("\n");
        informeCompleto.append("4. Granitos: ").append(vista.obtenerRespuesta(3)).append("\n");
        informeCompleto.append("5. Enrojecimiento: ").append(vista.obtenerRespuesta(4)).append("\n");
        informeCompleto.append("6. Irritación: ").append(vista.obtenerRespuesta(5)).append("\n");
        informeCompleto.append("7. Tirantez: ").append(vista.obtenerRespuesta(6)).append("\n");
        informeCompleto.append("8. Poros: ").append(vista.obtenerRespuesta(7)).append("\n");
        informeCompleto.append("9. Couperosis: ").append(vista.obtenerRespuesta(8)).append("\n");
        informeCompleto.append("10. Alergias: ").append(vista.obtenerRespuesta(9)).append("\n\n");

        String extra = vista.getSintomasExtra();
        if (!extra.isEmpty()) {
            informeCompleto.append("--- COMENTARIOS EXTRA ---\n");
            informeCompleto.append(extra);
        }

        vista.bloquearBoton(true);
        enviarDatosAFirestore(informeCompleto.toString(), "Test IDermo Completo");
    }

    private void enviarDatosAFirestore(String sintomas, String tipoPiel) {
        if (mAuth.getCurrentUser() == null) {
            vista.bloquearBoton(false);
            Toast.makeText(vista, "Error: Sesión caducada", Toast.LENGTH_SHORT).show();
            return;
        }

        String uidCliente = mAuth.getCurrentUser().getUid();


        String correoCliente = mAuth.getCurrentUser().getEmail();


        Consulta_Modelo nuevaConsulta = new Consulta_Modelo(uidCliente, correoCliente, sintomas, tipoPiel);

        db.collection("consultas_cutaneas").add(nuevaConsulta)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        vista.bloquearBoton(false);
                        Toast.makeText(vista, "¡Análisis enviado con éxito!", Toast.LENGTH_SHORT).show();
                        vista.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        vista.bloquearBoton(false);
                        Toast.makeText(vista, "Error al enviar: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}