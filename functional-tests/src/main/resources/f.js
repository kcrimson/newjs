var print = console.log;
		var var1 = 0;
		var var2 = 1;
		var var3;
	 
		var num = 20;
 
 
		for(var i=3; i <= num;i++)
		{
			var3 = var1 + var2;
			var1 = var2;
			var2 = var3;
 
			print(var3);
		}
