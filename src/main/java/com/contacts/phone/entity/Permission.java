package com.contacts.phone.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_READ("user:read"),             //read all info of the site
    USER_UPDATE("user:update"),         //update theirs data
    USER_CREATE("user:create"),         //make an order
    USER_REMOVE("user:remove"),         //remove product from shopping cart
    USER_DELETE("user:cancel"),         //cancel order
    USER_PAY("user:pay"),               //order's payment
    USER_FAV("user:starred"),           //add to favourites
    USER_DELIVERY("user:delivery");     //add to favourites

    @Getter
    private final String permission;
}