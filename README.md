# 💊 Dermapharma — App Android para Dermofarmacia

Aplicación móvil nativa para Android desarrollada en **Java** como Trabajo de Fin de Grado del ciclo de Desarrollo de Aplicaciones Multiplataforma (DAM).

Dermapharma permite gestionar un catálogo de productos de dermofarmacia, perfiles de usuario y citas, ofreciendo una experiencia fluida con Material Design 3 y autenticación segura con Firebase.

---

## 📱 Capturas de pantalla

<!-- INSTRUCCIONES: Crea una carpeta /screenshots en el repo, sube capturas
     de la app y descomenta las líneas de abajo sustituyendo los nombres.
     Las capturas multiplican el impacto visual del README. -->

<!--
<p align="center">
  <img src="screenshots/login.png" width="200" alt="Pantalla de login"/>
  <img src="screenshots/catalogo.png" width="200" alt="Catálogo de productos"/>
  <img src="screenshots/perfil.png" width="200" alt="Perfil de usuario"/>
  <img src="screenshots/citas.png" width="200" alt="Gestión de citas"/>
</p>
-->

> ⚠️ *Capturas de pantalla próximamente.*

---

## 🛠️ Tecnologías

| Capa | Tecnología |
|------|------------|
| **Lenguaje** | Java |
| **Plataforma** | Android (Android Studio) |
| **Autenticación** | Firebase Authentication |
| **Base de datos** | Cloud Firestore (NoSQL) |
| **Diseño UI** | Material Design 3 |
| **Navegación** | Android Navigation Component |
| **Arquitectura** | MVC (Model-View-Controller) |

---

## ✨ Funcionalidades principales

### Gestión de productos
- Catálogo de productos de dermofarmacia con información detallada.
- Búsqueda y filtrado de productos.

### Usuarios
- Registro e inicio de sesión seguros con Firebase Auth.
- Perfil de usuario con datos personales.

### Citas
- Solicitud y gestión de citas.
- Historial de consultas anteriores.

---

## 🏗️ Arquitectura del proyecto

```
app/
├── src/main/java/
│   ├── model/          # Entidades (Producto, Usuario, Cita)
│   ├── view/           # Activities y Fragments (UI)
│   ├── controller/     # Lógica de negocio
│   └── utils/          # Clases auxiliares
├── src/main/res/       # Layouts XML, drawables, strings
└── build.gradle        # Dependencias del módulo
```

La app sigue el patrón **MVC**: los modelos representan los datos (productos, usuarios, citas), las vistas manejan la interfaz y los controladores contienen la lógica que conecta ambas capas con Firebase.

---

## 🚀 Cómo ejecutar el proyecto

### Requisitos previos
- **Android Studio** (Arctic Fox o superior).
- **JDK 11** o superior.
- Una cuenta de Firebase con un proyecto configurado.

### Pasos
1. Clona el repositorio:
   ```bash
   git clone https://github.com/Nachodean/DERMPHARMA.git
   ```
2. Abre el proyecto en Android Studio.
3. Añade tu archivo `google-services.json` en la carpeta `app/` (no incluido en el repo por seguridad).
4. Sincroniza Gradle y ejecuta en un emulador o dispositivo físico.

---

## 📌 Estado del proyecto

- [x] Autenticación con Firebase
- [x] CRUD de productos
- [x] Gestión de perfiles de usuario
- [x] Sistema de citas
- [ ] Notificaciones push de recordatorio de citas
- [ ] Versión con back-end propio (Spring Boot + PostgreSQL) — *en desarrollo*

---

## 🎓 Contexto académico

Este proyecto fue desarrollado como **Trabajo de Fin de Grado (TFG)** del ciclo de FP de Grado Superior en **Desarrollo de Aplicaciones Multiplataforma (DAM)** en el [IES Venancio Blanco](https://iesvenancioblanco.centros.educa.jcyl.es/) de Salamanca (curso 2023-2026).

---

## 👤 Autor

**Pablo Ignacio De Andrés González**

- GitHub: [@Nachodean](https://github.com/Nachodean)
- Email: nachodean2@gmail.com

---

## 📄 Licencia

Este proyecto es de uso académico. Consulta el archivo `LICENSE` para más información.
