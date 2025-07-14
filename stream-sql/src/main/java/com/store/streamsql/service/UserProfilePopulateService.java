package com.store.streamsql.service;

import com.store.streamsql.model.profile.Department;
import com.store.streamsql.model.profile.Project;
import com.store.streamsql.model.profile.UserProfile;
import com.store.streamsql.repository.profile.DepartmentRepository;
import com.store.streamsql.repository.profile.ProjectRepository;
import com.store.streamsql.repository.profile.UserProfileRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfilePopulateService {

    private final UserProfileRepository userProfileRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;
    private final UserProfileDataGenerator generator;

    @Transactional
    public void populate(int count) {
        if (departmentRepository.count() < 0) {
            List<Department> departments = departmentRepository.saveAll(generator.generateDepartments(5));
            List<Project> projects = projectRepository.saveAll(generator.generateProjects(10));
            List<UserProfile> users = IntStream.range(0, count)
                    .mapToObj(i -> generator.generateUser(departments, projects))
                    .toList();
            userProfileRepository.saveAll(users);
        }
    }

}
