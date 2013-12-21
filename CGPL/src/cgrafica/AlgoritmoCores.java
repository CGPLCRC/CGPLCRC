/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgrafica;

public class AlgoritmoCores {

    //singleton
    private static AlgoritmoCores instance = null;

    public static AlgoritmoCores getInstance() {
        if (instance == null) {
            instance = new AlgoritmoCores();
        }
        return instance;
    }
    
    // [2][4] = linha 2, coluna 4 na tabela do enunciado
    private OrigemCarro lefts[][];
    private OrigemCarro rights[][];
    
    private AlgoritmoCores() {
        lefts = new OrigemCarro[8][8];
        rights = new OrigemCarro[8][8];

        //tabela left
        for (int i = 0; i < lefts.length; i++) {
            lefts[0][i] = OrigemCarro.EMPTY;
	    lefts[1][i] = OrigemCarro.EMPTY;
	    lefts[2][i] = OrigemCarro.EMPTY;
	    lefts[6][i] = OrigemCarro.EMPTY;
	    
	    lefts[3][i] = OrigemCarro.STAY;
	    lefts[7][i] = OrigemCarro.STAY;
	    
            lefts[4][i] = OrigemCarro.BACK;
            lefts[5][i] = OrigemCarro.BACK;
        }
        lefts[3][0] = OrigemCarro.EMPTY;
        lefts[0][3] = OrigemCarro.RIGHT;
        //fim da tablea left
	
	//tabela right
        for (int i = 0; i < rights.length; i++) {
            
	    rights[i][0] = OrigemCarro.EMPTY;
	    rights[i][1] = OrigemCarro.EMPTY;
	    rights[i][2] = OrigemCarro.EMPTY;
	    rights[i][6] = OrigemCarro.EMPTY;
	    
	    rights[i][3] = OrigemCarro.STAY;
	    rights[i][7] = OrigemCarro.STAY;
	    
            rights[i][4] = OrigemCarro.BACK;
            rights[i][5] = OrigemCarro.BACK;
        }
        rights[3][0] = OrigemCarro.LEFT;
        rights[0][3] = OrigemCarro.EMPTY;
        //fim da tabela right
    }

    public OrigemCarro[] result(boolean lb, boolean lm, boolean lf,
            boolean rb, boolean rm, boolean rf) {
        int rowIndex = 0;
        rowIndex += lb ? 2 * 2 : 0; //2^2
        rowIndex += lm ? 2     : 0; //2^1
        rowIndex += lf ? 1     : 0; //2^0

        int columnIndex = 0;
        columnIndex += rb ? 2 * 2 : 0; //2^2
        columnIndex += rm ? 2     : 0; //2^1
        columnIndex += rf ? 1     : 0; //2^0

        OrigemCarro leftResult = lefts[rowIndex][columnIndex];
        OrigemCarro rightResult = rights[rowIndex][columnIndex];

        return new OrigemCarro[]{leftResult, rightResult};
    }
    
    public enum OrigemCarro {
	EMPTY, BACK, STAY, RIGHT, LEFT
    }

}
