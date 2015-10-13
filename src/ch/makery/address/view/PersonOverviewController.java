package ch.makery.address.view;

/*
 * We must put it in the same package as the PersonOverview.fxml, 
 * otherwise the SceneBuilder won't find it.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

public class PersonOverviewController {
	
	/*
	 * We'll add some instance variables that give us access to the table and the labels inside the view. The fields and some methods have a special @FXML annotation. This is necessary for the fxml file to have access to private fields and private methods. 
	 * After we have everything set up in the fxml file, the application will automatically fill the variables when the fxml file is loaded. 
	 */
	
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * 
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    
    
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * 
     * To get informed when the user selects a person in the person table, we need to listen for changes.
     * There is an interface in JavaFX called ChangeListener with one method called changed(...). The method has three parameters: observable, oldValue, and newValue.
     * we will create such a ChangeListener using a Java 8 lambda expression. Let's add a few lines to the initialize() method in PersonOverviewController
     */
    
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
    	/*
    	 * The setCellValueFactory(...) that we set on the table colums are used to determine which field inside the Person objects should be used for the particular column. 
    	 * The arrow -> indicates that we're using a Java 8 feature called Lambdas. 
    	 */
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        
        // With showPersonDetails(null); we reset the person details.
        showPersonDetails(null);

        /* Listen for selection changes and show the person details when changed.
         * With personTable.getSelectionModel... we get the selectedItemProperty of the person table and add a listener to it. 
         * Whenever the user selects a person in the table, our lambda expression is executed. We take the newly selected person and pass it to the showPersonDetails(...) method.
         */
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }
    
    
        
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
        	
            // Fill the labels with info from the person object.
        	/*
        	 *  Go trough all the labels and set the text using setText(...) with details from the person. 
        	 *  If null is passed as parameter, all labels should be cleared.
        	 */
        	
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());

            // We need a way to convert the birthday into a String! 
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
            
        } else {
        	
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    
    
    /**
     * Called when the user clicks on the delete button.
     * 
     * There will be an ArrayIndexOutOfBoundsException because it could not remove a person item at index -1. 
     * The index -1 was returned by getSelectedIndex() - which means that there was no selection.
     * 
     * 
     */
    
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
    
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
    
}