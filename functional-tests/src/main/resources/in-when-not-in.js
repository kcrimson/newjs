//given
var a = [0, 1, 2];
var b = 6;

//when
var contains = (b in a);

//then
var assertResult = !contains;