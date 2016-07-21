package com.example.westerley.quizlibras.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.westerley.quizlibras.R;

public class DesenvolvedorActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desenvolvedor);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle(R.string.tituloDesenvolvedor);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView infoTextView = (TextView) findViewById(R.id.infoTextView);
        TextView descricaoTextView = (TextView) findViewById(R.id.descricaoTextView);
        infoTextView.setText("Sobre o desenvolvedor");
        descricaoTextView.setText(
                "O aplicativo 'Libras Brincando' foi desenvolvido pelo aluno Westerley Reis. \n" +
                        "Formação: Acadêmico do curso de Sistemas de Informação - 6º Semestre. \n" +
                        "Perfil: Interesses em desenvolvimento Web com HTML, PHP, CSS, JQuery e MySQL / desenvolvimento Mobile (Android). \n");

    }

    public void abrirLinkedin(View view) {
        String linkedin = "https://br.linkedin.com/in/westerley-da-silva-reis-9140b79a";
        Uri uri = Uri.parse(linkedin);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
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
