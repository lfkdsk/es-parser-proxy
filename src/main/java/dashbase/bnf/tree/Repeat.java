package dashbase.bnf.tree;

import dashbase.ast.base.AstList;
import dashbase.ast.base.AstNode;
import dashbase.bnf.BnfCom;
import dashbase.bnf.base.Element;
import dashbase.exception.ParseException;
import dashbase.lexer.JustLexer;

import java.util.List;

/**
 * 重复出现的语句节点
 * 比如block中会出现多次的simple
 * 还有Option
 */
public class Repeat extends Element {
    protected BnfCom parser;

    protected boolean onlyOne;

    /**
     * @param parser  BNF
     * @param onlyOne 节点出现次数
     */
    public Repeat(BnfCom parser, boolean onlyOne) {
        this.parser = parser;
        this.onlyOne = onlyOne;
    }

    @Override
    public void parse(JustLexer lexer, List<AstNode> nodes) throws ParseException {
        while (parser.match(lexer)) {

            AstNode node = parser.parse(lexer);
            // token or list
            if (node.getClass() != AstList.class || node.childCount() > 0) {
                nodes.add(node);
            }

            if (onlyOne)
                break;
        }
    }

    @Override
    public boolean match(JustLexer lexer) throws ParseException {
        return parser.match(lexer);
    }
}
