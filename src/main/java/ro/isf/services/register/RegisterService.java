package ro.isf.services.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterService {

  private final SaveUserPort saveUserPort;

  public Long registerUser(User user, boolean sendWelcomeMail) {
    user.setRegistrationDate(LocalDateTime.now());

    if(sendWelcomeMail){
    }

    return saveUserPort.saveUser(user);
  }

}
