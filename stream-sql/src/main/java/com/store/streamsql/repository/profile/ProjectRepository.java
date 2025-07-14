package com.store.streamsql.repository.profile;

import com.store.streamsql.model.profile.Project;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByTitle(String title);
}
