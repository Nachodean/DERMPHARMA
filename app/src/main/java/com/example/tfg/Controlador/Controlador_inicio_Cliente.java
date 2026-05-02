package com.example.tfg.Controlador;

import android.view.View;
import com.example.tfg.Activities.Cliente.InicioCliente_Activity;
import com.example.tfg.R;
import com.google.firebase.auth.FirebaseAuth;

public class Controlador_inicio_Cliente implements View.OnClickListener {

    private InicioCliente_Activity vista;
    private FirebaseAuth mAuth;

    public Controlador_inicio_Cliente(InicioCliente_Activity vista) {
        this.vista = vista;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_comenzar_test) {
            vista.abrirFormulario();
        } else if (id == R.id.card_historial) {
            vista.abrirHistorial();
        } else if (id == R.id.card_citas) {
            vista.abrirCitas();
        } else if (id == R.id.btn_cerrar_sesion_cliente) {

            vista.mostrarDialogoLogout();
        }
    }


    public void confirmarCierreSesion() {
        mAuth.signOut();
        vista.salirAlLogin();
    }
}