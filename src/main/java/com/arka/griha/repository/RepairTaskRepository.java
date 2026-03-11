package com.arka.griha.repository;

import com.arka.griha.model.RepairTask;
import com.arka.griha.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RepairTaskRepository extends JpaRepository<RepairTask, Long> {
    List<RepairTask> findByUser(User user);
}
