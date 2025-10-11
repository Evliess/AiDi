package evl.io.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;
import evl.io.entity.HistoryPlay;
import evl.io.jpa.HistoryPlayRepo;
import evl.io.utils.DateTimeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class HistoryPlayService {
    private final HistoryPlayRepo historyPlayRepo;

    @Autowired
    public HistoryPlayService(HistoryPlayRepo historyPlayRepo) {
        this.historyPlayRepo = historyPlayRepo;
    }

    @Transactional
    public ResponseEntity<String> create(String money, String itemName) {
        HistoryPlay historyPlay = new HistoryPlay();
        historyPlay.setMoney(money);
        historyPlay.setItemName(itemName);
        historyPlay.setConsumeAt(Instant.now().toEpochMilli());
        historyPlayRepo.save(historyPlay);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
        return ResponseEntity.ok(jsonObject.toString());
    }

    public ResponseEntity<String> findAllByDate(Long date) {
        List<HistoryPlay> list = historyPlayRepo.findAllByDate(date);
        JSONArray jsonObject = new JSONArray();
        for (HistoryPlay c : list) {
            JSONObject obj = new JSONObject();
            obj.put("itemName", c.getItemName());
            obj.put("money", c.getMoney());
            obj.put("consumeAt", DateTimeUtils.convertToFormattedStringDateTime(c.getConsumeAt()));
            jsonObject.add(obj);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }


}
