import java.util.Arrays;
import java.util.Random;

public class Main {
    
    // Define the maximum temperature and cooling rate for the annealing process
    private static final double MAX_TEMPERATURE = 10000;
    private static final double COOLING_RATE = 0.03;
    private static int[] values = {68, 64, 47, 55, 72, 53, 81, 60, 72, 80, 62, 42, 48, 47, 68, 51, 48, 68, 83, 55, 48, 44, 49, 68, 63, 71, 82, 55, 60, 63, 56, 75, 42, 76, 42, 60, 75, 68, 67, 42, 71, 58, 66, 72, 67, 78, 49, 50, 51};
    private static int[] weights = {21, 11, 11, 10, 14, 12, 12, 14, 17, 13, 11, 13, 17, 14, 16, 10, 18, 10, 16, 17, 19, 12, 12, 16, 16, 13, 17, 12, 16, 13, 21, 11, 11, 10, 14, 12, 12, 14, 17, 13, 11, 13, 17, 14, 16, 10, 18, 10, 16};

    private static int knapsackCapacity = 300;
    
    // Define the solution state variables
    private static boolean[] currentSolution;
    private static boolean[] bestSolution;
    private static int currentValue;
    private static int bestValue;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // Initialize the solution state variables
        currentSolution = new boolean[values.length];
        bestSolution = new boolean[values.length];
        currentValue = 0;
        bestValue = 0;
        
        // Initialize the random number generator
        Random random = new Random();

        // Find the feasible solution in greedy way, which fill according to Value/Weight ratio
        double[] ratio = new double[values.length];
        for(int i=0 ; i < ratio.length; i++){
            ratio[i] = (double) values[i]/weights[i];
        }

        boolean[] flag = Arrays.copyOf(currentSolution,currentSolution.length);

        int currentCapacity = knapsackCapacity;
        int count = flag.length;

        while (count != 0) {
            double max = 0;
            int index = 0;
            for(int i=0; i<ratio.length; i++){
                if(max <= ratio[i] && !flag[i]) {
                    max = ratio[i];
                    index = i;
                }
            }

            if(currentCapacity - weights[index] > 0){
                currentSolution[index] = true;
                currentValue += values[index];
                currentCapacity -= weights[index];
            }

            flag[index] = true;
            count--;
        }

        bestValue = currentValue;
        bestSolution = currentSolution;

        System.out.println(currentValue);

        // Start the simulated annealing process

        double temperature = MAX_TEMPERATURE;
        double threshold_temperature = 1;

        while (temperature > threshold_temperature) {
            // Generator of new neighbor solution
            boolean[] neighborSolution = Arrays.copyOf(currentSolution, currentSolution.length);
            int randomIndex = random.nextInt(neighborSolution.length);

            currentCapacity = calculateRemainingWeight(neighborSolution);

            if(currentCapacity > weights[randomIndex] && neighborSolution[randomIndex] == false){
                neighborSolution[randomIndex] = true;
            }

            else {
                neighborSolution[randomIndex] = !neighborSolution[randomIndex];
            }

            // Calculation the fitness value of the neighbor solution
            int neighborValue = calculateValue(neighborSolution);

            // Calculation the acceptance probability of the neighbor solution
            double acceptanceProbability = calculateAcceptanceProbability(currentValue, neighborValue, temperature);

            // Condition of accept or reject the neighbor solution
            if (acceptanceProbability > random.nextDouble()){
                currentSolution = neighborSolution;
                currentValue = neighborValue;
            }

            // Update the best solution
            if (currentValue > bestValue) {
                bestSolution = Arrays.copyOf(currentSolution, currentSolution.length);
                bestValue = currentValue;
            }
            System.out.println(currentValue + " " + bestValue);

            //Decrease the temperature
            temperature *= 1 - COOLING_RATE;
        }

        // Print the best solution found
        System.out.println("Best Solution: " + Arrays.toString(bestSolution));
        System.out.println("Best Value: " + bestValue);

        long end = System.currentTimeMillis();
        System.out.println("Cooling Rate: " + COOLING_RATE + " - Execution Time: " + (end-start) + " ms");
        System.out.println("Initial Temperature: " + MAX_TEMPERATURE + " - Stopping Temperature: " + threshold_temperature + " - Execution Time: " + (end-start) + " ms");
    }
    
    // Helper method to calculate the fitness value of a solution
    private static int calculateValue(boolean[] solution) {
        int value = 0;
        int weight = 0;
        for (int i = 0; i < solution.length; i++) {
            if (solution[i]) {
                value += values[i];
                weight += weights[i];
            }
        }
        if (weight > knapsackCapacity) {
            return 0;
        } else {
            return value;
        }
    }
    
    // Helper method to calculate the acceptance probability of a neighbor solution
    private static double calculateAcceptanceProbability(int currentValue, int neighborValue, double temperature) {
        if (neighborValue > currentValue) {
            return 1;
        } else {
            return Math.exp((neighborValue - currentValue) / temperature);
        }
    }

    // Helper method to calculate the total remaining weight of neighborSolution
    private static int calculateRemainingWeight(boolean[] solution) {
        int weight = 0;
        for (int i = 0; i < solution.length; i++) {
            if (solution[i]) {
                weight += weights[i];
            }
        }
        return knapsackCapacity - weight;
    }
}
