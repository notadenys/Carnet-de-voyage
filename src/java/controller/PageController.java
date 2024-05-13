package controller;

import carnet.Carnet;
import carnet.PageDay;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

public class PageController implements Observateur {
    @FXML
    private TextField title;
    private TextField description;

    private Carnet carnet;

    public PageController(Carnet carnet) {
        this.carnet = carnet;
        carnet.addObserver(this);
    }

    @FXML
    void update() {
        carnet.notifyObservers();
    }

    @Override
    public void reagir() {
        if (carnet.getCurrentPage() != 0) {
            PageDay page = carnet.getCurrentPageDay();
            assert title != null;
            page.setTitle(title.getText());
            page.setText(title.getText());
        }
    }
}
