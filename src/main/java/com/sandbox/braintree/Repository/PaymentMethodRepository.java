package com.sandbox.braintree.Repository;

import com.sandbox.braintree.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentEntity,Integer> {

   PaymentEntity findByCustomersId(Integer id);

   // Payment findByCustomersId(Integer id);
    //  Payment findByCust(Integer id);
}
