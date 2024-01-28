package org.example.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    StudentService studentService;

    @GetMapping
    public String getStudentsPage(Model model) {
        model.addAttribute("students", studentService.getStudentsList());
        return "students";
    }

    @GetMapping("/create")
    public String createStudentPage(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }

    @GetMapping("/update/{id}")
    public String updateStudentPage(Model model, @PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "update_student";
    }

    @PostMapping
    public String createStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create_student";
        }
        studentService.createStudent(student);
        return "redirect:/students";
    }

    @PostMapping("/{id}")
    public String updateStudent(@ModelAttribute("student") Student student, @PathVariable Long id) {
        studentService.updateStudent(student, id);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
