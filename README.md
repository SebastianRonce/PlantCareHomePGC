<div align="center">

# 🌿 Plant Care Home

**Desarrollo de una aplicación móvil para el cuidado de especies vegetales en los hogares de la provincia de Ubaté**

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?logo=jetpackcompose&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-Auth%20%2B%20Firestore-FFCA28?logo=firebase&logoColor=black)
![Android](https://img.shields.io/badge/Android-minSdk%2026-3DDC84?logo=android&logoColor=white)

Proyecto de Grado Complementario (PGC) · Ingeniería de Sistemas y Computación · Universidad de Cundinamarca · 2026-2

</div>

---

## 🎬 Video de demostración

<!-- REEMPLAZAR TU_VIDEO_ID por el ID del video de YouTube (lo que aparece después de v= en la URL) -->
[![Ver el video de Plant Care Home](https://img.youtube.com/vi/Gr5uda_oVp0/hqdefault.jpg)](https://youtube.com/shorts/Gr5uda_oVp0)

> Haz clic en la imagen para ver el recorrido por las interfaces y funcionalidades de la aplicación.

---

## 📋 Tabla de contenido

1. [Contexto y problemática](#-contexto-y-problemática)
2. [Propósito y objetivos](#-propósito-y-objetivos)
3. [Alcance, hipótesis y limitaciones](#-alcance-hipótesis-y-limitaciones)
4. [Funcionalidades y estado actual](#-funcionalidades-y-estado-actual)
5. [Tecnologías y arquitectura](#-tecnologías-y-arquitectura)
6. [Cómo descargar y ejecutar el proyecto](#-cómo-descargar-y-ejecutar-el-proyecto)
7. [Estructura del repositorio](#-estructura-del-repositorio)
8. [Metodología de trabajo](#-metodología-de-trabajo)
9. [Evidencias y enlaces](#-evidencias-y-enlaces)
10. [Equipo](#-equipo)

---

## 🌱 Contexto y problemática

Las plantas aportan beneficios ambientales, estéticos y de bienestar, pero mantenerlas en buen estado exige conocer las necesidades específicas de cada especie: riego, iluminación, sustrato y nutrientes.

En los hogares de la provincia de Ubaté, el mal cuidado de las plantas se debe principalmente al **desconocimiento de esas necesidades**. Esto genera prácticas inadecuadas (exceso o falta de riego, poca luz, sustrato equivocado), **pérdida de plantas** y **desperdicio de agua**.

Resultados de la encuesta diagnóstica aplicada:

| Indicador | Resultado |
|---|---|
| Encuestados que **no tienen** plantas en casa | **17,1 %** |
| De ese grupo, quienes no las tienen **por no saber cuidarlas** | **58,3 %** |
| Encuestados que consideran **útil una app** con información de cuidado | **94,7 %** |

Una herramienta digital accesible puede orientar el cuidado de las plantas y contribuir a los **ODS 12** (Producción y consumo responsables) y **ODS 15** (Vida de ecosistemas terrestres).

**Pregunta de investigación:** ¿Cómo mejorar el cuidado de las plantas ornamentales en los hogares de la provincia de Ubaté mediante una herramienta tecnológica que proporcione información confiable y facilite el seguimiento de sus necesidades?

---

## 🎯 Propósito y objetivos

**Propósito.** Facilitar el acceso a información confiable y organizada sobre el cuidado de plantas ornamentales, reduciendo los errores por desconocimiento de sus necesidades.

### Objetivo general

Desarrollar una aplicación móvil que ayude a los usuarios en la búsqueda y consulta de información sobre el cuidado de plantas en los hogares de la provincia de Ubaté.

### Objetivos específicos (formato SMART)

1. **Definir** la información de cuidado para al menos 40 especies de plantas presentes en los hogares de Ubaté durante el sprint 1, como base de recomendaciones claras para la aplicación.
2. **Construir** la aplicación móvil, integrando el diseño visual con el sistema de datos durante los sprints 1 y 2, de modo que responda a las necesidades del proyecto.
3. **Validar** la facilidad de uso y el correcto funcionamiento de la herramienta al finalizar la fase de desarrollo, mediante pruebas prácticas con al menos 50 personas de Ubaté, para identificar oportunidades de mejora basadas en la experiencia real de los usuarios.

---

## 🔭 Alcance, hipótesis y limitaciones

**Alcance (producto mínimo)**

- Aplicación Android (Kotlin + Firebase).
- Registro e inicio de sesión.
- Base de datos en Firestore (catálogo inicial de 10 plantas).
- Consulta de información de plantas.
- Visualización de recomendaciones de cuidado.
- Gestión de favoritos.

**Hipótesis.** La implementación de Plant Care Home facilitará el acceso a información y recomendaciones para mejorar el riego y el cuidado de las plantas ornamentales, reduciendo los errores por desconocimiento de sus necesidades.

**Limitaciones**

- Desarrollo incompleto (hay funcionalidades en construcción).
- Dependencia de la información disponible sobre las especies.
- Dependencia tecnológica (conexión a internet y servicios de Firebase).
- Compatibilidad limitada a Android.
- Validación con usuarios limitada.

---

## ✨ Funcionalidades y estado actual

| Funcionalidad | Estado |
|---|---|
| Registro de usuarios con validaciones | ✅ Implementado |
| Inicio de sesión (Firebase Authentication) | ✅ Implementado |
| Pantalla de inicio (Splash) y redirección | ✅ Implementado |
| Catálogo de plantas desde Firestore | ✅ Implementado |
| Búsqueda por nombre o código | ✅ Implementado |
| Detalle de cada planta con imagen | ✅ Implementado |
| Plantas favoritas | 🚧 En desarrollo |
| Perfil de usuario (editar nombre y cerrar sesión) | ✅ Implementado |
| Historial de cuidados | 🚧 En desarrollo |
| Calendario de riego y recordatorios | 🚧 En desarrollo |
| Diagnóstico por imagen con IA | 🔮 Trabajo futuro |

---

## 🛠️ Tecnologías y arquitectura

| Componente | Tecnología |
|---|---|
| Lenguaje | Kotlin 2.0.21 |
| Interfaz | Jetpack Compose + Material 3 (BOM 2024.10.01) |
| Arquitectura | MVVM + Repository |
| Navegación | Navigation Compose 2.8.5 |
| Imágenes | Coil 2.7.0 |
| Autenticación | Firebase Authentication |
| Base de datos | Cloud Firestore (Firebase BOM 33.7.0) |
| Asincronía | Kotlin Coroutines 1.9.0 |
| Build | Android Gradle Plugin 8.13.2 |

**Configuración Android:** `compileSdk 35` · `targetSdk 35` · `minSdk 26` · Java 11 · JDK 17 para ejecutar Gradle.

**Modelo de datos en Firestore**

```text
plants/                          → catálogo de plantas
users/{uid}/                     → datos del usuario
users/{uid}/favorites/{plantId}  → favoritas de cada usuario
```

---

## 💻 Cómo descargar y ejecutar el proyecto

### Requisitos previos

| Herramienta | Versión recomendada |
|---|---|
| [Android Studio](https://developer.android.com/studio) | Ladybug 2024.2.1 o superior |
| JDK | 17 (incluido en Android Studio) |
| Android SDK | Plataforma 35 |
| Git (opcional) | Cualquier versión reciente |
| Dispositivo | Emulador o teléfono Android 8.0 (API 26) o superior |

### Paso 1. Descargar el proyecto

**Opción A: clonar con Git**

```bash
git clone https://github.com/SebastianRonce/PlantCareHomePGC.git
cd PlantCareHomePGC
```

**Opción B: descargar el ZIP (sin Git)**

1. En la página del repositorio, pulsar el botón verde **`<> Code`**.
2. Elegir **Download ZIP**.
3. Descomprimir la carpeta en el equipo.

### Paso 2. Configurar Firebase

La aplicación necesita el archivo `google-services.json` para conectarse a Firebase (Authentication y Firestore). Si no se encuentra en la carpeta `app/`, entonces sigan los siguientes pasos.

1. Descargar `google-services.json` desde el enlace de OneDrive entregado junto con el proyecto: **[https://mailunicundiedu-my.sharepoint.com/:f:/r/personal/jronceria_ucundinamarca_edu_co/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fjronceria%5Fucundinamarca%5Fedu%5Fco%2FDocuments%2FAnexos%20PlantCareHome%2Fgoogle%2Dservices%2Ejson&share=cgr4Nu75oSdWRoLWmsltgVu%2DEgUCRpa2nQQzO%5FCZeyIuYxszrw]**.
2. Copiarlo en la carpeta `app/` del proyecto, de modo que quede en `app/google-services.json`.


### Paso 3. Abrir y ejecutar en Android Studio

1. Abrir Android Studio y elegir **File → Open**, seleccionando la carpeta del proyecto.
2. Esperar a que termine la sincronización de Gradle (**Gradle Sync**). La primera vez puede tardar unos minutos porque descarga dependencias.
3. Si Android Studio lo solicita, instalar el **Android SDK 35** desde *Settings → Languages & Frameworks → Android SDK*.
4. Verificar que Gradle use JDK 17: *Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK*.
5. Elegir un dispositivo:
   - **Emulador:** *Device Manager → Create Device* (por ejemplo, Pixel con API 34 o 35).
   - **Teléfono físico:** activar *Opciones de desarrollador* y *Depuración USB*, y conectarlo al equipo.
6. Pulsar **Run ▶** (o `Shift + F10`).

### Alternativa: compilar desde la terminal

**Windows (PowerShell)**

```powershell
.\gradlew.bat app:assembleDebug
```

**macOS / Linux**

```bash
chmod +x gradlew
./gradlew app:assembleDebug
```

El APK de depuración se genera en:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Ese archivo puede copiarse a un teléfono Android e instalarse directamente (es necesario permitir la instalación desde orígenes desconocidos).

### Instalar sin compilar

<!-- Opcional: subir el APK en GitHub → Releases y poner aquí el enlace -->
Para probar la aplicación sin configurar el entorno de desarrollo, se puede descargar el APK desde la sección **[Releases](https://github.com/TU_USUARIO/TU_REPOSITORIO/releases)** e instalarlo en un teléfono Android.

### Uso de la aplicación

1. Abrir la app y pulsar **Regístrate** para crear una cuenta con correo electrónico y contraseña (mínimo 8 caracteres y al menos un símbolo especial).
2. Iniciar sesión.
3. En la pantalla principal, explorar el catálogo, buscar por nombre o código, y abrir el detalle de una planta.
4. Marcar plantas como favoritas y filtrarlas.
5. Desde **Perfil**, editar el nombre de usuario o cerrar sesión.

### Solución de problemas frecuentes

| Problema | Solución |
|---|---|
| `google-services.json is missing` | Verificar que el archivo esté en `app/google-services.json`. |
| Error de sincronización de Gradle | Confirmar JDK 17 y conexión a internet; luego *File → Sync Project with Gradle Files*. |
| `SDK location not found` | Abrir el proyecto desde Android Studio, que genera `local.properties` automáticamente. |
| La lista de plantas aparece vacía | Revisar la conexión a internet y que la colección `plants` tenga datos en Firestore. |
| `.\gradlew.bat` no se reconoce | Ejecutar el comando desde la carpeta raíz del proyecto, donde está el archivo `gradlew.bat`. |

---

## 🗂️ Estructura del repositorio

```text
app/src/main/java/com/example/plantcarehomepgc/
├── data/
│   ├── model/            # Plant.kt, User.kt
│   └── repository/       # Auth, User, Plant y Favorite repositories
├── presentation/
│   ├── auth/             # Login y registro
│   ├── home/             # Catálogo y búsqueda
│   ├── plants/           # Detalle de planta
│   ├── profile/          # Perfil de usuario
│   ├── splash/           # Pantalla de carga
│   └── components/       # Componentes reutilizables
├── ui/theme/             # Colores, tema y tipografía
└── utils/                # Validaciones
```

---

## 🔄 Metodología de trabajo

El proyecto se desarrolla con **Scrum**: iteraciones cortas (sprints), con Product Backlog, Sprint Backlog e incrementos funcionales. El trabajo se organiza en tres épicas (gestión de usuarios, consulta y cuidado, seguimiento del cuidado) y cuatro sprints, con tablero en Trello y cronograma de actividades.

---

## 📎 Evidencias y enlaces

| Evidencia | Enlace |
|---|---|
| 🎬 Video de demostración | [YouTube](https://www.youtube.com/watch?v=TU_VIDEO_ID) |
| 📊 Análisis de la encuesta | [Enlace](ENLACE) |
| 🗓️ Cronograma | [Enlace](ENLACE) |
| 📋 Planeación ágil (User Stories y Story Map) | [Enlace](ENLACE) |
| 📑 Requerimientos y diagramas | [Enlace](ENLACE) |
| 🎨 Mockups en Figma | [Enlace](ENLACE) |
| 📈 Mediciones de rendimiento | [Enlace](ENLACE) |
| 🧪 Informe de usabilidad | [Enlace](ENLACE) |

---

## 👥 Equipo

| Integrante | Contacto |
|---|---|
| Juan Sebastian Ronceria Chiquiza | jronceria@ucundinamarca.edu.co |
| Juan Alejandro Rodriguez Poveda | juanalejandrorodriguez@ucundinamarca.edu.co |

Programa de Ingeniería de Sistemas y Computación · Universidad de Cundinamarca · Ubaté, Cundinamarca · 2026-2
