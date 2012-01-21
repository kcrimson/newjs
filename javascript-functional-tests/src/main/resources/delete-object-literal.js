var a = { "key" : "value"};

var assertResult = delete a.key;

assertResult = assertResult && !a.key;