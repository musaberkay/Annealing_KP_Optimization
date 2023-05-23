# Annealing_KP

## Introduction

The knapsack problem is a classical optimization problem in computer science and operations research. It involves selecting a subset of items from a given set, each with its own weight and value, to maximize the total value while respecting a weight constraint. Due to its practical applications in resource allocation, scheduling, and decision-making, the knapsack problem has garnered significant attention from researchers and practitioners alike. 

In this report, we present an implementation of a simulated annealing algorithm for solving the knapsack problem. Simulated annealing is a metaheuristic optimization technique inspired by the annealing process in metallurgy. It is known for its ability to efficiently explore large solution spaces and find near-optimal solutions even in the presence of local optima.

The goal of our implementation is to tackle the knapsack problem by utilizing the simulated annealing algorithm to search for the optimal or near-optimal solution. Simulated annealing employs a random search strategy, combined with a probabilistic acceptance criterion that allows the algorithm to escape local optima and potentially converge to a global optimum.

We aim to evaluate the effectiveness of our simulated annealing algorithm by comparing its performance against other commonly used optimization techniques for the knapsack problem. Additionally, we will analyze the impact of various parameters, such as the initial temperature, cooling schedule, and neighborhood exploration on the algorithm's convergence speed and solution quality.

## Questions & Answers

1. How does the execution time change when cooling rate increased? Report it by running the same algorithm for the given dataset.
> When the cooling rate is increased, the algorithm's execution time is shortened because our temperature drops and comes more quickly to predefined temperature threshold.
2. How does the execution time and solution quality change when the difference between starting temperature and stopping temperature is increased? Report it by running the same algorithm for the given datasets.
> When the difference between the start and stop temperature increases, the execution time of the algorithm increases, but the quality of the solution may increase or decrease because the indexes in the current solution are being randomly changed. So, we can not say certain increase or decrease for quality of the solution.
