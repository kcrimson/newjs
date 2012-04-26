var print = console.log;
var io = Host.java.io;

function Filesystem(){

  this.mkdir = function(){

    var file = new io.File("tmp");

    if(!file.exists()){
      print("Nie ma");
      if(file.mkdir()){
	print("No to stworzylem");
      }
    }
  }
}



var fs = new Filesystem();

fs.mkdir();
