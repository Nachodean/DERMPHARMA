package com.example.tfg.Activities.Cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Adaptador.Adaptador_Historial;
import com.example.tfg.Controlador.Controlador_Historial_Cliente;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;

import java.util.ArrayList;
import java.util.List;

public class Historial_Cliente_Activity extends AppCompatActivity {

    private ListView listaHistorial;
    private ImageView btnVolver;
    private Adaptador_Historial adaptadorTarjetas;
    private Controlador_Historial_Cliente controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recogerElementos();
        establecerAdaptadores();
        establecerEscuchadores();
        controlador.cargarHistorial();
    }

    private void establecerAdaptadores() {
        adaptadorTarjetas = new Adaptador_Historial(this, R.layout.item_tarjeta_historial, new ArrayList<Consulta_Modelo>());
        listaHistorial.setAdapter(adaptadorTarjetas);
    }

    private void recogerElementos() {
        listaHistorial = findViewById(R.id.lv_historial_cliente);
        btnVolver = findViewById(R.id.btn_volver_menu);
    }

    private void establecerEscuchadores() {
        controlador = new Controlador_Historial_Cliente(this);

        listaHistorial.setOnItemClickListener(controlador);
        btnVolver.setOnClickListener(controlador);
    }



    public void actualizarLista(List<Consulta_Modelo> datosNuevos) {
        adaptadorTarjetas.actualizarDatos(datosNuevos);
    }

    public void abrirDetalleReceta(String idDocumento) {
        Intent intent = new Intent(this, Detalle_Receta_Activity.class);
        intent.putExtra("ID_DOCUMENTO", idDocumento);
        startActivity(intent);
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}