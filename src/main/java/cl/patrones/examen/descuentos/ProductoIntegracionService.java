package cl.patrones.examen.descuentos;

import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.descuentos.FerresinDescuentoDecorator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoIntegracionService {

    public List<Producto> aplicarDescuentos(List<Producto> productosOriginales) {

        boolean esEmpleado = false;

        // Recuperar objeto UserDetails en Spring Security
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails usuario = (UserDetails) principal;

            // Verificar si el usuario autenticado tiene el rol de "empleado"
            esEmpleado = usuario.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_EMPLEADO"));
        }

        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        final boolean empleadoFinal = esEmpleado;

        // Envolver cada producto base con el Decorador
        return productosOriginales.stream()
                .map(productoBase -> new FerresinDescuentoDecorator(productoBase, empleadoFinal, diaActual))
                .collect(Collectors.toList());
    }
}