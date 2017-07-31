package com.rg.chessapplication.Chess.Board;

import com.rg.chessapplication.Chess.Figures.ChessPiece;
import com.rg.chessapplication.Chess.Figures.King;

/**
 * Created by HP PC on 23.12.2016.
 */

public class Cell {

    private int height;
    private int width;
    private ChessPiece figure;

    public Cell(int h, int w){
        height = h;
        width = w;
    }

    public ChessPiece getFigure() {
        return figure;
    }

    public void setFigure(ChessPiece figure) {
        this.figure = figure;
    }

    public boolean isEmpty(){
        if(figure == null) return true;
        return false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object obj) {
        Cell cell = (Cell) obj;

        if(cell.getHeight() == height && cell.getWidth() == width) return true;
        return false;
    }

    @Override
    public String toString() {
        return height + "  " + width;
    }
}
