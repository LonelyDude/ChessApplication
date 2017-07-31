package com.rg.chessapplication.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.rg.chessapplication.Chess.Board.ChessBoard;
import com.rg.chessapplication.Databases.DataHelper;
import com.rg.chessapplication.Databases.LocalDatabaseHandlerThread;
import com.rg.chessapplication.R;

/**
 * Created by HP PC on 16.01.2017.
 */

public class StartGameFragment extends Fragment {
    private Button startGame;
    private Button continueGame;
    private ChessBoard board = null;
    private SQLiteDatabase database;
    private ProgressBar progressBar;
    private LocalDatabaseHandlerThread databaseThread;
    private Handler messageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            board.setEmpty((Boolean) msg.obj);
            board.setQueryResult(true);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(database == null) database = new DataHelper(getContext()).getWritableDatabase();

        databaseThread = new LocalDatabaseHandlerThread(database, messageHandler);
        databaseThread.start();
        databaseThread.getLooper();

        if(board == null) board = new ChessBoard(databaseThread);

    }

    @Override
    public void onStart() {
        super.onStart();

        new AsyncBoard().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.start_game_fragment, container, false);

        startGame = (Button) view.findViewById(R.id.newGame);
        continueGame = (Button) view.findViewById(R.id.continueGame);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                board.fillBoardWithFigures(false);
                openFragment();
            }
        });

        continueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                board.fillBoardWithFigures(true);
                openFragment();
            }
        });

        return view;
    }

    private void openFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putSerializable("Board", board);

        Fragment game = new GameFragment();
        game.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, game).addToBackStack(null).commit();
    }

    private class AsyncBoard extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            startGame.setEnabled(false);
            continueGame.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            databaseThread.checkData();

            while (!board.isQueryResult()){
                try {
                    Log.d("Second thread", "Sleep...zzz...");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Log.d("Second thread", "Awake");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            startGame.setEnabled(true);
            continueGame.setEnabled(true);
            if(! board.isEmpty()) continueGame.setVisibility(Button.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseThread.quit();
    }
}
