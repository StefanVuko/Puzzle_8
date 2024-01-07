import java.util.Scanner;

public class Main {
    private static long totalTime = 0;
    private static long totalDepth = 0;
    private static long avgSearchCost = 0;

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
        int heuristicOption = input.nextInt();
        System.out.println();

        System.out.println("How many puzzles do you want to solve?");
        System.out.println();
        int rounds = input.nextInt();
        int count = 1;

        for (int i = 0; i < rounds; i++) {
            System.out.println("Puzzle #" + count);
            count++;
            Puzzle puzzle = new Puzzle();
            System.out.println("Generated puzzle");
            puzzle.printPuzzle();
            if (!puzzle.getIsPuzzleSolveable()) {
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

        totalTime += start.getTime() / 1000000;
        totalDepth += goal.getCurrentDepth(goal);
        avgSearchCost += start.getSearchCost()/goal.getCurrentDepth(goal);
    }
}