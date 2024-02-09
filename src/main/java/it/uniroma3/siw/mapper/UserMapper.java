package it.uniroma3.siw.mapper;

import it.uniroma3.siw.data.UserData;
import it.uniroma3.siw.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserData fromUser (User user);

    List<UserData> fromUser (List<User> user);

}
