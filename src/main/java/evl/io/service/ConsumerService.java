package evl.io.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;
import evl.io.entity.Consumer;
import evl.io.entity.ConsumerCharge;
import evl.io.entity.ConsumerPlay;
import evl.io.entity.ConsumerScore;
import evl.io.jpa.ConsumerChargeRepo;
import evl.io.jpa.ConsumerPlayRepo;
import evl.io.jpa.ConsumerRepo;
import evl.io.jpa.ConsumerScoreRepo;
import evl.io.utils.DateTimeUtils;
import evl.io.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ConsumerService {
    public static final String CONSUMER_NOT_EXIST = "会员不存在！";
    private final ConsumerRepo consumerRepo;
    private final ConsumerScoreRepo consumerScoreRepo;
    private final ConsumerChargeRepo consumerChargeRepo;
    private final ConsumerPlayRepo consumerPlayRepo;

    public ConsumerService(ConsumerRepo consumerRepo, ConsumerScoreRepo consumerScoreRepo
            , ConsumerChargeRepo consumerChargeRepo
            , ConsumerPlayRepo consumerPlayRepo) {
        this.consumerRepo = consumerRepo;
        this.consumerScoreRepo = consumerScoreRepo;
        this.consumerChargeRepo = consumerChargeRepo;
        this.consumerPlayRepo = consumerPlayRepo;
    }

    @Transactional
    public ResponseEntity<String> create(String phone, String name, String money, String type
            , String leftCount, String expiredAt, String chargeAt, String memo) {
        Consumer consumer = consumerRepo.findByPhone(phone);
        JSONObject jsonObject = new JSONObject();
        if (consumer == null) {
            consumer = new Consumer();
            consumer.setPhone(phone);
        }
        consumer.setName(name);
        consumer.setType(type);
        if (ServiceConstants.TYPE_DAY.equals(type)) {
            consumer.setLeftCount(Integer.parseInt(leftCount));
            consumer.setExpiredAt("");
        } else {
            consumer.setExpiredAt(expiredAt);
            consumer.setLeftCount(0);
        }
        consumer.setMemo(memo);
        consumerRepo.save(consumer);
        createConsumeCharge(phone, money, chargeAt);
        jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        return ResponseEntity.ok(jsonObject.toString());
    }

    private void createConsumeCharge(String phone, String money, String chargeAt) {
        ConsumerCharge consumerCharge = new ConsumerCharge();
        consumerCharge.setMoney(money);
        consumerCharge.setPhone(phone);
        consumerCharge.setChargeAt(DateTimeUtils.dateToMillis(chargeAt));
        consumerChargeRepo.save(consumerCharge);
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

    private JSONObject _findByPhone(String phone) {
        Consumer consumer = consumerRepo.findByPhone(phone);
        if (consumer == null) {
            consumer = consumerRepo.findByName(phone);
        }
        JSONObject jsonObject = new JSONObject();
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            phone = consumer.getPhone();
            jsonObject.put("phone", phone);
            jsonObject.put("name", consumer.getName());
            jsonObject.put("leftCount", consumer.getLeftCount());
            jsonObject.put("type", consumer.getType());
            jsonObject.put("memo", consumer.getMemo());
            jsonObject.put("expiredAt", consumer.getExpiredAt());
            ConsumerScore consumerScore = consumerScoreRepo.findByPhone(phone);
            if (consumerScore != null) {
                String score = consumerScore.getScore();
                if (ValidationUtils.isBlank(score)) {
                    score = ServiceConstants.NA;
                }
                jsonObject.put("score", score);
            }
            List<ConsumerCharge> consumerCharges = consumerChargeRepo.findAllByPhone(consumer.getPhone());
            if (!consumerCharges.isEmpty()) {
                ConsumerCharge consumerCharge = consumerCharges.get(0);
                jsonObject.put("money", consumerCharge.getMoney());
                jsonObject.put("chargeAt", DateTimeUtils.convertToFormattedString(consumerCharge.getChargeAt()));
            }
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        }
        return jsonObject;
    }

    public ResponseEntity<String> findByPhone(String phone) {
        JSONObject jsonObject = _findByPhone(phone);
        return ResponseEntity.ok(jsonObject.toString());
    }

    public ResponseEntity<String> updateByPhone(String phone, String leftCount, String memo, String expiredAt, String score) {
        Consumer consumer = consumerRepo.findByPhone(phone);
        JSONObject jsonObject = new JSONObject();
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            if (ValidationUtils.isNotBlank(memo)) consumer.setMemo(memo);
            if (ValidationUtils.isNotBlank(leftCount)) consumer.setLeftCount(Integer.parseInt(leftCount));
            if (ValidationUtils.isNotBlank(expiredAt)) consumer.setExpiredAt(expiredAt);
            if (ValidationUtils.isNotBlank(score)) {
                ConsumerScore consumerScore = consumerScoreRepo.findByPhone(phone);
                consumerScore.setScore(score);
                consumerScoreRepo.save(consumerScore);
            }
            consumerRepo.save(consumer);
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }

    public ResponseEntity<String> viewVip(String phone) {
        JSONObject jsonObject = _findByPhone(phone);
        phone = jsonObject.getString("phone");
        List<ConsumerCharge> consumerChargeList = consumerChargeRepo.findAllByPhone(phone);
        JSONArray chargeLists = new JSONArray();
        BigDecimal total = new BigDecimal("0");
        for (ConsumerCharge consumerCharge : consumerChargeList) {
            JSONObject chargeObject = new JSONObject();
            chargeObject.put("money", consumerCharge.getMoney());
            total = total.add(new BigDecimal(consumerCharge.getMoney()));
            chargeObject.put("chargeAt", DateTimeUtils.convertToFormattedString(consumerCharge.getChargeAt()));
            chargeLists.add(chargeObject);
        }
        jsonObject.put("chargeLists", chargeLists);
        jsonObject.put("chargeTotal", total);

        List<ConsumerPlay> consumerPlayList = consumerPlayRepo.findAllByPhone(phone);
        JSONArray playLists = new JSONArray();
        for (ConsumerPlay consumerPlay : consumerPlayList) {
            JSONObject playObject = new JSONObject();
            playObject.put("item", consumerPlay.getItemName());
            playObject.put("consumedAt", DateTimeUtils.convertToFormattedString(consumerPlay.getConsumeAt()));
            playLists.add(playObject);
        }
        jsonObject.put("playLists", playLists);

        return ResponseEntity.ok(jsonObject.toString());
    }

    public ResponseEntity<String> findAllOrdered() {
        JSONArray consumers = new JSONArray();
        List<Consumer> allConsumersOrdered = consumerRepo.findAllOrdered();
        for (Consumer consumer : allConsumersOrdered) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone", consumer.getPhone());
            jsonObject.put("name", consumer.getName());
            jsonObject.put("leftCount", consumer.getLeftCount());
            jsonObject.put("totalCount", consumer.getTotalCount());
            jsonObject.put("memo", consumer.getMemo());
            jsonObject.put("type", consumer.getType());
            jsonObject.put("expiredAt", consumer.getExpiredAt());
            List<ConsumerCharge> consumerCharges = consumerChargeRepo.findAllByPhone(consumer.getPhone());
            if (!consumerCharges.isEmpty()) {
                jsonObject.put("chargeAt",
                        DateTimeUtils.convertToFormattedString(consumerCharges.get(0).getChargeAt()));
            }
            consumers.add(jsonObject);
        }
        return ResponseEntity.ok(consumers.toString());
    }
}
