package org.example.patientmacroservice;

import org.example.patientmacroservice.controller.PatientController;
import org.example.patientmacroservice.model.Patient;
import org.example.patientmacroservice.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PatientControllerTest {

	private MockMvc mockMvc;

	@Mock
	private PatientService patientService;

	@InjectMocks
	private PatientController patientController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
	}

	@Test
	void testGetAllPatients() throws Exception {
		when(patientService.getAllPatients()).thenReturn(Collections.singletonList(new Patient()));

		mockMvc.perform(get("/api/patients"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").exists());

		verify(patientService, times(1)).getAllPatients();
	}

	@Test
	void testGetPatientById() throws Exception {
		Patient patient = new Patient();
		patient.setId("1");
		when(patientService.getPatientById("1")).thenReturn(Optional.of(patient));

		mockMvc.perform(get("/api/patients/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"));

		verify(patientService, times(1)).getPatientById("1");
	}

	@Test
	void testGetPatientByIdNotFound() throws Exception {
		when(patientService.getPatientById("1")).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/patients/1"))
				.andExpect(status().isNotFound());

		verify(patientService, times(1)).getPatientById("1");
	}

	@Test
	void testAddPatient() throws Exception {
		Patient patient = new Patient();
		patient.setId("1");
		when(patientService.addPatient(any(Patient.class))).thenReturn(patient);

		mockMvc.perform(post("/api/patients")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"id\":\"1\", \"firstName\":\"John\", \"lastName\":\"Doe\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"));

		verify(patientService, times(1)).addPatient(any(Patient.class));
	}

	@Test
	void testUpdatePatient() throws Exception {
		Patient patient = new Patient();
		patient.setId("1");
		when(patientService.updatePatient(eq("1"), any(Patient.class))).thenReturn(patient);

		mockMvc.perform(put("/api/patients/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"firstName\":\"John\", \"lastName\":\"Doe\"}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"));

		verify(patientService, times(1)).updatePatient(eq("1"), any(Patient.class));
	}

	@Test
	void testDeletePatient() throws Exception {
		doNothing().when(patientService).deletePatient("1");

		mockMvc.perform(delete("/api/patients/1"))
				.andExpect(status().isOk());

		verify(patientService, times(1)).deletePatient("1");
	}
}
