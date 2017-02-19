/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package net.nineseventwo;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

public class IntegerToStringWithSideEffectBenchmark {

	@State(Scope.Benchmark)
	public static class BenchmarkState {
		volatile int number = 31337;
	}

	@Benchmark
	public String concat_postfix_no_side_effect(BenchmarkState state) {
		return state.number + "";
	}

	@Benchmark
	public String concat_postfix(BenchmarkState state) {
		state.number--;

		return (state.number++) + "";
	}

	@Benchmark
	public String concat_prefix_no_side_effect(BenchmarkState state) {
		return "" + state.number;
	}

	@Benchmark
	public String concat_prefix(BenchmarkState state) {
		state.number--;

		return "" + (state.number++);
	}

	@Benchmark
	public String stringValueOf_no_side_effect(BenchmarkState state) {
		return String.valueOf(state.number);
	}

	@Benchmark
	public String stringValueOf(BenchmarkState state) {
		state.number--;

		return String.valueOf(state.number++);
	}

	@Benchmark
	public String integerToString_no_side_effect(BenchmarkState state) {
		return Integer.toString(state.number);
	}

	@Benchmark
	public String integerToString(BenchmarkState state) {
		state.number--;

		return Integer.toString(state.number++);
	}

	public static void main(String[] args) throws RunnerException {
		final Options options = new OptionsBuilder()
				.include(IntegerToStringWithSideEffectBenchmark.class.getSimpleName())
				.warmupIterations(5)
				.measurementIterations(10)
				.forks(1)
				.mode(Mode.AverageTime)
				.timeUnit(TimeUnit.NANOSECONDS)
				.build();

		new Runner(options).run();
	}

}
