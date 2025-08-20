package evliess.io.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import evliess.io.constant.ServiceConstants;
import evliess.io.entity.Consumer;
import evliess.io.entity.ConsumerCharge;
import evliess.io.jpa.ConsumerChargeRepo;
import evliess.io.jpa.ConsumerRepo;
import evliess.io.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;

import static evliess.io.service.ConsumerService.CONSUMER_NOT_EXIST;

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
        JSONObject jsonObject = new JSONObject();
        Consumer consumer = consumerRepo.findByPhone(phone);
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            ConsumerCharge consumerCharge = new ConsumerCharge();
            consumerCharge.setMoney(money);
            consumerCharge.setPhone(phone);
            consumerCharge.setChargeAt(Instant.now().toEpochMilli());
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
