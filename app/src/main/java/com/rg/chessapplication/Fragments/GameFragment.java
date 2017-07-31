package com.rg.chessapplication.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.rg.chessapplication.Chess.Board.ChessBoard;
import com.rg.chessapplication.ChessAdapter;
import com.rg.chessapplication.R;


/**
 * Created by HP PC on 23.12.2016.
 */

public class GameFragment extends Fragment {

    private ChessBoard board = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        board = (ChessBoard) getArguments().getSerializable("Board");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.board, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setAdapter(new ChessAdapter(getContext(), board));

        return view;
    }

}
