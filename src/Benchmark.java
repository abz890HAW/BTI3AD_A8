package aufgabe8.src;

import java.util.*;
import java.util.stream.Collectors;

public class Benchmark {

    /**
     * Enum containing values being saved during each benchmark-step
     */
    public enum BENCHMARK_RESULT {
        UPDATE_MIN, UPDATE_MAX, UPDATE_AVG, SUM_MIN, SUM_MAX, SUM_AVG
    }

    /**
     * Amount of executed benchmarks per size
     */
    public static final int BENCHMARK_COUNT = 100;

    /**
     * Upper limit of 10^BENCHMARK_MAX_EXPONENT for List-size during benchmarks
     */
    public static final int BENCHMARK_MAX_EXPONENT = 6;

    /**
     * Executes the benchmark
     */
    public static void main(String[] strings) {
        execBenchmark();
    }

    /**
     * Executes a complete benchmark for a given pivotSelector
     */
    private static void execBenchmark() {
        // Var init
        Map<Integer, Map<BENCHMARK_RESULT, Long>> testResults = new TreeMap<>();


        // Run benchmarks
        for (long curExponent = 1; curExponent <= BENCHMARK_MAX_EXPONENT; curExponent++) {
            int curSize = (int) Math.pow(10, curExponent);

            // Init benchmark
            Map<BENCHMARK_RESULT, Long> curBenchmarkResults = initSingleTestDatastructure();

            // Run every benchmark n times
            for (int curBenchmark = 0; curBenchmark < BENCHMARK_COUNT; curBenchmark++) {
                // Generate tree
                LinkedBinaryTree tree = generateTree(curSize);

                // Benchmark updateChildSums and limitSum
                tree.resetCounter();
                tree.updateChildSums();

                long lowerBound = (long) (Math.random()*curSize*10);
                long upperBound = (long) (Math.random()*curSize*10);

                if(lowerBound> upperBound) {
                    long tmp = upperBound;
                    upperBound = lowerBound;
                    lowerBound = tmp;
                }

                tree.limitSum(lowerBound, upperBound);
                evaluateSingleTest(curBenchmarkResults, tree);
            }

            testResults.put(curSize, curBenchmarkResults);
        }

        // Export
        resultsToMatlabVariables(testResults, "t");
    }

    /**
     * Init test-results datastructure
     * @return Generated map
     */
    private static Map<BENCHMARK_RESULT, Long> initSingleTestDatastructure() {
        Map<BENCHMARK_RESULT, Long> newBenchmarkResult = new TreeMap<>();
        newBenchmarkResult.put(BENCHMARK_RESULT.SUM_MIN, Long.MAX_VALUE);
        newBenchmarkResult.put(BENCHMARK_RESULT.SUM_MAX, Long.MIN_VALUE);
        newBenchmarkResult.put(BENCHMARK_RESULT.SUM_AVG, 0L);
        newBenchmarkResult.put(BENCHMARK_RESULT.UPDATE_MIN, Long.MAX_VALUE);
        newBenchmarkResult.put(BENCHMARK_RESULT.UPDATE_MAX, Long.MIN_VALUE);
        newBenchmarkResult.put(BENCHMARK_RESULT.UPDATE_AVG, 0L);

        return newBenchmarkResult;
    }

    /**
     * Evaluates a single test
     *
     * @param curBenchmarkResults
     * @param sort
     */
    private static void evaluateSingleTest(Map<BENCHMARK_RESULT, Long> curBenchmarkResults, LinkedBinaryTree sort) {
        // updateChildSums
        curBenchmarkResults.put(BENCHMARK_RESULT.UPDATE_AVG, (curBenchmarkResults.get(BENCHMARK_RESULT.UPDATE_AVG) + sort.getCounterUpdateChildSum()) / 2);
        if (sort.getCounterUpdateChildSum() > curBenchmarkResults.get(BENCHMARK_RESULT.UPDATE_MAX)) {
            curBenchmarkResults.put(BENCHMARK_RESULT.UPDATE_MAX, sort.getCounterUpdateChildSum());
        }
        if (sort.getCounterUpdateChildSum() < curBenchmarkResults.get(BENCHMARK_RESULT.UPDATE_MIN)) {
            curBenchmarkResults.put(BENCHMARK_RESULT.UPDATE_MIN, sort.getCounterUpdateChildSum());
        }

        // limitSum
        curBenchmarkResults.put(BENCHMARK_RESULT.SUM_AVG, (curBenchmarkResults.get(BENCHMARK_RESULT.SUM_AVG) + sort.getCounterLimitSum()) / 2);
        if (sort.getCounterLimitSum() > curBenchmarkResults.get(BENCHMARK_RESULT.SUM_MAX)) {
            curBenchmarkResults.put(BENCHMARK_RESULT.SUM_MAX, sort.getCounterLimitSum());
        }
        if (sort.getCounterLimitSum() < curBenchmarkResults.get(BENCHMARK_RESULT.SUM_MIN)) {
            curBenchmarkResults.put(BENCHMARK_RESULT.SUM_MIN, sort.getCounterLimitSum());
        }



        // Cleanup
        sort.resetCounter();
    }

    private static void resultsToMatlabVariables(Map<Integer, Map<BENCHMARK_RESULT, Long>> testResults, String varPrefix) {
        // Generate sizes String
        String sizes = varPrefix + "_sizes = " + testResults.keySet().toString() + ";";
        System.out.println(sizes);

        // Init variables
        for(BENCHMARK_RESULT curVar: testResults.get(testResults.keySet().iterator().next()).keySet()) {
            String curResultString = varPrefix + "_" + curVar.toString() + " = [";
            for(int curSize: testResults.keySet()) {
                curResultString += testResults.get(curSize).get(curVar) + ",";
            }
            System.out.println(curResultString.substring(0, curResultString.length()-1) + "];");
        }
    }

    private static LinkedBinaryTree generateTree(long size) {
        LinkedBinaryTree tree = new LinkedBinaryTree();

        // Fill random values
        for (long i = 0; i < size; i++) {
            tree.insert((long) (Math.random()*size*10));
        }

        return tree;
    }

}
