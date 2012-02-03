function NewA(){
	
	this.hello = function(){
		return "NewA";
	}
	
}

var a = new NewA();

var assertResult = "NewA" == a.hello();