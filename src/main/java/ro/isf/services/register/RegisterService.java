package ro.isf.services.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterService {

  private final SaveUserPort saveUserPort;

  private final SendMailPort sendMailPort;

  public Long registerUser(User user, boolean sendWelcomeMail) {
    user.setRegistrationDate(LocalDateTime.now());

    if(sendWelcomeMail){
      sendMailPort.sendMail("Welcome!", "Thanks for registering.");
    }

    return saveUserPort.saveUser(user);
  }

}
