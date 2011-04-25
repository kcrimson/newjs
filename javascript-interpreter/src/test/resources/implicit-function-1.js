var a = "a";
var b;

(function() {
	this.b = a;
})();

var assertResult = (b == "a");