package com.antigravity.learningplatform.controller;

import com.antigravity.learningplatform.entity.Course;
import com.antigravity.learningplatform.entity.Module;
import com.antigravity.learningplatform.entity.User;
import com.antigravity.learningplatform.repository.CourseRepository;
import com.antigravity.learningplatform.repository.ModuleRepository;
import com.antigravity.learningplatform.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;

    public TeacherController(CourseRepository courseRepository, UserRepository userRepository,
            ModuleRepository moduleRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User teacher = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Course> courses = courseRepository.findByInstructor(teacher);
        model.addAttribute("courses", courses);
        model.addAttribute("username", teacher.getUsername());
        return "teacher/dashboard";
    }

    @GetMapping("/course/create")
    public String createCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "teacher/create_course";
    }

    @PostMapping("/course/create")
    public String createCourse(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute Course course) {
        User teacher = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        course.setInstructor(teacher);
        courseRepository.save(course);
        return "redirect:/teacher/dashboard";
    }

    @GetMapping("/course/{id}/module/add")
    public String addModuleForm(@PathVariable Long id, Model model) {
        Course course = courseRepository.findById(id).orElseThrow();
        Module module = new Module();
        module.setCourse(course);
        model.addAttribute("module", module);
        model.addAttribute("courseId", id);
        return "teacher/add_module";
    }

    @PostMapping("/course/{id}/module/add")
    public String addModule(@PathVariable Long id, @ModelAttribute Module module) {
        Course course = courseRepository.findById(id).orElseThrow();
        module.setCourse(course);
        moduleRepository.save(module);
        return "redirect:/teacher/dashboard";
    }
}
