package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    ArrayList<String> keywordCars;
    ArrayList<String> keywordTracks;
    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("SetupMover");
        var source = new TextField("Source");
        var destination = new TextField("Destination");


        Button button = new Button("Move!");
        VBox vbox = new VBox(source,destination,button);
        button.setMinWidth(50);

        button.setOnAction(action -> {
            if(folderExist(source)){
                try {
                    filter(source,destination);
                } catch (IOException e) {
                    //TODO fehler
                    e.printStackTrace();
                }
            }else{
               //TODO fehler
            }
        });

        Scene scene = new Scene(vbox, 500, 375);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private boolean folderExist(TextField source) {
        File sourceFolder = new File(source.getText());
        return sourceFolder.exists() && sourceFolder.isDirectory();
    }



    public void filter(TextField src, TextField dest) throws IOException {
        File sourceFolder = new File(src.getText());
        File destinationFolder = new File(dest.getText());
        File[] setups = sourceFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".sto"));
        System.out.println("Alle Sets" + Arrays.toString(setups));
        if(setups!=null) {
            for (File f : setups) {
                move(sourceFolder,f.getName(), destinationFolder);
            }
        }
    }

    private void move(File src, String name, File dest) throws IOException {
        String car = "";
        String track = "";
        String[] names = name.split("_");
        System.out.println("Ein Set" + Arrays.toString(names));
        for (String s:names) {
            if(keywordCars.contains(s)) car = s;
            if(keywordTracks.contains(s)) track = s;
        }
        if(!car.isEmpty() && !track.isEmpty()) {
            File finalDest = new File(dest + car + track);
            if (!finalDest.exists()) {
                finalDest.mkdirs();
            }
            Files.move(Paths.get(src +"\\"+ name), Paths.get(finalDest +"\\"+ car +"\\"+ name));
            System.exit(1);
        }else{
//            TODO fehler
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
