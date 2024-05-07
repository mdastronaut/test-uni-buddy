package poc.uni.buddy.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import poc.uni.buddy.repository.model.Profile;

@Mapper
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mapping(target = "id", ignore = true)
    void updateExistingProfile(@MappingTarget Profile existingProfile, Profile newProfile);
}
