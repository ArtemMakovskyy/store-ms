package com.store.streamsql.service;

import com.store.streamsql.model.profile.Department;
import com.store.streamsql.model.profile.Project;
import com.store.streamsql.model.profile.UserProfile;
import com.store.streamsql.repository.profile.DepartmentRepository;
import com.store.streamsql.repository.profile.ProjectRepository;
import com.store.streamsql.repository.profile.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
        if (departmentRepository.count() == 0) {
            List<Department> departments = departmentRepository.saveAll(generator.generateDepartments(5));
            List<Project> projects = projectRepository.saveAll(generator.generateProjects(10));
            List<UserProfile> users = IntStream.range(0, count)
                    .mapToObj(i -> generator.generateUser(departments, projects))
                    .toList();
            userProfileRepository.saveAll(users);
        }
    }

    public UserProfile createUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    public Optional<UserProfile> getUserProfileById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Transactional
    public UserProfile updateUserProfile(Long id, UserProfile updatedUser) {
        UserProfile existing = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserProfile not found: " + id));

        existing.setUsername(updatedUser.getUsername());
        existing.setAddress(updatedUser.getAddress());
        existing.setDepartment(updatedUser.getDepartment());
        existing.setProjects(updatedUser.getProjects());
        existing.setTasks(updatedUser.getTasks());

        return existing;
    }

    public void deleteUserProfile(Long id) {
        if (!userProfileRepository.existsById(id)) {
            throw new EntityNotFoundException("UserProfile not found: " + id);
        }
        userProfileRepository.deleteById(id);
    }
}
