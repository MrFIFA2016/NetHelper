package com.gshell.server.controller;

import com.gshell.server.component.WebSocketServer;
import com.gshell.server.pojo.WsMsg;
import com.gshell.server.to.BaseResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cmd")
public class WsController {

    @PostMapping("/send")
    public BaseResp sendCmd(@RequestBody WsMsg msg) {
        int SENDED = WebSocketServer.sendInfo(msg.toString(), null);
        return BaseResp.map().append("sended", SENDED);
    }
}
