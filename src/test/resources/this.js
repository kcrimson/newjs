function hello() {
	return this.a;
}

var b = {
	a : true,
	c : hello
};

var assertResult = b.c();