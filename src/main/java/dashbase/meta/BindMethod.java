package dashbase.meta;

import lombok.Getter;

import java.lang.reflect.Method;

public class BindMethod {

    @Getter
    private final String name;

    @Getter
    private final String[] prefix;

    @Getter
    private final GrammarMode mode;

    @Getter
    private final Method method;

    /**
     * Used for efficient comparison
     */
    private String methodString;

    public BindMethod(String name,
                      String[] prefix,
                      GrammarMode mode,
                      Method method) {
        this.name = name;
        this.prefix = prefix;
        this.mode = mode;
        this.method = method;
    }

    private synchronized void checkMethodString() {
        if (methodString == null) {
            // Method.toString has more overhead, just take relevant parts of the method
            StringBuilder builder = new StringBuilder(64);
            builder.append(method.getDeclaringClass().getName());
            builder.append('#').append(method.getName());
            builder.append('(').append(mode.toString());
            methodString = builder.toString();
        }
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other instanceof BindMethod) {
            checkMethodString();
            BindMethod otherSubscriberMethod = (BindMethod) other;
            otherSubscriberMethod.checkMethodString();
            // Don't use method.equals because of http://code.google.com/p/android/issues/detail?id=7811#c6
            return methodString.equals(otherSubscriberMethod.methodString);
        } else {
            return false;
        }
    }

}
