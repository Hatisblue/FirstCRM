package org.lykov.crm.CRM.controllers;

import jakarta.validation.Valid;
import org.lykov.crm.CRM.dao.PersonDAO;
import org.lykov.crm.CRM.models.Person;
import org.lykov.crm.CRM.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String find(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.find(id));
        return "people/find";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person")@Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people/withIndex";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int person_id) {
        model.addAttribute("person", personDAO.find(person_id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int person_id) {

        person.setPerson_id(person_id);
        personValidator.validate(person, bindingResult);


        if (bindingResult.hasErrors())
            return "people/edit";


        personDAO.update(person_id, person);
        return "redirect:/people/withIndex";
    }


    @GetMapping("/withIndex")
    public String withIndex(Model model) {
        List<Map<String, Object>> peopleWithIssues = personDAO.indexWithIssues();
        model.addAttribute("peopleWithIssues", peopleWithIssues);
        return "people/withindex";
    }

    @GetMapping("/inProgress")
    public String InProgress(Model model) {
        List<Map<String, Object>> issuesInProgress = personDAO.issuesInProgress();
        model.addAttribute("issuesInProgress", issuesInProgress);
        return "people/inProgress";
    }





}
