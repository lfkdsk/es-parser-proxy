package dashbase.exception;


import dashbase.ast.base.AstNode;

/**
 * Compiler Exception
 *
 * @author liufengkai
 *         Created by liufengkai on 2017/7/20.
 */
public class CompilerException extends RuntimeException {

    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(String msg, AstNode tree) {
        super(msg + " " + tree.location());
    }
}
