package com.ecommerce.model.constants;


public enum OrderStatus {
    INPROGRESS("In progress"), PENDING("Pending"), CANCELLED("Cancelled"), DONE("Done");
    private String value;

    OrderStatus(String value) {
        this.value = value;
    }
}
