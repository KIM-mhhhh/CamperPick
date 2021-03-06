package kr.spring.reservation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.reservation.vo.ReservationVO;
import kr.spring.reservation.vo.ReserveNotificationVO;

public interface ReservationMapper {
	@Select("SELECT creserve_seq.nextval FROM dual")
	public int selectMem_num();
	@Insert("INSERT INTO creserve (res_num,res_email,res_name,res_phone,headcount,res_start,res_end,mem_num,room_num,camping_num,res_price) VALUES (#{res_num},#{res_email},#{res_name},#{res_phone},#{headcount},#{res_start},#{res_end},#{mem_num},#{room_num},#{camping_num},#{res_price})")
	public void insertReservation(ReservationVO reservation);
	public void updateReservation(ReservationVO reservation);
	@Delete("DELETE FROM creserve WHERE res_num=#{res_num}")
	public void deleteReservation(Integer res_num);
	@Delete("DELETE FROM creserve WHERE room_num=#{room_num}")
	public void deleteReservationToo(Integer room_num);
	@Delete("DELETE FROM creserve WHERE camping_num=#{camping_num}")
	public void deleteReservationFirst(Integer camping_num);
	public List<ReservationVO> getReservationList(Map<String,Object> map);
	public int getReservationCount(String email);
	public ReservationVO getReservation(Integer res_num);
	public ReservationVO getRecentReservation(Integer mem_num);
	//결제 완료 시 예약완료로 예약상태 바꿔줌.
	@Update("UPDATE creserve SET res_state='예약완료' WHERE res_num=#{res_num}")
	public void changeState(Integer res_num);
	@Update("UPDATE creserve SET res_state='예약취소' WHERE res_num=#{res_num}")
	public void changeState2(Integer res_num);
	@Select("SELECT * FROM creserve WHERE room_num=#{room_num} AND NOT res_state='예약취소'")
	public List<ReservationVO> getReservationByRoom(Integer room_num);
	
	@Insert("INSERT INTO creserve_notification(not_num, message, res_num, mem_num) VALUES (creserve_notification_seq.nextval, #{message}, #{res_num}, #{mem_num})")
	public void insertReserveNotfication(ReserveNotificationVO reserveNotificationVO);
	@Update("UPDATE creserve_notification SET read_time=SYSDATE WHERE res_num=#{res_num}")
	public void updateReserveNotfication(Integer res_num);
	@Delete("DELETE FROM creserve_notification WHERE res_num=#{res_num}")
	public void deleteReserveNotfication(Integer res_num);
	@Select("SELECT not_num, message, TO_CHAR(date_time,'YYYY-MM-DD HH24:MI:SS') date_time, TO_CHAR(read_time,'YYYY-MM-DD HH24:MI:SS') read_time, res_num, mem_num FROM creserve_notification WHERE ROWNUM <= 10 AND mem_num=#{mem_num} ORDER BY date_time DESC")
	public List<ReserveNotificationVO> getReserveNotificationList(Integer mem_num);
	@Select("SELECT COUNT(*) FROM(SELECT * FROM creserve_notification ORDER BY ROWNUM DESC) WHERE ROWNUM <= 10 AND mem_num=#{mem_num} AND date_time=read_time")
	public int getReserveNotificationCount(Integer mem_num);
	//타 테이블 삭제시
	@Delete("DELETE FROM creserve_notification WHERE res_num IN(SELECT res_num FROM creserve r JOIN croom c ON r.room_num=c.room_num WHERE c.room_num=#{room_num})")
	public void deleteReserveNotficationByRoom(Integer room_num);
	@Delete("DELETE FROM creserve_notification WHERE res_num IN(SELECT res_num FROM creserve r JOIN camping c ON r.camping_num=c.camping_num WHERE c.camping_num=#{camping_num})")
	public void deleteReserveNotficationByCamping(Integer camping_num);
}
