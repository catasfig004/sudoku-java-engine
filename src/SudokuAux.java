public class SudokuAux {

	//PARTE 1: sudoku aux
	//criacao, manipulacao e visualizacao do tabuleiro de jogo
	
	//1: validar se uma matriz de inteiros 9x9 representa um sudoku valido
	// ver se uma linha ou coluna eh valida (inclui 0)
	public static boolean isValidLine(int[] l){ 
		if (l.length == 9){
			int num = 1;
			while(num < 10){
				int count = 0;
				for(int i = 0; i < l.length; i++){
					if(l[i] == num)
						count += 1;
				}
				if(count > 1)
					return false;
				num++;
			}
			return true;
		}
		return false;
	}
	
	//ver se a linha ou coluna eh de um sudoku valido
	static boolean isSudokuLine(int[] l){
		if(isValidLine(l) == true){
			int count = 0;
			for(int i = 0; i < l.length; i++)
				count += l[i];
			if(count == 45)
				return true;
		}
		return false;
	}
	
	//receber uma coluna de uma matriz 
	public static int[] columnArray(int[][] m, int n){ // n: 0 a 9
		int[] coluna = new int[m.length];
		for(int i = 0; i < m.length; i++)
			coluna[i] = m[i][n];
		return coluna;
	}
	
	//ver se as linhas sao validas (inclui 0)
	static boolean allValidLines(int[][] m){
		for(int i = 0; i < m.length; i++)
			if(isValidLine(m[i]) == false)
				return false;
		return true;
	}
	
	//ver se as linhas de um sudoku sao validas
	static boolean allSudokuLines(int[][] m){
		for(int i = 0; i < m.length; i++)
			if(isSudokuLine(m[i]) == false)
				return false;
		return true;
	}
	
	//ver se as colunas sao validas (inclui 0)
		static boolean allValidColumns(int[][] m){
			for (int c = 0; c < 9; c++){
				int[] v = columnArray(m,c);
				if(isValidLine(v) == false)
					return false;
			}
			return true;
		}
	
	//ver se as colunas de um sudoku sao validas
	static boolean allSudokuColumns(int[][] m){
		for (int c = 0; c < 9; c++){
			int[] v = columnArray(m,c);
			if(isSudokuLine(v) == false)
				return false;
		}
		return true;
	}
	
	//stating point para um contador de linhas 
	//isto para facilitar buscar um bloco
	static int limitLine(int i){  // 0 <= i <= 9
		if(i == 0 || i == 1 || i == 2)
			return 0;
		else if(i == 3 || i == 4 || i == 5)
			return 3;
		else
			return 6;
	}
	//stating point para um contador de colunas 
	//isto para facilitar buscar um bloco
	static int limitColumn(int j){ // 0 <= j <= 9
		if(j == 0 || j == 3 || j == 6)
			return 0;
		else if(j == 1 || j == 4 || j == 7)
			return 3;
		else
			return 6;
	}
	
	//elementos de uma matriz 3x3 num vetor
	static int[] blockArray(int[][] m, int n){
		int[] array = new int[9];
		int i = limitLine(n);
		int maxI = i + 3;
		int count = 0;
		while(i < maxI){
			int j = limitColumn(n);
			int maxJ = j + 3;
			while(j < maxJ){
				array[count] = m[i][j];
				count++;
				j++;
			}
			i++;
		}
		return array;
	}
	
	//ver se todos os blocos 3x3 sao validos (inclui 0)
	static boolean isValidBlock(int[][] m, int n){ //n: 0 a 8
		int[] array = blockArray(m,n);
		if(isValidLine(array) != true)
			return false;
		return true;
	}
	
	//ver se todos os blocos 3x3 sao de um sudoku validos
	static boolean allValidBlocks(int[][] m){
		for(int c = 0; c < 10; c++){
			int[] blk = blockArray(m, c);
			if(isValidLine(blk) == false)
				return false;
		}
		return true;
	}
	
	//ver se todos os blocos 3x3 sao de um sudoku validos
	static boolean allSudokuBlocks(int[][] m){
		for(int c = 0; c < 10; c++){
			int[] blk = blockArray(m, c);
			if(isSudokuLine(blk) == false)
				return false;
		}
		return true;
	}
	
	//ver se uma matriz 9x9 eh valido
	public static boolean isValid(int[][] m){
		return allValidLines(m) == true && allValidColumns(m) == true && allValidBlocks(m) == true;
	}
	
	//ver se uma matriz 9x9 eh de um sudoku valido
	public static boolean isCompleteSudoku(int[][] m){
		return allSudokuLines(m) == true && allSudokuColumns(m) == true && allSudokuBlocks(m) == true;
	}
	
	
	
	
	//2: gerar uma matriz de jogo 
	public static void gameBoard(int[][] m, int percentage){
		int c = (int) (81 * (percentage / (double) 100));
		for(int i = 0; i < c; i++){
			int x = (int) (Math.random() * 9);
			int y = (int) (Math.random() * 9);
			if(m[x][y] != 0)
				m[x][y] = 0;
			else
				i--;
		}
	}
	
	//3: matriz -> string
	public static String matrixToString(int[][] m){
		String matrixStr = "";
		for(int i = 0; i < m.length; i++){
			for (int j = 0; j < m[i].length; j++){
				if(j < m[i].length - 1)
					matrixStr += m[i][j] + "";
				else
					matrixStr += m[i][j];
			}
			if(i < m.length - 1)
				matrixStr += "\n"; 
		}
		return matrixStr;		
	}
	
	//4: Produzir uma imagem a cores com o desenho do tabuleiro Sudoku a partir de uma matrix 9x9
	//desenhar a grelha
	public static ColorImage grid(int[][] matrizJogo){
		
		ColorImage img = new ColorImage(500,500,new Color(242,242,248));
		
		Color Blue = new Color(191,209,229);
		int y = img.getWidth()/9;
		for(int i = 2; i < img.getWidth(); i+=y)
			for(int j = 2; j < img.getHeight(); j++){
				img.setColor(i,j, Blue);
			}
		for(int n = 2; n < img.getHeight(); n+=y)
			for(int m = 2; m < img.getWidth(); m++){
				img.setColor(m,n, Blue);
			}
		
		Color blue = new Color(104,149,197);
		int x = img.getWidth()/3;
		for(int i = 0; i < img.getWidth(); i+=x)
			for(int j = 0; j < img.getHeight(); j++){
				img.setColor(i,j, blue);
				img.setColor(i+1,j, blue);
			}
		for(int n = 0; n < img.getHeight(); n+=x)
			for(int m = 0; m < img.getWidth(); m++){
				img.setColor(m,n, blue);
				img.setColor(m,n+1, blue);
			}
		
		for(int p = 0; p < 9; p++)
			for(int t = 0; t < 9; t++){
				if(matrizJogo[t][p] == 0)
					img.drawCenteredText(30 + p * 55, 30 + t * 55, "", 25, new Color(65,65,65));
				else
					img.drawCenteredText(30 + p * 55, 30 + t * 55, "" + matrizJogo[t][p], 25, new Color(0,0,0));
			}
		return img;
	}
	
	//5: alterar posicao
	public static void change(ColorImage img, int x, int y, int number){
		int cx = 0;
		int cy = 0;
		for(int i = 0; i < img.getWidth(); i += 55){
			cx ++;
			if (cx == x)
				for(int j = 0; j < img.getHeight(); j+= 55){
					cy ++;
					if (cy == y){
						for(int w = i + 5; w < i + 55; w++)
							for(int h = j + 5; h < j + 55; h++)
								img.setColor(w,h,new Color(242,242,248));
						img.drawCenteredText((img.getWidth()/9) * (cx - 1) + 30, (img.getHeight()/9) * (cy - 1) + 30, "" + number, 25, new Color(0,0,0));
					}
				}
		}
	}
	
	//6: marcar uma linha com contorno vermelho
	//indice: 0 a 8
	public static void redLine(ColorImage img, int index){
		Color red = new Color(255,0,0);
		int x = 2 + img.getHeight()/9 * index;
		for(int i = 0; i < img.getWidth(); i++){
			img.setColor(i ,x, red);
			img.setColor(i, x + 1, red);
			img.setColor(i, x + img.getHeight()/9, red);
			img.setColor(i, x + 1 + img.getHeight()/9, red);
		}
		for(int j = x; j < x + img.getHeight()/9; j++){
			img.setColor(0,j, red);
			img.setColor(1,j, red);
			img.setColor(img.getWidth() - 1,j, red);
			img.setColor(img.getWidth() - 2,j, red);
		}
	}
	
	//7: marcar uma coluna com contorno vermelho
	//indice 0 a 8
	public static void redColumn(ColorImage img, int index){
		Color red = new Color(255,0,0);
		int x = 2 + img.getWidth()/9 * index;
		for(int i = x; i < x + img.getWidth()/9; i++){
			img.setColor(i, 0, red);
			img.setColor(i, 1, red);
			img.setColor(i, img.getHeight() - 1, red);
			img.setColor(i, img.getHeight() - 2, red);
		}
		for(int j = 0; j < img.getHeight(); j++){
			img.setColor(x, j, red);
			img.setColor(x + 1, j, red);
			img.setColor(x + img.getWidth()/9,j, red);
			img.setColor(x + 1 + img.getWidth()/9, j, red);
		}
	}
	
	//8: marcar um segmento 3x3 com contorno vermelho
	//indice: 0 a 8
	public static void redBlock(ColorImage img, int index){
		Color red = new Color(255,0,0);
		int h = limitLine(index);
		int w = limitColumn(index);
		int x = 2 + img.getWidth()/9 * w;
		int y = 2 + img.getHeight()/9 * h;
		for(int i = x; i < x + img.getWidth()/3; i++){
			img.setColor(i, y, red);
			img.setColor(i, y + 1, red);
			img.setColor(i, y - 1 + img.getHeight()/3, red);
			img.setColor(i, y - 2 + img.getHeight()/3, red);
		}
		for(int j = y; j < y + img.getHeight()/3; j++){
			img.setColor(x, j, red);
			img.setColor(x + 1, j, red);
			img.setColor(x + img.getWidth()/3, j, red);
			img.setColor(x - 1 + img.getWidth()/3, j, red);
		}
	}
	
	//Extra: copia uma matriz
	public static int[][] copyBoard(int[][] m){
		int[][] copy = new int[9][9];
		for(int i = 0; i < m.length; i++)
			for(int j = 0; j < m[i].length; j++)
				copy[i][j] = m[i][j];
		return copy;
	}
	
	//Extra: fazer a imagem voltar ao normal
	public static void noRed(ColorImage img, int[][] matrizJogo){
	
		for(int i = 0; i < img.getWidth(); i++)
			for(int j = 0; j < img.getHeight(); j++)
				img.setColor(i,j, new Color(242,242,248));
			
		Color Blue = new Color(191,209,229);
		int y = img.getWidth()/9;
		for(int i = 2; i < img.getWidth(); i+=y)
			for(int j = 2; j < img.getHeight(); j++){
				img.setColor(i,j, Blue);
			}
		for(int n = 2; n < img.getHeight(); n+=y)
			for(int m = 2; m < img.getWidth(); m++){
				img.setColor(m,n, Blue);
			}
		
		Color blue = new Color(104,149,197);
		int x = img.getWidth()/3;
		for(int i = 0; i < img.getWidth(); i+=x)
			for(int j = 0; j < img.getHeight(); j++){
				img.setColor(i,j, blue);
				img.setColor(i+1,j, blue);
			}
		for(int n = 0; n < img.getHeight(); n+=x)
			for(int m = 0; m < img.getWidth(); m++){
				img.setColor(m,n, blue);
				img.setColor(m,n+1, blue);
			}
		
		for(int p = 0; p < 9; p++)
			for(int t = 0; t < 9; t++){
				if(matrizJogo[t][p] == 0)
					img.drawCenteredText(30 + p * 55, 30 + t * 55, "", 25, new Color(65,65,65));
				else
					img.drawCenteredText(30 + p * 55, 30 + t * 55, "" + matrizJogo[t][p], 25, new Color(0,0,0));
			}
	}
}