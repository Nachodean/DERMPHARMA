package com.example.tfg.Activities.Farmaceutico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Adaptador.Adaptador_Citas_Farma;
import com.example.tfg.Controlador.Controlador_Citas_Farmaceutico;
import com.example.tfg.Modelos.Cita_Modelo;
import com.example.tfg.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Citas_Farmaceutico_Activity extends AppCompatActivity {

    private ListView lvCitas;
    private ImageView btnVolver;
    private TextView tvFechaCabecera;

    private Adaptador_Citas_Farma adaptador;
    private Controlador_Citas_Farmaceutico controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_citas_farmaceutico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        recogerElementos();
        configurarFechaActual();
        establecerEscuchadores();
        controlador.escucharCitas();
    }

    private void recogerElementos() {
        controlador = new Controlador_Citas_Farmaceutico(this);
        lvCitas = findViewById(R.id.lv_citas_farma);
        btnVolver = findViewById(R.id.btn_volver_inicio_farma);
        tvFechaCabecera = findViewById(R.id.tv_fecha_actual_agenda);

        adaptador = new Adaptador_Citas_Farma(
                this,
                R.layout.item_cita_farma,
                new ArrayList<Cita_Modelo>(),
                controlador
        );
        lvCitas.setAdapter(adaptador);
    }

    private void establecerEscuchadores() {
        btnVolver.setOnClickListener(controlador);
    }

    private void configurarFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d 'de' MMMM", new Locale("es", "ES"));
        String fechaHoy = sdf.format(new Date());

        if (fechaHoy.length() > 0) {
            String fechaFormateada = fechaHoy.substring(0, 1).toUpperCase() + fechaHoy.substring(1);
            tvFechaCabecera.setText(fechaFormateada);
        }
    }



    public void pintarCitas(List<Cita_Modelo> listaNuevas) {
        if (adaptador != null) {
            adaptador.actualizar(listaNuevas);
        }
    }

    public void navegarAHistorialPaciente(String pacienteId, String correoPaciente) {
        Intent intent = new Intent(this, Historial_Paciente_Activity.class);
        intent.putExtra("PACIENTE_ID", pacienteId);
        intent.putExtra("PACIENTE_CORREO", correoPaciente);
        startActivity(intent);
    }

    public void mostrarMensaje(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    public void finalizarPantalla() {
        finish();
    }
}