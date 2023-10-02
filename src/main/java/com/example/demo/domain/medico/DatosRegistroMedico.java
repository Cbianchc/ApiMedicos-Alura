package com.example.demo.domain.medico;


import com.example.demo.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroMedico(
        @NotBlank //Hace lo mismo que notNull pero tambien blank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank //se le puede poner un Pattern pero no se que le pasa...
        String telefono,
        @NotBlank
        //@Pattern(regexp = "\\d(4,6)") //Esto es asi por que pintó, el el patron que tiene que esperar
        String documento,
        @NotNull //Especialidad es un ENUM este es not null
        Especialidad especialidad,
        @NotNull //en este caso es null por que es objeto, no va a llegar null
        @Valid
        DatosDireccion direccion) {


}
