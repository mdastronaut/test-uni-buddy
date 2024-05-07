package poc.uni.buddy.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poc.uni.buddy.repository.model.Profile;
import poc.uni.buddy.service.ProfileService;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("welcome")
    public String welcome() {
        return "greetings from uni-buddy";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("profile/create")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = profileService.createProfile(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("profile")
    public ResponseEntity<Profile> getProfileByNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(profileService.getProfileByNickname(nickname));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("profile/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable UUID id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("profile/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable UUID id, @RequestBody Profile profile) {
        return ResponseEntity.ok(profileService.updateProfile(id, profile));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("profile/delete/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable UUID id) {
        try {
            profileService.deleteProfile(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
