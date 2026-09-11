import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class SudokuBoard {

	// Parte 2
	int x;
	int y;
	int[][] board;
	int[][] inicialBoard;
	int[][] completeSudoku;
	int[][] memory = new int[81][3];
	int memoryi = 0;

	SudokuBoard(int[][] board) {
		this.board = SudokuAux.copyBoard(board);
		inicialBoard = SudokuAux.copyBoard(this.board);
		completeSudoku = SudokuAux.copyBoard(this.board);
	}

	// 1: Criar um jogo fornecendo uma matriz de inteiros 9x9
	int[][] newGame(int[][] sudoku, int percentage) {
		for (int i = 0; i < this.board.length; i++)
			for (int j = 0; j < this.board[i].length; j++) {
				this.board[i][j] = sudoku[i][j];
				completeSudoku[i][j] = sudoku[i][j];
			}
		if (SudokuAux.isCompleteSudoku(this.board) == true) {
			SudokuAux.gameBoard(this.board, percentage);
			inicialBoard = SudokuAux.copyBoard(this.board);

			return this.board;
		} else
			throw new IllegalArgumentException("Error: invalid sudoku.");
	}

	// 2: Obter o número que está numa coordenada
	int numberAt(int x, int y) {
		if (x > 0 && x < 10 && y > 0 && y < 10)
			return this.board[x - 1][y - 1];
		else
			throw new IllegalArgumentException("Error: invalid coordenates.");
	}

	// 3: Efetuar uma jogada dando a coordenada e o valor
	void writeNumberAt(int number, int x, int y) {
		if (number > 0 && number < 10) {
			if (x > 0 && x < 10 && y > 0 && y < 10 && inicialBoard[x - 1][y - 1] == 0) {
				memory[memoryi][0] = this.board[x - 1][y - 1];
				memory[memoryi][1] = x - 1;
				memory[memoryi][2] = y - 1;
				memoryi++;
				this.board[x - 1][y - 1] = number;
			} else
				throw new IllegalArgumentException("Error: invalid coordenates.");
		} else
			throw new IllegalArgumentException("Error: invalid number.");
	}

	// 4: jogada aleatoria num espaco vazio (um hint)
	void hint() {
		int x = (int) (Math.random() * 9);
		int y = (int) (Math.random() * 9);
		while (this.board[x][y] != 0) {
			x = (int) (Math.random() * 9);
			y = (int) (Math.random() * 9);
		}
		this.board[x][y] = completeSudoku[x][y];
	}

	// 5: reiniciar o tabuleiro
	public void restart() {
		for (int i = 0; i < this.board.length; i++)
			for (int j = 0; j < this.board[i].length; j++)
				this.board[i][j] = inicialBoard[i][j];
	}

	// 6: Obter os segmentos que não estão válidos para a solução do Sudoku
	public int[] wrongBlocks() {
		int count = 0;
		int[] w = new int[9];
		for (int n = 0; n < 9; n++)
			if (SudokuAux.isValidBlock(this.board, n) == false) {
				w[count] = n + 1;
				count++;
			}
		int[] wrong = new int[count];
		for (int i = 0; i < wrong.length; i++)
			wrong[i] = w[i];
		return wrong;
	}

	// 7: Obter as linhas que não estão válidos para a solução do Sudoku
	public int[] wrongLines() {
		int count = 0;
		int[] w = new int[9];
		for (int i = 0; i < this.board.length; i++)
			if (SudokuAux.isValidLine(this.board[i]) == false) {
				w[count] = i + 1;
				count++;
			}
		int[] wrong = new int[count];
		for (int n = 0; n < wrong.length; n++)
			wrong[n] = w[n];
		return wrong;
	}

	public int[] wrongColumns() {
		int count = 0;
		int[] w = new int[9];
		for (int j = 0; j < this.board[0].length; j++)
			if (SudokuAux.isValidLine(SudokuAux.columnArray(this.board, j)) == false) {
				w[count] = j + 1;
				count++;
			}
		int[] wrong = new int[count];
		for (int i = 0; i < wrong.length; i++)
			wrong[i] = w[i];
		return wrong;
	}

	// 8: Saber se o sudoku ja esta concluido
	public boolean isComplete() {
		if (SudokuAux.isCompleteSudoku(this.board) == false)
			return false;
		return true;
	}

	// 9: Undo
	public void undo() {
		if (memoryi > 0) {
			memoryi--;
			this.board[memory[memoryi][1]][memory[memoryi][2]] = memory[memoryi][0];
		}
	}

	public int[][] getMatrix() {
		int[][] m = new int[9][9];
		for (int i = 0; i < this.board.length; i++)
			for (int j = 0; j < this.board[i].length; j++)
				m[i][j] = this.board[i][j];
		return m;
	}

	public void save() {
		try {
			PrintWriter p = new PrintWriter(new File("sudoku.sudgame"));

			for (int i = 0; i < 9; i++){
				for (int j = 0; j < 9; j++){
					p.print(String.valueOf(inicialBoard[i][j] + " "));
				}
				p.println("");
			}

			p.println("");
			for (int i = 0; i < 9; i++){
				for (int j = 0; j < 9; j++){
					p.print(String.valueOf(this.board[i][j] + " "));
				}
				p.println("");
			}
			p.close();
			} 
		
		catch (FileNotFoundException e) {
			System.out.println("Not possible.");
		}
	}
	
	public void load(){
		try{
			Scanner scanner = new Scanner(new File("sudoku.sudgame"));
			
			for(int n = 0; n < 10; n++)
				scanner.nextLine();
			
			for(int i = 0; i < 9; i++)
				for(int j = 0; j < 9; j++)
					this.board[i][j] = scanner.nextInt();
					
			scanner.close();
		}
		catch(FileNotFoundException e){
			System.out.println("File not found.");
		}
	}
}