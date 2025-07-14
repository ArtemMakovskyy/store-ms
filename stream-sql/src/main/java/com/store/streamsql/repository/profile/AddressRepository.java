package com.store.streamsql.repository.profile;

import com.store.streamsql.model.profile.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByCityAndStreet(String city, String street);
}
