package poc.uni.buddy.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import poc.uni.buddy.repository.ProfileRepository;
import poc.uni.buddy.repository.model.Profile;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper = ProfileMapperImpl.INSTANCE;

    @Value("${entity.exists}")
    private String entityExists;

    public Profile createProfile(Profile profile) {
        if (profileRepository.findProfileByNickname(profile.getNickname()).isEmpty()) {
            return profileRepository.save(profile);
        } else {
            throw new EntityExistsException(entityExists);
        }
    }

    public Profile getProfileById(UUID id) {
        if (profileRepository.findProfileById(id).isPresent()) {
            return profileRepository.findProfileById(id).get();
        }
        return new Profile();
    }

    public Profile getProfileByNickname(String nickname) {
        if (profileRepository.findProfileByNickname(nickname).isPresent()) {
            return profileRepository.findProfileByNickname(nickname).get();
        }
        return new Profile();
    }

    public Profile updateProfile(UUID id, Profile newProfile) {
        if (profileRepository.findProfileById(id).isPresent()) {
            Profile existingProfile = profileRepository.findProfileById(id).get();
            profileMapper.updateExistingProfile(existingProfile, newProfile);
            return profileRepository.save(existingProfile);
        } else {
            throw new EntityNotFoundException("profile with id was not found in db.");
        }
    }

    public void deleteProfile(UUID id) {
        profileRepository.findProfileById(id)
                .ifPresentOrElse(
                        profileRepository::delete,
                        () -> {
                            throw new EntityNotFoundException("profile could not be found");
                        });
    }

}
