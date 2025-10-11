package evl.io.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import evl.io.entity.ConsumerCharge;
import evl.io.entity.HistoryPlay;
import evl.io.jpa.ConsumerChargeRepo;
import evl.io.jpa.ConsumerRepo;
import evl.io.jpa.HistoryPlayRepo;
import evl.io.utils.DateTimeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinService {
    private final ConsumerChargeRepo consumerChargeRepo;
    private final HistoryPlayRepo historyPlayRepo;
    private final ConsumerRepo consumerRepo;

    public FinService(HistoryPlayRepo historyPlayRepo, ConsumerChargeRepo consumerChargeRepo, ConsumerRepo consumerRepo) {
        this.consumerChargeRepo = consumerChargeRepo;
        this.historyPlayRepo = historyPlayRepo;
        this.consumerRepo = consumerRepo;
    }

    public ResponseEntity<String> findByDateGap(String start, String end) {
        JSONObject jsonObject = new JSONObject();
        List<ConsumerCharge> consumerChargeList =
                this.consumerChargeRepo.findByDateGap(DateTimeUtils.dateToMillis(start),
                        DateTimeUtils.dateToMillisPlus1Day(end));
        JSONArray chargeList = new JSONArray();
        BigDecimal total = new BigDecimal("0");
        for (ConsumerCharge consumerCharge : consumerChargeList) {
            JSONObject charge = new JSONObject();
            charge.put("money", consumerCharge.getMoney());
            total = total.add(new BigDecimal(consumerCharge.getMoney()));
            charge.put("name", consumerRepo.findNameByPhone(consumerCharge.getPhone()));
            charge.put("time", DateTimeUtils.convertToFormattedStringDate(consumerCharge.getChargeAt()));
            chargeList.add(charge);
        }
        jsonObject.put("chargeList", chargeList);

        List<HistoryPlay> historyPlayList = this.historyPlayRepo.findByDateGap(DateTimeUtils.dateToMillis(start),
                DateTimeUtils.dateToMillisPlus1Day(end));
        JSONArray historyList = new JSONArray();
        for (HistoryPlay historyPlay : historyPlayList) {
            JSONObject history = new JSONObject();
            history.put("money", historyPlay.getMoney());
            total = total.add(new BigDecimal(historyPlay.getMoney()));
            history.put("name", historyPlay.getItemName());
            history.put("time", DateTimeUtils.convertToFormattedStringDateTime(historyPlay.getConsumeAt()));
            historyList.add(history);
        }
        jsonObject.put("historyList", historyList);
        jsonObject.put("total", total.toString());
        return ResponseEntity.ok(jsonObject.toString());
    }
}
