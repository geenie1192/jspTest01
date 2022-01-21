package vo;

public class WelcomeVo {
	public int id;
	public String msg;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public WelcomeVo() {}
	public WelcomeVo(int id, String msg) {
		this.id=id;
		this.msg=msg;
	}
}
