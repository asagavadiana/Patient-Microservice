package org.example.patientmacroservice;

import org.example.patientmacroservice.model.Patient;
import org.example.patientmacroservice.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static graphql.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class deletePatientShouldReturnOkStatusAndRemovePatient {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PatientService patientService;

	@Test
	public void testDeletePatient() throws Exception {
		// Создание тестового пациента
		Patient testPatient = new Patient();
		testPatient.setId("somePatientId");
		testPatient.setName("John Doe");
		patientService.addPatient(testPatient);

		// Удаление пациента
		mockMvc.perform(delete("/api/patients/{id}", testPatient.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// Проверка, что пациент был удален
		assertFalse(patientService.getPatientById(testPatient.getId()).isPresent());
	}
}