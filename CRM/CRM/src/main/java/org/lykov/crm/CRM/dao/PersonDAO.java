package org.lykov.crm.CRM.dao;

import org.lykov.crm.CRM.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Show you all persons
    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person find(int id) {
        return jdbcTemplate.query("SELECT *FROM Person WHERE person_id=?", new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> find(String email){
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?", new Object[] {email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();

    }

    public void save(Person person){
    jdbcTemplate.update("INSERT INTO Person(name, organization, email) VALUES(?,?,?)", person.getName(),
            person.getOrganization(), person.getEmail());
    }

    public void update(int person_id, Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name=?, organization=?, email=? WHERE person_id=?", updatedPerson.getName(),
                    updatedPerson.getOrganization(), updatedPerson.getEmail(), person_id);

    }


    public List<Map<String, Object>> indexWithIssues() {
        String sql = "SELECT p.person_id, p.name, p.organization, p.email,\n" +
                "       i.issue_id, i.title, i.description, i.starttime, i.endtime,\n" +
                "       CASE\n" +
                "           WHEN i.endtime IS NOT NULL THEN 'исполнена'\n" +
                "           ELSE 'выполняется'\n" +
                "           END AS issue_status\n" +
                "FROM Person p\n" +
                "         JOIN Issue i ON p.person_id = i.person_id;";

        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> issuesInProgress() {
        String sql = "SELECT p.person_id, p.name, p.organization, p.email,\n" +
                "       i.issue_id, i.title, i.description, i.starttime, i.endtime,\n" +
                "       CASE\n" +
                "           WHEN i.endtime IS NOT NULL THEN 'исполнена'\n" +
                "           ELSE 'выполняется'\n" +
                "           END AS issue_status\n" +
                "FROM Person p\n" +
                "         JOIN Issue i ON p.person_id = i.person_id WHERE i.endtime IS NULL;";

        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> AVGforPersons() {
        String sql = "SELECT  p.name, count(*) as Count, ROUND(AVG(EXTRACT(EPOCH FROM (i.endtime - i.starttime))/3600),2) as AverageIssueDuration FROM Person p" +
        " JOIN Issue i ON p.person_id = i.person_id group by p.name";
        return jdbcTemplate.queryForList(sql);
    }
}


