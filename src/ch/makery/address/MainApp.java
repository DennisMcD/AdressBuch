package ch.makery.address;

import java.io.IOException;

import ch.makery.address.model.Person;
import ch.makery.address.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    /**
     * Daten als observable list of Persons - neue Collection Klasse.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Konstructor
     */
    public MainApp() {
        // Beispieldaten
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }

    
    /**
     * Getter von Observable list of Persons ausgeben.
     * A list that enables listeners to track changes when they occur
     * We are working with JavaFX view classes that need to be informed about any changes made to the list of persons. 
     * This is important, since otherwise the view would not be in sync with the data. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }
    

    @Override
    // Ruft sonstige Methoden auf
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        
        // �u�ere Fenster -> Men�bar
        initRootLayout();
        // Inneres Fenster
        showPersonOverview();
    }
    
    
    
    
    /**
     * Initializes the root layout --> �u�eres Fenster
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout --> inneres Fenster
     */
    
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}