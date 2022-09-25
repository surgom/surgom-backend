package com.greentea.surgom.repository;

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
    public void delete(String phone) {
        em.remove(findByPhone(phone));
    }

    @Override
    public void deleteAll() {
        em.clear();
    }

    @Override
    public List<Member> findAllWithAge_range(String range) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> from = cq.from(Member.class);
        Predicate where = cb.equal(from.get("age_range"), range);
        cq.where(where);
        TypedQuery<Member> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Member> findAllWithGender(char gender) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> from = cq.from(Member.class);
        Predicate where = cb.equal(from.get("gender"), gender);
        cq.where(where);
        TypedQuery<Member> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void update(Member member) {
        em.merge(member);
    }
}