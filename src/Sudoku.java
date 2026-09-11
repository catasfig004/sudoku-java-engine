import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Sudoku {
	
	private SudokuBoard sudokuBoard;
	public ColorImage boardImage;

	public Sudoku(String file, double difficulty){
		try{
			Scanner scanner = new Scanner(new File(file + ".sud"));
			int[][] sudoku = new int[9][9];
			for(int i = 0; i < sudoku.length; i++)
				for(int j = 0; j < sudoku[i].length; j++)
					sudoku[i][j] = scanner.nextInt();
			scanner.close();
			
			int d = (int)(difficulty * 100);
			sudokuBoard = new SudokuBoard(sudoku);
			sudokuBoard.newGame(sudoku,d);
			boardImage = SudokuAux.grid(sudokuBoard.board);
		}
		catch(FileNotFoundException e){
			System.out.println("File not found.");
		}
	}
	
	public static void main() {
		Sudoku sudoku = new Sudoku("sudoku", 0.5); 
		return;
		
	}
	
	//efetuar jogada
	public void play(int number, int Line, int Column){
		
		sudokuBoard.writeNumberAt(number, Line, Column);
		SudokuAux.change(boardImage, Column, Line, number);
		int[][] m = sudokuBoard.getMatrix();
		SudokuAux.noRed(boardImage,m);
		
		if(sudokuBoard.wrongBlocks() != null){
			int[] b = sudokuBoard.wrongBlocks();
			for(int i = 0; i < b.length; i++){
				int index = b[i] - 1;
				SudokuAux.redBlock(boardImage, index);
			}
		}
		if(sudokuBoard.wrongLines() != null){
			int[] l = sudokuBoard.wrongLines();
			for(int i = 0; i < l.length; i++){
				int index = l[i] - 1;
				SudokuAux.redLine(boardImage, index);
			}
		}
		if(sudokuBoard.wrongColumns() != null){
			int[] c = sudokuBoard.wrongColumns();
			for(int i = 0; i < c.length; i++){
				int index = c[i] - 1;
				SudokuAux.redColumn(boardImage, index);
			}
		}
	}
	
	//dica
	public void hint(){
		sudokuBoard.hint();
		int[][] m = sudokuBoard.getMatrix();
		SudokuAux.noRed(boardImage,m);
	}
	
	//recomecar
	public void restart(){
		sudokuBoard.restart();
		int[][] m = sudokuBoard.getMatrix();
		SudokuAux.noRed(boardImage,m);
	}
	
	public void undo(){
		sudokuBoard.undo();
		int[][] m = sudokuBoard.getMatrix();
		SudokuAux.noRed(boardImage,m);
		
		if(sudokuBoard.wrongBlocks() != null){
			int[] b = sudokuBoard.wrongBlocks();
			for(int i = 0; i < b.length; i++){
				int index = b[i] - 1;
				SudokuAux.redBlock(boardImage, index);
			}
		}
		if(sudokuBoard.wrongLines() != null){
			int[] l = sudokuBoard.wrongLines();
			for(int i = 0; i < l.length; i++){
				int index = l[i] - 1;
				SudokuAux.redLine(boardImage, index);
			}
		}
		if(sudokuBoard.wrongColumns() != null){
			int[] c = sudokuBoard.wrongColumns();
			for(int i = 0; i < c.length; i++){
				int index = c[i] - 1;
				SudokuAux.redColumn(boardImage, index);
			}
		}
	}
	
	public void save(){
		sudokuBoard.save();
	}
	
	public void load(){
		sudokuBoard.load();
		int[][] m = sudokuBoard.getMatrix();
		SudokuAux.noRed(boardImage,m);
		if(sudokuBoard.wrongBlocks() != null){
			int[] b = sudokuBoard.wrongBlocks();
			for(int i = 0; i < b.length; i++){
				int index = b[i] - 1;
				SudokuAux.redBlock(boardImage, index);
			}
		}
		if(sudokuBoard.wrongLines() != null){
			int[] l = sudokuBoard.wrongLines();
			for(int i = 0; i < l.length; i++){
				int index = l[i] - 1;
				SudokuAux.redLine(boardImage, index);
			}
		}
		if(sudokuBoard.wrongColumns() != null){
			int[] c = sudokuBoard.wrongColumns();
			for(int i = 0; i < c.length; i++){
				int index = c[i] - 1;
				SudokuAux.redColumn(boardImage, index);
			}
		}
	}
}