package dashbase.bnf.tree;

import dashbase.ast.base.AstLeaf;
import dashbase.ast.base.AstNode;
import dashbase.bnf.BnfCom;
import dashbase.bnf.base.Element;
import dashbase.bnf.base.Factory;
import dashbase.bnf.base.Operators;
import dashbase.bnf.base.Precedence;
import dashbase.exception.ParseException;
import dashbase.lexer.JustLexer;
import dashbase.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * 表达式子树
 */
public class Expr extends Element {
    protected Factory factory;

    protected Operators ops;

    protected BnfCom factor;

    public Expr(Class<? extends AstNode> clazz, BnfCom factor, Operators ops) {

        this.factory = Factory.getForAstList(clazz);
        this.factor = factor;
        this.ops = ops;
    }

    @Override
    public void parse(JustLexer lexer, List<AstNode> nodes) throws ParseException {
        AstNode right = factor.parse(lexer);

        Precedence prec;

        while ((prec = nextOperator(lexer)) != null) {
            right = doShift(lexer, right, prec.value);
        }

        nodes.add(right);
    }

    private AstNode doShift(JustLexer lexer, AstNode left, int prec) throws ParseException {
        ArrayList<AstNode> list = new ArrayList<>();

        list.add(left);
        // 读取一个符号
        list.add(new AstLeaf(lexer.read()));
        // 返回节点放在右子树
        AstNode right = factor.parse(lexer);

        Precedence next;
        // 子树向右拓展
        while ((next = nextOperator(lexer)) != null && rightIsExpr(prec, next)) {
            right = doShift(lexer, right, next.value);
        }

        list.add(right);

        return factory.make(list);
    }

    /**
     * 那取下一个符号
     *
     * @param lexer 词法
     * @return 符号
     * @throws ParseException
     */
    private Precedence nextOperator(JustLexer lexer) throws ParseException {
        Token token = lexer.peek(0);

        if (token.isIdentifier()) {
            // 从符号表里找对应的符号
            return ops.get(token.getText());
        } else {
            return null;
        }
    }

    /**
     * 比较和右侧符号的结合性
     *
     * @param prec     优先级
     * @param nextPrec 下一个符号的优先级
     * @return tof?
     */
    private static boolean rightIsExpr(int prec, Precedence nextPrec) {
        if (nextPrec.leftAssoc) {
            return prec > nextPrec.value;
        } else {
            return prec >= nextPrec.value;
        }
    }

    @Override
    public boolean match(JustLexer lexer) throws ParseException {
        return factor.match(lexer);
    }
}
