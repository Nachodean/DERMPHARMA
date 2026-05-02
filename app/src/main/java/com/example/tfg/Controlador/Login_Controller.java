package com.example.tfg.Controlador;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;


import com.example.tfg.Activities.Farmaceutico.Inicio_Farmaceutico_Activity; // Asegúrate de que se llame así
import com.example.tfg.Activities.Cliente.InicioCliente_Activity;
import com.example.tfg.Activities.Login_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login_Controller implements View.OnClickListener {

    private Login_Activity loginActivity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public Login_Controller(Login_Activity loginActivity) {
        this.loginActivity = loginActivity;
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        String email = loginActivity.getEmail();
        String pass = loginActivity.getPassword();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(loginActivity, "Introduce email y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String uid = mAuth.getCurrentUser().getUid();
                        comprobarRolYEntrar(uid);
                    } else {

                        Toast.makeText(loginActivity, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void comprobarRolYEntrar(String uid) {

        db.collection("Usuarios").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {

                        String rol = documentSnapshot.getString("rol");
                        saltarAPantallaPrincipal(rol);
                    } else {
                        Map<String, Object> nuevoUser = new HashMap<>();
                        nuevoUser.put("rol", "cliente");
                        nuevoUser.put("correo", mAuth.getCurrentUser().getEmail());

                        db.collection("Usuarios").document(uid).set(nuevoUser)
                                .addOnSuccessListener(aVoid -> saltarAPantallaPrincipal("cliente"));
                    }
                });
    }

    private void saltarAPantallaPrincipal(String rol) {
        Intent intent;
        if ("farma".equals(rol)) {
            intent = new Intent(loginActivity, Inicio_Farmaceutico_Activity.class);
        } else {
            intent = new Intent(loginActivity, InicioCliente_Activity.class);
        }
        loginActivity.startActivity(intent);
        loginActivity.finish();
    }
}
