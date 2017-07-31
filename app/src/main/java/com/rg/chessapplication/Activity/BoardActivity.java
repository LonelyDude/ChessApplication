package com.rg.chessapplication.Activity;

import android.support.v4.app.Fragment;

import com.rg.chessapplication.Fragments.StartGameFragment;

/**
 * Created by HP PC on 23.12.2016.
 */

public class BoardActivity extends AbstractActivity {
    @Override
    Fragment createFragment() {
        return new StartGameFragment();
    }
}
