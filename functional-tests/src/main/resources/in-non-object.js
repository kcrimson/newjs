//given
var a = 0;
var b = 0;

var caught = false;
try{
	//when
	var c = (b in a)
}catch(e){
	caught = true;
}

//then we expect the exception (TypeError) to be thrown
var assertResult = caught;