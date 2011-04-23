function silnia(a) {
	if (a == 1) {
		return 1;
	} else {
		return a * silnia(a - 1);
	}
}

var arg = 3;
var s;
s = silnia(arg);

var same = false;
if (s == arg) {
	same = true;
}

var assertResult = (s == 6);