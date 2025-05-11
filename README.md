# Quasar Operation

## Descripción General
Quasar Operation es un sistema de comunicación y localización basado en satélites, diseñado para recibir, decodificar y procesar mensajes de una red de satélites con el fin de determinar la ubicación de naves espaciales y su contenido de mensajes.

## Características Principales
- Recepción y decodificación de mensajes de múltiples satélites
- Triangulación de posición basada en la distancia a los satélites
- Procesamiento de mensajes completos y parciales/fragmentados
- API REST para la recepción de datos y entrega de resultados
- Almacenamiento temporal de datos de satélites

## Requisitos Previos
- Java 21 o superior
- Maven 3.6.x o superior
- Spring Boot 2.5.x o superior

## Instalación

### Clonar el repositorio
```bash
git clone https://github.com/SalVal/quasar-operation-meli.git
cd quasar-operation
```

### Compilar el proyecto
```bash
mvn clean install
```

### Ejecutar la aplicación
```bash
java -jar target/quasar-operation-1.0.0.jar
```

O usando Maven:
```bash
mvn spring-boot:run
```

## Uso de la API

### Endpoint Top Secret
Recibe información de todos los satélites para calcular la posición y decodificar el mensaje.

**POST /api/topsecret**
```json
{
  "satellites": [
    {
      "name": "kenobi",
      "distance": 100.0,
      "message": ["este", "", "", "mensaje", ""]
    },
    {
      "name": "skywalker",
      "distance": 115.5,
      "message": ["", "es", "", "", "secreto"]
    },
    {
      "name": "sato",
      "distance": 142.7,
      "message": ["este", "", "un", "", ""]
    }
  ]
}
```

**Respuesta**
```json
{
  "position": {
    "x": -487.2859125,
    "y": 1557.014225
  },
  "message": "este es un mensaje secreto"
}
```

### Endpoint Top Secret Split
Permite enviar información de los satélites de forma individual.

**POST /api/topsecret_split/{satellite_name}**
```json
{
  "distance": 100.0,
  "message": ["este", "", "", "mensaje", ""]
}
```

**GET /api/topsecret_split**
Obtiene la posición de la nave y el mensaje si se tienen suficientes datos.

## Configuración

La configuración de la aplicación se encuentra en el archivo `application.properties`:

```properties
# Configuración del servidor
server.port=8080

# Posiciones conocidas de los satélites (ejemplo)
satellite.kenobi.position.x=-500
satellite.kenobi.position.y=-200
satellite.skywalker.position.x=100
satellite.skywalker.position.y=-100
satellite.sato.position.x=500
satellite.sato.position.y=100
```

## Contribución
1. Haz un fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-caracteristica`)
3. Haz commit de tus cambios (`git commit -am 'Añadir nueva característica'`)
4. Haz push a la rama (`git push origin feature/nueva-caracteristica`)
5. Crea un nuevo Pull Request
