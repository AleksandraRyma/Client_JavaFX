package checks;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogAlert {

  public static void samePasswordsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Регистрация");
        alert.setContentText("Пароли не совпадают");
        alert.showAndWait();
    }

    public static void showAlertExistLoginEmployee(Object meg){
      Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Регистрация");
        String message = String.valueOf(meg);
        alert.setContentText(message);
       // alert.setContentText("Такой пользователь уже существует!\nПридумайте пожалуйста другой логин");
        alert.showAndWait();
    }

    public static void showAlertAuthorisationErrow(Object meg){//вывод ошибок при авторизации пользователя
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Вход");
        String message = String.valueOf(meg);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showAlertNoDataInAuthRegForm(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText("Заполните все поля");
        alert.showAndWait();
    }

    public static void showAlertShortPassword(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText("Пароль должен содержать хотя бы 4 символа");
        alert.showAndWait();
    }


    public static boolean showConfirmationDialog(String title, String header, String content) {//пробное подтверждение выхода
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType yesButton = new ButtonType("Да");
        ButtonType noButton = new ButtonType("Нет");
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    public static void showAlertIsValidName(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
