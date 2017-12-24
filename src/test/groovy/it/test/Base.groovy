package it.test

import it.test.Pogo.Address
import it.test.Pogo.CreditCard
import it.test.Pogo.Person

class Base {

    Person person

    Person fakePerson() {
        Person person = new Person()
        Address addr = new Address()
        CreditCard creditCard1 = new CreditCard()
        CreditCard creditCard2 = new CreditCard()
        addr.with {
            country = "USA"
            city = "NY"
            street = "12 street"
            postCode = 1234
        }
        creditCard1.with {
            number = "12345678"
            date = new Date(2016 - 1900, 7, 16)
            cvv = 123
        }
        creditCard2.with {
            number = "87654321"
            date = new Date(2016 - 1900, 2, 11)
            cvv = 321
        }
        person.with {
            firstName = "John"
            lastName = "Doe"
            age = 38
            address = addr
            creditCards = [creditCard1, creditCard2]
            skills = ["Accounting", "Administrative", "Analytical"]
        }
        return person
    }

}

