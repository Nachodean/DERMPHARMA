package com.example.tfg.Controlador;

import android.view.View;
import android.widget.AdapterView; // Muy importante para el clic de la lista

import androidx.annotation.Nullable;

import com.example.tfg.Activities.Farmaceutico.Pacientes_Farmaceutico_Activity;
import com.example.tfg.Modelos.Usuario_Modelo;
import com.example.tfg.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Controlador_Pacientes_Farmaceutico implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String COLECCION_USUARIOS = "Usuarios";
    private static final String CAMPO_ROL = "rol";
    private static final String VALOR_CLIENTE = "cliente";

    private Pacientes_Farmaceutico_Activity vista;
    private FirebaseFirestore db;

    public Controlador_Pacientes_Farmaceutico(Pacientes_Farmaceutico_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Usuario_Modelo seleccionado = (Usuario_Modelo) parent.getItemAtPosition(position);

        if (seleccionado != null) {
            vista.irAFicha(seleccionado);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_volver_lista) {
            vista.finalizarPantalla();
        }
    }

    public void descargarPacientes() {
        db.collection(COLECCION_USUARIOS)
                .whereEqualTo(CAMPO_ROL, VALOR_CLIENTE)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) return;

                        if (value != null) {
                            List<Usuario_Modelo> lista = new ArrayList<>();
                            for (DocumentSnapshot doc : value.getDocuments()) {
                                try {
                                    Usuario_Modelo u = doc.toObject(Usuario_Modelo.class);
                                    if (u != null) {
                                        u.setUid(doc.getId());
                                        lista.add(u);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            vista.actualizarInterfaz(lista);
                        }
                    }
                });
    }
}