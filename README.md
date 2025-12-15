# Pastelería Backend

API REST para la gestión de una pastelería, desarrollada con Spring Boot.

## 1. Cómo Ejecutar la Aplicación

Para iniciar el servidor, ejecuta el siguiente comando en la raíz del proyecto:

```bash
# En Windows
./mvnw.cmd spring-boot:run

# En MacOS/Linux
./mvnw spring-boot:run
```


Una vez ejecutado, la aplicación estará disponible en `http://localhost:8080`.

## 2. Herramientas de Desarrollo

### Swagger UI (Documentación y Pruebas de API)

Para explorar y probar los endpoints de la API de forma interactiva.

- **URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Consola H2 (Base de Datos)

Para interactuar directamente con la base de datos en memoria.

- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**Credenciales de Conexión:**
- **JDBC URL:** `jdbc:h2:mem:pasteleria_db`
- **User Name:** `sa`
- **Password:** (dejar en blanco)

> **Nota:** Debes usar la URL de JDBC exacta para conectarte a la base de datos correcta.

## 3. Ejemplos de Pruebas con Swagger

A continuación se muestran ejemplos para probar las operaciones CRUD sobre los productos. En cada ejemplo, recuerda hacer clic en **"Try it out"** para activar los campos.

### Crear un Nuevo Producto (POST)

1.  Expande el endpoint `POST /api/productos`.
2.  En el campo `Request body`, pega el siguiente JSON:
    ```json
    {
      "nombre": "Cheesecake de Frambuesa",
      "descripcion": "Cremoso cheesecake con una capa de mermelada de frambuesa casera.",
      "precio": 6000,
      "stock": 15,
      "categoria": "Individual"
    }
    ```
3.  Haz clic en **"Execute"**. Deberías recibir una respuesta `201 Created`. Anota el `id` del producto creado para usarlo en los siguientes pasos.

### Obtener un Producto por su ID (GET)

1.  Expande el endpoint `GET /api/productos/{id}`.
2.  En el campo `id`, introduce el ID del producto que creaste (ej. `17`).
3.  Haz clic en **"Execute"**. Deberías recibir una respuesta `200 OK` con los datos del producto.

### Actualizar un Producto (PUT)

1.  Expande el endpoint `PUT /api/productos/{id}`.
2.  Introduce el `id` del producto a actualizar.
3.  En el `Request body`, pega el siguiente JSON para modificar su stock y precio:
    ```json
    {
      "nombre": "Cheesecake de Frambuesa",
      "descripcion": "Cremoso cheesecake con una capa de mermelada de frambuesa casera.",
      "precio": 6500,
      "stock": 12,
      "categoria": "Individual"
    }
    ```
4.  Haz clic en **"Execute"**. Deberías recibir una respuesta `200 OK`.

### Eliminar un Producto (DELETE)

1.  Expande el endpoint `DELETE /api/productos/{id}`.
2.  Introduce el `id` del producto a eliminar.
3.  Haz clic en **"Execute"**. Deberías recibir una respuesta `204 No Content`.

## 4. Funcionamiento de la Base de Datos (Importante)

La aplicación está configurada para un entorno de desarrollo.

- **La base de datos se borra y se recrea en cada reinicio** (debido a la propiedad `spring.jpa.hibernate.ddl-auto=create-drop`).
- Después de crearse, se puebla con los datos iniciales del archivo `src/main/resources/data.sql`.

Esto significa que cualquier cambio que hagas (crear, modificar, eliminar productos) se perderá cuando detengas y vuelvas a iniciar la aplicación. Este comportamiento es intencional para asegurar un estado limpio y predecible durante el desarrollo y las pruebas.
