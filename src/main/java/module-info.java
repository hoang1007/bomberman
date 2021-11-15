module uet.gryffindor {
  requires javafx.controls;
  requires javafx.fxml;
  requires transitive javafx.graphics;
  requires deeplearning4j.nn;
  requires deeplearning4j.core;
  requires nd4j.api;

  opens uet.gryffindor.scenes to javafx.fxml;
  
  exports uet.gryffindor;
}
