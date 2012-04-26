var print = console.log;
var io = Host.java.io;

function Filesystem() {

	this.directory = "tmp";

	this.mkdir = function() {

		var file = new io.File(this.directory);

		if (!file.exists()) {
			print("Nie ma");
			if (file.mkdir()) {
				print("No to stworzylem");
			}
		}
	}

	this.exists = function() {
		return new io.File(this.directory).exists();
	}
}

var fs = new Filesystem();

var assertResult = fs.exists();
