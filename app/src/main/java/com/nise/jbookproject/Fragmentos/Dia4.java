package com.nise.jbookproject.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nise.jbookproject.Modulos.Sala;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Dia4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dia4 extends Fragment {

    private static final String SALA_ACTUAL = "param1";
    private static final String FECHA_ACTUAL = "param2";

    private Sala mParam1;
    private Date mParam2;

    private static final String TAG = "Dia4";

    ArrayList<Date> horas;
    RecyclerView recyclerView;

    private TextView titulo;

    // TODO: poner adapterHora

    public Dia4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dia4.
     */
    // TODO: Rename and change types and number of parameters
    public static Dia4 newInstance(Sala param1, Date param2) {
        Dia4 fragment = new Dia4();
        Bundle args = new Bundle();
        args.putSerializable(SALA_ACTUAL, param1);
        args.putSerializable(FECHA_ACTUAL, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Sala) getArguments().getSerializable(SALA_ACTUAL);
            mParam2 = (Date) getArguments().getSerializable(FECHA_ACTUAL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dia4, container, false);
        titulo = v.findViewById(R.id.tituloDia4);
        titulo.setText(mParam2.toString());
        return v;
    }

}
