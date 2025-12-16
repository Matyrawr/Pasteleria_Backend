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

Para interactuar directamente con la base de datos local.

- **URL:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**Credenciales de Conexión (modo archivo):**
- **JDBC URL:** `jdbc:h2:file:./data/pasteleria_db`
- **User Name:** `sa`
- **Password:** (dejar en blanco)

> **Nota:** Usa exactamente la URL indicada arriba para la BD local en archivo.

## 2.1. Ejecutar Tests (Maven)

Este proyecto incluye pruebas unitarias e integraciones con Spring Boot.

- Ejecutar todos los tests:

```bash
# En Windows
./mvnw.cmd test

# En MacOS/Linux
./mvnw test
```

- Ejecutar ciclo completo (compilar + test + verificación):

```bash
# En Windows
./mvnw.cmd verify

# En MacOS/Linux
./mvnw verify
```

- Ejecutar un test específico (por clase):

```bash
# Ejemplos (ajusta el nombre de la clase)
./mvnw.cmd -Dtest=ProductoControllerIntegrationTest test    # En Windows
./mvnw -Dtest=ProductoControllerIntegrationTest test        # En MacOS/Linux
```

Resultados de pruebas:
- `target/surefire-reports/` (unitarias)
- `target/failsafe-reports/` (integración, si aplica)

## 2.2. Acceso como Administrador

La aplicación crea automáticamente un usuario administrador al iniciar.

- Correo: `admin@example.com`
- Contraseña: `admin`

Para obtener un token JWT:

```bash
# Solicitar login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","password":"admin"}'

# Respuesta esperada (ejemplo)
{
  "token": "eyJ...",
  "message": "Inicio de sesión exitoso"
}

# Usar el token en llamadas protegidas (ej. crear producto)
TOKEN=eyJ... # reemplaza por tu token
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" \
  -d '{"nombre":"Torta Prueba","descripcion":"...","precio":10000,"stock":5,"categoria":"Especial"}'
```

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

## 4. Funcionamiento de la Base de Datos

La aplicación usa H2 en modo archivo y mantiene los datos entre reinicios (`spring.jpa.hibernate.ddl-auto=update`).

### 5. Cargar productos del catálogo del frontend en H2
El frontend incluye un catálogo con campos adicionales (`codigo`, `tamano`, `imagen`). El modelo backend usa:
- `nombre`, `descripcion`, `precio` (numérico), `stock` (entero), `categoria`.

Para insertarlos en H2:
1. Inicia el backend y abre la consola H2.
2. Conéctate con `jdbc:h2:file:./data/pasteleria_db`, usuario `sa`.
3. Ejecuta estas sentencias SQL (ajusta `stock` si lo deseas):

```sql
-- Cuadradas
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Torta Cuadrada de Chocolate', 'Deliciosa torta de chocolate con capas de ganache y un toque de avellanas. Personalizable con mensajes especiales.', 45000, 10, 'Cuadrada');
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Torta Cuadrada de Frutas', 'Una mezcla de frutas frescas y crema chantilly sobre un suave bizcocho de vainilla, ideal para celebraciones.', 50000, 10, 'Cuadrada');

-- Circulares
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Torta Circular de Vainilla', 'Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con un glaseado dulce.', 40000, 10, 'Circular');
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Torta Circular de Manjar', 'Torta tradicional chilena con manjar y nueces.', 42000, 10, 'Circular');

-- Individual
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Mousse de Chocolate', 'Postre individual cremoso y suave, hecho con chocolate de alta calidad.', 5000, 50, 'Individual');
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Tiramisú Clásico', 'Postre italiano con capas de café, mascarpone y cacao.', 5500, 50, 'Individual');

-- Sin Azúcar
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Torta Sin Azúcar de Naranja', 'Torta ligera y deliciosa, endulzada naturalmente.', 48000, 8, 'Sin Azúcar');
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Cheesecake Sin Azúcar', 'Suave y cremoso, una opción para disfrutar sin culpa.', 47000, 8, 'Sin Azúcar');

-- Tradicional
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Empanada de Manzana', 'Pastelería tradicional rellena de manzanas especiadas.', 3000, 100, 'Tradicional');
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Tarta de Santiago', 'Tarta hecha con almendras, azúcar y huevos.', 6000, 20, 'Tradicional');

-- Sin Gluten
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Brownie Sin Gluten', 'Rico y denso, perfecto para evitar el gluten.', 4000, 40, 'Sin Gluten');
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Pan Sin Gluten', 'Suave y esponjoso, ideal para acompañar comidas.', 3500, 40, 'Sin Gluten');

-- Vegana
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Torta Vegana de Chocolate', 'Torta húmeda y deliciosa, sin productos de origen animal.', 50000, 6, 'Vegana');
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Galletas Veganas de Avena', 'Crujientes y sabrosas, excelente snack saludable.', 4500, 60, 'Vegana');

-- Especial
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Torta Especial de Cumpleaños', 'Personalizable con decoraciones y mensajes únicos.', 55000, 5, 'Especial');
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES
('Torta Especial de Boda', 'Elegante y deliciosa, centro de atención en bodas.', 60000, 3, 'Especial');
```

Alternativa: vía API (requiere ADMIN)
1. Login en `/api/auth/login` y copia el `token`.
2. Inserta por REST:

```bash
API=http://localhost:8080
TOKEN=eyJ... # tu JWT
curl -X POST "$API/api/productos" \
  -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" \
  -d '{"nombre":"Torta Cuadrada de Chocolate","descripcion":"Deliciosa torta de chocolate","precio":45000,"stock":10,"categoria":"Cuadrada"}'
```
