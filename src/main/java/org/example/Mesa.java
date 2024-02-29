package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Esta clase se encarga de representar las mesas del bar
 */
public class Mesa {
    private int id;
    private int num_mesa;
    private double precio_total;
    private LocalDate fecha;
    private LocalTime hora;
    private ObservableList<Producto> productos;

    public Mesa() {
        productos=FXCollections.observableArrayList();
    }

    public Mesa(int id, int num_mesa, double precio_total, LocalDate fecha, LocalTime hora) {
        this.id=id;
        this.num_mesa = num_mesa;
        this.precio_total = precio_total;
        this.fecha = fecha;
        this.hora = hora;
        productos=FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObservableList<Producto> getProductos() {
        return productos;
    }

    public void addProducto(Producto p){
        productos.add(p);
    }

    public void removeProducto(Producto p){
        productos.remove(p);
    }

    public void setProductos(ObservableList<Producto> productos) {
        this.productos = productos;
    }

    public int getNum_mesa() {
        return num_mesa;
    }

    public void setNum_mesa(int num_mesa) {
        this.num_mesa = num_mesa;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void factura(){

    }
    public void pagar(){
        precio_total=0;
        for (Producto p: productos) {
            precio_total+=p.getPrecio();
        }
    }

    @Override
    public String toString() {
        return "Mesa{" +
                " num_mesa=" + num_mesa +
                ", precio_total=" + precio_total +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}
