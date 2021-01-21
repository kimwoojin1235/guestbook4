package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;


@Controller
@RequestMapping(value = "/guest")
public class GuestController {
	@Autowired//이세팅을 하면 다오를 부를때 마다 자동적으로 new가 된다.
	private GuestDao guestDao;
	
	@RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
	public String List(Model model) {
		System.out.println("list");
		List<GuestVo> guestlist = guestDao.getList();
		model.addAttribute("glist",guestlist);
		return "addList";
		//Model를 사용하여서 list에 정보를 보내준
	}
	@RequestMapping(value = "/write",method = {RequestMethod.GET,RequestMethod.POST})
	public String write(@ModelAttribute GuestVo guestVo) {
		//@ModelAttribute를 사용하여서 vo의 값을가져온뒤 입력을함
		//리스트와 같이 있있어Model을 사용할 필요x
		System.out.println("등록");
		System.out.println(guestVo.toString());
		guestDao.guestinsert(guestVo);
		
		return "redirect:/guest/list";
	}
	@RequestMapping(value = "/deleteForm",method = {RequestMethod.GET,RequestMethod.POST})
	public String deleteForm() {
		System.out.println("삭제 폼입니다.");
		//삭제 창으로 보내준다.
		return "deleteForm";
	}
	@RequestMapping(value = "/delete",method = {RequestMethod.GET,RequestMethod.POST})
	public String delete(@ModelAttribute GuestVo guestVo) {
		System.out.println("삭제입니다");
		int delete =guestDao.guestdelete(guestVo);
		if(delete==1) {	//성공
			return "redirect:/guest/list";
		}else {//실패
			return"/passerror";
		}
		//리스트에서 no값을 적어주어 포워드 해줌으로 no값을 여기서 포워드하지 않아도됨
		
		
	}
}
