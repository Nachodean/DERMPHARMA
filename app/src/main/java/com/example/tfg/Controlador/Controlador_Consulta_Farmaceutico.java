package com.example.tfg.Controlador;

import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;

import com.example.tfg.Activities.Farmaceutico.Consultas_Farmaceutico_Activity;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Controlador_Consulta_Farmaceutico implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Consultas_Farmaceutico_Activity vista;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    private List<Consulta_Modelo> listaConsultas;
    private List<String> listaIds;

    public Controlador_Consulta_Farmaceutico(Consultas_Farmaceutico_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.listaConsultas = new ArrayList<>();
        this.listaIds = new ArrayList<>();
    }

    public void cargarConsultasPendientes() {

        db.collection("consultas_cutaneas")
                .whereEqualTo("estado", "PENDIENTE")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (mAuth.getCurrentUser() == null) return;
                        if (error != null) {
                            vista.mostrarMensaje("Error al leer datos: " + error.getMessage());
                            return;
                        }

                        if (value != null) {
                            listaConsultas.clear();
                            listaIds.clear();
                            List<String> titulosParaLaVista = new ArrayList<>();


                            for (DocumentSnapshot doc : value.getDocuments()) {
                                Consulta_Modelo c = doc.toObject(Consulta_Modelo.class);
                                if (c != null) {
                                    listaConsultas.add(c);
                                    listaIds.add(doc.getId());


                                    String textoMostrar = "Paciente: " + c.getCorreo_cliente();

                                    if (c.getCorreo_cliente() == null) {
                                        textoMostrar = "Paciente: Sin correo (Antiguo)";
                                    }

                                    titulosParaLaVista.add(textoMostrar);
                                }
                            }


                            vista.actualizarLista(titulosParaLaVista);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Consulta_Modelo seleccionada = listaConsultas.get(position);
        String idDocumento = listaIds.get(position);


        vista.abrirDetalleConsulta(idDocumento, seleccionada.getSintomas(), seleccionada.getTipo_piel());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_volver_atras) {
            vista.finish();
        }
    }
}