package com.example.tfg.Activities.Cliente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Controlador.Controlador_Cita_Cliente;
import com.example.tfg.R;

import java.util.List;

public class Citas_Cliente_Activity extends AppCompatActivity {
    private CalendarView calendarCitas;
    private RadioGroup rgHorarios;
    private Button btnConfirmarCita;
    private ImageView btnVolver;

    private String fechaSeleccionada = "";
    private String horaSeleccionada = "";
    private Controlador_Cita_Cliente controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_citas_cliente);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recogerElementos();
        establecerEscuchadores();
    }

    private void recogerElementos() {
        calendarCitas = findViewById(R.id.calendar_citas);
        rgHorarios = findViewById(R.id.rg_horarios);
        btnConfirmarCita = findViewById(R.id.btn_confirmar_cita);
        btnVolver = findViewById(R.id.btn_volver_citas);
    }

    private void establecerEscuchadores() {
        controlador = new Controlador_Cita_Cliente(this);


        controlador.configurarCalendario(calendarCitas);


        rgHorarios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbSeleccionado = findViewById(checkedId);
                if (rbSeleccionado != null) {
                    horaSeleccionada = rbSeleccionado.getText().toString();
                }
            }
        });

        btnConfirmarCita.setOnClickListener(controlador);
        btnVolver.setOnClickListener(controlador);
    }

    public void actualizarEstadoHorarios(List<String> ocupadas) {
        for (int i = 0; i < rgHorarios.getChildCount(); i++) {
            View v = rgHorarios.getChildAt(i);
            if (v instanceof RadioButton) {
                RadioButton rb = (RadioButton) v;
                String textoHora = rb.getText().toString();
                if (ocupadas.contains(textoHora)) {
                    rb.setEnabled(false);
                    rb.setChecked(false);
                } else {
                    rb.setEnabled(true);
                }
            }
        }
    }

    public void setFechaSeleccionada(String fecha) {
        this.fechaSeleccionada = fecha;
    }

    public String getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public String getHoraSeleccionada() {
        return horaSeleccionada;
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void cerrarPantalla() {
        finish();
    }
}