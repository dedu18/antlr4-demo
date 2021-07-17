package my;

import my.gen.CustomIndicatorExpressionBaseVisitor;
import my.gen.CustomIndicatorExpressionParser;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author
 * @version 1.0
 * @description: Visitor
 * @date 2021/7/10 4:58 下午
 */
public class AdCustomIndicatorExpressionVisitor extends CustomIndicatorExpressionBaseVisitor<String> {

  /**
   * event 事件
   */
  private Set<String> events = new HashSet<>();

  /**
   * advertising 事件
   */
  private Set<String> advertisingEvents = new HashSet<>();

  private Set<String> dependencyCustomIndicators = new HashSet<>();

  /**
   * 字符串常量
   */
  protected final String CUSTOM_INDICATOR = "custom.indicator";
  protected final String ADVERTISING_EVENT_PREFIX = "advertising.";
  protected final String DOT = ".";
  protected final String QUOTES = "\"";
  protected final String BLANK = "";
  protected final String LEFT_BRACKET = "(";
  protected final String RIGHT_BRACKET = ")";
  protected final String SEPARATOR = "\\.";

  @Override
  public String visitPrefixExpr(CustomIndicatorExpressionParser.PrefixExprContext ctx) {
    return ctx.getChild(0).getText() + visit(ctx.getChild(1));
  }

  @Override
  public String visitNumber(CustomIndicatorExpressionParser.NumberContext ctx) {
    return ctx.getText();
  }

  @Override
  public String visitTableMeasure(CustomIndicatorExpressionParser.TableMeasureContext ctx) {
    String name = ctx.getChild(0).getText().replace(QUOTES, BLANK);
    String dot = ctx.getChild(1).getText().replace(QUOTES, BLANK);
    String operator = ctx.getChild(2).getText().replace(QUOTES, BLANK);
    events.add(name.split(SEPARATOR)[1]);
    if (Objects.equals(CUSTOM_INDICATOR, name)) {
      dependencyCustomIndicators.add(operator);
      return LEFT_BRACKET + name + dot + operator + RIGHT_BRACKET;
    }
    if (name.startsWith(ADVERTISING_EVENT_PREFIX)) {
      advertisingEvents.add(name);
    }
    return operator + LEFT_BRACKET + name + RIGHT_BRACKET;
  }

  @Override
  public String visitPropertyMeasure(CustomIndicatorExpressionParser.PropertyMeasureContext ctx) {
    events.add(ctx.getChild(0).getText().replace(QUOTES, BLANK).split(SEPARATOR)[1]);
    return ctx.getChild(4).getText().replace(QUOTES, BLANK) + LEFT_BRACKET + ctx.getChild(0).getText().replace(QUOTES, BLANK)
            + ctx.getChild(1).getText().replace(QUOTES, BLANK) + ctx.getChild(2).getText().replace(QUOTES, BLANK) + RIGHT_BRACKET;
  }

  @Override
  public String visitAddSubExpr(CustomIndicatorExpressionParser.AddSubExprContext ctx) {
    return visit(ctx.getChild(0)) + ctx.getChild(1).getText() + visit(ctx.getChild(2));
  }

  @Override
  public String visitMulDivExpr(CustomIndicatorExpressionParser.MulDivExprContext ctx) {
    return visit(ctx.getChild(0)) + ctx.getChild(1).getText() + visit(ctx.getChild(2));
  }

  @Override
  public String visitTableExpressionMeasure(CustomIndicatorExpressionParser.TableExpressionMeasureContext ctx) {
    return QUOTES + ctx.getChild(2).getText() + QUOTES + DOT + QUOTES + ctx.getChild(0).getText() + QUOTES;
  }

  @Override
  public String visitPropertyExpressionMeasure(CustomIndicatorExpressionParser.PropertyExpressionMeasureContext ctx) {
    return QUOTES + ctx.getChild(2).getText() + QUOTES + DOT + QUOTES + ctx.getChild(4).getText() + QUOTES + DOT +
            QUOTES + ctx.getChild(0).getText() + QUOTES;
  }

  @Override
  public String visitBrackets(CustomIndicatorExpressionParser.BracketsContext ctx) {
    return ctx.getChild(0).getText() + visit(ctx.getChild(1)) + ctx.getChild(2).getText();
  }

  public Set<String> getEvents() {
    return events;
  }

  public void setEvents(Set<String> events) {
    this.events = events;
  }

  public Set<String> getDependencyCustomIndicators() {
    return dependencyCustomIndicators;
  }

  public void setDependencyCustomIndicators(Set<String> dependencyCustomIndicators) {
    this.dependencyCustomIndicators = dependencyCustomIndicators;
  }

  public Set<String> getAdvertisingEvents() {
    return advertisingEvents;
  }

  public void setAdvertisingEvents(Set<String> advertisingEvents) {
    this.advertisingEvents = advertisingEvents;
  }

  public void clearExitsData() {
    events.clear();
    dependencyCustomIndicators.clear();
    advertisingEvents.clear();
  }

}

