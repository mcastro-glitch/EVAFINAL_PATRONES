package cl.patrones.examen.descuentos;

import cl.patrones.examen.productos.domain.Producto;
import java.time.DayOfWeek;

public class FerresinDescuentoDecorator extends ProductoDecorator {

    // ¡Variables marcadas como final gracias al consejo del IDE!
    private final boolean esEmpleado;
    private final DayOfWeek diaActual;

    public FerresinDescuentoDecorator(Producto productoBase, boolean esEmpleado, DayOfWeek diaActual) {
        super(productoBase);
        this.esEmpleado = esEmpleado;
        this.diaActual = diaActual;
    }

    @Override
    public Long getDescuento() {
        long descuentoMaximo = 0;
        long precioLista = productoBase.getPrecioLista();
        String nombreCategoria = productoBase.getCategoria().getNombre();

        // Evaluar descuento fijo Empleado (5%)
        if (esEmpleado) {
            descuentoMaximo = Math.max(descuentoMaximo, Math.round(precioLista * 0.05));
        }

        // Evaluar descuentos dinámicos por Día y Categoría
        if (diaActual == DayOfWeek.MONDAY && nombreCategoria.equalsIgnoreCase("Compresores")) {
            descuentoMaximo = Math.max(descuentoMaximo, Math.round(precioLista * 0.06));

        } else if (diaActual == DayOfWeek.TUESDAY && nombreCategoria.equalsIgnoreCase("Esmeriles")) {
            descuentoMaximo = Math.max(descuentoMaximo, Math.round(precioLista * 0.08));

        } else if (diaActual == DayOfWeek.WEDNESDAY && nombreCategoria.equalsIgnoreCase("Taladros")) {
            descuentoMaximo = Math.max(descuentoMaximo, Math.round(precioLista * 0.10));
        }

        return descuentoMaximo;
    }

    @Override
    public Long getPrecioFinal() {
        long precioCalculado = productoBase.getPrecioLista() - getDescuento();
        return Math.max(0, precioCalculado); // Límite para evitar precios negativos
    }
}