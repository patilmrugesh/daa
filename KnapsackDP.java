import java.util.Scanner;

public class KnapsackDP {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        int[] value = new int[n];
        int[] weight = new int[n];

        System.out.println("Enter value and weight of each item:");

        for (int i = 0; i < n; i++) {

            System.out.print("Value of item " + (i + 1) + ": ");
            value[i] = sc.nextInt();

            System.out.print("Weight of item " + (i + 1) + ": ");
            weight[i] = sc.nextInt();
        }

        System.out.print("Enter knapsack capacity: ");
        int W = sc.nextInt();

        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {

            for (int w = 0; w <= W; w++) {

                if (weight[i - 1] <= w) {

                    dp[i][w] = Math.max(
                            dp[i - 1][w],
                            value[i - 1] + dp[i - 1][w - weight[i - 1]]
                    );

                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        System.out.println("Maximum value = " + dp[n][W]);

        System.out.println("Selected items:");

        int w = W;

        for (int i = n; i > 0; i--) {

            if (dp[i][w] != dp[i - 1][w]) {

                System.out.println("Item " + i);
                w = w - weight[i - 1];
            }
        }

        sc.close();
    }
}



/*

Last program: 0/1 Knapsack using Dynamic Programming
Your uploaded KnapsackDP.java solves the 0/1 Knapsack problem using a 2D DP table. KnapsackDP(1).java

Core idea
For each item and each capacity, decide:
- Exclude the item: keep dp[i-1][w]
- Include the item: add its value to the best solution for remaining capacity
- Store the maximum of these two choices.
Each item can be selected at most once.
Important code
int[][] dp = new int[n + 1][W + 1];

for (int i = 1; i <= n; i++) {
    for (int w = 0; w <= W; w++) {
        if (weight[i - 1] <= w) {
            dp[i][w] = Math.max(
                dp[i - 1][w],
                value[i - 1] + dp[i - 1][w - weight[i - 1]]
            );
        } else {
            dp[i][w] = dp[i - 1][w];
        }
    }
}


What dp[i][w] means
Maximum value achievable using the first i items with capacity w.
- dp[0][w] = 0: no items means zero value.
- dp[i][0] = 0: zero capacity means zero value.
- If item weight exceeds capacity, exclude it.
- Otherwise, take the maximum of including or excluding it.
Example
Items: (value, weight) = (60,10), (100,20), (120,30)
Capacity = 50

Best selection: items 1 and 3
Total weight = 40; maximum value = 180.
Selected items logic
int w = W;
for (int i = n; i > 0; i--) {
    if (dp[i][w] != dp[i - 1][w]) {
        System.out.println("Item " + i);
        w = w - weight[i - 1];
    }
}


Backtracking from the bottom-right of the DP table identifies which items were included. If the value differs from the row above, item i was selected.
Complexity
- Time: O(n × W)
- Space: O(n × W)
Viva quick answers
- Why dynamic programming? The problem has overlapping subproblems and optimal substructure.
- Why 0/1? Each item is either selected once or not selected.
- Why n+1 rows and W+1 columns? To include the zero-items and zero-capacity base cases.
- Difference from fractional knapsack? 0/1 knapsack does not allow taking fractions; greedy ratio is not generally optimal.
- Why i-1? Java arrays are zero-indexed, while DP rows count items from 1.
- What does Math.max do? Chooses the better value between including and excluding the item.
Correction to the example: Items 2 and 3 are optimal:
- Item 2: value 100, weight 20
- Item 3: value 120, weight 30
- Total weight = 50
- Maximum value = 220
This matches the program’s output for those inputs.

















explain the problem in short what the problem is exactly













0/1 Knapsack Problem
You are given n items, each having a value and a weight, and a knapsack with a fixed capacity W.

Your task is to select items so that:
- The total weight does not exceed the knapsack capacity.
- The total value is as large as possible.
- Each item is either taken completely or not taken at all (no fractions allowed).
Example: Capacity = 5 kg; items are (value, weight) = (10,2), (15,3), (20,4).
Choose the first two items: total weight = 5 kg, total value = 25.

Goal: Find the maximum achievable value without exceeding the capacity.

*/
