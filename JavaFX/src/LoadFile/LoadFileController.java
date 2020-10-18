package LoadFile;

import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class LoadFileController {

    @FXML private Label progressLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Label percentLabel;

    public void bindTask(Task task) {
        this.progressLabel.textProperty().bind(task.messageProperty());
        this.progressBar.progressProperty().bind(task.progressProperty());
        this.percentLabel.textProperty().bind(Bindings.concat(Bindings.format(
                "%.0f", Bindings.multiply(task.progressProperty(), 100)), "%"));

        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            clearBinds();
        });
    }

    public void clearBinds(){
        this.percentLabel.textProperty().unbind();
        this.progressBar.progressProperty().unbind();
        this.progressLabel.textProperty().unbind();
    }
}