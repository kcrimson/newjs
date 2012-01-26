var a = {
	b : "b"
};

Object.seal(a);

var desc = Object.getOwnPropertyDescriptor(a,"b");

var assertResult = !Object.isExtensible(a) && !desc.configurable;