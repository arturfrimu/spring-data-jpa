package com.artur.springjpa.service;

import com.artur.springjpa.entity.User;
import com.artur.springjpa.entity.UserPersonalInformation;
import com.artur.springjpa.mapper.UserMapper;
import com.artur.springjpa.repository.UserRepository;
import com.artur.springjpa.service.random.RandomCreateUserCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CreateUserService createUserService;

    @Test
    public void testCreateUserValid() {
        var command = RandomCreateUserCommand.builder().build().get();
        var actual = new User(command.phoneNumber(), new UserPersonalInformation(command.username(), command.password()));

        when(userMapper.toUser(command)).thenReturn(actual);

        createUserService.create(command);

        verify(userRepository).save(actual);
    }

    @Test
    public void testCreateUserInvalidPhoneNumber() {
        var command = RandomCreateUserCommand.builder().phoneNumber(null).build().get();
        var exception = assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
        assertEquals(format("Phone number can not be blank %s", command.phoneNumber()), exception.getMessage());
    }

    @Test
    public void testCreateUserInvalidUsername() {
        var command = RandomCreateUserCommand.builder().username(null).build().get();
        var exception = assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
        assertEquals(format("Username can not be blank %s", command.username()), exception.getMessage());
    }


    @Test
    public void testCreateUserInvalidPassword() {
        var command = RandomCreateUserCommand.builder().password(null).build().get();
        var exception = assertThrows(IllegalArgumentException.class, () -> createUserService.create(command));
        assertEquals(format("Password can not be blank %s", command.password()), exception.getMessage());
    }
}