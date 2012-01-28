package net.primitive.javascript.tests.performance;

import java.io.File;

public interface BenchmarkDriver {

	void benchmark(int repCount, File testScript) throws Exception;

}