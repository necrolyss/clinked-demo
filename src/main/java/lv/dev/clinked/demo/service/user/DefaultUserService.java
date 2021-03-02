package lv.dev.clinked.demo.service.user;

import lv.dev.clinked.demo.model.user.User;
import lv.dev.clinked.demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Autowired
    DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User userById(Long authorId) {
        return userRepository
                .findById(authorId)
                .orElseThrow(() -> new UserNotFound("id", authorId));
    }

}
