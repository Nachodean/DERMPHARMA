package com.example.tfg.Activities.Farmaceutico;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Adaptador.Adaptador_Productos;
import com.example.tfg.Controlador.Controlador_Catalogo;
import com.example.tfg.Modelos.Producto_Modelo;
import com.example.tfg.R;

import java.util.List;

public class Catalogo_Productos_Activity extends AppCompatActivity {
    private ImageView btnVolver, btnAnadir;
    private EditText etBuscador;
    private ListView lvProductos;
    private Adaptador_Productos adaptador;
    private Controlador_Catalogo controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_catalogo_productos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        recogerElementos();
        establecerEscuchadores();
        controlador.descargarProductos();
    }

    private void recogerElementos() {
        btnVolver = findViewById(R.id.btn_volver_catalogo);
        btnAnadir = findViewById(R.id.btn_anadir_producto_cabecera);
        etBuscador = findViewById(R.id.et_buscador_productos);
        lvProductos = findViewById(R.id.lv_lista_productos);

        adaptador = new Adaptador_Productos(this);
        lvProductos.setAdapter(adaptador);
    }

    private void establecerEscuchadores() {
        controlador = new Controlador_Catalogo(this);
        btnVolver.setOnClickListener(controlador);
        btnAnadir.setOnClickListener(controlador);


        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptador.filtrarBusqueda(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }



    public void actualizarListaProductos(List<Producto_Modelo> lista) {
        adaptador.cargarDatos(lista);
    }

    public void abrirPantallaAnadir() {
        Intent i = new Intent(this, Anadir_Producto_Activity.class);
        startActivity(i);
    }

    public void finalizarActivity() {
        finish();
    }

    public void mostrarMensaje(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }
}