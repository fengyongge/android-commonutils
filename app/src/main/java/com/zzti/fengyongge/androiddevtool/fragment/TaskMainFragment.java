package com.zzti.fengyongge.androiddevtool.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzti.fengyongge.androiddevtool.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskMainFragment extends Fragment {


    public TaskMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_main, container, false);
    }

}
