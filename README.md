# Primera Práctica

## Resolución con semáforos

### Problema a resolver

Se tiene un sistema con un número indeterminado de procesos productores y consumidores
donde la operación de consumir es más lenta que la de producir, y se trata de que los consumidores
no retarden la ejecución de los productores. Además, se tienen algunas características particulares:

- Hay dos clases de datos, que llamaremos **A** y **B**.
- Hay 2 clases de productores, los que solo producen datos de clase **A** y los que solo
producen datos de clase **B**.
- Hay 3 tipos de consumidores:
    - Los que solo consumen datos de clase **A**.
    - Los que solo consumen datos de clase **B**.
    - Los que consumen datos de clase **A** o **B** indistintamente.

Los productores generan datos en rachas de 1 a 3, que deben ser almacenados “de golpe”.
Se tiene capacidad para almacenar **NA** y **NB** datos de clase **A** o **B**, respectivamente.

La lectura de los datos por parte de los consumidores debe hacerse respetando el orden en
que han sido depositados por los productores

### Proceso general:

Los elementos a tener en cuenta son:
- Los **Datos** tienen un id y tipoDato, que será **A** o **B**.
- Los **Productores** de datos crean entre 1 y 3 datos en cada producción y los ponen a disposición de los Consumidores. Un productor tarda en producir un dato un tiempo aleatorio entre MIN_TIEMPO_PRODUCCIÓN y MAX_TIEMPO_PRODUCCIÓN. Los datos se consumen en el orden en que fueron depositados en el buffer, según su tipo.
- Los **Consumidores** de datos toman un dato de los que hay disponibles y los procesan en un tiempo MIN_TIEMPO_CONSUMO + VARIACIÓN_TIEMPO. Hay 3 tipos de **Consumidores**: los que consumen datos de tipo **A**, los que consumen datos de tipo **B** y los que consumen de ambos.
