package my;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 * @version 1.0
 * @description:
 * @date 2021/7/10 4:57 下午
 */
public class Main {

  public static final String REPORT_TYPE_FEED = "FEED";
  public static final String REPORT_TYPE_SEM = "SEM";
  public static final String REPORT_TYPE_APPSTORE = "APPSTORE";


  public static final Set<String> CUSTOM_INDICATOR_FORMAT = Collections.unmodifiableSet(
          new HashSet<>(Arrays.asList(
                  REPORT_TYPE_FEED, REPORT_TYPE_SEM, REPORT_TYPE_APPSTORE
          ))
  );

  public static void main(String[] args) {
    String[] split = "FEED|SEM|APPSTORE".split("\\|");
    for (String s : split) {
      if (CUSTOM_INDICATOR_FORMAT.contains(s)) {
        System.out.println(s);
      }
    }
  }

  public static void main11(String[] args) {
//    自定义表达式
//    String request = "10*\"event.$Anything\".\"uniqcount\"/100+ROAS/20-(\"event.scorefinish\".\"$event_duration\".\"uniqcount\")";
    String request = "\"event.$Anything\".\"count\" / \"event.$ChannelExposure\".\"count\" / \"custom.indicator\".\"5\" + \"advertising.show_num\".\"general\"";
    AdCustomIndicatorExpressionVisitor sampleVisitor = new AdCustomIndicatorExpressionVisitor();
    String expression = sampleVisitor.visit(AntlrUtils.generateCustomIndicatorParseTreeFromString(request));
    System.out.println();
    System.out.println(expression);
    System.out.println();
    sampleVisitor.getDependencyCustomIndicators().forEach(t -> System.out.println(t));
    sampleVisitor.getAdvertisingEvents().forEach(t -> System.out.println(t));
  }

  public static void main1(String[] args) {
//    自定义表达式
//    String request = "10*\"event.$Anything\".\"uniqcount\"/100+ROAS/20-(\"event.scorefinish\".\"$event_duration\".\"uniqcount\")";
    String request = "\"event.$Anything\".\"count\" / \"event.$ChannelExposure\".\"count\" / \"custom.indicator\".\"5\" + \"advertising.show_num\".\"general\"";
    CustomIndicatorExpressionVisitor sampleVisitor = new CustomIndicatorExpressionVisitor();
    sampleVisitor.getAllCustomIndicator().put("custom.indicator.5", "\"event.$Anything\".\"count\" + \"custom.indicator\".\"6\"");
//    sampleVisitor.getAllCustomIndicator().put("custom.indicator.6", "\"advertising.show_num\".\"general\"");
    sampleVisitor.getAllCustomIndicator().put("custom.indicator.6", "");
    String expression = sampleVisitor.visit(AntlrUtils.generateCustomIndicatorParseTreeFromString(request));
    System.out.println();
    System.out.println(expression);
    System.out.println();
    sampleVisitor.getDependencyCustomIndicators().forEach(t -> System.out.println(t));
    sampleVisitor.getAdvertisingEvents().forEach(t -> System.out.println(t));
  }
}
