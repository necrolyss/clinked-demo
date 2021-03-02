package lv.dev.clinked.demo.service.user;

import lv.dev.clinked.demo.model.user.User;
import lv.dev.clinked.demo.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class DefaultUserServiceTest {

    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = 2L;

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;

    @InjectMocks
    private DefaultUserService defaultUserService;

    @Test
    void findsUserById() {
        given(userRepository.findById(EXISTING_USER_ID)).willReturn(Optional.of(user));

        User existingUser = defaultUserService.userById(EXISTING_USER_ID);

        assertThat(existingUser).isEqualTo(user);
    }

    @Test
    void failsToFindUserById() {
        given(userRepository.findById(NOT_EXISTING_USER_ID)).willReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> defaultUserService.userById(EXISTING_USER_ID));
    }
}