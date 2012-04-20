function List(){
	
}

List.prototype = Array.prototype;

var list = new List();

list.push(1);

var assertResult = list[0];