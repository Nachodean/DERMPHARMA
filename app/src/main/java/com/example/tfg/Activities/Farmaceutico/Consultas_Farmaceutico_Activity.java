package com.example.tfg.Activities.Farmaceutico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Activities.Farmaceutico.Detalle_Consulta;
import com.example.tfg.Controlador.Controlador_Consulta_Farmaceutico;
import com.example.tfg.R;

import java.util.ArrayList;
import java.util.List;

public class Consultas_Farmaceutico_Activity extends AppCompatActivity {

    private ListView Lista_Consultas;
    private ArrayAdapter<String> Adaptador_Lista;
    private Controlador_Consulta_Farmaceutico controlador;
    private ImageView btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultas_farmaceutico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recogerElementos();
        establecerEscuchadores();
        establecerAdaptador();
        controlador.cargarConsultasPendientes();
    }

    private void recogerElementos() {

        Lista_Consultas = findViewById(R.id.listview_consultas);
        btnVolver = findViewById(R.id.btn_volver_atras);
    }

    private void establecerEscuchadores() {
        controlador = new Controlador_Consulta_Farmaceutico(this);
        Lista_Consultas.setOnItemClickListener(controlador);
        btnVolver.setOnClickListener(controlador);
    }
    private void establecerAdaptador() {
        Adaptador_Lista = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        Lista_Consultas.setAdapter(Adaptador_Lista);

    }

    public void actualizarLista(List<String> datosNuevos) {
        Adaptador_Lista.clear();
        Adaptador_Lista.addAll(datosNuevos);
        Adaptador_Lista.notifyDataSetChanged();
    }

    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void abrirDetalleConsulta(String idDocumento, String sintomas, String tipoPiel) {
        Intent intent = new Intent(this, Detalle_Consulta.class);
        intent.putExtra("ID_DOCUMENTO", idDocumento);
        intent.putExtra("SINTOMAS", sintomas);
        intent.putExtra("TIPO_PIEL", tipoPiel);
        startActivity(intent);
    }
}