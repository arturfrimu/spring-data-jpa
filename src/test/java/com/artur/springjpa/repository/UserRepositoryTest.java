package com.artur.springjpa.repository;

import annotation.DatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.artur.springjpa.repository.random.RandomUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DatabaseTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        var user = RandomUser.builder().build().get();

        var result = userRepository.saveAndFlush(user);

        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void testFindUserByConvertedPhoneNumber() {
        var user = RandomUser.builder().phoneNumber("010-12-34-56").build().get();

        userRepository.saveAndFlush(user);

        var optionalSavedUser = userRepository.findByPhoneNumber("010123456");
        assertTrue(optionalSavedUser.isPresent(), "Saved user should exist");

        var savedUser = optionalSavedUser.get();
        assertEquals(user.getPhoneNumber(), savedUser.getPhoneNumber(), "Phone numbers should match after conversion");
    }

    @Test
    public void testFindUserByNonConvertedPhoneNumber() {
        var user = RandomUser.builder().phoneNumber("010-12-34-56").build().get();

        userRepository.saveAndFlush(user);

        var optionalSavedUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        assertTrue(optionalSavedUser.isPresent(), "Saved user should exist");

        var savedUser = optionalSavedUser.get();
        assertEquals(user.getPhoneNumber(), savedUser.getPhoneNumber(), "Phone numbers should match after conversion");
    }

    @Test
    public void testFindUserByPersonalInformation() {
        var user = RandomUser.builder().build().get();

        userRepository.saveAndFlush(user);

        var optionalSavedUser = userRepository.findByPersonalInformation(user.getPersonalInformation());
        assertTrue(optionalSavedUser.isPresent(), "Saved user should exist");

        var result = optionalSavedUser.get();
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void testFindUserByUsername() {
        var user = RandomUser.builder().build().get();

        userRepository.saveAndFlush(user);

        var optionalSavedUser = userRepository.findByPersonalInformationUsername(user.getPersonalInformation().getUsername());
        assertTrue(optionalSavedUser.isPresent(), "Saved user should exist");

        var result = optionalSavedUser.get();
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void testFindUserByPassword() {
        var user = RandomUser.builder().build().get();

        userRepository.saveAndFlush(user);

        var optionalSavedUser = userRepository.findByPersonalInformationPassword(user.getPersonalInformation().getPassword());
        assertTrue(optionalSavedUser.isPresent(), "Saved user should exist");

        var result = optionalSavedUser.get();
        assertThat(result).usingRecursiveComparison().isEqualTo(user);
    }
}