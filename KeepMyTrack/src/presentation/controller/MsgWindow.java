package presentation.controller;

/**
 * Para crear ventanas Alert
 *
 * @author José Manuel García && Ángel Santillana Sánchez
 */

import javafx.scene.control.Alert;

public class MsgWindow extends Alert{

    public MsgWindow(Alert.AlertType type, String title, String head, String content) {
        super(type);
        super.setTitle(title);
        super.setHeaderText(head);
        super.setContentText(content);
    }
    
}
