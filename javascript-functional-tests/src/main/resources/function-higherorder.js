
function timestwo(x) {
	return x*2;
}

var executor = function(f, value) {
	return f(value);
};


var r = executor(timestwo,2);

var assertResult = ( r == 4);