package com.aliyaman.noteapp.repository;

import com.aliyaman.noteapp.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("user repository save all return saved user")
    void UserRepository_SaveAll_ReturnSavedUser(){
        User user = new User().builder()
                .name("ali")
                .email("aliyaman")
                .password("yamanali10")
                .role("admin")
                .verificationCode("1234")
                .enabled(false)
                .verificationExpiresAt(LocalDateTime.now())
                .notes(null)
                .build();
        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser.getName()).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }


    @Test
    @DisplayName("User Repository Find By id Return optional user")
    void UserRepository_FindById_ReturnOptionalUser() {
        User user = User.builder()
                .name("Ali")
                .email("aliyaman@example.com")
                .password("yamanali10")
                .role("admin")
                .verificationCode("1234")
                .enabled(false)
                .verificationExpiresAt(LocalDateTime.now())
                .notes(null)
                .build();

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        Assertions.assertThat(foundUser).isPresent();
        Assertions.assertThat(foundUser.get().getEmail()).isEqualTo("aliyaman@example.com");
    }


}
