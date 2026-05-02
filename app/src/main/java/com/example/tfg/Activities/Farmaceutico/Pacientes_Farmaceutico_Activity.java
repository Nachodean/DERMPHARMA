package com.example.tfg.Activities.Farmaceutico;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.tfg.Adaptador.Adaptador_Pacientes;
import com.example.tfg.Controlador.Controlador_Pacientes_Farmaceutico;
import com.example.tfg.Modelos.Usuario_Modelo;
import com.example.tfg.R;
import java.util.List;

public class Pacientes_Farmaceutico_Activity extends AppCompatActivity {

    private ListView lvPacientes;
    private EditText etBuscador;
    private ImageView btnVolver;
    private Adaptador_Pacientes adaptador;
    private Controlador_Pacientes_Farmaceutico controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pacientes_farmaceutico);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recogerElementos();
        establecerAdaptador();
        establecerEscuchadores();

        controlador.descargarPacientes();
    }

    private void recogerElementos() {
        controlador = new Controlador_Pacientes_Farmaceutico(this);
        lvPacientes = findViewById(R.id.lv_pacientes_full);
        etBuscador = findViewById(R.id.et_buscador_correo);
        btnVolver = findViewById(R.id.btn_volver_lista);
    }

    private void establecerAdaptador() {
        adaptador = new Adaptador_Pacientes(this);
        lvPacientes.setAdapter(adaptador);
    }

    private void establecerEscuchadores() {
        btnVolver.setOnClickListener(controlador);
        lvPacientes.setOnItemClickListener(controlador);

        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adaptador != null) adaptador.filtrar(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    public void actualizarInterfaz(List<Usuario_Modelo> lista) {
        if (adaptador != null) {
            adaptador.cargarDatos(lista);
        }
    }

    public void irAFicha(Usuario_Modelo u) {
        if (u == null || u.getUid() == null) return;

        Intent i = new Intent(this, Historial_Paciente_Activity.class);
        i.putExtra("PACIENTE_ID", u.getUid());
        i.putExtra("PACIENTE_CORREO", u.getCorreo() != null ? u.getCorreo() : "Sin correo");
        startActivity(i);
    }

    public void finalizarPantalla() {
        finish();
    }
}