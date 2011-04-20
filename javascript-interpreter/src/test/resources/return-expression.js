function hello() {
	return 16;
}

var a = 1;

var b = a + hello()+hello();

var assertResult = (b == 18);