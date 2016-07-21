package com.example.westerley.quizlibras.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.westerley.quizlibras.R;

public class InfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle(R.string.tituloInfo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView infoTextView = (TextView) findViewById(R.id.infoTextView);
        TextView descricaoTextView = (TextView) findViewById(R.id.descricaoTextView);
        infoTextView.setText("Informações sobre o jogo");
        descricaoTextView.setText(
                "O aplicativo 'Libras Brincando' tem por finalidade ensinar Libras sem ser cansativo. \n" +
                "O Jogo foi dividido em duas categorias (Animais e Frutas) cada categoria é composta por uma imagem e uma sequencia de alternativas " +
                "referente a imagem correspondente. \n" +
                "São no total 10 perguntas que ao termino do teste é mostrado o total de acertos");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        return true;
    }

}
