var x=[0,1,2,3,4,5];
var y=[x];

var assertResult = (y[0][5] == 5);
x[0]=1;
assertResult = assertResult && (y[0][0] == 1);