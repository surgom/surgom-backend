package com.greentea.surgom.repository;

import com.greentea.surgom.domain.Gender;
import com.greentea.surgom.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemoryMemberRepository implements MemberRepository {

//    @PersistenceContext
    private final EntityManager em;

    @Override
    public String save(Member member) {
        em.persist(member);
        return member.getPhone();
    }

    @Override
    public Member findByPhone(String phone) {
        return em.find(Member.class, phone);
    }

    @Override
    public Member findByPhoneAndIdentifier(String phone, String identifier) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> from = cq.from(Member.class);

        Predicate where1 = cb.equal(from.get("phone"), phone);
        Predicate where2 = cb.equal(from.get("identifier"), identifier);

        Predicate where = cb.or(where1, where2);
        cq.where(where);
        TypedQuery<Member> query = em.createQuery(cq);
        return query.getResultList().get(0);
    }

    @Override
    public void delete(String phone) {
        em.remove(findByPhone(phone));
    }

    @Override
    public void deleteAll() {
        em.clear();
    }

    @Override
    public List<Member> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> from = cq.from(Member.class);
        TypedQuery<Member> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Member> findAllWithAge_range(int first, int last) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> from = cq.from(Member.class);
        Predicate where = cb.between(from.get("age"), first, last);
        cq.where(where);
        TypedQuery<Member> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Member> findAllWithGender(String gender) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> from = cq.from(Member.class);

        Gender user_gender;
        if (gender.equals("FEMALE")) user_gender = Gender.FEMALE;
        else user_gender = Gender.MALE;

        Predicate where = cb.equal(from.get("gender"), user_gender);
        cq.where(where);
        TypedQuery<Member> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Member> findAllWithAgeAndGender(int first, int last, String gender) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> from = cq.from(Member.class);

        Gender user_gender;
        if (gender.equals("FEMALE")) user_gender = Gender.FEMALE;
        else user_gender = Gender.MALE;

        Predicate where1 = cb.equal(from.get("gender"), user_gender);
        Predicate where2 = cb.between(from.get("age"), first, last);

        Predicate where = cb.and(where1, where2);
        cq.where(where);
        TypedQuery<Member> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void update(Member member) {
        em.detach(member);
        em.merge(member);
    }
}