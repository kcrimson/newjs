/*
 * Copyright (C) 2012 Primitive Team <jpalka@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
var print = console.log;
function test_fallthrough_default_case_should_not_be_executed(a){
	var b = 0;
	switch (a) {
		case 1:
			b = b+1;
		case 2:
			b = b+2;
			break;
		default:
			b = 5;
	}
	return b;
}

var arg = 1;
var result = test_fallthrough_default_case_should_not_be_executed(arg);
print(result);
var assertResult = (result == 3);