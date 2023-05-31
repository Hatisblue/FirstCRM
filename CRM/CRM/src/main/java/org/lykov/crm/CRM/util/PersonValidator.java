package org.lykov.crm.CRM.util;

import org.lykov.crm.CRM.dao.PersonDAO;
import org.lykov.crm.CRM.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        Optional<Person> existingPerson = personDAO.find(person.getEmail());
        if (existingPerson.isPresent()) {
            System.out.println("Existing person ID: " + existingPerson.get().getPerson_id());
            System.out.println("Current person ID: " + person.getPerson_id());

            if (existingPerson.get().getPerson_id() != person.getPerson_id()) {
                errors.rejectValue("email", "", "This email is already taken");
            }
        }
    }

}
