package evliess.io.service;

import com.alibaba.fastjson2.JSONObject;
import evliess.io.component.PlayItemConfig;
import evliess.io.constant.ServiceConstants;
import evliess.io.entity.PlayItem;
import evliess.io.jpa.PlayItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayItemService {

    private final PlayItemConfig playItemConfig;
    private final PlayItemRepo playItemRepo;

    @Autowired
    public PlayItemService(PlayItemConfig playItemConfig, PlayItemRepo playItemRepo) {
        this.playItemConfig = playItemConfig;
        this.playItemRepo = playItemRepo;
    }

    public Map<String, String> getItems() {
        return playItemConfig.getItems();
    }

    public ResponseEntity<String> findByName(String name) {
        PlayItem item = playItemRepo.findByName(name);
        JSONObject jsonObject = new JSONObject();
        if (item == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, "项目不存在!");
            return ResponseEntity.ok(jsonObject.toString());
        }
        jsonObject.put("name", item.getName());
        jsonObject.put("money", item.getMoney());
        return ResponseEntity.ok(jsonObject.toString());
    }

    @Transactional
    public ResponseEntity<String> updateByName(String name, String money) {
        PlayItem item = playItemRepo.findByName(name);
        JSONObject jsonObject = new JSONObject();
        if (item == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            jsonObject.put(ServiceConstants.MESSAGE, "项目不存在!");
            return ResponseEntity.ok(jsonObject.toString());
        }
        Integer result = playItemRepo.updateByName(name, money);
        jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        jsonObject.put(ServiceConstants.MESSAGE, result);
        return ResponseEntity.ok(jsonObject.toString());
    }

    @Transactional
    public ResponseEntity<String> save(String name, String money) {
        PlayItem item = playItemRepo.findByName(name);
        if (item == null) {
            item = new PlayItem();
            item.setName(name);
            item.setMoney(money);
            playItemRepo.save(item);
        } else {
            playItemRepo.updateByName(name, money);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        return ResponseEntity.ok(jsonObject.toString());
    }

    @Transactional
    public ResponseEntity<String> deleteByName(String name) {
        playItemRepo.deleteByName(name);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        return ResponseEntity.ok(jsonObject.toString());
    }
}
