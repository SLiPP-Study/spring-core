package jyp.spring.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * @author jinyoung.park89
 * @date 2016. 1. 22.
 */

@Getter
@Setter
@ToString
@Component
public class Tyre {
    private String brand;
}
