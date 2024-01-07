//This class calculates the heuristic values of puzzles
public class HeuristicFunction {

    //This function caluclates the hamming value (number of misplaced numbers in the puzzle)
    public static int Hamming(int[] puzzle) {
        int missplacements = 0;

        for(int i = 0; i < puzzle.length; i++){
            if(puzzle[i] != i)
                missplacements++;
        }

        return missplacements;
    }

    //This function calculates the manhattan value (distance of misplaced tiles to their goal state)
    public static int Manhattan(int[] puzzle) {
        int result = 0;

        for(int i = 0; i < puzzle.length; i++) {

            if(puzzle[i] != 0 && puzzle[i] != i) {
                int delta = Math.abs(i-puzzle[i]);
                result += (delta % 3)+(delta/3);
            }
        }

        return result;
    }
}
