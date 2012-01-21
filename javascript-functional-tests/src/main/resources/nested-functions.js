function outer(a) {
	this.a = a;
	function inner() {
		return this.a;
	}
	return inner;
}

var o = outer("5");

var i = o();

var assertResult = (i == "5");