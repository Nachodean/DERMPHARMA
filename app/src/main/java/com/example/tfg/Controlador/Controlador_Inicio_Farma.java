package com.example.tfg.Controlador;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.tfg.Activities.Farmaceutico.Catalogo_Productos_Activity;
import com.example.tfg.Activities.Farmaceutico.Citas_Farmaceutico_Activity;
import com.example.tfg.Activities.Farmaceutico.Inicio_Farmaceutico_Activity;
import com.example.tfg.Activities.Farmaceutico.Pacientes_Farmaceutico_Activity;
import com.example.tfg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Controlador_Inicio_Farma implements View.OnClickListener {

    private Inicio_Farmaceutico_Activity vista;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    public Controlador_Inicio_Farma(Inicio_Farmaceutico_Activity vista) {
        this.vista = vista;
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        escucharConsultasPendientes();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.card_casos_pendientes) {

            vista.abrirBandeja();
        }
        else if (v.getId() == R.id.btn_logout_farma) {
            vista.mostrarDialogoLogout();
        } else if (v.getId() == R.id.card_agenda_farma) {
            Intent intent = new Intent(vista, Citas_Farmaceutico_Activity.class);
            vista.startActivity(intent);
        } else if (v.getId() == R.id.card_pacientes_farma) {
            Intent intent = new Intent(vista, Pacientes_Farmaceutico_Activity.class);
            vista.startActivity(intent);
        } else if (v.getId() == R.id.card_productos_farma) {
            Intent intent = new Intent(vista, Catalogo_Productos_Activity.class);
            vista.startActivity(intent);
        }
    }


    public void confirmarCierreSesion() {
        mAuth.signOut();
        vista.salirAlLogin();
    }


    private void escucharConsultasPendientes() {
        db.collection("consultas_cutaneas")
                .whereEqualTo("estado", "PENDIENTE")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error == null && value != null) {

                            int total = value.size();
                            vista.actualizarContador(total);
                        }
                    }
                });
    }
}