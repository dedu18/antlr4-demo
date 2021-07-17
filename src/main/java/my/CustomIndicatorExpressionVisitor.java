package my;

import my.gen.CustomIndicatorExpressionBaseVisitor;
import my.gen.CustomIndicatorExpressionParser;

import java.util.*;

/**
 * @author
 * @version 1.0
 * @description:
 * @date 2021/7/10 4:58 下午
 */
public class CustomIndicatorExpressionVisitor extends CustomIndicatorExpressionBaseVisitor<String> {

  /**
   * event 事件
   */
  private Set<String> events = new HashSet<>();

  /**
   * advertising 事件
   */
  private Set<String> advertisingEvents = new HashSet<>();

  /**
   * 该指标依赖的自定义指标
   */
  private Set<String> dependencyCustomIndicators = new HashSet<>();


  private Map<String, String> allCustomIndicator = new HashMap<>();

  /**
   * 字符串常量
   */
  private final String CUSTOM_INDICATOR = "custom.indicator";
  private final String ADVERTISING_EVENT_PREFIX = "advertising.";
  private final String DOT = ".";
  private final String QUOTES = "\"";
  private final String BLANK = "";
  private final String LEFT_BRACKET = "(";
  private final String RIGHT_BRACKET = ")";
  private final String SEPARATOR = "\\.";

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
    events.add(ctx.getChild(0).getText().replace(QUOTES, BLANK).split(SEPARATOR)[1]);
    String custom = ctx.getChild(0).getText().replace(QUOTES, BLANK);
    if (Objects.equals(CUSTOM_INDICATOR, custom)) {
      String customIndicator = ctx.getChild(2).getText().replace(QUOTES, BLANK);
      dependencyCustomIndicators.add(customIndicator);
      CustomIndicatorExpressionVisitor sampleVisitor = new CustomIndicatorExpressionVisitor();
      sampleVisitor.setAllCustomIndicator(this.getAllCustomIndicator());
      return LEFT_BRACKET + sampleVisitor.visit(AntlrUtils.generateCustomIndicatorParseTreeFromString(allCustomIndicator.get(CUSTOM_INDICATOR + DOT + customIndicator))) + RIGHT_BRACKET;
    }
    if (custom.startsWith(ADVERTISING_EVENT_PREFIX)) {
      String defaultIndicator = ctx.getChild(0).getText().replace(QUOTES, BLANK);
      advertisingEvents.add(defaultIndicator);
      return ctx.getChild(2).getText().replace(QUOTES, BLANK) + LEFT_BRACKET + ctx.getChild(0).getText().replace(QUOTES, BLANK) + RIGHT_BRACKET;
    }
    return ctx.getChild(2).getText().replace(QUOTES, BLANK) + LEFT_BRACKET + ctx.getChild(0).getText().replace(QUOTES, BLANK) + RIGHT_BRACKET;
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

  public Map<String, String> getAllCustomIndicator() {
    return allCustomIndicator;
  }

  public void setAllCustomIndicator(Map<String, String> allCustomIndicator) {
    this.allCustomIndicator = allCustomIndicator;
  }

  public void clearExitsData() {
    events.clear();
    dependencyCustomIndicators.clear();
    advertisingEvents.clear();
  }

}

