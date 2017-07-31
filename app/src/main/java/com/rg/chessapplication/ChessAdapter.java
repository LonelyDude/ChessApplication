package com.rg.chessapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.rg.chessapplication.Chess.Board.Cell;
import com.rg.chessapplication.Chess.Board.ChessBoard;
import com.rg.chessapplication.Fragments.FigureShadowBuilder;

import java.util.ArrayList;

/**
 * Created by HP PC on 29.12.2016.
 */

public class ChessAdapter extends BaseAdapter implements View.OnDragListener, View.OnLongClickListener{
    private ChessBoard board;
    private ArrayList<Cell> cells;
    private Context context;
    private LayoutInflater inflater;
    private Drawable drawable;
    private ArrayList<ImageView> figureImages = new ArrayList<>(64);

    public ChessAdapter(Context context, ChessBoard board){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.board = board;
        this.cells = board.getCells();
    }

    @Override
    public int getCount() {
        return cells.size();
    }

    @Override
    public Object getItem(int position) {
        return cells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item, parent, false);
        }

        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        Cell cell = (Cell) getItem(position);

        imageView.setTag(cell);

        figureImages.add(imageView);

        fillColor(imageView, cell);

        pasteImage(imageView, cell);

        imageView.setOnLongClickListener(this);
        imageView.setOnDragListener(this);

        return view;
    }

    private void fillColor(ImageView imageView, Cell cell){
        if((cell.getHeight() + cell.getWidth()) % 2 == 0){
            imageView.setBackgroundColor(Color.parseColor("#33691E"));
        }
        else{
            imageView.setBackgroundColor(Color.parseColor("#F1F8E9"));
        }
    }

    private void pasteImage(ImageView imageView, Cell cell){
        if(cell.getFigure() != null){
            switch (cell.getFigure().toString()){
                case "Pawn":
                    if(cell.getFigure().isColour() == true) imageView.setImageResource(R.drawable.pawn_white);
                    else{
                        imageView.setImageResource(R.drawable.pawn_black);
                        imageView.setEnabled(false);
                    }
                    break;
                case "Castle":
                    if(cell.getFigure().isColour() == true) imageView.setImageResource(R.drawable.castle_white);
                    else{
                        imageView.setImageResource(R.drawable.castle_black);
                        imageView.setEnabled(false);
                    }
                    break;
                case "Knight":
                    if(cell.getFigure().isColour() == true) imageView.setImageResource(R.drawable.knight_white);
                    else{
                        imageView.setImageResource(R.drawable.knight_black);
                        imageView.setEnabled(false);
                    }
                    break;
                case "Bishop":
                    if(cell.getFigure().isColour() == true) imageView.setImageResource(R.drawable.bishop_white);
                    else{
                        imageView.setImageResource(R.drawable.bishop_black);
                        imageView.setEnabled(false);
                    }
                    break;
                case "Queen":
                    if(cell.getFigure().isColour() == true) imageView.setImageResource(R.drawable.queen_white);
                    else{
                        imageView.setImageResource(R.drawable.queen_black);
                        imageView.setEnabled(false);
                    }
                    break;
                case "King":
                    if(cell.getFigure().isColour() == true) imageView.setImageResource(R.drawable.king_white);
                    else{
                        imageView.setImageResource(R.drawable.king_black);
                        imageView.setEnabled(false);
                    }
                    break;
            }
            switchColour(board.isStepColour());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onLongClick(View v) {
        Cell cell = (Cell) v.getTag();
        if(cell.getFigure() == null) return false;

        ImageView imageView = (ImageView) v;

        Drawable clone = imageView.getDrawable().getConstantState().newDrawable();
        drawable = clone;

        FigureShadowBuilder shadow = new FigureShadowBuilder(imageView);

        shadow.getView().setAlpha(1);

        enableAll();

        v.startDrag(null,shadow,v,0);

        imageView.setImageResource(0);

        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        View view = (View) event.getLocalState();
        Cell c = (Cell) v.getTag();
        switch (event.getAction()){
            case DragEvent.ACTION_DROP:
                Log.d("TAG", c.toString());
                return checkMove(view,v);

        }
        return true;
    }

    private boolean checkMove(View fromView, View toView){
        ImageView fromImage = (ImageView) fromView;
        ImageView toImage = (ImageView) toView;

        Cell from = (Cell) fromView.getTag();
        Cell to = (Cell) toView.getTag();

        boolean colour = from.getFigure().isColour();

        if(board.step(from,to,colour)) {
            toImage.setEnabled(true);
            toImage.setImageDrawable(drawable);

            switchColour(!colour);

            if(board.mate(!colour)) Toast.makeText(context,"Check and mate!", Toast.LENGTH_LONG).show();
            else if(board.checkToKing(!colour)) Toast.makeText(context,"Check!", Toast.LENGTH_LONG).show();

            return true;
        }

        switchColour(colour);
        fromImage.setImageDrawable(drawable);
        return false;
    }

    private void switchColour(boolean colour){
        for(ImageView imageView: figureImages){
            Cell cell = (Cell) imageView.getTag();
            if(cell.getFigure() != null){
                if(cell.getFigure().isColour() == !colour){
                    imageView.setEnabled(false);
                }
                else {
                    imageView.setEnabled(true);
                }
            }
        }
    }

    private void enableAll(){
        for(ImageView imageView: figureImages){
            Cell cell = (Cell) imageView.getTag();
            if(cell.getFigure() != null) imageView.setEnabled(true);
        }
    }

}
