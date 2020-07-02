package proit.test.repositories;

import org.jooq.DSLContext;
import org.jooq.util.maven.example.tables.pojos.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import proit.test.models.OrgList;

import java.util.List;
import java.util.UUID;

import static org.jooq.impl.DSL.count;
import static org.jooq.util.maven.example.tables.Employee.EMPLOYEE;
import static org.jooq.util.maven.example.tables.Organization.ORGANIZATION;

@Repository
public class OrgRepo {
    @Autowired
    private DSLContext dsl;

    public List<OrgList> selectPage(int offset, int limit){
        org.jooq.util.maven.example.tables.Organization org1 = ORGANIZATION.as("org1");
        org.jooq.util.maven.example.tables.Organization org2 = ORGANIZATION.as("org2");

        return dsl.select(org1.asterisk(), org2.NAME.as("nameHeadorg"), count(EMPLOYEE.ID).as("countEmpl"))
                .from(org1)
                .leftJoin(org2).on(org1.ID_HEADORG.eq(org2.ID))
                .leftJoin(EMPLOYEE)
                .on(org1.ID.eq(EMPLOYEE.ID_ORG))
                .groupBy(org1.ID, org2.NAME)
                .offset(offset)
                .limit(limit)
                .fetchInto(OrgList.class);
    }

    public int countOrg(){
        return dsl.fetchCount(ORGANIZATION);
    }

    public List<Organization> select(){
        return dsl.selectFrom(ORGANIZATION)
                .fetchInto(Organization.class);
    }

    public Organization selectOrg(UUID id_org){
        return dsl.selectFrom(ORGANIZATION)
                .where(ORGANIZATION.ID.eq(id_org))
                .fetchAny()
                .into(Organization.class);
    }

    public Organization insert(Organization org){
        return dsl.insertInto(ORGANIZATION, ORGANIZATION.NAME, ORGANIZATION.ID_HEADORG)
                .values(org.getName(), org.getIdHeadorg())
                .returning()
                .fetchOne().into(Organization.class);//.getId();
    }

    public int update(Organization org){
        return dsl.update(ORGANIZATION)
                .set(ORGANIZATION.NAME, org.getName())
                .set(ORGANIZATION.ID_HEADORG, org.getIdHeadorg())
                .where(ORGANIZATION.ID.eq(org.getId()))
                .execute();
    }


    public void delete(UUID id_org){
        dsl.delete(ORGANIZATION)
                .where(ORGANIZATION.ID.eq(id_org))
                .execute();
    }

    public List<Organization> subOrg(UUID id_headOrg) {
        return dsl.select(ORGANIZATION.asterisk())
                .from(ORGANIZATION)
                .where(ORGANIZATION.ID_HEADORG.eq(id_headOrg))
                .fetchInto(Organization.class);
    }

    public int countSubOrg(UUID id_headOrg) {
        return dsl.selectCount()
                .from(ORGANIZATION)
                .where(ORGANIZATION.ID_HEADORG.eq(id_headOrg))
                .fetchOne(0, int.class);
    }

    public List<Organization> orgDontHaveHead(){
        return dsl.select(ORGANIZATION.asterisk())
                .from(ORGANIZATION)
                .where(ORGANIZATION.ID_HEADORG.isNull())
                .fetchInto(Organization.class);

    }

    public Organization headOrg(UUID id_headOrg) {
        return dsl.select(ORGANIZATION.asterisk())
                .from(ORGANIZATION)
                .where(ORGANIZATION.ID.eq(id_headOrg))
                .fetchAny()
                .into(Organization.class);
    }

    public List<OrgList> filterOrgList(String str, int offset, int limit){
        org.jooq.util.maven.example.tables.Organization org1 = ORGANIZATION.as("org1");
        org.jooq.util.maven.example.tables.Organization org2 = ORGANIZATION.as("org2");

        return dsl.select(org1.asterisk(), org2.NAME.as("nameHeadorg"), count(EMPLOYEE.ID).as("countEmpl"))
                .from(org1)
                .leftJoin(org2).on(org1.ID_HEADORG.eq(org2.ID))
                .leftJoin(EMPLOYEE)
                .on(org1.ID.eq(EMPLOYEE.ID_ORG))
                .where(org1.NAME.like(str+'%'))
                .groupBy(org1.ID, org2.NAME)
                .offset(offset)
                .limit(limit)
                .fetchInto(OrgList.class);

    }

    public int countFilterOrg(String str){
        return dsl.selectCount()
                .from(ORGANIZATION)
                .where(ORGANIZATION.NAME.like(str+'%'))
                .fetchOne(0, int.class);
    }

}
