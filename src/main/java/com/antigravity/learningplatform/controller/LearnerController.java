package com.antigravity.learningplatform.controller;

import com.antigravity.learningplatform.entity.Course;
import com.antigravity.learningplatform.entity.Subscription;
import com.antigravity.learningplatform.entity.User;
import com.antigravity.learningplatform.repository.CourseRepository;
import com.antigravity.learningplatform.repository.SubscriptionRepository;
import com.antigravity.learningplatform.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/learner")
public class LearnerController {

    private final CourseRepository courseRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public LearnerController(CourseRepository courseRepository, SubscriptionRepository subscriptionRepository,
            UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User learner = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Subscription> subscriptions = subscriptionRepository.findByLearner(learner);
        model.addAttribute("subscriptions", subscriptions);
        model.addAttribute("username", learner.getUsername());
        return "learner/dashboard";
    }

    @GetMapping("/courses")
    public String availableCourses(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User learner = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Course> allCourses = courseRepository.findAll();
        List<Subscription> mySubscriptions = subscriptionRepository.findByLearner(learner);
        List<Long> subscribedCourseIds = mySubscriptions.stream()
                .map(s -> s.getCourse().getId())
                .collect(Collectors.toList());

        List<Course> availableCourses = allCourses.stream()
                .filter(c -> !subscribedCourseIds.contains(c.getId()))
                .collect(Collectors.toList());

        model.addAttribute("courses", availableCourses);
        return "learner/available_courses";
    }

    @PostMapping("/course/{id}/subscribe")
    public String subscribe(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User learner = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Course course = courseRepository.findById(id).orElseThrow();

        if (subscriptionRepository.findByLearnerAndCourse(learner, course).isEmpty()) {
            Subscription subscription = new Subscription();
            subscription.setLearner(learner);
            subscription.setCourse(course);
            subscription.setProgress(0);
            subscriptionRepository.save(subscription);
        }
        return "redirect:/learner/dashboard";
    }

    @GetMapping("/course/{id}")
    public String viewCourse(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User learner = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Course course = courseRepository.findById(id).orElseThrow();

        // Verify subscription
        if (subscriptionRepository.findByLearnerAndCourse(learner, course).isEmpty()) {
            return "redirect:/learner/courses";
        }

        model.addAttribute("course", course);
        return "learner/course_view";
    }
}
