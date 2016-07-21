package com.example.westerley.quizlibras.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.westerley.quizlibras.classes.Categoria;
import com.example.westerley.quizlibras.activities.MainActivity;
import com.example.westerley.quizlibras.activities.QuizLibraActivity;
import com.example.westerley.quizlibras.R;
import com.example.westerley.quizlibras.adapters.CategoriaAdapter;
import com.example.westerley.quizlibras.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;

public class CategoriaFragment extends Fragment implements RecyclerViewOnClickListenerHack {

    private RecyclerView mRecyclerView;
    private List<Categoria> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mList = ((MainActivity) getActivity()).getSetCategoriaList(2);
        CategoriaAdapter adapter = new CategoriaAdapter(getActivity(), mList);
        adapter.setRecyclerViewOnClickListenerHack(this);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClickListener(View view, int posicao) {

        boolean habilitaSom = ((MainActivity) getActivity()).getHabilitaSom();
        String nome = mList.get(posicao).getNome();

        Intent intent = new Intent(getActivity(), QuizLibraActivity.class);

        Bundle params = new Bundle();
        params.putString("msg", nome);
        params.putBoolean("habilitaSom", habilitaSom);
        intent.putExtras(params);

        startActivity(intent);
    }
}
