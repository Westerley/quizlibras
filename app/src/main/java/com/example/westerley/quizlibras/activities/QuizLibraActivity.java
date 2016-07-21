package com.example.westerley.quizlibras.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.westerley.quizlibras.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class QuizLibraActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private static final String TAG = "QuizLibras Activity";

    private List<String> listaImagens; //nomes de imagens do assets
    private List<String> imagensQuiz; //nomes das imagens de teste
    private Map<String, Boolean> categorias; //Quais categorias estão habilitadas
    private String respostaCorreta; //Armazena a resposta correta
    private int totalPalpites; //Total de Palpites
    private int totalCorretas; //Total de corretas
    private int numLinhas; //Numero de linhas de botoes
    private Random random; //gerador de numeros aleatórios
    private MediaPlayer mp; //Som dos botões
    private boolean verificaSom; //verifica se o som esta habilitado
    private ProgressBar progressoProgressBar; //progresso do teste, incrementa os palpites
    private ImageView imagemImageView; //Imagens de teste
    private TableLayout botoesTableLayout; //Tabela dos componentes de botoes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        verificaSom = params.getBoolean("habilitaSom");
        String titulo = params.getString("msg");

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle(titulo);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaImagens = new ArrayList<>();
        imagensQuiz = new ArrayList<>();
        categorias = new HashMap<>();
        random = new Random();
        numLinhas = 2;

        progressoProgressBar = (ProgressBar) findViewById(R.id.progressoProgressBar);
        imagemImageView = (ImageView) findViewById(R.id.imagemImageView);
        botoesTableLayout = (TableLayout) findViewById(R.id.botoesTableLayout);

        if (verificaSom) {
            mp = MediaPlayer.create(QuizLibraActivity.this, R.raw.som);
        }

        String[] nomesCategorias = getResources().getStringArray(R.array.categorias);

        for (String cat : nomesCategorias) {
            if (cat.equals(titulo)) {
                categorias.put(cat, true);
            }
        }

        resetarQuiz();

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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Tem certeza que deseja retornar?")
                    .setMessage("Ao retornar o jogo será finalizado")
                    .setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });


            builder.create();

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        return true;
    }

    private void resetarQuiz() {

        AssetManager assetManager = getAssets();

        listaImagens.clear();

        try {
            Set<String> listaCategoria = categorias.keySet();

            for (String categoria : listaCategoria) {
                if (categorias.get(categoria)) {
                    String[] paths = assetManager.list(categoria);

                    for (String path : paths) {
                        listaImagens.add(path.replace(".jpg", ""));
                    }
                }
            }

        } catch (IOException erro) {
            Log.e(TAG, "Erro ao carregar imagem");
        }

        totalCorretas = 0;
        totalPalpites = 0;
        imagensQuiz.clear();

        int cont = 1;
        int totalImagens = listaImagens.size();

        while (cont <= 10) {
            int aleatorio = random.nextInt(totalImagens);

            String nome = listaImagens.get(aleatorio);

            if (!imagensQuiz.contains(nome)) {
                imagensQuiz.add(nome);
                cont++;
            }
        }

        carregarProximaImagem();

    }

    private void carregarProximaImagem() {

        String proximaImagem = imagensQuiz.remove(0);
        respostaCorreta = proximaImagem;

        String categoria = proximaImagem.substring(0, proximaImagem.indexOf('-'));

        AssetManager assetManager = getAssets();

        InputStream stream;

        try {
            stream = assetManager.open(categoria + "/" + proximaImagem + ".jpg" );

            Drawable imagem = Drawable.createFromStream(stream, proximaImagem);
            imagemImageView.setImageDrawable(imagem);

        } catch (IOException erro) {
            Log.e(TAG, "Erro ao carregar imagem " + proximaImagem, erro);
        }

        for (int row = 0; row < botoesTableLayout.getChildCount(); row++) {
            ((TableRow) botoesTableLayout.getChildAt(row)).removeAllViews();
        }

        Collections.shuffle(listaImagens);

        int correta = listaImagens.indexOf(respostaCorreta);
        listaImagens.add(listaImagens.remove(correta));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Lista para verificar se o botão ja foi inserido
        List<String> listaBotao = new ArrayList<>();

        for (int row = 0; row < numLinhas; row++) {
            TableRow linhaAtual = getLinhaAtual(row);

            for (int column = 0; column < 3; column++) {
                Button novoBotao = (Button) inflater.inflate(R.layout.botoes, null);
                String nomeBotao = listaImagens.get(row + column);

                if (!listaBotao.contains(nomeBotao)) {
                    listaBotao.add(nomeBotao);
                    novoBotao.setText(getNomeBotao(nomeBotao));
                } else {
                    nomeBotao = listaImagens.get(row + column + 3);
                    listaBotao.add(nomeBotao);
                    novoBotao.setText(getNomeBotao(nomeBotao));
                }

                novoBotao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickBotao((Button) v);
                    }
                });

                linhaAtual.addView(novoBotao);
            }
        }

        int linha = random.nextInt(numLinhas);
        int coluna = random.nextInt(3);
        TableRow randomTableRow = getLinhaAtual(linha);

        String nome = getNomeBotao(respostaCorreta);
        if (!listaBotao.contains(nome)) {
            ((Button) randomTableRow.getChildAt(coluna)).setText(nome);
        }

    }

    private String getNomeBotao(String nomeBotao) {
        return nomeBotao.substring(nomeBotao.indexOf('-') + 1).replace('_', ' ');
    }

    private TableRow getLinhaAtual(int row) {
        return (TableRow) botoesTableLayout.getChildAt(row);
    }

    private void clickBotao(Button botao) {
        String respostafornecida = botao.getText().toString();
        String respostacerta = getNomeBotao(respostaCorreta);
        ++totalPalpites;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        progressoProgressBar.setProgress(totalPalpites);

        if (respostafornecida.equals(respostacerta)) {
            ++totalCorretas;

            if (totalPalpites < 10) {

                if (verificaSom) {
                    mp.start();
                }
                builder.setTitle("Muito bem!!!")
                        .setMessage("Resposta Correta ... ")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        carregarProximaImagem();
                                        SystemClock.sleep(1000);
                                    }
                                });
                            }
                        });
                builder.create();

                AlertDialog dialog = builder.create();
                dialog.show();


            } else {

                if (verificaSom) {
                    mp.start();
                }

                builder.setTitle("Parábens!!!")
                        .setMessage("Você Concluiu o Jogo ...\nTotal de respostas corretas: " + totalCorretas)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builder.create();

                AlertDialog dialog = builder.create();
                dialog.show();

            }

        } else {

            if (verificaSom) {
                mp.start();
            }

            if (totalPalpites < 10) {

                builder.setTitle("Ops!!!")
                        .setMessage("Resposta Incorreta ... ")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        carregarProximaImagem();
                                        SystemClock.sleep(1000);
                                    }
                                });
                            }
                        });
                builder.create();

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {

                builder.setTitle("Ops!!!")
                        .setMessage("Você Concluiu o Jogo ...\nTotal de respostas corretas: " + totalCorretas)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                builder.create();

                AlertDialog dialog = builder.create();
                dialog.show();

            }

        }

    }
}
