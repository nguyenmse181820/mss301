package se.customerservice.exception;

public class AccountDisabledException extends RuntimeException {
  public AccountDisabledException() {
    super("User account is disabled");
  }
}
