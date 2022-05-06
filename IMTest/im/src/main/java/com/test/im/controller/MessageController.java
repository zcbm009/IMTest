package com.test.im.controller;

import com.test.im.pojo.Message;
import com.test.im.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
@CrossOrigin
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("history")
    public Map<String, Object> requestHistory(@RequestParam Long fromId, @RequestParam Long toId,
                                              @RequestParam Integer page, @RequestParam Integer rows) {
        List<Message> messageByPage = messageService.findMessageByPage(fromId, toId, page, rows);
        messageByPage.forEach(ele ->{
            if(ele.getStatus() == 1){
                // 如果存在消息未读，那么就更改消息为已读状态
                messageService.updateMessageStatus(ele.getId(), 2);
                ele.setStatus(2);
            }
        });
        return new HashMap<String, Object>() {{
            put("msg", "success");
            put("data", messageByPage);
        }};
    }

    public Map<String, Object> getAllParentInfo(){
        // TODO 获取所有好友的消息
        // 1、获取所有的好友列表

        // 2、针对每个好友查询发送的消息


        return new HashMap<String, Object>() {{
            put("msg", "success");
            put("data", "");
        }};
    }

}
