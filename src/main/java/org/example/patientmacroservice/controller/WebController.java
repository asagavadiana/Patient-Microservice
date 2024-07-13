package org.example.patientmacroservice.controller;

import org.example.patientmacroservice.model.Patient;
import org.example.patientmacroservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/patients")
public class WebController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public String getAllPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    @GetMapping("/add")
    public String addPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patientForm";
    }

    @PostMapping("/add")
    public String addPatient(@ModelAttribute Patient patient) {
        patientService.addPatient(patient);
        return "redirect:/web/patients";
    }

    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable String id, Model model) {
        patientService.getPatientById(id).ifPresent(patient -> model.addAttribute("patient", patient));
        return "patientForm";
    }

    @PostMapping("/edit/{id}")
    public String updatePatient(@PathVariable String id, @ModelAttribute Patient patient) {
        patientService.updatePatient(id, patient);
        return "redirect:/web/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return "redirect:/web/patients";
    }
}
