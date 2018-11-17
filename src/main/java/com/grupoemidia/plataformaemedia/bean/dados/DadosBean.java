package com.grupoemidia.plataformaemedia.bean.dados;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grupoemidia.plataformaemedia.model.Dados;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class DadosBean implements Serializable {
    private Dados dados;

    public void getDadosServidor() {
        Client c = Client.create();
        WebResource wr = c.resource("https://5bef11775b9d1a001324457d.mockapi.io/api/v1/dados");
        Gson gson = new Gson();
        String json = wr.get(String.class);
        dados = gson.fromJson(json, new TypeToken<Dados>() {
        }.getType());

    }

    public int diferençaDeLeads() {
        return dados.getTotalDeLeads() - dados.getNovosLeads();
    }

    public String porcentagemDeDiferencaDeLeads() {
        if(dados.getTotalDeLeads() != 0){
            double totalDeLeads = dados.getTotalDeLeads();
            return String.format("%.2f",((dados.getNovosLeads() * 100) / totalDeLeads));
        }
        return "Não temos Leads no momento";
    }

    public Dados getDados() {
        return dados;
    }

    public void setDados(Dados dados) {
        this.dados = dados;
    }
}
