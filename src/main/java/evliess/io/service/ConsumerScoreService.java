package evliess.io.service;

import com.alibaba.fastjson2.JSONObject;
import evliess.io.constant.ServiceConstants;
import evliess.io.entity.Consumer;
import evliess.io.entity.ConsumerScore;
import evliess.io.jpa.ConsumerRepo;
import evliess.io.jpa.ConsumerScoreRepo;
import evliess.io.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static evliess.io.service.ConsumerService.CONSUMER_NOT_EXIST;

@Service
public class ConsumerScoreService {

    private final ConsumerScoreRepo consumerScoreRepo;
    private final ConsumerRepo consumerRepo;

    @Autowired
    public ConsumerScoreService(ConsumerScoreRepo consumerScoreRepo, ConsumerRepo consumerRepo) {
        this.consumerScoreRepo = consumerScoreRepo;
        this.consumerRepo = consumerRepo;
    }

    @Transactional
    public ResponseEntity<String> createOrUpdate(String phone, String score) {
        JSONObject jsonObject = new JSONObject();
        Consumer consumer = consumerRepo.findByPhone(phone);
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            ConsumerScore consumerScore = this.consumerScoreRepo.findByPhone(phone);
            if (consumerScore != null) {
                consumerScore.setScore(score);
            } else {
                consumerScore = new ConsumerScore();
                consumerScore.setPhone(phone);
                consumerScore.setScore(score);
            }
            consumerScore.setConsumeAt(Instant.now().toEpochMilli());
            this.consumerScoreRepo.save(consumerScore);
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }

    public ResponseEntity<String> findByPhone(String phone) {
        ConsumerScore consumerScore = this.consumerScoreRepo.findByPhone(phone);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);
        if (consumerScore == null) {
            jsonObject.put("score", "0");
            jsonObject.put("consumeAt", -1);
        } else {
            jsonObject.put("score", consumerScore.getScore());
            jsonObject.put("consumeAt", DateTimeUtils.convertToFormattedString(consumerScore.getConsumeAt()));
        }
        jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        return ResponseEntity.ok(jsonObject.toString());
    }
}
