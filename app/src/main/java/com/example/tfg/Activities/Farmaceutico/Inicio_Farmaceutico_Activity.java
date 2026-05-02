package com.example.tfg.Activities.Farmaceutico;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Activities.Login_Activity;
import com.example.tfg.Controlador.Controlador_Inicio_Farma;
import com.example.tfg.R;

public class Inicio_Farmaceutico_Activity extends AppCompatActivity {

    private Controlador_Inicio_Farma Inicio_Controlador;
    private CardView Card_Tests, Card_Agenda, Card_Pacientes, Card_Productos;
    private ImageView Boton_Logout;
    private TextView Texto_Contador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_farmaceutico);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recogerElementos();

        establecerEscuchadores();
    }

    private void recogerElementos() {
        Card_Tests = findViewById(R.id.card_casos_pendientes);
        Card_Agenda = findViewById(R.id.card_agenda_farma);
        Card_Pacientes = findViewById(R.id.card_pacientes_farma);
        Card_Productos = findViewById(R.id.card_productos_farma);
        Boton_Logout = findViewById(R.id.btn_logout_farma);
        Texto_Contador = findViewById(R.id.tv_contador_pendientes);
    }

    private void establecerEscuchadores() {
        Inicio_Controlador = new Controlador_Inicio_Farma(this);
        Card_Tests.setOnClickListener(Inicio_Controlador);
        Boton_Logout.setOnClickListener(Inicio_Controlador);
        Card_Agenda.setOnClickListener(Inicio_Controlador);
        Card_Pacientes.setOnClickListener(Inicio_Controlador);
        Card_Productos.setOnClickListener(Inicio_Controlador);
    }

    public void actualizarContador(int n) {
        if (Texto_Contador != null) {
            Texto_Contador.setText(String.valueOf(n));
        }
    }

    public void abrirBandeja() {
        startActivity(new Intent(this, Consultas_Farmaceutico_Activity.class));
    }

    public void mostrarDialogoLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Deseas salir del panel de control?");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Inicio_Controlador.confirmarCierreSesion();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(Inicio_Farmaceutico_Activity.this, "Acción cancelada", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    public void salirAlLogin() {
        Intent intent = new Intent(this, Login_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}