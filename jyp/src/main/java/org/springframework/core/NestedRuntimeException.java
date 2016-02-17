package org.springframework.core;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author jinyoung.park89
 * @date 2016. 2. 16.
 */
public abstract class NestedRuntimeException extends RuntimeException {

    private Throwable cause;

    public NestedRuntimeException(String msg) {
        super(msg);
    }

    public NestedRuntimeException(String msg, Throwable ex) {
        super(msg);
        this.cause = ex;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public String getMessage() {
        if (this.cause == null) {
            return super.getMessage();
        } else {
            return super.getMessage() + "; nested exception is " + this.cause.getClass().getName() +
                    ": " + this.cause.getMessage();
        }
    }

    public void printStackTrace(PrintStream ps) {
        if (this.cause == null) {
            super.printStackTrace(ps);
        } else {
            ps.println(this);
            this.cause.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        if (this.cause == null) {
            super.printStackTrace(pw);
        } else {
            pw.println(this);
            this.cause.printStackTrace(pw);
        }
    }
}
