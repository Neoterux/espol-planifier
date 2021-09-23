# Espol planifier

Este proyecto busca agilizar (al menos personalmente) la forma de 
planificar los horarios de nuevos semestres.
<br/>
Hace uso de `Gson` para poder parsear los horarios y trabajar con ellos.

### Formato JSON
El formato JSON a parsear tiene el siguiente formato:<br/>
```
{
    "subject": "Nombre Materia",
    "code": "codigo materia",
    "schedules": [
    // aquí se almacenan todos los posibles horarios de una materia en específico
        {
            "id" : "número del paralelo",
            // class-no va a servir para identificar a este horario
            "class-no": "nombre de la clase ej. A107",
            // Optcional, por defecto es ""
            "teacher-name": "nombre profesor",
            // aquí se almacena la distribución de horas en una semana normal
            "daily-schedule": [
                {
                    "day": "Dia",
                    "init-hour": "Hora incio de clase",
                    "end-hour": "Hora fin de clase"
                }, ...
            ],
            "exam-schedule": {
                "day": "Día",
                "init-hour": "09:00",
                "end-hour": "10:00"
            }
        },
        ...
    ] 
}
``` 

También puedes visualizar un archivo de ejemplo: [sample_data.json](/src/test/resources/sample_data.json)

nota: Cada archivo JSON corresponde a una sola materia, por lo que si quieres planificar <br/>
más de una, agrega más archivos JSON al directorio `data`

### Script de ayuda
Para ayudar a crear los JSON tambien se añade un pequeño script para tampermonkey, se encuentra localizado en: <br/>
`tampermonkey-script/`

### Requerimientos
- Java >= 16

### Ejecutable
Se puede crear una imagen del proyecto que sea ejecutable y nativa para poder crearla solo ejecuta el comando:<br/>
`./gradlew jpackage`, si estás en un sistema operativo Unix, o `gradlew.bat jpackage` si estás en windows.
<br/><p/>
El ejecutable se encontrará en la carpeta `build/image-output/Espol Planifier/bin`.
