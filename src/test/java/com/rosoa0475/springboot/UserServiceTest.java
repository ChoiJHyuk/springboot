package com.rosoa0475.springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.rosoa0475.springboot.user.entity.LocalUser;
import com.rosoa0475.springboot.user.service.UserService;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testSignup() {
        // given
        Integer lastId = this.userService.getLastId();
        String username = String.format("testuser_%d", lastId);
        String password = "123123";
        String nickname = String.format("testnick_%d", lastId);

        // when
        LocalUser savedUser = this.userService.saveUser(username, password, nickname);

        // then
        LocalUser user = this.userService.findUserWithId(savedUser.getId());
        assertEquals(savedUser.getUsername(), user.getUsername());
    }

    @Test
    public void testDuplicateUserSignip() {
        Integer lastId = this.userService.getLastId();

        String username1 = String.format("testusername_%d", lastId);
        String password1 = "123123";
        String nickname1 = String.format("testnickname_%d", lastId);

        String username2 = new String(username1);
        String password2 = "123123";
        String nickname2 = String.format("testnickname_%d", lastId + 1);

        this.userService.saveUser(username1, password1, nickname1);

        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class,
                () -> this.userService.saveUser(username2, password2, nickname2));

        assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
    }
}
