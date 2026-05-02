package com.example.tfg.Activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tfg.Controlador.Login_Controller;
import com.example.tfg.R;

public class Login_Activity extends AppCompatActivity {

    private Login_Controller Login_Controlador;
    private Button Boton_Login;
    private EditText Texto_Usuario,Texto_Contraseña;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recogerElementos();
        establecerEscuchadores();
    }

    private void establecerEscuchadores() {

        Login_Controlador  = new Login_Controller(this);
        Boton_Login.setOnClickListener(Login_Controlador);

    }

    private void recogerElementos() {

        Boton_Login = findViewById(R.id.btn_login);
        Texto_Usuario = findViewById(R.id.et_email);
        Texto_Contraseña = findViewById(R.id.et_password);
    }
    public String getEmail() {
        return Texto_Usuario.getText().toString().trim();
    }

    public String getPassword() {
        return Texto_Contraseña.getText().toString().trim();
    }

}