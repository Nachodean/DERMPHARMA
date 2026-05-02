package com.example.tfg.Activities.Cliente;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog; // NUEVO
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Activities.Login_Activity;
import com.example.tfg.Controlador.Controlador_inicio_Cliente;
import com.example.tfg.R;

public class InicioCliente_Activity extends AppCompatActivity {

    private Button btnComenzarTest;
    private CardView cardHistorial, cardCitas;
    private ImageView btnCerrarSesion;
    private Controlador_inicio_Cliente controlador_inicio_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_cliente);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recogerElementos();
        establecerEscuchadores();
    }

    private void recogerElementos() {
        btnComenzarTest = findViewById(R.id.btn_comenzar_test);
        cardHistorial = findViewById(R.id.card_historial);
        cardCitas = findViewById(R.id.card_citas);
        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion_cliente);
    }

    private void establecerEscuchadores() {
        controlador_inicio_cliente = new Controlador_inicio_Cliente(this);
        btnComenzarTest.setOnClickListener(controlador_inicio_cliente);
        cardCitas.setOnClickListener(controlador_inicio_cliente);
        cardHistorial.setOnClickListener(controlador_inicio_cliente);
        btnCerrarSesion.setOnClickListener(controlador_inicio_cliente);
    }
    public void mostrarDialogoLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Deseas salir de la aplicación?");

        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                controlador_inicio_cliente.confirmarCierreSesion();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    public void salirAlLogin() {
        Intent intent = new Intent(this, Login_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void abrirFormulario() {
        startActivity(new Intent(this, FormularioPiel_Activity.class));
    }
    public void abrirHistorial() {
        startActivity(new Intent(this, Historial_Cliente_Activity.class));
    }
    public void abrirCitas() {
        startActivity(new Intent(this, Citas_Cliente_Activity.class));
    }
}