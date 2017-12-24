package it.test.Pogo

import it.test.Mappable
import it.test.PogoHelper

class Person extends PogoHelper implements Mappable {
    String firstName
    String lastName
    Integer age
    Address address
    CreditCard[] creditCards
    String[] skills


}

class Address implements Mappable {
    String country
    String city
    String street
    Integer postCode
}

class CreditCard implements Mappable {
    String number
    Date date
    Integer cvv
}
