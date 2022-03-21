module com.td.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.td.tictactoe to javafx.fxml;
    exports com.td.tictactoe;
}