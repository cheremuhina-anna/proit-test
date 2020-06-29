package proit.test.repositories;

import org.jooq.DSLContext;
import org.jooq.util.maven.example.tables.pojos.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import proit.test.models.EmplList;

import java.util.List;
import java.util.UUID;

import static org.jooq.util.maven.example.tables.Employee.EMPLOYEE;
import static org.jooq.util.maven.example.tables.Organization.ORGANIZATION;

@Repository
public class EmplRepo {

    @Autowired
    private DSLContext dsl;

    public List<EmplList> selectPage(int offset, int limit){
        org.jooq.util.maven.example.tables.Employee empl1 = EMPLOYEE.as("empl1");
        org.jooq.util.maven.example.tables.Employee empl2 = EMPLOYEE.as("empl2");
        return dsl.select(empl1.asterisk(), ORGANIZATION.NAME.as("name_org"), empl2.NAME.as("name_headempl"))
                .from(empl1)
                .leftJoin(ORGANIZATION)
                .onKey()
                .leftJoin(empl2)
                .on(empl1.ID_HEADEMPL.eq(empl2.ID))
                .offset(offset)
                .limit(limit)
                .fetchInto(EmplList.class);
    }

    public int countEmpl(){
        return dsl.fetchCount(EMPLOYEE);
    }

    public List<Employee> select(){
        return dsl.selectFrom(EMPLOYEE)
                .fetchInto(Employee.class);
    }

    public List<Employee> selectEmplOrg(UUID id_org){
        return dsl.selectFrom(EMPLOYEE)
                .where(EMPLOYEE.ID_ORG.eq(id_org))
                .fetchInto(Employee.class);
    }

    public UUID insert(Employee empl){
        return dsl.insertInto(EMPLOYEE, EMPLOYEE.NAME, EMPLOYEE.ID_ORG, EMPLOYEE.ID_HEADEMPL)
                .values(empl.getName(), empl.getIdOrg(), empl.getIdHeadempl())
                .returning(EMPLOYEE.ID)
                .fetchOne().getId();
    }

    public int update(Employee empl){
        return dsl.update(EMPLOYEE)
                .set(EMPLOYEE.NAME, empl.getName())
                .set(EMPLOYEE.ID_ORG, empl.getIdOrg())
                .set(EMPLOYEE.ID_HEADEMPL, empl.getIdHeadempl())
                .where(EMPLOYEE.ID.eq(empl.getId()))
                .execute();
    }

    public List<UUID> canDelete(UUID id_empl) {
        return dsl.select(EMPLOYEE.ID)
                .from(EMPLOYEE)
                .where(EMPLOYEE.ID_HEADEMPL.eq(id_empl))
                .fetchInto(UUID.class);
    }

    public void delete(UUID id_empl){
        dsl.delete(EMPLOYEE)
                .where(EMPLOYEE.ID.eq(id_empl))
                .execute();
    }

    public List<Employee> subEmpl(UUID id_headEmpl) {
        return dsl.select(EMPLOYEE.asterisk())
                .from(EMPLOYEE)
                .where(EMPLOYEE.ID_HEADEMPL.eq(id_headEmpl))
                .fetchInto(Employee.class);
    }

    public List<Employee> emplDontHaveHead(){
        return dsl.select(EMPLOYEE.asterisk())
                .from(EMPLOYEE)
                .where(EMPLOYEE.ID_HEADEMPL.isNull())
                .fetchInto(Employee.class);
    }
}
