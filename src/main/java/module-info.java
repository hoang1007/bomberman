module uet.gryffindor {
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires javafx.media;

  opens uet.gryffindor.scenes to javafx.fxml;
  
  exports uet.gryffindor;
}
