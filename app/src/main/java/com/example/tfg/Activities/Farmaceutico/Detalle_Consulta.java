package com.example.tfg.Activities.Farmaceutico;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Controlador.Controlador_Detalle_Consulta;
import com.example.tfg.R;

import java.util.List;

public class Detalle_Consulta extends AppCompatActivity {

    private TextView tvPiel, tvSintomas;
    private ImageView btnVolver;
    private EditText etValoracion;
    private Spinner spLimpieza, spHidratacion, spTratamiento, spProteccion;
    private Button btnEnviar;
    private Controlador_Detalle_Consulta controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_consulta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recogerElementos();
        establecerEscuchadores();

    }

    private void establecerEscuchadores() {
        String idDoc = getIntent().getStringExtra("ID_DOCUMENTO");
        tvPiel.setText(getIntent().getStringExtra("TIPO_PIEL"));
        tvSintomas.setText(getIntent().getStringExtra("SINTOMAS"));

        controlador = new Controlador_Detalle_Consulta(this, idDoc);
        btnEnviar.setOnClickListener(controlador);
        btnVolver.setOnClickListener(controlador);
    }

    private void recogerElementos() {
        tvPiel = findViewById(R.id.tv_detalle_tipo_piel);
        tvSintomas = findViewById(R.id.tv_detalle_sintomas);

        etValoracion = findViewById(R.id.et_valoracion_clinica);

        spLimpieza = findViewById(R.id.sp_rutina_limpieza);
        spHidratacion = findViewById(R.id.sp_rutina_hidratacion);
        spTratamiento = findViewById(R.id.sp_rutina_tratamiento);
        spProteccion = findViewById(R.id.sp_rutina_proteccion);

        btnEnviar = findViewById(R.id.btn_enviar_respuesta);
        btnVolver = findViewById(R.id.btn_volver);
    }

    // Métodos para rellenar los Spinners sin tocar el onCreate
    public void setListaLimpieza(List<String> items) {
        configurarSpinner(spLimpieza, items);
    }
    public void setListaHidratacion(List<String> items) {
        configurarSpinner(spHidratacion, items);
    }
    public void setListaTratamiento(List<String> items) {
        configurarSpinner(spTratamiento, items);
    }
    public void setListaProteccion(List<String> items) {
        configurarSpinner(spProteccion, items);
    }

    private void configurarSpinner(Spinner spinner, List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // --- GETTERS PARA QUE EL CONTROLADOR LEA LOS DATOS (Sin atajos) ---
    public String getValoracion() {
        return etValoracion.getText().toString().trim();
    }

    public String getLimpieza() {
        if (spLimpieza.getSelectedItem() != null) {
            return spLimpieza.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    public String getHidratacion() {
        if (spHidratacion.getSelectedItem() != null) {
            return spHidratacion.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    public String getTratamiento() {
        if (spTratamiento.getSelectedItem() != null) {
            return spTratamiento.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    public String getProteccion() {
        if (spProteccion.getSelectedItem() != null) {
            return spProteccion.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    public void mostrarMensaje(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    public void bloquearBoton(boolean bloquear) {
        btnEnviar.setEnabled(!bloquear);
        if (bloquear) {
            btnEnviar.setText("ENVIANDO...");
        } else {
            btnEnviar.setText("ENVIAR DIAGNÓSTICO");
        }
    }
}