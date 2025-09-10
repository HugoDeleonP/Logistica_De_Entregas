package br.com.logisticaentrega;

import br.com.logisticaentrega.service.ServiceCRUD;
import br.com.logisticaentrega.view.Viewer;

public class Main {
    public static void main(String[] args) {
        var crud = new ServiceCRUD();
        var viewer = new Viewer();

        int opcao;
        do{
            opcao = viewer.mainMenu();
            crud.mainRouter(opcao);
        }while(opcao != 0);

    }
}