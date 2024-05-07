package poc.uni.buddy.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profile {

    @Id
    @UuidGenerator
    private UUID id;

    private String gender;

    private String nickname;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String email;

    private String university;

    private String major;

    private String phoneNumber;

    private LocalDate dateOfAdmission;
}

