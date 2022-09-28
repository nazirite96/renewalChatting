package com.woongs.chat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.woongs.chat.domain.Room;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    List<Room> roomList = new ArrayList<Room>();
    static int roomNumber = 0;


    @GetMapping("/chat")
    public String chat(Model model){
        return "chat";
    }


    /**
     * 방 페이지
     * */
    @GetMapping("/room")
    public String room(Model model){
        return "room";
    }


    /**
     * 방 생성하기
     * @param params
     * @return
     */
    @PostMapping("/createRoom")
    @ResponseBody
    public List<Room> createRoom(@RequestParam HashMap<Object, Object> params){
        String roomName = (String) params.get("roomName");
        if(roomName != null && !roomName.trim().equals("")) {
            Room room = new Room();
            room.setRoomNumber(++roomNumber);
            room.setRoomName(roomName);
            roomList.add(room);
        }
        return roomList;
    }

    /**
     * 방 정보가져오기
     * @return
     */
    @PostMapping("/getRoom")
    @ResponseBody
    public List<Room> getRoom(){
        return roomList;
    }
    /**
     * 채팅방
     * @return
     */
    @GetMapping("/moveChating")
    public String chating(RoomForm roomForm, Model model) {
        int roomNumber = roomForm.getRoomNumber();
        System.out.println(roomNumber);
        String whereToGo = "";
        List<Room> new_list = roomList.stream()
                .filter(o->o.getRoomNumber()==roomNumber)
                .collect(Collectors.toList());
        if(new_list != null && new_list.size() > 0) {
            model.addAttribute("roomName", roomForm.getRoomName());
            model.addAttribute("roomNumber",roomNumber);
            whereToGo = "chat";
        }else {
            whereToGo = "room";
        }
        return whereToGo;
    }

}