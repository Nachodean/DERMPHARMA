package com.example.tfg.Controlador;

import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;

import com.example.tfg.Activities.Cliente.Historial_Cliente_Activity;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controlador_Historial_Cliente implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Historial_Cliente_Activity vista;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private List<Consulta_Modelo> listaConsultas;
    private List<String> listaIds;

    public Controlador_Historial_Cliente(Historial_Cliente_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.listaConsultas = new ArrayList<>();
        this.listaIds = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.btn_volver_menu){
            vista.finish();
        }
    }


    private class ParConsulta {
        Consulta_Modelo modelo;
        String idDocumento;

        ParConsulta(Consulta_Modelo modelo, String idDocumento) {
            this.modelo = modelo;
            this.idDocumento = idDocumento;
        }
    }

    public void cargarHistorial() {
        if (mAuth.getCurrentUser() == null) {
            vista.mostrarMensaje("Error: Usuario no identificado.");
            return;
        }

        String miUid = mAuth.getCurrentUser().getUid();

        db.collection("consultas_cutaneas")
                .whereEqualTo("uid_Cliente", miUid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            vista.mostrarMensaje("Error al descargar el historial: " + error.getMessage());
                            return;
                        }

                        if (value != null) {
                            listaConsultas.clear();
                            listaIds.clear();


                            List<ParConsulta> listaTemporal = new ArrayList<>();

                            for (DocumentSnapshot doc : value.getDocuments()) {
                                Consulta_Modelo c = doc.toObject(Consulta_Modelo.class);
                                if (c != null) {
                                    listaTemporal.add(new ParConsulta(c, doc.getId()));
                                }
                            }


                            Collections.sort(listaTemporal, new Comparator<ParConsulta>() {
                                @Override
                                public int compare(ParConsulta p1, ParConsulta p2) {
                                    long tiempo1 = p1.modelo.getTimestamp();
                                    long tiempo2 = p2.modelo.getTimestamp();


                                    if (tiempo1 < tiempo2) return 1;
                                    if (tiempo1 > tiempo2) return -1;
                                    return 0;
                                }
                            });


                            for (ParConsulta par : listaTemporal) {
                                listaConsultas.add(par.modelo);
                                listaIds.add(par.idDocumento);
                            }

                            if (listaConsultas.isEmpty()) {
                                vista.mostrarMensaje("Aún no tienes consultas en tu historial.");
                            } else {
                                vista.actualizarLista(listaConsultas);
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listaIds.isEmpty() || position >= listaIds.size()) return;

        String idDocumentoSeleccionado = listaIds.get(position);
        vista.abrirDetalleReceta(idDocumentoSeleccionado);
    }
}