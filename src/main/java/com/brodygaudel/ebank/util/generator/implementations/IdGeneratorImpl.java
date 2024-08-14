

package com.brodygaudel.ebank.util.generator.implementations;

import com.brodygaudel.ebank.util.generator.IdGenerator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
public class IdGeneratorImpl implements IdGenerator {

    private final CompterRepository compterRepository;

    public IdGeneratorImpl(CompterRepository compterRepository) {
        this.compterRepository = compterRepository;
    }


    /**
     * This method generates the (unique) identifier of the bank account.
     * the identifier of the bank accounts represents its bic code (or account details)
     *
     * @return id as String
     */
    @Override
    public String autoGenerate() {
        List<Compter> compters = compterRepository.findAll();
        Compter compter;
        if(compters.isEmpty()) {
            compter = new Compter((long) 999999);
        }
        else {
            compter = compters.get(compters.size() - 1);
            compterRepository.deleteById(compter.getId());
        }
        Long increment = compter.getId()+1;
        compterRepository.save(new Compter(increment));
        log.info("id generated");
        return "2023"+increment;
    }

}
