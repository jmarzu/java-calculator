import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    private Stage window;
    private TextField textField;
    private String calcString = "";
    private Label result;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primary) throws Exception {
        window = primary;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> closeProgram());

        // top of calculator, shows current value being clicked
        VBox topOfCalculator = new VBox();
        topOfCalculator.setPadding(new Insets(5, 5, 5, 5));
        topOfCalculator.setSpacing(5);
        textField = new TextField();
        result = new Label();

        // calculator buttons
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 0, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // row 1
        Button btn1 = new Button("1");
        btn1.setOnAction(e -> getButtonValue("1"));
        GridPane.setConstraints(btn1, 0, 1);

        Button btn2 = new Button("2");
        btn2.setOnAction(e -> getButtonValue("2"));
        GridPane.setConstraints(btn2, 1, 1);

        Button btn3 = new Button("3");
        btn3.setOnAction(e -> getButtonValue("3"));
        GridPane.setConstraints(btn3, 2, 1);

        Button btnClear = new Button("AC");
        btnClear.setOnAction(e -> clearCalculator());
        GridPane.setConstraints(btnClear, 3, 1);

        // row 2
        Button btn4 = new Button("4");
        btn4.setOnAction(e -> getButtonValue("4"));
        GridPane.setConstraints(btn4, 0, 2);

        Button btn5 = new Button("5");
        btn5.setOnAction(e -> getButtonValue("5"));
        GridPane.setConstraints(btn5, 1, 2);

        Button btn6 = new Button("6");
        btn6.setOnAction(e -> getButtonValue("6"));
        GridPane.setConstraints(btn6, 2, 2);

        Button btnEquals = new Button("=");
        btnEquals.setOnAction(e -> {
            try {
                evaluateCalculator();
            } catch (ScriptException e1) {
                e1.printStackTrace();
            }
        });
        GridPane.setConstraints(btnEquals, 3, 2);

        // row 3
        Button btn7 = new Button("7");
        btn7.setOnAction(e -> getButtonValue("7"));
        GridPane.setConstraints(btn7, 0, 3);

        Button btn8 = new Button("8");
        btn8.setOnAction(e -> getButtonValue("8"));
        GridPane.setConstraints(btn8, 1, 3);

        Button btn9 = new Button("9");
        btn9.setOnAction(e -> getButtonValue("9"));
        GridPane.setConstraints(btn9, 2, 3);

        // row 4
        Button btnMinus = new Button("-");
        btnMinus.setOnAction(e -> getButtonValue(" - "));
        GridPane.setConstraints(btnMinus, 0, 4);

        Button btn0 = new Button("0");
        btn0.setOnAction(e -> getButtonValue("0"));
        GridPane.setConstraints(btn0, 1, 4);

        Button btnAdd = new Button("+");
        btnAdd.setOnAction(e -> getButtonValue(" + "));
        GridPane.setConstraints(btnAdd, 2, 4);

        grid.getChildren().addAll(textField, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnMinus, btnAdd, btnClear, btnEquals);

        topOfCalculator.getChildren().addAll(textField, result);
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topOfCalculator);
        borderPane.setCenter(grid);

        Scene scene = new Scene(borderPane, 200, 250);

        window.setScene(scene);
        window.setTitle("Log In");
        window.show();
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Confirm", "Are you sure you want to close?");

        if (answer) {
            window.close();
        }
    }

    private void getButtonValue(String value) {
        calcString += value;
        textField.setText(calcString);
    }

    private void clearTextField() {
        calcString = "";
        textField.setText("");
    }

    private void clearCalculator() {
        calcString = "";
        textField.setText("");
        result.setText("");
    }

    private void evaluateCalculator() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        
        Object answer = scriptEngine.eval(calcString);
        result.setText(calcString + " = " + answer.toString());

        // clean up calculator
        clearTextField();
    }
}
