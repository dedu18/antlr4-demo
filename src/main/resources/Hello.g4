grammar Hello;
heel: 'hello' ID;
ID: [a-zA-Z0-9_$]+;
WS: [ \t\r\n]+ -> skip ;