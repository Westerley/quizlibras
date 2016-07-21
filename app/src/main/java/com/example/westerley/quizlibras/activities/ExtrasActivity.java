package com.example.westerley.quizlibras.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.westerley.quizlibras.R;

public class ExtrasActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle(R.string.tituloExtra);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView infoTextView = (TextView) findViewById(R.id.infoTextView);
        TextView descricaoTextView = (TextView) findViewById(R.id.descricaoTextView);
        infoTextView.setText("Finalidade do aplicativo");
        descricaoTextView.setText(
                "O aplicativo 'Libras Brincando' foi desenvolvido como requisito parcial para obtenção de nota na disciplinas de LIBRAS " +
                        "ministrada pelo professor Mauricio Loubet");
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
