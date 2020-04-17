package com.picc.riskctrl.common.proxy;

import com.picc.riskctrl.common.proxy.vo.PrpDclassVo;
import com.picc.riskctrl.common.proxy.vo.PrpDriskVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Service
@FeignClient(name = "01014020")
public interface CPZXService {
    @RequestMapping(value = "/getClass", method = RequestMethod.POST)
    List<PrpDclassVo> getClass(@RequestBody com.picc.riskctrl.common.proxy.vo.RequestBody requestBody);

    @RequestMapping(value = "/getRisk", method = RequestMethod.POST)
    PrpDriskVo getRisk(@RequestBody com.picc.riskctrl.common.proxy.vo.RequestBody requestBody);
}
