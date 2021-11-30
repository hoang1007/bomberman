module uet.gryffindor {
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires javafx.media;
  requires java.desktop;

  opens uet.gryffindor.scenes to javafx.fxml;
  
  exports uet.gryffindor;
}
