package ru.mls.parts.model;

import java.sql.Date;

public class Part {
    private String name;
    private String number;
    private String vendor;
    private Integer qty;
    private Date snipped;
    private Date receive;



    public Part(String name, String number, String vendor, Integer qty, Date snipped, Date receive) {
        this.name = name;
        this.number = number;
        this.vendor = vendor;
        this.qty = qty;
        this.snipped = snipped;
        this.receive = receive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Date getSnipped() {
        return snipped;
    }

    public void setSnipped(Date snipped) {
        this.snipped = snipped;
    }

    public Date getReceive() {
        return receive;
    }

    public void setReceive(Date receive) {
        this.receive = receive;
    }

    @Override
    public String toString() {
        return "Part{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", vendor='" + vendor + '\'' +
                ", qty=" + qty +
                ", snipped=" + snipped +
                ", receive=" + receive +
                '}';
    }
}
