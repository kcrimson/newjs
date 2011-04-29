var global_var = 1;
global_novar = 2;
(function() { global_fromfunc = 3; }());

var a = delete global_var;
var b = delete global_novar;
var c = delete global_fromfunc;
var d = typeof global_var == "number";
var e = typeof global_novar == "undefined";
var f = typeof global_fromfunc == "undefined";

var assertResult = !a && b && c && d && e && f;

print(assertResult);
