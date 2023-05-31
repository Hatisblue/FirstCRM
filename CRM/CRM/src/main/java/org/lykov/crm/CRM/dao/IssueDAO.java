package org.lykov.crm.CRM.dao;

import org.lykov.crm.CRM.models.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class IssueDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IssueDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Issue> index(){
        return jdbcTemplate.query("SELECT * FROM Issue", new BeanPropertyRowMapper<>(Issue.class));
    }

    public Issue find(int id){
        return jdbcTemplate.query("SELECT * FROM Issue WHERE  issue_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Issue.class)).stream().findAny().orElse(null);
    }

   public void save(Issue issue){
        jdbcTemplate.update("INSERT INTO Issue(person_id, title, description, starttime, endtime) VALUES (?,?,?,?,?)",  issue.getPerson_id(), issue.getTitle(), issue.getDescription(), issue.getStarttime(), issue.getEndtime());
   }

    public void update(int id, Issue updatedIssue) {
        jdbcTemplate.update("UPDATE Issue SET person_id=?, title=?, description=? WHERE issue_id=?", updatedIssue.getPerson_id() ,updatedIssue.getTitle(),
                updatedIssue.getDescription(), id);
    }

    public void closeIssue(int id, Issue updatedIssue) {
        if(updatedIssue.getEndtime() == null){
            updatedIssue.setEndtime(LocalDateTime.now());
        }
        jdbcTemplate.update("UPDATE Issue SET  endtime=? WHERE issue_id=?", updatedIssue.getEndtime(), id);

    }

    public Map<String, Object> information(int id) {
        String sql = "SELECT p.name, p.organization, p.email, i.issue_id, i.title, i.description, i.starttime, i.endtime, " +
                "       CASE\n" +
                "           WHEN i.endtime IS NOT NULL THEN 'исполнена'\n" +
                "           ELSE 'выполняется'\n" +
                "           END AS issue_status\n" +
                "FROM Person p " +
                "JOIN Issue i ON p.person_id = i.person_id WHERE i.issue_id = " + id;

        return jdbcTemplate.queryForMap(sql);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Issue WHERE issue_id=?", id);
    }

}
