package ro.isf.persistence;

import org.springframework.stereotype.Component;
import ro.isf.services.register.SaveUserPort;
import ro.isf.services.register.User;

@Component
public class PersistenceAdapter implements SaveUserPort {
    private final UserRepository userRepository;

    public PersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long saveUser(User user) {
        return userRepository.save(new UserEntity(user.getName(), user.getEmail())).getId();
    }
}
