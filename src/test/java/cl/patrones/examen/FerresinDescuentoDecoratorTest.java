package cl.patrones.examen;

import cl.patrones.examen.descuentos.FerresinDescuentoDecorator;
import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FerresinDescuentoDecoratorTest {

    private Producto productoMock;
    private Categoria categoriaMock;

    @BeforeEach
    void setUp() {
        // Preparación de los mocks (Aislamiento de dependencias)
        productoMock = mock(Producto.class);
        categoriaMock = mock(Categoria.class);

        when(productoMock.getCategoria()).thenReturn(categoriaMock);
        // Se fija un precio base de 100.000 para facilitar el cálculo y aserción de porcentajes
        when(productoMock.getPrecioLista()).thenReturn(100000L);
    }

    // Partición de Equivalencia: Lunes + Compresores (6%) vs Cliente Normal (0%)
    // Límite: Se espera que el sistema elija 6000L por ser el límite superior.
    @Test
    void testDescuentoLunesCompresor_AplicaSeisPorciento() {
        when(categoriaMock.getNombre()).thenReturn("Compresores");

        Producto productoDecorado = new FerresinDescuentoDecorator(productoMock, false, DayOfWeek.MONDAY);

        assertEquals(6000L, productoDecorado.getDescuento());
        assertEquals(94000L, productoDecorado.getPrecioFinal());
    }

    // Partición de Equivalencia: Miércoles + Taladros (10%) superpuesto con rol Empleado (5%)
    // Límite: El sistema debe discriminar la colisión de reglas y elegir el 10% (10000L).
    @Test
    void testDescuentoMiercolesTaladro_AplicaDiezPorciento() {
        when(categoriaMock.getNombre()).thenReturn("Taladros");

        Producto productoDecorado = new FerresinDescuentoDecorator(productoMock, true, DayOfWeek.WEDNESDAY);

        assertEquals(10000L, productoDecorado.getDescuento());
    }

    // Partición de Equivalencia: Empleado en un día sin promoción coincidente para su categoría
    // Límite: La evaluación dinámica falla, por lo que actúa el límite de empleado (5% fijo).
    @Test
    void testDescuentoEmpleado_AplicaCincoPorcientoFijo() {
        when(categoriaMock.getNombre()).thenReturn("Esmeriles");

        // Es lunes, la promoción de lunes es exclusiva de compresores. Aplica el 5% de empleado.
        Producto productoDecorado = new FerresinDescuentoDecorator(productoMock, true, DayOfWeek.MONDAY);

        assertEquals(5000L, productoDecorado.getDescuento());
    }

    // Partición de Equivalencia: Cliente Normal en un día sin promociones activas (Viernes)
    // Límite: Límite inferior absoluto. El descuento debe ser 0 y el precio final igual al de lista.
    @Test
    void testClienteNormalSinPromocion_DescuentoCero() {
        when(categoriaMock.getNombre()).thenReturn("Taladros");

        Producto productoDecorado = new FerresinDescuentoDecorator(productoMock, false, DayOfWeek.FRIDAY);

        assertEquals(0L, productoDecorado.getDescuento());
        assertEquals(100000L, productoDecorado.getPrecioFinal());
    }
}