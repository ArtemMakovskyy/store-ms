package com.store.streamsql.service;

import com.github.javafaker.Faker;
import com.store.streamsql.model.profile.Address;
import com.store.streamsql.model.profile.Department;
import com.store.streamsql.model.profile.Project;
import com.store.streamsql.model.profile.Task;
import com.store.streamsql.model.profile.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserProfileDataGenerator {

    private final Faker faker = new Faker();

    public UserProfile generateUser(List<Department> departments, List<Project> projects) {
        Address address = Address.builder()
                .city(faker.address().city())
                .street(faker.address().streetAddress())
                .build();

        Department department = faker.options().nextElement(departments);

        List<Project> userProjects = getRandomSublist(projects, faker.number().numberBetween(1, 3));

        UserProfile user = UserProfile.builder()
                .username(faker.name().username())
                .address(address)
                .department(department)
                .projects(userProjects)
                .build();

        List<Task> tasks = generateTasks(user);
        user.setTasks(tasks);

        return user;
    }

    private <T> List<T> getRandomSublist(List<T> source, int count) {
        List<T> copy = new ArrayList<>(source);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }

    public List<Task> generateTasks(UserProfile user) {
        List<Task> tasks = new ArrayList<>();
        int taskCount = faker.number().numberBetween(1, 5);
        for (int i = 0; i < taskCount; i++) {
            Task task = Task.builder()
                    .description(faker.lorem().sentence())
                    .assignedUser(user)
                    .build();
            tasks.add(task);
        }
        return tasks;
    }

    public List<Department> generateDepartments(int count) {
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            departments.add(Department.builder()
                    .name(faker.company().industry())
                    .build());
        }
        return departments;
    }

    public List<Project> generateProjects(int count) {
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            projects.add(Project.builder()
                    .title(faker.app().name())
                    .build());
        }
        return projects;
    }
}
