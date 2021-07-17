package my;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;
import my.gen.ArithmeticExpressionBaseVisitor;
import my.gen.ArithmeticExpressionParser;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * 算数 Visitor
 *
 * @author
 * @version 1.0.0
 * @since 2021/07/01 18:42
 */
public class ArithmeticExpressionDenominatorVisitor extends ArithmeticExpressionBaseVisitor<Void> {
  ParseTreeProperty<Boolean> values = new ParseTreeProperty<>();

  Set<String> denominators = new LinkedHashSet<>();

  @Override
  public Void visitPrefixExpr(ArithmeticExpressionParser.PrefixExprContext ctx) {
    if (values.get(ctx) != null && values.get(ctx)) {
      values.put(ctx.getChild(1), true);
    }
    visit(ctx.getChild(1));
    return null;
  }

  @Override
  public Void visitAddSubExpr(ArithmeticExpressionParser.AddSubExprContext ctx) {
    if (values.get(ctx) != null && values.get(ctx)) {
      values.put(ctx.getChild(0), true);
      values.put(ctx.getChild(2), true);
    }
    visitChildren(ctx);
    return null;
  }

  @Override
  public Void visitVariable(ArithmeticExpressionParser.VariableContext ctx) {
    if (values.get(ctx) != null && values.get(ctx)) {
      denominators.add(ctx.getText());
    }
    return null;
  }


  @Override
  public Void visitMulDivExpr(ArithmeticExpressionParser.MulDivExprContext ctx) {
    // 如果是 DIV 的右子树，则需要标记所有的叶子节点
    if (values.get(ctx) != null && values.get(ctx)) {
      values.put(ctx.getChild(0), true);
      values.put(ctx.getChild(2), true);
    } else {
      ParseTree operator = ctx.getChild(1);
      // 如果该操作符是 DIV 则标记右子树所有的叶子节点
      if (operator instanceof TerminalNode && ((TerminalNode) operator).getSymbol().getType() == 7) {
        values.put(ctx.getChild(2), true);
      }
    }
    visitChildren(ctx);
    return null;
  }

  @Override
  public Void visitBrackets(ArithmeticExpressionParser.BracketsContext ctx) {
    if (values.get(ctx) != null && values.get(ctx)) {
      values.put(ctx.getChild(1), true);
    }
    visitChildren(ctx);
    return null;
  }
}