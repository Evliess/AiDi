package evliess.io.service;

import evliess.io.component.PlayItemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayItemService {
    @Autowired
    private PlayItemConfig playItemConfig;

    public Map<String, String> getItems(){
        return playItemConfig.getItems();
    }
}
