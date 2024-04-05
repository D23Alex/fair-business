package com.d23alex.fairbusiness.payload.mappers;

import com.d23alex.fairbusiness.model.Passport;
import com.d23alex.fairbusiness.payload.PassportPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PassportMapper {
    PassportMapper INSTANCE = Mappers.getMapper(PassportMapper.class);

    @Mapping(target = "givenBy", expression = "java(passport.getGivenBy().getName() + passport.getGivenBy().getAddress())")
    PassportPayload toPassportPayload(Passport passport);
}
