package com.antigravity.learningplatform.repository;

import com.antigravity.learningplatform.entity.Course;
import com.antigravity.learningplatform.entity.Subscription;
import com.antigravity.learningplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByLearner(User learner);

    Optional<Subscription> findByLearnerAndCourse(User learner, Course course);
}
