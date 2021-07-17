grammar CustomIndicatorExpression;

expr  : STRING '.' STRING                           # tableMeasure
      | STRING '.' STRING '.' STRING                # propertyMeasure
      | VARIABLE '(' TABLE ')'                      # tableExpressionMeasure
      | VARIABLE '(' TABLE '.' VARIABLE ')'         # propertyExpressionMeasure
      | expr ('*'|'/') expr                         # mulDivExpr
      | expr ('+'|'-') expr                         # addSubExpr
      | ('+'|'-') expr                              # prefixExpr
      | NUMBER                                      # number
      | '(' expr ')'                                # brackets
      ;

STRING        : '"' .*? '"';

VARIABLE      : [a-zA-Z_$][a-zA-Z0-9_$]+;
TABLE         : VARIABLE '.' VARIABLE;
NUMBER        : [1-9][0-9]*('.'[0-9]+)?
              | '0'('.'[0-9]+)?;
WS            : [ \t]+ -> skip;

DOT           :   '.';
MUL           :   '*';
DIV           :   '/';
ADD           :   '+';
SUB           :   '-';