package ro.isf.services.register;

public interface SendMailPort {

  void sendMail(String subject, String text);

}
