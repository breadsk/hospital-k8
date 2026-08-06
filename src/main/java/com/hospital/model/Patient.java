package com.hospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rut", nullable = false, unique = true, length = 10)
    @Pattern(regexp = "^[0-9]{7,8}-[0-9kK]{1}$", message = "RUT debe tener el formato: 12345678-9")
    private String rut;

    @Transient // No debe ser persistido en la base de datos.
    private String dv;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(nullable = false, unique = true, length = 255)
    @Email(message = "Email debe tener un formato válido")
    @NotBlank(message = "Email no puede estar vacío")
    private String email;

}
