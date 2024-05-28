package outils;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.LocalDate;

public class DateChecker {

    private DateChecker(){
    }

    public static void setBeginDateBounds(DatePicker begin_date, LocalDate end_date ){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean cond = !item.isBefore(end_date);
                        if (cond){
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;");
                        }else{
                            setDisable(false);
                            setStyle("-fx-background-color: #CCFFFF;");
                            setStyle("-fx-font-fill: black;");
                        }
                    }
                };
            }
        };
        begin_date.setDayCellFactory(dayCellFactory);
    }

    public static void setEndDateBounds(DatePicker end_date, LocalDate begin_date ){
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        boolean cond = !item.isAfter(begin_date);
                        if (cond){
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;");
                        }else{
                            setDisable(false);
                            setStyle("-fx-background-color: #CCFFFF;");
                            setStyle("-fx-font-fill: black;");
                        }
                    }
                };
            }
        };
        end_date.setDayCellFactory(dayCellFactory);
    }
}
