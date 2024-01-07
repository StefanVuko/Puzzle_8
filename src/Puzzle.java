import java.util.Random;
import java.util.stream.IntStream;


//This class represents a puzzle
public class Puzzle {
    private int [] puzzleBoard = new int[] {0,1,2,3,4,5,6,7,8}; //The single values of a puzzle
    private final boolean isPuzzleSolveable; //Boolean that shows, whether the puzzle is solveable or not
    private final int missplacements; //Number of misplaced numbers (Hamming)
    private final int sumOfDistance; //Number of the distance of wrong numbers (Manhattan)


    //Default constructor, that randomly generates a puzzle
    public Puzzle() {
        puzzleBoard = shufflePuzzle(puzzleBoard);
        missplacements = HeuristicFunction.Hamming(puzzleBoard);
        sumOfDistance = HeuristicFunction.Manhattan(puzzleBoard);
        isPuzzleSolveable = isSolvable(puzzleBoard);
    }

    //Constructor to create a puzzle with pre-defined values.
    public Puzzle(int [] numbersBoard) {
        this.puzzleBoard = numbersBoard;
        missplacements = HeuristicFunction.Hamming(numbersBoard);
        sumOfDistance = HeuristicFunction.Manhattan(numbersBoard);
        isPuzzleSolveable = isSolvable(numbersBoard);
    }

    //Returns whether the puzzle is solveable or not
    public boolean getIsPuzzleSolveable() {
        return isPuzzleSolveable;
    }

    //Returns the array of numbers from the puzzle
    public int[] getPuzzleArray(){
        return puzzleBoard;
    }

    // Prints the puzzle
    public void printPuzzle(){
        for(int r = 0; r<=2; r++){ 		// row
            for(int c = 0; c<=2; c++){   // column
                if(c != 2){
                    System.out.print(puzzleBoard[c+(r*3)]+ " | ");
                }
                else {
                    System.out.print(puzzleBoard[c+(r*3)]); // making sure the last index does not have a " | "
                }
            }
            System.out.println(" ");
            if(r != 2) {
                System.out.println("----------"); // making sure the last row does not have dashed lines at end
            }
        }
    }

    //Gets the indexes of possibles swaps
    public int[] getSwaps() {
        int emptySpace = IntStream.range(0, puzzleBoard.length).filter(i -> puzzleBoard[i] == 0).sum();
        int[] possibleSwappableIndex = new int[4]; //If an empty space is in the middle, there could be 4 possibilities at most.

        //Calculations for the index
        int upIndex = emptySpace - 3; // going up in table
        int downIndex = emptySpace + 3; // going down in table
        int leftIndex = emptySpace - 1; // going left in table
        int rightIndex = emptySpace + 1; // going right in table

        possibleSwappableIndex[0] = (emptySpace % 3 != 0 && leftIndex >= 0) ? leftIndex : -1;
        possibleSwappableIndex[1] = (rightIndex % 3 != 0 && rightIndex <= 8) ? rightIndex : -1;
        possibleSwappableIndex[2] = (upIndex < 9 && upIndex >= 0) ? upIndex : -1;
        possibleSwappableIndex[3] = (downIndex < 9 && downIndex >= 0) ? downIndex : -1;

        return possibleSwappableIndex;
    }

    //Returns the empty space in the grid
    public int getEmptySpace() {
        for(int i = 0; i < puzzleBoard.length; i++){
            if(puzzleBoard[i] == 0)
                return i;
        }
        //Should never reach this
        return 0;
    }

    //Returns whether the puzzle has been solved or not
    public boolean isSolved(){
        for(int i = 0; i < puzzleBoard.length; i++){
            if(puzzleBoard[i] != i)
                return false;
        }
        return true;
    }

    //Returns the heuristic value depending on which one was chosen. (1 = Hamming, 2 = Manhattan)
    public int getHeuristic (int heuristic) {
        if(heuristic == 1)
            return missplacements; // "Hamming"
        else
            return sumOfDistance; // "Manhattan"
    }

    //Shuffles the puzzle randomly
    //Input is an int array, Output is the shuffled array
    private int[] shufflePuzzle(int[] puzzle) {
        Random random = new Random();

        for (int i = puzzle.length - 1; i > 0; i--) {
            // Generate a random index from 0 to i
            int j = random.nextInt(i + 1);

            // Swap elements at indices i and j
            int temp = puzzle[i];
            puzzle[i] = puzzle[j];
            puzzle[j] = temp;
        }

        return puzzle;
    }

    //Returns the number of invasions
    //Input is the array of the puzzle, output is the number of inversions
    private int getInvCount(int[] puzzle) {
        int inv_count = 0;
        for (int i = 0; i < 9; i++)
            for (int j = i + 1; j < 9; j++)
                if (puzzle[i] > 0 && puzzle[j] > 0 && puzzle[i] > puzzle[j])
                    inv_count++;
        return inv_count;
    }

    //Calculates whether the puzzle is solveable or not
    private boolean isSolvable(int[] puzzle) {
        int invCount = getInvCount(puzzle);
        return (invCount % 2 == 0); //If the number of inversions is odd, the puzzle is not solveable
    }
}