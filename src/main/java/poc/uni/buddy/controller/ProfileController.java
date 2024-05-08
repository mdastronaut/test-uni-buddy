package poc.uni.buddy.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poc.uni.buddy.api.ProfileApi;
import poc.uni.buddy.model.ProfileRequest;
import poc.uni.buddy.model.ProfileResponse;
import poc.uni.buddy.repository.model.Profile;
import poc.uni.buddy.service.ProfileMapper;
import poc.uni.buddy.service.ProfileService;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class ProfileController implements ProfileApi {

    ProfileMapper profileMapper = ProfileMapper.INSTANCE;

    private final ProfileService profileService;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("greetings fom uni-buddy");
    }

    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProfileResponse> createProfile(@RequestBody ProfileRequest profileRequest) {
        Profile createdProfile = profileService.createProfile(profileMapper.mapToProfile(profileRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(profileMapper.mapToProfileResponse(createdProfile));
    }

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileResponse> getProfileByNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(profileMapper.mapToProfileResponse(profileService.getProfileByNickname(nickname)));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("profile/{id}")
    public ResponseEntity<ProfileResponse> getProfileById(@PathVariable UUID id) {
        return ResponseEntity.ok(profileMapper.mapToProfileResponse(profileService.getProfileById(id)));
    }

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable UUID id, @RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(profileMapper.mapToProfileResponse(
                profileService.updateProfile(id, profileMapper.mapToProfile(profileRequest))));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
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
