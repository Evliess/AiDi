package evliess.io.service;

import evliess.io.jpa.ConsumerPlayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerPlayService {
    private final ConsumerPlayRepo consumerPlayRepo;

    @Autowired
    public ConsumerPlayService(ConsumerPlayRepo consumerPlayRepo) {
        this.consumerPlayRepo = consumerPlayRepo;
    }


}
