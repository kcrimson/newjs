function A() {
}
function B() {
}
function C() {
}

function NewC() {
	A.prototype = {};
	B.prototype = new A();
	C.prototype = new B();
	var result = new C();
	result.A = A.prototype;
	result.B = B.prototype;
	result.C = C.prototype;
	return result;
}

// Check that we can read properties defined in prototypes.
var c = NewC();
c.A.x = 1;
c.B.y = 2;
c.C.z = 3;

var assertResult = (1 == c.x) && (2 == c.y) && (3 == c.z);
