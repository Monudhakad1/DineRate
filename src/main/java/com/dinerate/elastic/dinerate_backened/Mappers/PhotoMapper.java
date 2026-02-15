package com.dinerate.elastic.dinerate_backened.Mappers;


import com.dinerate.elastic.dinerate_backened.domains.dtos.PhotoDto;
import com.dinerate.elastic.dinerate_backened.domains.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface PhotoMapper {

    PhotoDto toDto(Photo photo);
}
