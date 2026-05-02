package com.example.tfg.Activities.Cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Controlador.Controlador_Formulario;
import com.example.tfg.R;

public class FormularioPiel_Activity extends AppCompatActivity {

    private Button btnEnviar;
    private EditText etSintomasExtra;
    private ImageView btnVolver;

    private RadioGroup[] gruposPreguntas = new RadioGroup[10];

    private Controlador_Formulario controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_piel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recogerElementos();



        establecerEscuchadores();
    }

    private void establecerEscuchadores() {
        controlador = new Controlador_Formulario(this);
        btnEnviar.setOnClickListener(controlador);
        btnVolver.setOnClickListener(controlador);
    }

    private void recogerElementos() {
        btnEnviar = findViewById(R.id.btn_enviar_formulario);
        btnVolver = findViewById(R.id.btn_volver_test);
        etSintomasExtra = findViewById(R.id.et_sintomas_extra);
        gruposPreguntas[0] = findViewById(R.id.rg_q1);
        gruposPreguntas[1] = findViewById(R.id.rg_q2);
        gruposPreguntas[2] = findViewById(R.id.rg_q3);
        gruposPreguntas[3] = findViewById(R.id.rg_q4);
        gruposPreguntas[4] = findViewById(R.id.rg_q5);
        gruposPreguntas[5] = findViewById(R.id.rg_q6);
        gruposPreguntas[6] = findViewById(R.id.rg_q7);
        gruposPreguntas[7] = findViewById(R.id.rg_q8);
        gruposPreguntas[8] = findViewById(R.id.rg_q9);
        gruposPreguntas[9] = findViewById(R.id.rg_q10);
    }

    public RadioGroup[] getGruposPreguntas() {
        return gruposPreguntas;
    }

    public String obtenerRespuesta(int indicePregunta) {
        RadioGroup rg = gruposPreguntas[indicePregunta];
        int idSeleccionado = rg.getCheckedRadioButtonId();
        if (idSeleccionado == -1) return ""; // No marcó nada

        RadioButton rb = findViewById(idSeleccionado);
        return rb.getText().toString();
    }

    public String getSintomasExtra() {
        return etSintomasExtra.getText().toString().trim();
    }

    public void bloquearBoton(boolean bloquear) {
        btnEnviar.setEnabled(!bloquear);
        if (bloquear) {
            btnEnviar.setText("ENVIANDO...");
        } else {
            btnEnviar.setText("ENVIAR ANÁLISIS");
        }
    }
}