package com.example.tfg.Activities.Farmaceutico;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Adaptador.Adaptador_Historial_Paciente;
import com.example.tfg.Controlador.Controlador_Historial_Paciente_Farma;
import com.example.tfg.Modelos.Consulta_Modelo;
import com.example.tfg.R;

import java.util.ArrayList;
import java.util.List;

public class Historial_Paciente_Activity extends AppCompatActivity {

    private ListView lvConsultas;
    private TextView tvCorreo;
    private ImageView btnVolver;

    private Adaptador_Historial_Paciente adaptador;
    private Controlador_Historial_Paciente_Farma controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historial_paciente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recogerElementos();
        establecerAdaptador();
        establecerEscuchadores();
    }

    private void recogerElementos() {
        lvConsultas = findViewById(R.id.lv_consultas_paciente);
        tvCorreo = findViewById(R.id.tv_correo_paciente_historial);
        btnVolver = findViewById(R.id.btn_volver_historial);

        controlador = new Controlador_Historial_Paciente_Farma(this);
    }

    private void establecerAdaptador() {
        adaptador = new Adaptador_Historial_Paciente(this, R.layout.item_consulta_paciente, new ArrayList<Consulta_Modelo>());
        lvConsultas.setAdapter(adaptador);
    }

    private void establecerEscuchadores() {

        btnVolver.setOnClickListener(controlador);


        String idPaciente = getIntent().getStringExtra("PACIENTE_ID");
        String correoPaciente = getIntent().getStringExtra("PACIENTE_CORREO");

        if (correoPaciente != null) {
            tvCorreo.setText(correoPaciente);
        }


        if (idPaciente != null) {
            controlador.cargarConsultasDelPaciente(idPaciente);
        }
    }



    public void pintarConsultas(List<Consulta_Modelo> lista) {
        if (adaptador != null) {
            adaptador.actualizar(lista);
        }
    }

    public void finalizarPantalla() {
        finish();
    }
}