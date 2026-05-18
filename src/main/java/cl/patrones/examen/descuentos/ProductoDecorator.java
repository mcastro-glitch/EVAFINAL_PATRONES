package cl.patrones.examen.descuentos;

import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;

public abstract class ProductoDecorator implements Producto {
    protected Producto productoBase;

    public ProductoDecorator(Producto productoBase) {
        this.productoBase = productoBase;
    }

    @Override public String getSku() { return productoBase.getSku(); }
    @Override public String getNombre() { return productoBase.getNombre(); }
    @Override public String getImagen() { return productoBase.getImagen(); }
    @Override public Long getCosto() { return productoBase.getCosto(); }
    @Override public Long getPrecioLista() { return productoBase.getPrecioLista(); }
    @Override public Categoria getCategoria() { return productoBase.getCategoria(); }

    @Override public Long getDescuento() { return productoBase.getDescuento(); }
    @Override public Long getPrecioFinal() { return productoBase.getPrecioFinal(); }
}