package com.example.westerley.quizlibras.classes;

public class Categoria {

    private String nome;
    private String info;
    private int imagem;

    public Categoria(String nome, String info, int imagem) {
        this.nome = nome;
        this.info = info;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
