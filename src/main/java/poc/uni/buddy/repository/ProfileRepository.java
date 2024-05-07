package poc.uni.buddy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poc.uni.buddy.repository.model.Profile;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findProfileById(UUID id);

    Optional<Profile> findProfileByNickname(String email);
}
