package evl.io.service;

import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;
import evl.io.entity.SugarUser;
import evl.io.jpa.SugarUserRepo;
import evl.io.utils.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SugarUserService {
    private final SugarUserRepo sugarUserRepo;

    @Autowired
    public SugarUserService(SugarUserRepo sugarUserRepo) {
        this.sugarUserRepo = sugarUserRepo;
    }

    public ResponseEntity<String> login(String name, String accessKey) {
        JSONObject jsonObject = new JSONObject();
        SugarUser user = sugarUserRepo.findByName(name);
        if (user == null) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
            return ResponseEntity.ok(jsonObject.toString());
        }
        if (user.getAccessKey().equals(accessKey)) {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.OK);
            jsonObject.put(ServiceConstants.TOKEN, RestUtils.encryptWithSalt(name, name));
        } else {
            jsonObject.put(ServiceConstants.STATUS, ServiceConstants.NG);
        }
        return ResponseEntity.ok(jsonObject.toString());
    }
}
