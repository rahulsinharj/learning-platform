package com.antigravity.learningplatform.repository;

import com.antigravity.learningplatform.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
