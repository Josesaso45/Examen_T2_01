# Examen T2 - Aplicación de Configuración (Tramites)

Este proyecto es una aplicación Android desarrollada en **Kotlin** para el examen T2. La aplicación muestra una lista de opciones de configuración ("Trámites") obtenidas desde un servicio REST (API Mock) y presentadas con una interfaz moderna basada en Material Design.

## 📱 Vista Previa
La aplicación consta de una pantalla principal que muestra:
- Un encabezado con el título "Configura mi App".
- Una lista (RecyclerView) de tarjetas interactivas.
- Iconos personalizados para cada opción.
- Navegación interna mediante Fragments.

## 🚀 Características
- **Consumo de API REST:** Uso de **Retrofit** para obtener datos en formato JSON de forma asíncrona.
- **Corrutinas de Kotlin:** Manejo de hilos para no bloquear la interfaz de usuario durante las peticiones de red.
- **View Binding:** Interacción segura con los elementos de la vista sin usar `findViewById`.
- **Material Design 3:** Implementación de `MaterialCardView`, elevaciones y tipografía moderna.
- **Manejo de Errores:** Sistema de respaldo que carga datos locales si el servidor no responde o hay problemas de internet.
- **Carga Dinámica de Recursos:** Los iconos se asignan dinámicamente según el nombre recibido desde el JSON.

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **Arquitectura:** Fragment-based UI logic.
- **Networking:** [Retrofit 2](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
- **UI Components:** RecyclerView, ConstraintLayout, Material Components.
- **Gestión de Imágenes:** Acceso dinámico a recursos `drawable`.

## 📦 Estructura del Proyecto
- `MainActivity.kt`: Punto de entrada que aloja el fragmento principal.
- `TramitesFragment.kt`: Lógica de obtención de datos y configuración de la lista.
- `TramiteAdapter.kt`: Adaptador personalizado para renderizar cada ítem de la lista.
- `TramiteService.kt`: Interface que define los endpoints de la API.
- `Tramite.kt`: Modelos de datos (POJOs) para el mapeo del JSON.

## ⚙️ Configuración e Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/Examen_T2_01.git
   ```
2. Abrir el proyecto en **Android Studio (Ladybug o superior)**.
3. Sincronizar los archivos de Gradle.
4. Asegurarse de tener conexión a internet (para la API).
5. Ejecutar en un emulador o dispositivo físico con Android 7.0 (API 24) o superior.

## 🌐 API Reference
El proyecto consume un endpoint de Postman Mock:
`GET https://05d0a0f6-fc0b-4105-96f7-748e7a92e611.mock.pstmn.io/configs`

**Ejemplo de Respuesta:**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "titulo": "Alertas y notificación",
      "subtitulo": "",
      "imagen": "ic_registro"
    }
  ]
}
```

---
**Autor:** [Tu Nombre/Usuario]  
**Institución:** CIBERTEC  
**Curso:** Desarrollo de Aplicaciones Móviles I