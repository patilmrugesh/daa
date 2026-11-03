import java.util.*;

public class GreedyHuffmanEncoding {

    static class Node {
        char ch;
        int freq;
        Node left, right;

        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Node(int freq, Node left, Node right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }

    static Node root;
    static Map<Character, String> codes = new HashMap<>();
    static String originalText = "";

    // Build Huffman Tree
    static void buildTree(String text) {

        Map<Character, Integer> freq = new HashMap<>();

        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(n -> n.freq));

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }

        // Special case: only one unique character
        if (pq.size() == 1) {
            Node only = pq.poll();
            root = new Node(only.freq, only, null);
        } else {
            while (pq.size() > 1) {

                Node left = pq.poll();
                Node right = pq.poll();

                Node parent = new Node(
                        left.freq + right.freq,
                        left,
                        right
                );

                pq.add(parent);
            }

            root = pq.poll();
        }

        codes.clear();
        generateCodes(root, "");
    }

    // Generate Huffman Codes
    static void generateCodes(Node node, String code) {

        if (node == null)
            return;

        if (node.left == null && node.right == null) {
            codes.put(node.ch, code.length() == 0 ? "0" : code);
            return;
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    // Encode Text
    static String encode(String text) {

        StringBuilder encoded = new StringBuilder();

        for (char c : text.toCharArray()) {
            encoded.append(codes.get(c));
        }

        return encoded.toString();
    }

    // Decode Text
    static String decode(String encoded) {

        StringBuilder decoded = new StringBuilder();

        Node current = root;

        for (char bit : encoded.toCharArray()) {

            if (bit == '0')
                current = current.left;
            else
                current = current.right;

            if (current.left == null && current.right == null) {
                decoded.append(current.ch);
                current = root;
            }
        }

        return decoded.toString();
    }

    // Display Huffman Codes
    static void displayCodes() {

        System.out.println("\nHuffman Codes:");

        for (Map.Entry<Character, String> entry : codes.entrySet()) {

            char c = entry.getKey();

            if (c == ' ')
                System.out.println("[space] : " + entry.getValue());
            else
                System.out.println(c + " : " + entry.getValue());
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter text: ");
        originalText = sc.nextLine();

        if (originalText.isEmpty()) {
            System.out.println("Text cannot be empty.");
            sc.close();
            return;
        }

        buildTree(originalText);

        int choice;

        do {
            System.out.println("\n----- HUFFMAN ENCODING -----");
            System.out.println("1. Build Huffman Tree");
            System.out.println("2. Encode the Text");
            System.out.println("3. Decode the Text");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    buildTree(originalText);
                    System.out.println("\nHuffman Tree built successfully.");
                    displayCodes();
                    break;

                case 2:
                    String encoded = encode(originalText);

                    System.out.println("\nOriginal Text : " + originalText);
                    System.out.println("Encoded Text : " + encoded);

                    displayCodes();
                    break;

                case 3:
                    String encodedText = encode(originalText);
                    String decodedText = decode(encodedText);

                    System.out.println("\nEncoded Text : " + encodedText);
                    System.out.println("Decoded Text : " + decodedText);
                    break;

                case 4:
                    System.out.println("Program terminated.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        sc.close();
    }
}






/* 
Program 2: Huffman Encoding
We'll study your uploaded GreedyHuffmanEncoding.java in depth, including the code structure, tree construction, encoding, decoding, and practical viva counter questions. GreedyHuffmanEncoding.java

Practical syllabus
- Algorithm: Greedy
- Technique: Huffman Coding
- Data structures: HashMap, PriorityQueue, Binary Tree
- Key concepts: Frequency counting, prefix codes, tree traversal, encoding and decoding

1. Aim of the program
To implement Huffman Encoding using a Greedy algorithm to compress text by assigning variable-length binary codes to characters based on their frequencies.

Core idea: Characters that occur more frequently receive shorter codes, while less frequent characters receive longer codes.

For example:
Character	Frequency	Huffman code
A	5	0
B	2	10
C	1	110
D	1	111

The exact codes may differ depending on how ties are resolved, but the code lengths and compression properties remain valid.
2. Important concepts before the code
What is Huffman Coding?
Huffman Coding is a lossless data compression algorithm.

Lossless means that the original data can be reconstructed exactly from the encoded data.
What is a prefix code?
A prefix code is a code in which no character's code is the prefix of another character's code.

Example of valid codes:
A = 0
B = 10
C = 110
D = 111


Invalid example:
A = 0
B = 01


Here, 0 is a prefix of 01, making decoding ambiguous.

Huffman Coding produces prefix-free codes, allowing us to decode the bitstream without separators.
Why is it greedy?
At every step, the algorithm selects the two nodes with the lowest frequencies, combines them, and inserts their parent back into the priority queue.

This local choice leads to an optimal prefix code.
3. Code walkthrough — line by line
A. Import and Node class
import java.util.*;

public class GreedyHuffmanEncoding {

    static class Node {
        char ch;
        int freq;
        Node left, right;

        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Node(int freq, Node left, Node right) {
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }


The Node class represents a node in the Huffman tree.
Variable	Purpose
ch	Character stored in a leaf node
freq	Frequency of the character or combined frequency
left	Left child
right	Right child

There are two constructors.

Constructor 1: Leaf node
Node(char ch, int freq)


Used for a character from the original text.

Example:
new Node('A', 5);


This creates a leaf node representing character A with frequency 5.

Constructor 2: Internal node
Node(int freq, Node left, Node right)


Used to combine two nodes.

Example:
new Node(8, left, right);


This creates a parent node whose frequency is the sum of its children's frequencies.

Internal nodes do not represent actual characters, so their ch field is left at Java's default value '\0'.

Viva: How do we identify a leaf node?
node.left == null && node.right == null


A leaf has no children.

B. Static variables
static Node root;
static Map<Character, String> codes = new HashMap<>();
static String originalText = "";


- root: Root of the Huffman tree.
- codes: Stores each character and its binary code.
- originalText: Stores the text entered by the user.
Example of codes:
A -> 0
B -> 10
C -> 11


Why Map<Character, String>?
- Key: Character
- Value: Its Huffman code
Why is the code stored as a String and not an integer?

Because binary codes can begin with zero.

For example, "001" must preserve its leading zeros. As an integer, it would become 1.

C. Build the Huffman tree
static void buildTree(String text) {

    Map<Character, Integer> freq = new HashMap<>();

    for (char c : text.toCharArray()) {
        freq.put(c, freq.getOrDefault(c, 0) + 1);
    }


This counts the frequency of each character.
Understanding toCharArray()
It converts a String into a character array.

For example:
"ABACA".toCharArray()


Produces:
A B A C A


Understanding getOrDefault()
freq.getOrDefault(c, 0) + 1


- If character c already exists, get its current frequency.
- Otherwise, use 0.
- Add 1 and store the updated frequency.
Example for "ABACA":
Character processed	Frequency map
A	A:1
B	A:1, B:1
A	A:2, B:1
C	A:2, B:1, C:1
A	A:3, B:1, C:1

Final frequency:
A = 3
B = 1
C = 1


Viva: Why use a HashMap?

It provides convenient character-to-frequency mapping. Average insertion and lookup are O(1).

D. PriorityQueue
PriorityQueue<Node> pq =
        new PriorityQueue<>(
            Comparator.comparingInt(n -> n.freq)
        );


A PriorityQueue stores nodes according to their priority.

Here, the priority is the node's frequency.

Important: Java's default PriorityQueue behavior with this comparator is a min-heap, meaning the node with the smallest frequency is removed first.

Then:
for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
    pq.add(new Node(entry.getKey(), entry.getValue()));
}


Every unique character becomes a leaf node and is inserted into the priority queue.

For "ABACA":
A:3
B:1
C:1


The priority queue contains nodes with frequencies 1, 1, and 3.

Viva: Why not use a normal queue?

A normal queue removes elements in insertion order. Huffman Coding requires repeatedly removing the two minimum-frequency nodes, so a priority queue is appropriate.

E. Special case: Only one unique character
if (pq.size() == 1) {
    Node only = pq.poll();
    root = new Node(only.freq, only, null);
}


Suppose input is:
AAAAAA


Only one unique character exists.

If the tree contained only one leaf, its generated code would be an empty string.

To avoid this, the code creates a parent root with the character node as its left child.

The generated code becomes:
A = 0


Viva: Why is this special case necessary?

To ensure the only character receives a non-empty code and can be encoded and decoded properly.

F. Main tree-building loop
else {
    while (pq.size() > 1) {

        Node left = pq.poll();
        Node right = pq.poll();

        Node parent = new Node(
                left.freq + right.freq,
                left,
                right
        );

        pq.add(parent);
    }

    root = pq.poll();
}


This is the heart of Huffman Coding.

At each iteration:
1. Remove the two nodes with minimum frequencies.
2. Make them the left and right children of a new parent.
3. Set the parent's frequency to their sum.
4. Insert the parent back into the priority queue.
5. Repeat until one node remains.
That final node becomes the root.
Example: "ABACA"
Frequencies:
A = 3
B = 1
C = 1


Step 1: Remove B:1 and C:1.

Create parent:
BC = 2


Priority queue now contains:
BC:2
A:3


Step 2: Remove BC:2 and A:3.

Create parent:
Root = 5


Tree:
        (5)
       /   \
     (2)    A:3
    /   \
  B:1   C:1


The actual code assigns the first removed node to the left and the second to the right.

Viva: What is the stopping condition?
while (pq.size() > 1)


When one node remains, all nodes have been merged into one Huffman tree.

G. Generate Huffman codes
codes.clear();
generateCodes(root, "");


The map is cleared so that old codes are removed if the tree is rebuilt.

Then code generation starts at the root with an empty string.
static void generateCodes(Node node, String code) {

    if (node == null)
        return;

    if (node.left == null && node.right == null) {
        codes.put(node.ch, code.length() == 0 ? "0" : code);
        return;
    }

    generateCodes(node.left, code + "0");
    generateCodes(node.right, code + "1");
}


This uses recursive tree traversal.

Rules:
- Going left appends 0.
- Going right appends 1.
- When a leaf is reached, store the accumulated code.
For the example tree:
A = 1
B = 00
C = 01


The code lengths are:
- A, frequency 3: length 1
- B, frequency 1: length 2
- C, frequency 1: length 2
The most frequent character gets the shortest code.

Viva: Which traversal is used?

A recursive depth-first traversal of the binary tree.

Viva: Why does the code return after reaching a leaf?

Because a leaf represents a complete character code. There are no children to explore.

H. Encode the text
static String encode(String text) {

    StringBuilder encoded = new StringBuilder();

    for (char c : text.toCharArray()) {
        encoded.append(codes.get(c));
    }

    return encoded.toString();
}


For each character in the original text:
1. Look up its Huffman code in the map.
2. Append that code to the output.
3. Return the complete encoded bitstring.
Example:
Original: ABACA

A = 1
B = 00
C = 01


Encoding:
A  B  A  C  A
1  00 1  01 1


Encoded text:
1001011


Viva: Why use StringBuilder?

Repeated String concatenation creates many intermediate String objects because Strings are immutable. StringBuilder allows efficient appending.

I. Decode the encoded text
static String decode(String encoded) {

    StringBuilder decoded = new StringBuilder();

    Node current = root;

    for (char bit : encoded.toCharArray()) {

        if (bit == '0')
            current = current.left;
        else
            current = current.right;

        if (current.left == null && current.right == null) {
            decoded.append(current.ch);
            current = root;
        }
    }

    return decoded.toString();
}


This is the reverse operation.

The decoder does not need the original text. It only needs the Huffman tree and encoded bits.
How decoding works
1. Start at the root.
2. Read one bit.
3. If bit is 0, move left.
4. If bit is 1, move right.
5. When a leaf is reached, output its character.
6. Reset to the root and continue.
For encoded string:
1001011


Using the example tree:
1  -> A
00 -> B
1  -> A
01 -> C
1  -> A


Decoded output:
ABACA


Viva: Why reset current = root after decoding a character?

Because the next bits represent the next character and must be decoded from the root again.

Viva: Why is decoding unambiguous?

Because Huffman codes are prefix-free. A code can be identified as soon as a leaf is reached.

J. Display codes
static void displayCodes() {

    System.out.println("\nHuffman Codes:");

    for (Map.Entry<Character, String> entry : codes.entrySet()) {

        char c = entry.getKey();

        if (c == ' ')
            System.out.println("[space] : " + entry.getValue());
        else
            System.out.println(c + " : " + entry.getValue());
    }
}


This prints each character and its assigned code.

The program displays spaces as [space] to make them visible.

Counter question: Is the output order guaranteed?

No. The codes are stored in a HashMap, which does not guarantee iteration order.

Also, if two nodes have equal frequencies, the priority queue may choose either order, so different valid Huffman codes can be produced.

K. Main method and menu
Scanner sc = new Scanner(System.in);

System.out.print("Enter text: ");
originalText = sc.nextLine();


nextLine() is used because the input may contain spaces.
if (originalText.isEmpty()) {
    System.out.println("Text cannot be empty.");
    sc.close();
    return;
}


The program rejects empty input.
buildTree(originalText);


The tree is built before showing the menu.

The do-while loop repeatedly displays options until the user chooses 4.
choice = sc.nextInt();
sc.nextLine();


nextInt() reads the number, and nextLine() consumes the leftover newline.

Why is nextLine() needed here?

It prevents the newline from interfering with any subsequent line-based input.

Menu options:
Choice	Action
1	Rebuild tree and display codes
2	Encode original text
3	Encode then decode original text
4	Exit

Note: Option 3 encodes the original text internally and then decodes it. It does not ask the user to enter a separate encoded string.
4. Complete dry run for practical viva
Input:
Enter text: ABACA


Step 1: Frequency count
A = 3
B = 1
C = 1


Step 2: Build the tree
Combine the two minimum frequencies:
B:1 + C:1 = 2


Then combine:
BC:2 + A:3 = 5


Tree:
        5
       / \
      2   A:3
     / \
   B:1 C:1


Step 3: Generate codes
A = 1
B = 00
C = 01


Step 4: Encode
Original: ABACA
Encoded : 1001011


Step 5: Decode
Encoded : 1001011
Decoded : ABACA


The decoded text matches the original text, confirming lossless encoding.
5. Time and space complexity
Let:
- n = total number of characters in the input
- k = number of unique characters
- L = length of the encoded bitstring
Operation	Complexity
Frequency counting	O(n)
Building priority queue	O(k log k)
Building Huffman tree	O(k log k)
Generating codes	O(k)
Encoding	O(n + L)
Decoding	O(L)
Overall	O(n + k log k + L)

For a fixed character set, k is bounded, so the overall processing is effectively linear in the input/output size.

Space complexity: O(k + n + L) including the tree, code map, and stored output strings. The tree and code map alone require O(k) space.
6. High-value viva counter questions
Algorithm questions
Q1. Is Huffman Coding lossless or lossy?

Lossless. The original data can be reconstructed exactly.

Q2. Is Huffman Coding greedy or dynamic programming?

Greedy. It repeatedly combines the two minimum-frequency nodes.

Q3. Why choose the two minimum-frequency nodes?

This ensures the least frequent characters are placed deeper in the tree, receiving longer codes, which minimizes the weighted path length.

Q4. What is the objective function?

Minimize:
\[
\sum_{i=1}^{k} f_i \times l_i
\]

Where:
- \(f_i\) = frequency of character i
- \(l_i\) = length of its Huffman code
Q5. What is the maximum number of leaf nodes?

There are k leaf nodes, one for each unique character.

Q6. How many internal nodes are there in a full Huffman tree?

For k leaves, there are k - 1 internal nodes.

Q7. What is the maximum number of nodes in the tree?

For k unique characters:
\[
2k-1
\]

Java and data structure questions
Q8. What is a PriorityQueue?

A queue that removes elements according to priority rather than insertion order.

Q9. What is a min-heap?

A heap where the minimum element is at the root and can be removed efficiently.

Q10. Why is Node declared as a static inner class?

It allows the main method and static methods to create Node objects without creating an instance of the outer class.

Q11. Why use HashMap for frequencies?

To efficiently map each character to its frequency.

Q12. Why use StringBuilder?

For efficient string construction through repeated appending.
Tricky questions
Q13. Can two characters have the same Huffman code?

No. Each character must have a unique code.

Q14. Can two different characters have codes of the same length?

Yes.

Q15. Can Huffman codes differ for the same input?

Yes. If frequencies tie, different valid tree arrangements may result. The codes can differ while retaining optimal code lengths.

Q16. What happens if all characters occur equally often?

The tree is built by repeatedly combining minimum-frequency nodes. The resulting codes will generally have similar lengths.

Q17. What happens for input "AAAAA"?

The special case creates a root with one left child, producing:
A = 0
Encoded = 00000
Decoded = AAAAA


Q18. Does Huffman Coding always reduce the size of the data?

No. For small inputs or data with nearly equal character frequencies, the encoded data plus the tree/code-table overhead may be larger than the original.

Q19. Does this program save the encoded data as a file?

No. It prints the encoded string to the console.

Q20. What if the encoded input ends midway through a code?

The current decoder does not validate incomplete or malformed bitstrings. It assumes valid encoded data generated from the same tree.
7. Important limitation in your code
Your implementation encodes only originalText and decodes using the tree stored in root.

For decoding in a separate program or on another machine, the Huffman tree or code table must also be transmitted or reconstructed.

The encoded bitstring alone is not sufficient unless the decoder knows the same tree.
8. Practical explanation to memorize
This program implements Huffman Encoding using a Greedy algorithm. First, it calculates the frequency of each character using a HashMap. Each character is inserted into a min-priority queue as a leaf node. The algorithm repeatedly removes the two nodes with minimum frequencies, combines them into a parent node, and inserts the parent back into the queue until a single Huffman tree remains. We generate codes by traversing left with 0 and right with 1. Encoding replaces each character with its code, while decoding traverses the tree bit by bit and outputs a character whenever a leaf is reached. Huffman Coding is a lossless compression technique that produces prefix-free codes.

*/
