var Module = function() {
  var message;

  function init() {
    message = "hello, world!";
  }

  function run() {
    return message == "hello, world!";
  }

  return {
    init: init,
    run : run
  }
}();

Module.init();
var assertResult = Module.run();

