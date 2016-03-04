package jyp.springframework.context;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 17.
 */
public interface MessageSourceResolvable {

    public String[] getCodes();

    public Object[] getArguments();

    public String getDefaultMessage();
}
