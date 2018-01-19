package dashbase.ast.env;

/**
 * Evaluable 可执行的 node
 *
 * @author liufengkai
 */
public interface Evaluable {

    /**
     * call this node
     *
     * @param context context =>
     */
    void eval(Context context);
}
