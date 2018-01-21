package dashbase.bnf.tree;

import dashbase.ast.base.AstNode;
import dashbase.bnf.BnfCom;
import dashbase.bnf.base.Element;
import dashbase.exception.ParseException;
import dashbase.lexer.JustLexer;

import java.util.List;

/**
 * 开一棵子树
 * Tree中并没有对处理细节的描述
 * 只是个构造基类
 */
public class Tree extends Element {
    protected BnfCom parser;

    public Tree(BnfCom parser) {
        this.parser = parser;
    }

    @Override
    public void parse(JustLexer lexer, List<AstNode> nodes) throws ParseException {
        nodes.add(parser.parse(lexer));
    }

    @Override
    public boolean match(JustLexer lexer) throws ParseException {
        return parser.match(lexer);
    }
}
