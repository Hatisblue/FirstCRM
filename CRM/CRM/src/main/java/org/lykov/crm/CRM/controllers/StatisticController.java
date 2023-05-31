package org.lykov.crm.CRM.controllers;

import org.lykov.crm.CRM.dao.IssueDAO;
import org.lykov.crm.CRM.dao.PersonDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statistic")
public class StatisticController {

    private final PersonDAO personDAO;

    private final IssueDAO issueDAO;


    public StatisticController(PersonDAO personDAO, IssueDAO issueDAO) {
        this.personDAO = personDAO;
        this.issueDAO = issueDAO;
    }

    @GetMapping("/personStatistic")
    public String AVGforPerson(Model model) {
        List<Map<String, Object>> avgForPeople = personDAO.AVGforPersons();
        model.addAttribute("avgForPeople", avgForPeople);
        return "statistic/statistic";
    }
}
