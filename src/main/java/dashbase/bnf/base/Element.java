package dashbase.bnf.base;

import dashbase.ast.base.AstNode;
import dashbase.exception.ParseException;
import dashbase.lexer.JustLexer;

import java.util.List;

public abstract class Element {
    /**
     * 语法分析
     *
     * @param lexer 语法分析器
     * @param nodes 节点
     * @throws ParseException
     */
    public abstract void parse(JustLexer lexer, List<AstNode> nodes)
            throws ParseException;

    /**
     * 匹配
     *
     * @param lexer 语法分析器
     * @return tof?
     * @throws ParseException
     */
    public abstract boolean match(JustLexer lexer) throws ParseException;


    @Override
    public boolean equals(Object obj) {
        String thisClassName = this.getClass().getSimpleName();
        String objClassName = obj.getClass().getSimpleName();

        return thisClassName.equals(objClassName);
    }
}