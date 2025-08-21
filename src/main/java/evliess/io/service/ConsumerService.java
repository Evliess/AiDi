package evliess.io.service;

import com.alibaba.fastjson2.JSONObject;
import evliess.io.constant.ServiceConstants;
import evliess.io.entity.Consumer;
import evliess.io.entity.ConsumerScore;
import evliess.io.jpa.ConsumerRepo;
import evliess.io.jpa.ConsumerScoreRepo;
import evliess.io.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    public static final String CONSUMER_NOT_EXIST = "会员不存在！";
    private final ConsumerRepo consumerRepo;
    private final ConsumerScoreRepo consumerScoreRepo;

    public ConsumerService(ConsumerRepo consumerRepo, ConsumerScoreRepo consumerScoreRepo) {
        this.consumerRepo = consumerRepo;
        this.consumerScoreRepo = consumerScoreRepo;
    }

    @Transactional
    public ResponseEntity<String> create(String phone, String name) {
        Consumer consumer = consumerRepo.findByPhone(phone);
        JSONObject jsonObject = new JSONObject();
        if (consumer == null) {
            consumer = new Consumer();
            consumer.setName(name);
            consumer.setPhone(phone);
            consumerRepo.save(consumer);
        } else {
            consumerRepo.updateNameByPhone(phone, name);
        }
        jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        return ResponseEntity.ok(jsonObject.toString());
    }

    @Transactional
    public ResponseEntity<String> updateLeftCountByPhone(String phone, String leftCount) {
        Consumer consumer = consumerRepo.findByPhone(phone);
        JSONObject jsonObject = new JSONObject();
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            consumerRepo.updateLeftCountByPhone(phone, Integer.parseInt(leftCount));
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }

    @Transactional
    public ResponseEntity<String> updateNameByPhone(String phone, String name) {
        Consumer consumer = consumerRepo.findByPhone(phone);
        JSONObject jsonObject = new JSONObject();
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            consumerRepo.updateNameByPhone(phone, name);
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }

    public ResponseEntity<String> findByPhone(String phone) {
        Consumer consumer = consumerRepo.findByPhone(phone);
        JSONObject jsonObject = new JSONObject();
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            jsonObject.put("phone", consumer.getPhone());
            jsonObject.put("name", consumer.getName());
            jsonObject.put("leftCount", consumer.getLeftCount());
            ConsumerScore consumerScore = consumerScoreRepo.findByPhone(phone);
            String score = consumerScore.getScore();
            if (ValidationUtils.isBlank(score)) {
                score = ServiceConstants.NA;
            }
            jsonObject.put("score", score);
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }
}
