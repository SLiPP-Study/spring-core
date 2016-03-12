package bean;

/**
 * Created by hotjoyit on 16. 3. 12.
 */
public abstract class CommonBean {

  public CommonBean() {
    System.out.println(String.format("@Construct : %s instantiated", getName()));
  }

  private String secret;

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
    System.out.println(String.format("@Setter : %s property is set", getName()));
  }

  public abstract String getName();
}
