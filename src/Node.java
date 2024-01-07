import java.util.Arrays;
import java.util.PriorityQueue;

//This class represents a node
public class Node implements Comparable<Node> {
    private final int MAXPOSSIBLEMOVES = 4; //Number of maximum possible moves
    private final Puzzle puzzle; //Puzzle
    private Node parent; //Parent of this node
    private Node child; //Children of this node
    private int pathCost; //Cost for this node
    private int searchCost; //Searchcost for this node
    private long startTime; //Time it takes to solve


    //Root node constructor
    public Node(Puzzle puzzle, int heuristic){
        this.puzzle = puzzle;
        this.parent = null;
        this.pathCost = puzzle.getHeuristic(heuristic);
        this.startTime = System.nanoTime();
    }

    //Child node constructor
    public Node(Puzzle puzzle, int heuristic, Node parent, int action){
        this.child = this;
        this.puzzle = puzzle;
        this.parent = parent;
        this.pathCost = getCurrentDepth(this) + puzzle.getHeuristic(heuristic);
        this.startTime = System.nanoTime();
    }

    //Returns the int array of the puzzle
    public int[] getPuzzleArray() {
        return puzzle.getPuzzleArray();
    }

    //Returns the elapsed time
    public long getTime() {
        return (System.nanoTime() - startTime);
    }

    //Returns the possible swappable indexes
    public int[] getPossibleSwappableIndex(){
        return puzzle.getSwaps();
    }
    //Returns the parent of a node
    public Node getParent() {
        return parent;
    }
    //Returns whether the puzzle has been solved or not
    public boolean isFinished () {
        return puzzle.isSolved();
    }
    //Returns the puzzle
    public Puzzle getPuzzle() {
        return puzzle;
    }
    //Returns the empty space in a puzzle
    public int getEmptySpace() {
        return puzzle.getEmptySpace();
    }
    //Returns the path cost of this node
    public int getPathCost() {
        return pathCost;
    }
    //Returns the search cost of this node
    public int getSearchCost(){
        return searchCost;
    }

    //Used to compare 2 nodes by their pathcost
    @Override
    public int compareTo(Node n) {
        return Integer.compare(this.getPathCost(), n.getPathCost());
    }

    //Used to compare two puzzles
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Node))
            return false;
        if(obj == this)
            return true;

        return Arrays.equals(this.getPuzzle().getPuzzleArray(), ((Node) obj).getPuzzle().getPuzzleArray());
    }

    //A* algorithm
    //Input is the puzzle itself and the heuristic function you want to use
    public Node AStar(Puzzle puzzle, int heuristic) {
        Node rootNode = new Node(puzzle, heuristic);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(rootNode);


        while(!priorityQueue.isEmpty()) {
            Node current = priorityQueue.poll();

            if(current.isFinished()){
                return current;
            }

            int [] possibleActions = current.getPossibleSwappableIndex();
            updateSearchCost(possibleActions);

            for(int i = 0; i < MAXPOSSIBLEMOVES; i++){
                if(possibleActions[i] != -1){
                    Puzzle secondPuzzle = createNewNode(possibleActions[i], current);
                    Node child = new Node(secondPuzzle, heuristic, current, i);

                    if(!priorityQueue.contains(child))
                        priorityQueue.add(child);
                }
            }
        }

        return rootNode;
    }

    //Used to update the searchCost
    private void updateSearchCost(int[] possibleActions) {
        for(int i = 0; i < MAXPOSSIBLEMOVES ; i++){
            if(possibleActions[i] != -1)
                searchCost++;
        }
    }

    //Creates a new node in the tree.
    public Puzzle createNewNode(int index, Node node){
        int[] oldPuzzleArray = node.getPuzzleArray();
        int blankSpaceIndex = node.getEmptySpace();

        int[] newPuzzleArray = oldPuzzleArray.clone();

        newPuzzleArray[blankSpaceIndex] = oldPuzzleArray[index];
        newPuzzleArray[index] = 0;

        return new Puzzle(newPuzzleArray);
    }

    //Returns the current depth in the tree.
    public int getCurrentDepth(Node n) {
        int depth = 0;
        while(n.getParent() != null) {
            depth++;
            n = n.getParent();
        }
        return depth;
    }
}
