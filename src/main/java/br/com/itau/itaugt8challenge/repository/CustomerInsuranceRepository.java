package br.com.itau.itaugt8challenge.repository;

import br.com.itau.itaugt8challenge.model.CustomerInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInsuranceRepository extends JpaRepository<CustomerInsurance, Long> {
    CustomerInsurance findByCpf(String cpf);
}
