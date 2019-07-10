/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univaq.tirocini.data.model;

import com.univaq.tirocini.vo.IVA;

/**
 *
 * @author carlo
 */
public interface Azienda {
       
    public String getNome();

    public void setNome(String nome);

    public String getSede();

    public void setSede(String sede);

    public IVA getIva();

    public void setIva(IVA iva);

    public String getForoCompetenza();

    public void setForoCompetenza(String foroCompetenza);

    public String getNomeResponsabile();

    public void setNomeResponsabile(String nomeResponsabile);

    public String getCognomeResponsabile();

    public void setCognomeResponsabile(String cognomeResponsabile);

    public String getTelefonoResponsabile();

    public void setTelefonoResponsabile(String telefonoResponsabile);

    public String getEmailResponsabile();

    public void setEmailResponsabile(String emailResponsabile);

    public String getNomeCognomeLegale();

    public void setNomeCognomeLegale(String nomeCognomeLegale);

    public int getVoto();

    public void setVoto(int voto);

    public boolean isConvenzionata();

    public void setConvenzionata(boolean Convenzionata);
}
