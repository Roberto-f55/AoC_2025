# Advent of Code - Día 10: Sincronización de Máquinas

Esta es mi solución para el desafío del **Día 10**. El objetivo es configurar una serie de máquinas complejas, primero alineando sus luces mediante interruptores y, posteriormente, calculando la secuencia de voltajes necesaria para activarlas.

## El Problema

El desafío se divide en dos fases de reconstrucción de estados:

### Parte 1: Alineación de Luces
Se debe encontrar la combinación de botones que encienda los indicadores correctos.
* **Mecánica:** Cada botón invierte el estado (toggle) de un conjunto específico de luces. Se utiliza aritmética binaria (`XOR`) para determinar la configuración mínima de botones.

### Parte 2: Cálculo de Voltaje Recursivo
Se busca el coste mínimo para llevar los voltajes de las máquinas a cero.
* **Mecánica:** Es un problema de recursividad inversa. Dado que los voltajes crecen exponencialmente, la solución fue calcular desde el objetivo hacia atrás, aplicando divisiones a los voltajes pares.

---

## Fundamentos de Diseño

La arquitectura se ha estructurado siguiendo los conceptos básicos de la ingeniería del software para garantizar la calidad y mantenibilidad del sistema.

* **Alta Cohesión**
  Se busca que las partes de un módulo estén estrechamente relacionadas y enfocadas en una única tarea. En esta solución, cada clase tiene una responsabilidad única y clara:
    * **`MachineParser`**: Se dedica exclusivamente a la interpretación y limpieza de las cadenas de texto del input.
    * **`FactorySolver`**: Contiene puramente la lógica algorítmica y matemática para resolver ambas partes del problema.
    * **`BitUtils`**: Agrupa operaciones genéricas de bajo nivel sobre bits, independientes de las reglas de negocio.
    * **`Machine`**: Actúa como un contenedor de datos puro e inmutable (Record), manteniendo el estado de la máquina sin contener lógica compleja.
    * **`Button`**: Representa únicamente la configuración física y la máscara de bits de un interruptor.
    * **`Indicator`**: Encapsula el estado de las luces y define las operaciones válidas (toggle) sobre ellas.
    * **`MachineManager`**: Se encarga exclusivamente de la orquestación, coordinando la creación de las máquinas y la ejecución secuencial de los algoritmos.

* **Bajo Acoplamiento**
  El diseño promueve módulos o componentes que tienen pocas interdependencias.
    * El record `Machine` actúa como un contenedor de datos puro y no tiene conocimiento de cómo se resolverán sus problemas (lógica que reside en `FactorySolver`). Esto facilita la modificación de los algoritmos sin afectar a la estructura de datos.

* **Abstracción**
  La abstracción es fundamental para manejar la complejidad, ocultando los detalles complejos detrás de una interfaz simple.
    * La clase MachineManager actúa como una fachada que abstrae todo el proceso de resolución. Ofrece al cliente métodos directos como solveA() o solveB(), ocultando completamente la existencia, instanciación y complejidad matemática del motor de cálculo (FactorySolver). Esto permite usar el sistema sin necesitar "desentrañar la compleja red de operaciones" internas.

* **Código Expresivo**
  El código debe ser intuitivo, permitiendo comprender rápidamente su funcionamiento sin dedicar tiempo excesivo al análisis.
    * Se han utilizado nombres descriptivos como `voltageDrop` o `applyAndDivide` para hacer el código autoexplicativo, evitando la necesidad de comentarios excesivos explicativos.

---

## Principios de Diseño

Se han aplicado reglas que orientan cómo aplicar los fundamentos en la práctica:

* **Principio de Responsabilidad Única (SRP)**
  Cada clase debe tener una sola razón para cambiar, reflejando la alta cohesión mencionada anteriormente.
    * Las funciones y clases son pequeñas. Por ejemplo, se extrajo la lógica de lectura a `MachineParser` para que `Machine` sea solo una estructura de datos limpia.

* **Principio DRY (Don't Repeat Yourself)**
  Cada pieza de conocimiento en un software debería tener una representación única inequívoca.
    * El método `findAllLightSolutions` se implementa una sola vez en `FactorySolver` y es reutilizado tanto para la resolución de la Parte A como para encontrar máscaras de paridad en la Parte B.

* **Principio de Mínima Sorpresa**
  Las abstracciones deben comportarse de manera predecible, sin efectos secundarios inesperados.
    * El uso de métodos estáticos de creación como `MachineManager.create()` o `Machine.from()` sigue convenciones estándares de Java, permitiendo a otros desarrolladores intuir su uso inmediatamente.

* **Principio YAGNI (You Aren't Gonna Need It)**
  Se aconseja no añadir funcionalidad hasta que sea realmente necesaria.
    * Se deben evitar soluciones excesivamente complejas cuando hay alternativas más simples. Por ello, la caché implementada en `solveB` es un `HashMap` estándar (un diccionario), evitando la sobreingeniería de sistemas de caché externos.

---

## Patrones de Diseño

Se han utilizado soluciones típicas a problemas comunes en el diseño de software:

* **Patrón Factory Method**
  En lugar de usar directamente el constructor para crear objetos complejos, se llama a un método estático que encapsula la creación.
    * **Implementación:** `MachineParser.parse(line)` y su puente `Machine.from(line)`. Esto centraliza la lógica de creación y limpieza de datos (como el parsing de voltajes) fuera de la clase principal, permitiendo cambiar el formato de entrada sin tocar la definición de la máquina.

* **Value Objects (Records)**
  El uso de `record` para `Machine`, `Button` e `Indicator` refuerza el principio de inmutabilidad. Su igualdad se basa en su valor y no en su identidad, lo que simplifica el razonamiento sobre el estado del programa y evita efectos secundarios no deseados durante la recursividad.

---

## Lógica de Resolución

### Parte A: Fuerza Bruta Optimizada (Bitmask)
El `FactorySolver` utiliza máscaras de bits (`long`) para representar el estado de las luces.
1.  Se genera un espacio de búsqueda de $2^N$ combinaciones.
2.  Se aplica `XOR` para simular las pulsaciones.
3.  Se selecciona la solución con el menor número de bits activos (`Long.bitCount`).

### Parte B: Ingeniería Inversa Recursiva
Debido a la magnitud de los números, no se puede simular hacia adelante.
1.  **Paridad:** Se calcula qué botones son necesarios para corregir la paridad (impar/par) de los voltajes actuales (`findAllPairParity`).
2.  **Reducción:** Se calcula la "caída de voltaje" (`voltageDrop`) producida por esos botones, es decir, cuantas veces fue modificado.
3.  **Recursión:** Se aplica la fórmula `(voltaje - voltageDrop) / 2` y se llama recursivamente.
4.  **Memoización:** Se utiliza un `Map` para almacenar los estados de voltaje ya resueltos y podar ramas del árbol.
