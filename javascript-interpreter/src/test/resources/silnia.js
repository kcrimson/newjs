function silnia(a) {
	if (a == 1) {
		return 1;
	} else {
		return a * silnia(a - 1);
	}
}

var s = silnia(3);

var assertResult = (s == 6);