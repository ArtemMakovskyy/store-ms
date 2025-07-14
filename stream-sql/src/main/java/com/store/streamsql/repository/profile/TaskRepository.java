package com.store.streamsql.repository.profile;

import com.store.streamsql.model.profile.Task;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByDescription(String description);
}
