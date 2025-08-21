package evliess.io.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import evliess.io.constant.ServiceConstants;
import evliess.io.entity.Consumer;
import evliess.io.entity.ConsumerPlay;
import evliess.io.jpa.ConsumerPlayRepo;
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
public class ConsumerPlayService {
    private final ConsumerPlayRepo consumerPlayRepo;
    private final ConsumerRepo consumerRepo;

    @Autowired
    public ConsumerPlayService(ConsumerPlayRepo consumerPlayRepo, ConsumerRepo consumerRepo) {
        this.consumerPlayRepo = consumerPlayRepo;
        this.consumerRepo = consumerRepo;
    }

    @Transactional
    public ResponseEntity<String> create(@RequestBody String body) {
        JSONObject jsonNode = JSON.parseObject(body);
        String phone = jsonNode.getString("phone");
        String item = jsonNode.getString("item");
        JSONObject jsonObject = new JSONObject();
        Consumer consumer = consumerRepo.findByPhone(phone);
        if (consumer == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, CONSUMER_NOT_EXIST);
        } else {
            ConsumerPlay consumerPlay = new ConsumerPlay();
            consumerPlay.setPhone(phone);
            consumerPlay.setItemName(item);
            consumerPlay.setConsumeAt(Instant.now().toEpochMilli());
            consumerPlayRepo.save(consumerPlay);
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }

    public ResponseEntity<String> findByPhone(String phone) {
        List<ConsumerPlay> list = consumerPlayRepo.findAllByPhone(phone);
        JSONArray jsonObject = new JSONArray();
        for (ConsumerPlay c : list) {
            JSONObject obj = new JSONObject();
            obj.put("itemName", c.getItemName());
            obj.put("name", consumerRepo.findByPhone(phone).getName());
            obj.put("phone", c.getPhone());
            obj.put("consumeAt", DateTimeUtils.convertToFormattedString(c.getConsumeAt()));
            jsonObject.add(obj);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }


}
