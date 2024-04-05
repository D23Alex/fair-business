package com.d23alex.fairbusiness.payload.mappers;

import com.d23alex.fairbusiness.model.Person;
import com.d23alex.fairbusiness.payload.PersonPayload;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonPayload toPersonPayload(Person person);
}
