/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uaspt2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 *
 * @author HD
 */
public class itemproductController implements Initializable{
    @FXML
    private ImageView barangfoto;

    @FXML
    private Label barangharga;

    @FXML
    private Label barangnama;
    
    private productdata prodData;

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
