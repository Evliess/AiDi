package evl.io.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;
import evl.io.entity.Consumer;
import evl.io.entity.ConsumerCharge;
import evl.io.jpa.ConsumerChargeRepo;
import evl.io.jpa.ConsumerRepo;
import evl.io.utils.DateTimeUtils;
import evl.io.utils.ValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static evl.io.controller.ConsumerController.ILLEGAL_ARGS_MSG;
import static evl.io.service.ConsumerService.CONSUMER_NOT_EXIST;

@Service
public class ConsumerChargeService {
    private final ConsumerChargeRepo consumerChargeRepo;
    private final ConsumerRepo consumerRepo;

    @Autowired
    public ConsumerChargeService(ConsumerChargeRepo consumerChargeRepo,
                                 ConsumerRepo consumerRepo) {
        this.consumerChargeRepo = consumerChargeRepo;
        this.consumerRepo = consumerRepo;
    }

    @Transactional
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String phone = jsonNode.getString("phone");
        String money = jsonNode.getString("money");
        if (ValidationUtils.isBlank(money)) throw new IllegalArgumentException(ILLEGAL_ARGS_MSG);
        String type = jsonNode.getString("type");
        String leftCount = jsonNode.getString("leftCount");
        String expiredAt = jsonNode.getString("expiredAt");
        JSONObject jsonObject = new JSONObject();
        Consumer consumer = consumerRepo.findByPhone(phone);
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            consumer.setType(type);
            if (ServiceConstants.TYPE_DAY.equals(type)) {
                consumer.setLeftCount(Integer.parseInt(leftCount));
                consumer.setExpiredAt("");
            } else {
                consumer.setExpiredAt(expiredAt);
                consumer.setLeftCount(0);
            }
            String memo = jsonNode.getString("memo");
            consumer.setMemo(memo);
            consumerRepo.save(consumer);
            ConsumerCharge consumerCharge = new ConsumerCharge();
            consumerCharge.setMoney(money);
            consumerCharge.setPhone(phone);
            String chargeAt = jsonNode.getString("chargeAt");
            consumerCharge.setChargeAt(DateTimeUtils.dateToMillis(chargeAt));
            consumerChargeRepo.save(consumerCharge);
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }


    public ResponseEntity<String> findByPhone(String phone) {
        List<ConsumerCharge> list = consumerChargeRepo.findAllByPhone(phone);
        JSONArray jsonObject = new JSONArray();
        for (ConsumerCharge c : list) {
            JSONObject obj = new JSONObject();
            obj.put("money", c.getMoney());
            obj.put("chargeAt", DateTimeUtils.convertToFormattedString(c.getChargeAt()));
            jsonObject.add(obj);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }
}
