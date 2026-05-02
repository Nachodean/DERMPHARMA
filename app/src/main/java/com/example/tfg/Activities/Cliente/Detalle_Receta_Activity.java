package com.example.tfg.Activities.Cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Controlador.Controlador_Detalle_Consulta_Cliente;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;

public class Detalle_Receta_Activity extends AppCompatActivity {

    private ImageView btnVolver;
    private Button btnEntendido;
    private TextView tvPregunta, tvValoracion, tvTituloProductos;
    private TextView tvLimpieza, tvHidratacion, tvTratamiento, tvProteccion;
    private LinearLayout llLimpieza, llHidratacion, llTratamiento, llProteccion;
    private CardView cvRutina;

    private Controlador_Detalle_Consulta_Cliente controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_receta);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recogerElementos();
        establecerEscuchadores();
    }

    private void recogerElementos() {
        btnVolver = findViewById(R.id.btn_volver_historial);
        btnEntendido = findViewById(R.id.btn_entendido);
        tvPregunta = findViewById(R.id.tv_pregunta_cliente);
        tvValoracion = findViewById(R.id.tv_valoracion_medico);
        tvTituloProductos = findViewById(R.id.tv_titulo_productos);
        cvRutina = findViewById(R.id.cv_rutina_pautada);
        tvLimpieza = findViewById(R.id.tv_prod_limpieza);
        tvHidratacion = findViewById(R.id.tv_prod_hidratacion);
        tvTratamiento = findViewById(R.id.tv_prod_tratamiento);
        tvProteccion = findViewById(R.id.tv_prod_proteccion);
        llLimpieza = findViewById(R.id.ll_limpieza);
        llHidratacion = findViewById(R.id.ll_hidratacion);
        llTratamiento = findViewById(R.id.ll_tratamiento);
        llProteccion = findViewById(R.id.ll_proteccion);
    }

    private void establecerEscuchadores() {
        String idDocumento = getIntent().getStringExtra("ID_DOCUMENTO");
        controlador = new Controlador_Detalle_Consulta_Cliente(this, idDocumento);
        btnVolver.setOnClickListener(controlador);
        btnEntendido.setOnClickListener(controlador);
    }

    public void mostrarDatos(Consulta_Modelo consulta) {
        tvPregunta.setText(consulta.getSintomas());

        if (consulta.getValoracion_clinica() != null && !consulta.getValoracion_clinica().isEmpty()) {
            tvValoracion.setText(consulta.getValoracion_clinica());
        } else {
            tvValoracion.setText("El farmacéutico aún está revisando tu consulta.");
        }

        boolean hayRutina = false;

        // Limpieza
        if (consulta.getRutina_limpieza() != null && !consulta.getRutina_limpieza().trim().isEmpty() && !consulta.getRutina_limpieza().equals("Seleccionar Limpiador")) {
            tvLimpieza.setText(consulta.getRutina_limpieza());
            llLimpieza.setVisibility(View.VISIBLE);
            hayRutina = true;
        } else {
            llLimpieza.setVisibility(View.GONE);
        }

        // Hidratación
        if (consulta.getRutina_hidratacion() != null && !consulta.getRutina_hidratacion().trim().isEmpty() && !consulta.getRutina_hidratacion().equals("Seleccionar Hidratante")) {
            tvHidratacion.setText(consulta.getRutina_hidratacion());
            llHidratacion.setVisibility(View.VISIBLE);
            hayRutina = true;
        } else {
            llHidratacion.setVisibility(View.GONE);
        }

        // Tratamiento
        if (consulta.getRutina_tratamiento() != null && !consulta.getRutina_tratamiento().trim().isEmpty() && !consulta.getRutina_tratamiento().equals("Seleccionar Tratamiento")) {
            tvTratamiento.setText(consulta.getRutina_tratamiento());
            llTratamiento.setVisibility(View.VISIBLE);
            hayRutina = true;
        } else {
            llTratamiento.setVisibility(View.GONE);
        }

        // Protección Solar
        if (consulta.getRutina_proteccion() != null && !consulta.getRutina_proteccion().trim().isEmpty() && !consulta.getRutina_proteccion().equals("Seleccionar Protección")) {
            tvProteccion.setText(consulta.getRutina_proteccion());
            llProteccion.setVisibility(View.VISIBLE);
            hayRutina = true;
        } else {
            llProteccion.setVisibility(View.GONE);
        }

        if (hayRutina) {
            tvTituloProductos.setVisibility(View.VISIBLE);
            cvRutina.setVisibility(View.VISIBLE);
        } else {
            tvTituloProductos.setVisibility(View.GONE);
            cvRutina.setVisibility(View.GONE);
        }
    }

    public void mostrarMensaje(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}