package dashbase.meta;

public interface BindInfo {

    Class<?> getBindClass();

    BindMethod[] getBindMethods();
}
