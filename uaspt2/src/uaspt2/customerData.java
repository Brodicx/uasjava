/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uaspt2;

import java.util.Date;

/**
 *
 * @author HD
 */

public class customerData {

    private Integer id;
    private Integer customerID;
    private Double total;
    private Date date;
    private String Musername;

    public customerData(Integer id, Integer customerID, Double total,
             Date date, String Musername) {
        this.id = id;
        this.customerID = customerID;
        this.total = total;
        this.date = date;
        this.Musername = Musername;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public Double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public String getMusername() {
        return Musername;
    }
}
