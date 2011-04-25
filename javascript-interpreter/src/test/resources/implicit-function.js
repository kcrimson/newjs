var a = "a";

(function() {
	var a = "b";
})();

var assertResult = (a == "a");