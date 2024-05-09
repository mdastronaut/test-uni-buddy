package poc.uni.buddy.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import poc.uni.buddy.repository.model.AuthUser;

import java.util.Optional;

@Repository
public interface AuthRepository extends MongoRepository<AuthUser, String> {

    Optional<AuthUser> findByEmail(String email);
}
