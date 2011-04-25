function sum(a) {
	return function(b) {
		return a + b;
	};
}

var a = sum(1);
var b = a(1);

var assertResult = (b == 2);