grammar ArithmeticExpression;

@header { package com.sensorsdata.queryengine.antlr; }

expr  : expr ('*'|'/') expr         # mulDivExpr
      | expr ('+'|'-') expr         # addSubExpr
      | ('+'|'-') expr              # prefixExpr
      | NUMBER                      # number
      | VARIABLE                    # variable
      | '(' expr ')'                # brackets
      ;

NUMBER    : [1-9][0-9]*('.'[0-9]+)?
          | '0'('.'[0-9]+)?;
VARIABLE  : [a-zA-Z]+;
WS        : [ \t]+ -> skip;

MUL   :   '*';
DIV   :   '/';
ADD   :   '+';
SUB   :   '-';