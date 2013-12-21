/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgrafica;

public class Algoritmo {

    //singleton
    private static Algoritmo instance = null;

    public static Algoritmo getInstance() {
        if (instance == null) {
            instance = new Algoritmo();
        }
        return instance;
    }
    
    // [2][4] = linha 2, coluna 4 na tabela do enunciado
    private boolean lefts[][];
    private boolean rights[][];
    
    private Algoritmo() {
        lefts = new boolean[8][8];
        rights = new boolean[8][8];

        //tabela left
        for (int i = 0; i < lefts.length; i++) {
            lefts[3][i] = true;
            lefts[4][i] = true;
            lefts[5][i] = true;
            lefts[7][i] = true;
        }
        lefts[3][0] = false;
        lefts[0][3] = true;
        //fim da tablea left
	
	//tabela right
        for (int i = 0; i < rights.length; i++) {
            rights[i][3] = true;
            rights[i][4] = true;
            rights[i][5] = true;
            rights[i][7] = true;
        }
        rights[3][0] = true;
        rights[0][3] = false;
        //fim da tabela right
    }

    public boolean[] result(boolean lb, boolean lm, boolean lf,
            boolean rb, boolean rm, boolean rf) {
        int rowIndex = 0;
        rowIndex += lb ? 2 * 2 : 0; //2^2
        rowIndex += lm ? 2     : 0; //2^1
        rowIndex += lf ? 1     : 0; //2^0

        int columnIndex = 0;
        columnIndex += rb ? 2 * 2 : 0; //2^2
        columnIndex += rm ? 2     : 0; //2^1
        columnIndex += rf ? 1     : 0; //2^0

        boolean leftResult = lefts[rowIndex][columnIndex];
        boolean rightResult = rights[rowIndex][columnIndex];

        return new boolean[]{leftResult, rightResult};
    }

}
