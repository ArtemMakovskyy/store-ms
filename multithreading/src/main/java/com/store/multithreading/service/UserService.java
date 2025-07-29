package com.store.multithreading.service;

import com.github.javafaker.Faker;
import com.store.multithreading.entity.AddressUsers;
import com.store.multithreading.entity.User;
import com.store.multithreading.repository.UserRepository;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final Faker faker = new Faker();
    private final Random random = new Random();

    //    @PostConstruct
    public void init() {
        log.info("Initializing UserService");
        IntStream.rangeClosed(1, 99)
                .forEachOrdered(i -> add(createFakeUser(i)));

    }

    public User add(User user) {
        return userRepository.save(user);
    }

    @Async("dbTaskExecutor")
    public void add2(User user, int threadNumber) {
        log.info(threadNumber + " > > > Starting async thread " + Thread.currentThread().getName());
        userRepository.save(user);
        log.info(threadNumber + "<<< Ending async thread " + Thread.currentThread().getName());
    }

    public User createUser(String name, String surname, String street) {
        int i = random.nextInt(1000) + 1;
        return new User()
                .setName(name)
                .setSurname(surname)
                .setEmail(name + "." + surname + "@mail")
                .setAddressUsers(
                        new AddressUsers()
                                .setStreet(street)
                                .setNumber(i)
                );
    }

    public User createFakeUser(int number) {
        String firstName = faker.name().firstName();
        String surname = faker.name().lastName();
        return new User()
                .setName(firstName)
                .setSurname(surname)
                .setEmail(firstName.toLowerCase() + "."
                        + surname.toLowerCase() + "@mail")
                .setAddressUsers(
                        new AddressUsers()
                                .setStreet(faker.address().streetAddress())
                                .setNumber(number)
                );
    }

}
