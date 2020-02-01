package ro.isf.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
public class RegisterJpaTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Sql("createUser.sql")
    public void testRegisteUser() {
        UserEntity user = userRepository.findByName("Zaphod Beeblebrox");
        assertThat(user).isNotNull();
    }

}
