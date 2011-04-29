var Module = function() {
  var message;

  function init() {
    message = "hello, world!";
  }

  function run() {
    return this.message == "hello, world";
  }

  return {
    init: init,
    run : run
  }
}();

Module.init();
assertResult = Module.run();
