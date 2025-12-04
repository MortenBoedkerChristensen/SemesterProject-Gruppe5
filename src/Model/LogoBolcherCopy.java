package Model;

public class LogoBolcherCopy extends LogoBolcher {
	private String copyNo;
	public LogoBolcherCopy(int id, String type, int price, int stock, int minStock, int maxStock, String Firma, String copyNo, java.sql.Date date) {
		super(id, type, price, stock, minStock, maxStock, date);
		this.copyNo = copyNo;
	}
	public String getCopyNo() {
		return copyNo;
	}
	public void setCopyNo(String copyNo) {
		this.copyNo = copyNo;
	}
}
