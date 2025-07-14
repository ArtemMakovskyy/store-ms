package com.store.streamsql.cintroller;

import com.store.streamsql.model.profile.UserProfile;
import com.store.streamsql.service.UserProfilePopulateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfilePopulateService service;

    @PostMapping
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile user) {
        return ResponseEntity.ok(service.createUserProfile(user));
    }

    @GetMapping
    public List<UserProfile> getAll() {
        return service.getAllUserProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getById(@PathVariable Long id) {
        return service.getUserProfileById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> update(@PathVariable Long id, @RequestBody UserProfile user) {
        return ResponseEntity.ok(service.updateUserProfile(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}
