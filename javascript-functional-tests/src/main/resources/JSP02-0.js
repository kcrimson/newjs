var result = 0;

function sum(x, y) {
    result = x + y;
    return result;
}

var assertResult = 3 == sum(1, 2) && this.result == 3
