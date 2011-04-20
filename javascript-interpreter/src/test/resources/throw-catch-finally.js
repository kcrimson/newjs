function hello(){
	throw "error";
}

var a;

try{
	hello();
} catch (e){
	a = e;
}

var assertResult = (a == "error");