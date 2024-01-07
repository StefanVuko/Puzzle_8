import java.util.Scanner;

public class Main {
    private static long totalTime = 0; // variable for totalTime of all Puzzles
    private static long totalDepth = 0; // variable for totalDepth of all Puzzles
    private static long avgSearchCost = 0; // variable for averageCost of all Puzzles

    // calling up the start of the program
    public static void main(String[] args) {
        Start();
    }

    //Starts the program
    public static void Start(){
        System.out.println("This program solves 100 8-Puzzles using A* with Hamming or Manhattan heuristics and prints out statistics at the end");
        System.out.println("Please choose the heuristic function ");
        System.out.println();
        System.out.println("1 = Hamming");
        System.out.println("2 = Manhattan");
        System.out.println();
        Scanner input = new Scanner(System.in);
        int heuristicOption = input.nextInt(); // input for heuristic
        System.out.println();

        System.out.println("How many puzzles do you want to solve?");
        System.out.println();
        int rounds = input.nextInt(); // input for how many random puzzles should be solved
        int count = 1;

        for (int i = 0; i < rounds; i++) {
            System.out.println("Puzzle #" + count);
            count++;
            Puzzle puzzle = new Puzzle();
            System.out.println("Generated puzzle");
            puzzle.printPuzzle();
            if (!puzzle.getIsPuzzleSolveable()) { // check if the puzzle can be solved
                System.out.println("Not solveable!");
            }
            else {
                Node root = new Node(puzzle, heuristicOption);
                printOutput(puzzle, root, heuristicOption);
            }

            System.out.println("-------------------");
        }

        System.out.println("In total it took " + totalTime + " ms," + totalDepth + " was the total depth and the average search cost was " + avgSearchCost);
    }

    //Prints the output
    private static void printOutput(Puzzle puzzle, Node root, int heuristicOption) {
        System.out.println();
        Node goalTree = root.AStar(puzzle, heuristicOption);
        System.out.println("Puzzle input");
        root.getPuzzle().printPuzzle();
        System.out.println();
        System.out.println("Solved puzzle with option: "+ heuristicOption);
        goalTree.getPuzzle().printPuzzle();
        System.out.println();
        printStats(root, goalTree);
        System.out.println();
    }

    //Prints the stats at the end of each solved puzzle + adds them to the overall stats.
    private static void printStats(Node start, Node goal) {
        System.out.println("DFS : " + goal.getCurrentDepth(goal) +
                ", Total Time: "  + start.getTime() / 1000000 + "ms" +
                ", Cost: " + start.getSearchCost() +
                ", Avg. Cost: " + start.getSearchCost()/goal.getCurrentDepth(goal));

        // adding values of all puzzles in the end
        totalTime += start.getTime() / 1000000;
        totalDepth += goal.getCurrentDepth(goal);
        avgSearchCost += start.getSearchCost()/goal.getCurrentDepth(goal);
    }
}