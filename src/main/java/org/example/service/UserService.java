package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.authenticate.Authenticator;
import org.example.dao.IUserRepository;
import org.example.dto.CreateUserDto;
import org.example.dto.UserDto;
import org.example.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    public Collection<UserDto> getUsers() {
        Collection<UserDto> userDtos = new ArrayList<>();
        Collection<User> users = userRepository.getUsers();
        for (User user : users) {
            UserDto userDto = new UserDto(user.getLogin(), user.getRole());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public UserDto getUser(String login) {
        User user = userRepository.getUser(login);
        if (user != null) {
            return new UserDto(user.getLogin(), user.getRole());
        } else {
            return null;
        }
    }
    public String createUser(CreateUserDto createUserDto) {
        User newUser = new User();
        newUser.setLogin(createUserDto.getLogin());
        newUser.setPassword(Authenticator.hashPassword(createUserDto.getPassword()));
        newUser.setRole(User.Role.USER);
        userRepository.addUser(newUser);
        return "success";
        //TODO: dokoncz to
    }
    public String deleteUser(String login) {
        User user = userRepository.getUser(login);
        if (user == null)
            return "not found";
        else if (user.getVehicle() != null)
            return "vehicle is not null";
        else
            userRepository.removeUser(login);
        return "deleted";
    }

}


