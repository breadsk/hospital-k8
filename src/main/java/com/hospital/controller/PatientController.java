package com.hospital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.hospital.dto.ApiResponseDTO;
import com.hospital.model.Patient;
import com.hospital.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "Patients", description = "Operaciones relacionadas con los pacientes")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    @Operation(summary = "Obtener lista de pacientes", description = "Obtiene una lista de todos los pacientes registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacientes obtenidos satisfactoriamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron pacientes en el sistema")
    })

    public ResponseEntity<ApiResponseDTO<List<Patient>>> list() {

        List<Patient> patients = patientService.findAll();

        if (patients.isEmpty()) {
            ApiResponseDTO<List<Patient>> response = new ApiResponseDTO<>(
                    false,
                    HttpStatus.NO_CONTENT.value(),
                    "No se encontraron pacientes en el sistema",
                    null,
                    0L);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }

        ApiResponseDTO<List<Patient>> response = new ApiResponseDTO<>(
                true,
                HttpStatus.OK.value(),
                "Pacientes obtenidos satisfactoriamente",
                patients,
                (long) patients.size());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<Patient>> save(@RequestBody Patient patient) {

        System.out.println("Esta pasando por aqui!!!!");
        try {

            Patient newPatient = patientService.save(patient);

            ApiResponseDTO<Patient> response = new ApiResponseDTO<>(
                    true,
                    HttpStatus.CREATED.value(),
                    "Paciente guardado satisfactoriamente",
                    newPatient,
                    1L);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            e.printStackTrace();

            ApiResponseDTO<Patient> response = new ApiResponseDTO<>(
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error: " + e.getMessage(),
                    null,
                    0L);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Patient>> update(@PathVariable Long id, @RequestBody Patient patientData) {

        try {

            // 1. buscar el paciente que exista en la bd
            Patient existingPatient = patientService.findById(id);

            // 2. Actualizar los ca mpos del paciente existente con los nuevos datos
            existingPatient.setRut(patientData.getRut());
            existingPatient.setFirstName(patientData.getFirstName());
            existingPatient.setLastName(patientData.getLastName());
            existingPatient.setBirthDate(patientData.getBirthDate());
            existingPatient.setEmail(patientData.getEmail());

            // 3. Guuardar ( actualizar ) el paciente existente
            Patient updPatient = patientService.save(existingPatient);

            ApiResponseDTO<Patient> response = new ApiResponseDTO<>(
                    true,
                    HttpStatus.OK.value(),
                    "Paciente actualizado satisfactoriamente",
                    updPatient,
                    1L);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("Error actualizando paciente: " + ex.getMessage());
            ApiResponseDTO<Patient> response = new ApiResponseDTO<>(
                    false,
                    HttpStatus.NOT_FOUND.value(),
                    "Paciente no encontrado",
                    null,
                    0L);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

}
