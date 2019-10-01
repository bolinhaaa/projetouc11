package com.back4app.quickstartexampleapp.Util;

import java.util.HashMap;

public class ParseErros {

    //Essa classe vai exibir e tratar alguns códigos de erro ao cadastrar
    private HashMap<Integer,String> erros;

    public ParseErros() {
        this.erros=new HashMap<>();
        this.erros.put(1,"Preencha todos os campos!");
        this.erros.put(101,"Usuário e/ou Senha inválidos!");
        this.erros.put(200,"Usuário e/ou Senha em branco!");
        this.erros.put(201,"A Senha não foi preenchida!");
        this.erros.put(202,"O Usuário já existe. Escolha outro UserName.");
        //daqui inserir os outros...se houver..
    }

    public String getErro(int codErro){
        return this.erros.get(codErro);
    }
}
