/**
 * Copyright [2024] [bo-IM]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.james.platform.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.james.common.result.Result;
import com.james.platform.pojo.entity.Friend;
import com.james.platform.pojo.vo.FriendVO;
import com.james.platform.service.FriendService;
import com.james.platform.session.SessionContext;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;


/**
 * @author james
 */
@RestController
@RequestMapping("/api/friend")
@Tag(name = "好友")
public class FriendController {

    private  FriendService friendService;

    @GetMapping("/list")
    @Operation(summary = "好友列表",description="好友列表")
    public Result<List<FriendVO>> list() {
         List<Friend> friends = friendService.findFriendByUserId(SessionContext.getSession().getUserId());
        List<FriendVO> vos = friends.stream().map(f -> {
            FriendVO vo = new FriendVO();
            vo.setId(f.getFriendId());
            vo.setHeadImage(f.getFriendHeadImage());
            vo.setNickName(f.getFriendNickName());
            return vo;
        }).collect(Collectors.toList());
        return Result.OK(vos);
    }
    
    @PostMapping("/add")
    @Operation(summary = "添加好友", description = "双方建立好友关系")
    public Result addFriend(@NotEmpty(message = "好友id不能为空") @RequestParam("friendId") Long friendId) {
        friendService.addFriend(friendId);
        return Result.OK();
    }
    


}
