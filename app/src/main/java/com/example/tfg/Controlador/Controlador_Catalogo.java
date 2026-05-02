package com.example.tfg.Controlador;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tfg.Activities.Farmaceutico.Catalogo_Productos_Activity;
import com.example.tfg.Modelos.Producto_Modelo;
import com.example.tfg.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Controlador_Catalogo implements View.OnClickListener {

    private Catalogo_Productos_Activity vista;
    private FirebaseFirestore db;

    public Controlador_Catalogo(Catalogo_Productos_Activity vista) {
        this.vista = vista;
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_volver_catalogo) {
            vista.finalizarActivity();
        }
        else if (id == R.id.btn_anadir_producto_cabecera) {
            vista.abrirPantallaAnadir();
        }
    }

    public void descargarProductos() {
        db.collection("Productos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    vista.mostrarMensaje("Error al escuchar cambios: " + error.getMessage());
                    return;
                }

                if (value != null) {
                    List<Producto_Modelo> lista = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : value) {
                        Producto_Modelo p = doc.toObject(Producto_Modelo.class);
                        if (p != null) {
                            p.setId(doc.getId());
                            lista.add(p);
                        }
                    }
                    vista.actualizarListaProductos(lista);
                }
            }
        });
    }
}