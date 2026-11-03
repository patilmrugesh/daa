import java.util.Scanner;

public class NQueens {

    static int[][] board;
    static int n;

    // Check whether a position is safe
    static boolean isSafe(int row, int col) {

        // Check column above
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1)
                return false;
        }

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1;
             i >= 0 && j >= 0; i--, j--) {

            if (board[i][j] == 1)
                return false;
        }

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1;
             i >= 0 && j < n; i--, j++) {

            if (board[i][j] == 1)
                return false;
        }

        return true;
    }

    // Solve using backtracking
    static boolean solveNQueens(int row) {

        if (row == n)
            return true;

        // First queen is already placed
        if (row == 0)
            return solveNQueens(row + 1);

        for (int col = 0; col < n; col++) {

            if (isSafe(row, col)) {

                board[row][col] = 1;

                if (solveNQueens(row + 1))
                    return true;

                // Backtrack
                board[row][col] = 0;
            }
        }

        return false;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the value of N: ");
        n = sc.nextInt();

        if (n < 1) {
            System.out.println("N must be at least 1.");
            sc.close();
            return;
        }

        board = new int[n][n];

        // Place the first queen at (0, 0)
        board[0][0] = 1;

        if (solveNQueens(0)) {

            System.out.println("Solution Matrix:");

            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {
                    System.out.print(board[i][j] + " ");
                }

                System.out.println();
            }

        } else {
            System.out.println(
                    "No solution exists with the first queen at (0, 0)."
            );
        }

        sc.close();
    }
}


/*
Program 3: N-Queens using Backtracking
NQueens.java
1. Aim and concept
Place N queens on an N × N chessboard so that no two queens attack each other.

Queens attack along:
- Same row
- Same column
- Both diagonals
Algorithm: Backtracking.

Your code fixes the first queen at (0,0) and attempts to place the remaining queens row by row.
2. Core logic
isSafe(row, col)
Checks whether a queen can be placed at the given position.

It checks only:
1. Column above: board[i][col]
2. Upper-left diagonal: board[i][j], decrement both indices
3. Upper-right diagonal: decrement row, increment column
Why only upward directions? Because queens are placed row by row from top to bottom. No queens exist in the lower rows yet.
return true;


If no attacking queen is found, the position is safe.
solveNQueens(row)
if (row == n)
    return true;

if (row == 0)
    return solveNQueens(row + 1);

for (int col = 0; col < n; col++) {
    if (isSafe(row, col)) {
        board[row][col] = 1;

        if (solveNQueens(row + 1))
            return true;

        board[row][col] = 0;
    }
}
return false;


Logic:
1. If row == n, all queens are placed → success.
2. Row 0 is skipped because the first queen is already placed.
3. Try every column in the current row.
4. If safe, place the queen (1).
5. Recursively solve the next row.
6. If that fails, remove the queen (0) and try another column.
This removal is called backtracking.
3. Dry run concept
For N = 4, first queen is fixed at (0,0).

The algorithm tries positions row by row. If a placement leads to a dead end, it removes that queen and tries the next column.

One valid solution is:
1 0 0 0
0 0 1 0
0 0 0 1
0 1 0 0


Each row and column contains exactly one queen, and no two queens share a diagonal.

Important limitation of your code: It fixes the first queen at (0,0). Therefore, it reports no solution for N = 2 or N = 3, and it finds a solution for N = 1 and N ≥ 4.
4. Complexity
- Time: O(N!) worst-case upper bound for backtracking search.
- Space: O(N²) for the board; O(N) recursion stack.
5. High-probability viva questions
Question	Answer
What is backtracking?	Trying a choice, recursively exploring it, and undoing it if it fails.
Why use recursion?	Each recursive call places a queen in the next row.
Why check only upward diagonals?	Previous queens are only in earlier rows.
What does board[row][col] = 1 mean?	A queen is placed there.
Why set it back to 0?	To undo an unsuccessful choice.
What is the base case?	row == n, meaning all queens are placed.
Why one queen per row?	The algorithm places exactly one queen before moving to the next row.
Why one queen per column?	isSafe() rejects columns containing an earlier queen.
Why check diagonals?	Queens can attack diagonally.
What if isSafe() always returns true?	The program may place attacking queens and produce invalid solutions.
Does this code print all solutions?	No, it stops after finding the first solution.
What does false mean from solveNQueens()?	No valid placement was found under the current choices.

Counter questions
Q: Why is the first queen fixed at (0,0)?

To reduce the search space by fixing the first queen's position. However, this means the program does not search all possible first-row positions.

Q: How can we modify it to find all solutions?

Remove the special first-row handling, try every column in every row, and store solutions instead of returning immediately after the first one.

Q: What does n = 1 produce?
1


Q: What does n = 2 or n = 3 produce?

No solution exists for the N-Queens problem.
6. Practical explanation to memorize
This program solves the N-Queens problem using backtracking. The first queen is placed at position (0,0). Then the algorithm attempts to place one queen in each subsequent row. Before placing a queen, it checks the column and both upper diagonals. If a position is safe, it places the queen and recursively processes the next row. If no valid arrangement is found, it removes the queen and tries another position. When all N queens are placed, the solution is printed.

Remember these three things: isSafe() checks attacks, solveNQueens() performs recursive placement, and setting the board cell back to 0 is the backtracking step.

*/
