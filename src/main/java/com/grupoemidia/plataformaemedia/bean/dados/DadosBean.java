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

    public String diferencaDeLeads() {
        if (verificaTotalMaiorQueZero()) {
            if (!verificaNovosMenorQueZero()) {
                if (verificaSeTotalMaiorQueNovos()) {
                    return "+" + String.valueOf(dados.getTotalDeLeads() - dados.getNovosLeads()) + "(";
                } else {
                    return "Inconsistência de dados, total de Leads consta no servidor menor que os de novos leads ";
                }
            } else {
                return "Inconsistência de dados, no servidor o número de novos Leads está registrado com valor menor que zero";
            }
        }
        return "Inconsistência de dados, no servidor o número total de Leads está registrado com valor menor ou igual a zero";
    }

    public String porcentagemDeDiferencaDeLeads() {
        if (verificaTotalMaiorQueZero()) {
            if (!verificaNovosMenorQueZero()) {
                if (verificaSeTotalMaiorQueNovos()) {
                    double totalDeLeads = dados.getTotalDeLeads();
                    return String.format("%.2f", ((dados.getNovosLeads() * 100) / totalDeLeads)) + "%)";
                } else {
                    return " ";
                }
            }
            return " ";
        }
        return " ";
    }

    public Dados getDados() {
        return dados;
    }

    public void setDados(Dados dados) {
        this.dados = dados;
    }

    private boolean verificaTotalMaiorQueZero() {
        return dados.getTotalDeLeads() > 0;
    }

    private boolean verificaNovosMenorQueZero() {
        return dados.getNovosLeads() < 0;
    }

    private boolean verificaSeTotalMaiorQueNovos() {
        return dados.getTotalDeLeads() >= dados.getNovosLeads();
    }

    public String retornaCor() {
        if (verificaTotalMaiorQueZero()) {
            if (!verificaNovosMenorQueZero()) {
                if (verificaSeTotalMaiorQueNovos()) {
                    return "#1edc71";
                }
                return "#ee4266";
            }
            return "#ee4266";
        }
        return "#ee4266";
    }
}
