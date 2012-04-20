function doFirst(){
	return doSecond();
}

function doSecond(){
	return 1;
}

var assertResult = (doFirst() == 1);