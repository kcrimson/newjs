var result;

function sum(x, y) {
	var result = x + y;
	return result;
}

var assertResult = 3 == sum(1, 2) && typeof this.result == 'undefined';
