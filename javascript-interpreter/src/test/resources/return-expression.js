function hello() {
	return 1;
}

var a = 1;

var b = a + hello() + hello();

var assertResult = (b == 3);