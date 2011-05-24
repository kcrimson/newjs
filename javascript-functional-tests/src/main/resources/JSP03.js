var b = 1;

function foo() {
  var a = b = 0;
}

foo();

var assertResult = b == 0;

