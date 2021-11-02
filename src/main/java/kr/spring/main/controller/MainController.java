package kr.spring.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import kr.spring.camping.service.CampingService;
import kr.spring.camping.vo.CampingVO;
import kr.spring.reservation.service.ReservationService;
import kr.spring.util.PagingUtil;

@Controller
public class MainController{
	
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private CampingService campingService;
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping("/main/main.do")
	public String main(Model model) {
	Map<String,Object> map = new HashMap<String,Object>();
			
			int count = campingService.selectRowCount(map);
			
			logger.debug("<<count>> : " + count);
																
			PagingUtil page = new PagingUtil(1, count, 8, 10, null);
			
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
			
			List<CampingVO> list = null;
			if(count>0) {
				list = campingService.selectList(map);
				logger.debug("<<list>>" + list);
			}
			
			model.addAttribute("count",count);
			model.addAttribute("list",list);
			
			return "main";//타일스 식별자
	}
	
	//알림 수
	@RequestMapping("/main/notificationCount.do")
	@ResponseBody
	public Map<String, String> processNotification(HttpSession session){
				
		Integer user_num = (Integer)session.getAttribute("user_num");
			
		Map<String,String> map = new HashMap<String,String>();
				
		if(user_num != 0) {
			int notificationCount = reservationService.getReserveNotificationCount(user_num);
			String count = Integer.toString(notificationCount);
					
			System.out.println("notificationCount : " + notificationCount);
					
			if(notificationCount <= 10 && notificationCount >= 1) {
				map.put("result", count);
			}else if(notificationCount == 0) {
				map.put("result", count);
			}
					
			return map;
		}
				
		return map;
	}
	
}
