import java.util.*;

class Item {
    int value;
    int weight;
    double ratio;

    Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
        this.ratio = (double) value / weight;
    }
}

public class FractionalKnapsack {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of items: ");
        int n = sc.nextInt();

        Item[] items = new Item[n];

        System.out.println("Enter value and weight of each item:");

        for (int i = 0; i < n; i++) {

            System.out.print("Item " + (i + 1) + " value: ");
            int value = sc.nextInt();

            System.out.print("Item " + (i + 1) + " weight: ");
            int weight = sc.nextInt();

            items[i] = new Item(value, weight);
        }

        System.out.print("Enter knapsack capacity: ");
        int capacity = sc.nextInt();

        // Sort according to value/weight ratio
        Arrays.sort(items, (a, b) ->
                Double.compare(b.ratio, a.ratio));

        double totalValue = 0;
        int remainingCapacity = capacity;

        System.out.println("\nSelected Items:");

        for (int i = 0; i < n; i++) {

            if (remainingCapacity == 0)
                break;

            if (items[i].weight <= remainingCapacity) {

                remainingCapacity -= items[i].weight;
                totalValue += items[i].value;

                System.out.println(
                        "Item " + (i + 1) +
                        " -> 100% selected"
                );

            } else {

                double fraction =
                        (double) remainingCapacity / items[i].weight;

                totalValue += items[i].value * fraction;

                System.out.printf(
                        "Item %d -> %.2f%% selected%n",
                        i + 1,
                        fraction * 100
                );

                remainingCapacity = 0;
            }
        }

        System.out.printf(
                "\nMaximum value = %.2f%n",
                totalValue
        );

        sc.close();
    }
}





/*
Program 1: Fractional Knapsack
FractionalKnapsack.java
Practical syllabus
- Algorithm: Greedy
- Problem: Fractional Knapsack
- Language: Java
- Key concepts: Classes, objects, arrays, sorting, comparator, greedy selection

1. Aim of the program
To implement the Fractional Knapsack problem using the Greedy algorithm and find the maximum value that can be placed in a knapsack of limited capacity.
What is the problem?
We are given items, each having:
- A value (profit)
- A weight
- A knapsack with limited capacity
We need to maximize the total value without exceeding the capacity.

Important: In Fractional Knapsack, we can take a fraction of an item. We are not restricted to taking the entire item.

Example:
Item	Value	Weight	Value/Weight
1	60	10	6
2	100	20	5
3	120	30	4

Knapsack capacity = 50

We select:
- Item 1: 100% → value 60
- Item 2: 100% → value 100
- Item 3: 20/30 = 66.67% → value 80
Maximum value = 240
2. Core concept: Greedy Algorithm
A Greedy algorithm makes the best possible choice at the current step, hoping to achieve the overall optimal solution.

For Fractional Knapsack, the greedy choice is:
Always select the item with the highest value-to-weight ratio first.

\[
\text{Ratio}=\frac{\text{Value}}{\text{Weight}}
\]

Why ratio instead of just value?

Because we want the maximum value per unit of weight.

For example:
- Item A: value 100, weight 50 → ratio 2
- Item B: value 60, weight 10 → ratio 6
Even though A has greater total value, B gives more value per unit of capacity.

Viva distinction: Greedy works optimally for Fractional Knapsack, but not always for 0/1 Knapsack.
3. Code walkthrough
A. Import and Item class
import java.util.*;

class Item {
    int value;
    int weight;
    double ratio;

    Item(int value, int weight) {
        this.value = value;
        this.weight = weight;
        this.ratio = (double) value / weight;
    }
}


Why do we create an Item class?

Each item has three related properties. The class groups them into one object.
Variable	Meaning
value	Profit obtained from the item
weight	Weight of the item
ratio	Value earned per unit of weight

Why is ratio a double?

Because the division may produce a decimal value.

Example:
60 / 10 = 6


But:
60 / 30 = 2.0


If both operands were integers, Java would perform integer division.
(int) 60 / 30       // 2
(int) 60 / 30        // integer division


For a fractional ratio:
(double) 60 / 30     // 2.0
(double) 100 / 30    // 3.333...


Counter question: Why cast only value?

Casting one operand to double makes the division floating-point division.

Counter question: What is this.value?

this refers to the current object.
this.value = value;


- this.value is the instance variable.
- value is the constructor parameter.
The constructor initializes each Item object.

B. Taking input
Scanner sc = new Scanner(System.in);

System.out.print("Enter number of items: ");
int n = sc.nextInt();

Item[] items = new Item[n];


Item[] items creates an array capable of storing n Item references.

Important: This does not create n Item objects yet.

Objects are created inside the loop:
for (int i = 0; i < n; i++) {
    int value = sc.nextInt();
    int weight = sc.nextInt();

    items[i] = new Item(value, weight);
}


For each item:
1. Read value.
2. Read weight.
3. Create an Item object.
4. Calculate its ratio through the constructor.
5. Store the object in the array.
Viva: Why use an array of objects instead of three separate arrays?

Because each item's value, weight, and ratio remain grouped together, making sorting and accessing items easier.

C. Sorting using Greedy strategy
Arrays.sort(items, (a, b) ->
        Double.compare(b.ratio, a.ratio));


This is one of the most important lines for viva.

Arrays.sort() sorts the Item objects.

The comparator compares two objects, a and b, based on their ratios.
Double.compare(b.ratio, a.ratio)


This sorts in descending order.

Why b.ratio comes first?
- Larger ratio should come before smaller ratio.
- Highest value-per-weight item is selected first.
Example:

Before sorting:
Item 1: ratio 6
Item 2: ratio 5
Item 3: ratio 4


After sorting:
6 → 5 → 4


Viva counter question: What if we write this?
Double.compare(a.ratio, b.ratio)


It sorts in ascending order, which is incorrect for this greedy strategy.

Why use Double.compare() instead of subtraction?

A comparator should return a negative number, zero, or a positive number. Double.compare() safely performs this comparison for floating-point values without relying on arithmetic subtraction.

D. Initialize the knapsack
double totalValue = 0;
int remainingCapacity = capacity;


- totalValue: accumulates the profit.
- remainingCapacity: tracks how much space is left.
Initially, the remaining capacity equals the total capacity.

E. Main greedy selection loop
for (int i = 0; i < n; i++) {

    if (remainingCapacity == 0)
        break;


If the knapsack is full, stop processing.
Case 1: Entire item fits
if (items[i].weight <= remainingCapacity) {

    remainingCapacity -= items[i].weight;
    totalValue += items[i].value;

    System.out.println(
            "Item " + (i + 1) +
            " -> 100% selected"
    );
}


If the complete item fits:
- Subtract its weight from remaining capacity.
- Add its full value to total value.
Example:
Remaining capacity = 40
Item weight = 20
Item value = 100


After selection:
Remaining capacity = 20
Total value = 100


Case 2: Only a fraction fits
else {

    double fraction =
            (double) remainingCapacity / items[i].weight;

    totalValue += items[i].value * fraction;

    System.out.printf(
            "Item %d -> %.2f%% selected%n",
            i + 1,
            fraction * 100
    );

    remainingCapacity = 0;
}


Suppose:
Remaining capacity = 20
Item weight = 30
Item value = 120


Fraction selected:
\[
\frac{20}{30}=0.6667
\]

Percentage selected:
\[
0.6667 \times 100=66.67\%
\]

Value obtained:
\[
120 \times 0.6667=80
\]

The knapsack becomes full, so:
remainingCapacity = 0;


Why multiply value by fraction?

The program assumes the item's value is distributed proportionally across its weight.

If we take 50% of the weight, we receive 50% of its value.

4. Complete dry run
Input:
Number of items: 3

Item 1: value = 60, weight = 10
Item 2: value = 100, weight = 20
Item 3: value = 120, weight = 30

Capacity = 50


After sorting by ratio:
Item	Ratio	Weight	Value
1	6	10	60
2	5	20	100
3	4	30	120

Execution:
Step	Selection	Remaining capacity	Total value
1	Item 1: 100%	40	60
2	Item 2: 100%	20	160
3	Item 3: 66.67%	0	240

Output:
Selected Items:
Item 1 -> 100% selected
Item 2 -> 100% selected
Item 3 -> 66.67% selected

Maximum value = 240.00


Note: The code prints the index after sorting, not the original item identity. This is a potential limitation if the examiner asks about identifying original input items.

5. Complexity analysis
Let n be the number of items.
Operation	Complexity
Input and object creation	O(n)
Sorting	O(n log n)
Greedy selection loop	O(n)
Overall time complexity	O(n log n)
Extra space	O(n) for the Item array

The sorting operation dominates the total time complexity.

6. Important practical viva questions
Basic questions
Q1. Which algorithm is used?

Greedy algorithm.

Q2. What is the objective?

Maximize total value without exceeding knapsack capacity.

Q3. What is the selection criterion?

Maximum value-to-weight ratio.

Q4. What is Fractional Knapsack?

A knapsack problem where we can select a fraction of an item.

Q5. What is the difference between Fractional and 0/1 Knapsack?
Fractional	0/1
Items can be divided	Items cannot be divided
Greedy by ratio is optimal	Greedy by ratio is not always optimal
Typically solved in O(n log n)	Common DP solution O(nW)

Tricky counter questions
Q6. Why is greedy optimal here?

Because items are divisible. Selecting the highest-value-per-unit-weight portion first cannot be improved by replacing it with a lower-ratio portion.

Q7. What if two items have the same ratio?

Either order gives the same value per unit weight, so either can be selected first.

Q8. What if capacity is greater than the total weight of all items?

All items are selected, and the total value is the sum of their values.

Q9. What if capacity is zero?

The loop immediately stops, and maximum value remains zero.

Q10. What if an item's weight is zero?

The ratio calculation causes division by zero. The current program does not handle this case. Practical inputs should have positive weights.

Q11. What if the number of items is zero?

An empty array is created, the loop does not execute, and the result is zero.

Q12. Why use double totalValue?

Because fractional selection can produce a decimal value.

Q13. What does %.2f mean?

Print a floating-point value with two digits after the decimal point.

Q14. What does %n mean?

It prints a platform-independent newline.

Q15. Why does the algorithm stop after selecting a fraction?

The remaining capacity becomes zero, so no further item can be added.

7. Potential code improvements / examiner traps
1. Original item numbering: After sorting, i + 1 refers to the sorted position, not necessarily the original item number.
2. Invalid input: Negative capacity, negative values, or zero weights are not validated.
3. Comparator: Descending order is essential; reversing the comparator changes the greedy choice.
4. Fractional selection: The algorithm assumes value is proportional to the fraction of weight taken.
5. 0/1 Knapsack: This exact greedy implementation is not guaranteed to solve 0/1 Knapsack optimally.
8. How to explain this program in the practical exam
A concise explanation to the examiner:
This program implements the Fractional Knapsack problem using a Greedy algorithm. Each item is represented by an object containing its value, weight, and value-to-weight ratio. The items are sorted in descending order of this ratio. We then select complete items as long as they fit into the remaining capacity. If an item does not fit completely, we take the fraction that fits and add its proportional value. The algorithm stops when the knapsack is full. Its overall time complexity is O(n log n), due to sorting.
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           */
