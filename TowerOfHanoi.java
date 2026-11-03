/*import java.util.Scanner;

// 1. Iterative Fibonacci 
public class FibonacciIterative {

    static int fibonacci(int n) {
        if (n == 0)
            return 0;

        int a = 0;
        int b = 1;

        for (int i = 2; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }

        return b;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        System.out.println("Fibonacci number = " + fibonacci(n));

        sc.close();
    }
}
*/

/*  2. Recursive Fibonacci
import java.util.Scanner;

public class FibonacciRecursive {

    static int fibonacci(int n) {

        if (n == 0)
            return 0;

        if (n == 1)
            return 1;

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n: ");
        int n = sc.nextInt();

        System.out.println("Fibonacci number = " + fibonacci(n));

        sc.close();
    }
}
*/

// 3. Recursive Tower of Hanoi
// import java.util.Scanner;

// public class TowerOfHanoi {

//     static void towerOfHanoi(int n, char source, char auxiliary, char destination) {

//         if (n == 1) {
//             System.out.println("Move disk 1 from " + source + " to " + destination);
//             return;
//         }

//         towerOfHanoi(n - 1, source, destination, auxiliary);

//         System.out.println("Move disk " + n + " from " + source + " to " + destination);

//         towerOfHanoi(n - 1, auxiliary, source, destination);
//     }

//     public static void main(String[] args) {

//         Scanner sc = new Scanner(System.in);

//         System.out.print("Enter number of disks: ");
//         int n = sc.nextInt();

//         towerOfHanoi(n, 'A', 'B', 'C');

//         sc.close();
//     }
// }





/*
Program 4: Tower of Hanoi using Recursion
TowerOfHanoi.java
1. Aim and concept
Move N disks from a source rod to a destination rod using an auxiliary rod.

Rules:
1. Move only one disk at a time.
2. Only the top disk can be moved.
3. A larger disk cannot be placed on a smaller disk.
Algorithm: Recursion.
2. Core code logic
static void towerOfHanoi(
    int n, char source,
    char auxiliary, char destination) {

    if (n == 1) {
        System.out.println(
            "Move disk 1 from " + source +
            " to " + destination);
        return;
    }

    towerOfHanoi(n - 1, source,
                 destination, auxiliary);

    System.out.println(
        "Move disk " + n + " from " +
        source + " to " + destination);

    towerOfHanoi(n - 1, auxiliary,
                 source, destination);
}


Three essential steps
To move n disks from A to C using B:
1. Move n-1 disks from A to B using C.
2. Move the largest disk n from A to C.
3. Move n-1 disks from B to C using A.
Base case
if (n == 1)


When only one disk remains, move it directly to the destination and return.

Without the base case, recursion would continue indefinitely until a stack overflow.
3. Dry run for N = 3
towerOfHanoi(3, A, B, C)


Output:
Move disk 1 from A to C
Move disk 2 from A to B
Move disk 1 from C to B
Move disk 3 from A to C
Move disk 1 from B to A
Move disk 2 from B to C
Move disk 1 from A to C


Total moves = 7

Formula:
\[
T(n)=2T(n-1)+1
\]

\[
\boxed{T(n)=2^n-1}
\]

4. Complexity
- Time complexity: O(2ⁿ)
- Auxiliary space: O(n), due to recursion stack.
- Minimum moves: \(2^n-1\)
5. High-probability viva questions
Question	Answer
Which technique is used?	Recursion
What is the base case?	n == 1
Why use three rods?	Source, auxiliary, destination
Why move n-1 disks first?	To expose the largest disk
Minimum moves for 4 disks?	15
Minimum moves for 5 disks?	31
What is the recurrence relation?	\(T(n)=2T(n-1)+1\)
Why is time complexity exponential?	Each call generates two recursive calls for n-1
What happens for n = 1?	One direct move
What happens for n = 0 in this code?	It has no valid base case and recurses indefinitely; input should be positive.

Tricky counter questions
Q: Why are auxiliary and destination swapped in the first recursive call?
towerOfHanoi(n - 1, source, destination, auxiliary);


We need to move the smaller disks onto the auxiliary rod, using the destination rod temporarily.

Q: Why is the largest disk moved only once?

It must move from source to destination after all smaller disks are removed from above it.

Q: Can we solve it iteratively?

Yes, but this program uses the recursive approach.

Q: What is the output for N = 3?

Seven moves, as shown in the dry run.
6. Practical explanation to memorize
Tower of Hanoi is solved using recursion. To move N disks from source to destination, we first move N−1 disks from source to auxiliary, then move the largest disk to destination, and finally move N−1 disks from auxiliary to destination. The base case is when only one disk remains. The recurrence relation is T(n) = 2T(n−1) + 1, giving a time complexity of O(2ⁿ).

Important for your practical: Your uploaded file has the Tower of Hanoi code commented out. If the examiner asks you to execute it, uncomment the code and ensure the class name matches the filename.

Quick final revision of all four programs
Program	Algorithm	Key concept
Fractional Knapsack	Greedy	Sort by value/weight
Huffman Encoding	Greedy	Min-priority queue and tree
N-Queens	Backtracking	Safe placement and undo
Tower of Hanoi	Recursion	Two recursive calls
*/
