package UIRS.flightSimulation.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AboutTheProgram {
    @FXML
    public TextArea textArea;

    @FXML
    public void initialize () {
    textArea.setText(getTextInfofromFile()); //задаем текст из файла на информационное поле
    }

    /**закрытие окна по кнопке*/
    public void onBtClose(ActionEvent actionEvent) {
        Stage stage = (Stage) textArea.getScene().getWindow();
        stage.close();
    }

    /**метод считывает текст из /resources/info/ProjectInfo.txt */
    private String getTextInfofromFile () {
        StringBuilder text= new StringBuilder("");
        File file = new File("src/main/resources/info/ProjectInfo.txt");
        try {
            Scanner scanner = new Scanner(file);
            do {
                text.append(scanner.nextLine()).append("\n");
            }while (scanner.hasNext());
            } catch (FileNotFoundException e) {
            System.out.println("файл с информацией пуст");
            //e.printStackTrace();
        }
        return text.toString();
    }
}
