package com.example.tfg.Activities.Farmaceutico;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Controlador.Controlador_Anadir_Producto;
import com.example.tfg.R;

public class Anadir_Producto_Activity extends AppCompatActivity {
    private ImageView btnVolver;
    private EditText etNombre;
    private Spinner spCategoria;
    private Button btnGuardar;
    private Controlador_Anadir_Producto controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anadir_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recogerElementos();
        establecerAdaptadores();
        establecerEscuchadores();


    }

    private void establecerAdaptadores() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                controlador.obtenerCategorias());
        spCategoria.setAdapter(adapter);
    }

    private void recogerElementos() {
        controlador = new Controlador_Anadir_Producto(this);
        btnVolver = findViewById(R.id.btn_volver_add);
        etNombre = findViewById(R.id.et_nombre_nuevo_prod);
        spCategoria = findViewById(R.id.sp_categoria_nuevo_prod);
        btnGuardar = findViewById(R.id.btn_guardar_nuevo_prod);



    }

    private void establecerEscuchadores() {

        btnVolver.setOnClickListener(controlador);
        btnGuardar.setOnClickListener(controlador);
    }



    public String getNombreIngresado() {
        return etNombre.getText().toString().trim();
    }

    public String getCategoriaSeleccionada() {
        return spCategoria.getSelectedItem().toString();
    }

    public void mostrarMensaje(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_SHORT).show();
    }

    public void notificarExito() {
        Toast.makeText(this, "Producto guardado con éxito", Toast.LENGTH_SHORT).show();
        cerrarPantalla();
    }

    public void cerrarPantalla() {
        finish();
    }
}