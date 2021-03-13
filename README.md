[![logo](https://www.gnu.org/graphics/gplv3-127x51.png)](https://choosealicense.com/licenses/gpl-3.0/)
# Primera Práctica

## Resolución con semáforos

#### Problema a resolver

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

#### Proceso general:

Los elementos a tener en cuenta son:
- Los **Datos** tienen un id y tipoDato, que será **A** o **B**.
- Los **Productores** de datos crean entre 1 y 3 datos en cada producción y los ponen a disposición de los Consumidores. Un productor tarda en producir un dato un tiempo aleatorio entre MIN_TIEMPO_PRODUCCIÓN y MAX_TIEMPO_PRODUCCIÓN. Los datos se consumen en el orden en que fueron depositados en el buffer, según su tipo.
- Los **Consumidores** de datos toman un dato de los que hay disponibles y los procesan en un tiempo MIN_TIEMPO_CONSUMO + VARIACIÓN_TIEMPO. Hay 3 tipos de **Consumidores**: los que consumen datos de tipo **A**, los que consumen datos de tipo **B** y los que consumen de ambos.
- Se construirán **NUM_PRODUCTORES** Productores de datos.
- Se construirán **NUM_CONSUMIDORES** Consumidores de datos.
- Cada productor de datos construirá un número aleatorio de datos.
- Cuando los Productores han generado todos sus datos, terminan su ejecución.
- Cuando no quedan datos en la lista y los Productores han terminado su ejecución, los Consumidores interrumpirán su ejecución.

### Análisis del problema

Estructuras de datos, variables compartidas y procedimientos necesarios.

#### Variables compartidas

- `buffer: Cola<Datos>`: buffer de datos de tipos A y B.
- `indexInsert: Cola<Registro>`: buffer de registros.
- `numA: integer` Número de datos de tipo A.
- `numB: integer` Número de datos de tipo B.
- `MAX_DATOS: integer` Número máximo de datos.
- `esperaHueco: Semáforo` Semáforo para impedir que se añadan datos a la cola si está llena.
- `esperaDato: Semáforo` Semáforo para controlar que no se saque ningún dato de la cola de datos si está vacía.
- `mutexBuffer: Mutex` Mutex para controlar que la escritura/lectura en la cola de datos se haga en exclusión mútua.
- `mutexMain: Mutex` Mutex para impedir que los consumidores accedan a la vez a la cola de datos.

#### Tipos de Datos
Tipos de datos abstractos para una correcta implementación posterior.
- TAD Dato. 
    - Campos:
        - `idDato: integer`.
        - `tipoDato: char` (A o B).
- TAD Registro. 
    - Campos:
        - `tipoDato: char` (A o B).
        - `numRafaga: integer`.
    - Operaciones:
        - `Registro()`. Crea un Registro.
- TAD Productor.
    - Campos:
        - `idProductor: integer` Identificador del Productor:
        - `listaDatos: array<Datos>` Lista con la ráfaga de Datos.
    - Operaciones:
        - `producir()` Genera los Datos.
        - `generarRáfaga()` Genera una ráfaga de Datos.
- TAD Consumidor.
    - Campos:
        - `idConsumidor: integer` Identificador del Consumidor.
        - `tipo: Char` Tipo de Datos que consume.
        - `registro: Registro` Siguiente registro disponible.
    - Operaciones:
        - `consumir()` Lee y procesa Datos. Ejecución.
- TAD Hilo Sistema.
    - Datos Compartidos:
        - `Buffer: cola<Datos>` Buffer de Datos de tipo A/B.
        - `indexRegistros: cola<Registro>` Buffer de Registro.
        - `contA: integer`, `contB: integer`, `MAX_DATOS: integer`.
        - `esperaHueco: semáforo`.
        - `esperaDato: semáforo`.
        - `mutexBuffer: semáforo`.
        - `mutexMain: semáforo`.
    - Operaciones:
        - `ejecutaProductores()` Crea los Productores del Sistema.
        - `ejecutaConsumidores()` Crea los Consumidores del Sistema.
        - `esperaFin()` Espera final de ejecución de los procesos.
        - `finalizaProductores()` Finaliza los Productores del Sistema.
        - `finalizaConsumidores()` Finaliza los Consumidores del Sistema.
