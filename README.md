Proyecto correspondiente a la evaluación final de **Patrones de Diseño**. Consiste en la extensión de un sistema de comercio electrónico de herramientas (Ferresin), implementando reglas de negocio dinámicas para el cálculo de descuentos sin alterar el código fuente de una librería encapsulada (JAR).

## Arquitectura y Patrón de Diseño

Para dar cumplimiento al principio **Open/Closed** (Abierto a la extensión, cerrado a la modificación), este proyecto implementa el **Patrón Decorator (Estructural)**. 

El decorador (`FerresinDescuentoDecorator`) envuelve la interfaz `Producto` base, interceptando y calculando los descuentos en tiempo de ejecución basándose en:
- El día de la semana.
- La categoría del producto (Compresores, Esmeriles, Taladros).
- El rol del usuario autenticado (Cliente vs. Empleado).

Todo el proceso de envoltura se inyecta a través de un servicio de integración (`ProductoIntegracionService`) conectado directamente al contexto de autenticación de **Spring Security**.

## Pruebas Unitarias

El sistema cuenta con una cobertura de pruebas automatizadas construidas con **JUnit 5** y **Mockito**. El diseño de los casos de prueba se realizó utilizando:
- **Partición de Clases Equivalentes:** Para agrupar usuarios con promociones aplicables vs. sin promociones.
- **Análisis de Límites:** Para asegurar que el sistema siempre escoja el descuento de mayor valor en caso de colisión de reglas, y que los precios nunca caigan por debajo de cero.

## Instalación y Ejecución

1. Clonar este repositorio.
2. Abrir terminal en la raíz del proyecto.
3. Para compilar y ejecutar las **pruebas unitarias**:
   ```bash
   ./gradlew test
Para levantar el servidor web:

Bash
./gradlew bootRun
