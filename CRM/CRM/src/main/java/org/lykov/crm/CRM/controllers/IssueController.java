package org.lykov.crm.CRM.controllers;

import jakarta.validation.Valid;
import org.lykov.crm.CRM.dao.IssueDAO;
import org.lykov.crm.CRM.dao.PersonDAO;
import org.lykov.crm.CRM.models.Issue;
import org.lykov.crm.CRM.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/issue")
public class IssueController {
    private final IssueDAO issueDAO;
    private final PersonDAO personDAO;


    @Autowired
    public IssueController(IssueDAO issueDAO, PersonDAO personDAO) {
        this.issueDAO = issueDAO;
        this.personDAO = personDAO;
    }



    @GetMapping("/new_issue")
    public String newIssue(Model model){
        Issue issue = new Issue();
        issue.setPerson_id(1);  // Установите здесь подходящее дефолтное значение
        model.addAttribute("issue", issue);
        // Получите список людей
        model.addAttribute("people", personDAO.index());  // и добавьте его в модель

        return "issue/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("issue") @Valid Issue issue,
                         BindingResult bindingResult,
                         Model model) {
        if (0 == issue.getPerson_id() | bindingResult.hasErrors()) {
            System.out.println("99999");
            model.addAttribute("people", personDAO.index());
            return "issue/new";
        }
        issueDAO.save(issue);

        return "redirect:/people/withIndex";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int issue_id) {
        model.addAttribute("issue", issueDAO.find(issue_id));
        model.addAttribute("people", personDAO.index());
        return "issue/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("issue") @Valid Issue issue,
                         @PathVariable("id") int issue_id) {

        issueDAO.update(issue_id, issue);
        return "redirect:/people/withIndex";
    }

    @PostMapping("/{id}/closeIssue")
    public String closeIssue(@PathVariable("id") int issue_id, Model model ) {
        Issue issue = issueDAO.find(issue_id);
        model.addAttribute("issue", issue);
        issueDAO.closeIssue(issue_id, issue);
        return "redirect:/people/withIndex";
    }

    @GetMapping("/{id}")
    public String information(@PathVariable("id") int id, Model model) {
        Map<String, Object> informationAboutIssues = issueDAO.information(id);
        model.addAttribute("informationAboutIssues", informationAboutIssues);
        return "issue/informationAboutIssue";
    }


}
