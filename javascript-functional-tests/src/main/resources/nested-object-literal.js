var obj = {
	a : 7,
	b : {
		x : 12,
		y : 24,
		c : {
			d : 'Zebra'
		}
	}
}

var assertResult = 7 == obj.a;
assertResult = assertResult && (12 == obj.b.x);
assertResult = assertResult && (24 == obj.b.y);
assertResult = assertResult && ('Zebra' == obj.b.c.d);
