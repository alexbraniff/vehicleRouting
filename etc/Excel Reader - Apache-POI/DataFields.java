package excelImport;

public class DataFields {

	private String client;
	private String carrier;
	private String scac;
	private String clientMode;
	private String shipDate;
	private String freightNum;
	private String paidAmount;
	private String terms;
	private int comClass;
	private int pieces;
	private int weight;
	private String shipCity;
	private String shipState;
	private String shipZip;
	private String cosCity;
	private String cosState;
	private String cosZip;

	public DataFields(String cli, String car, String cac, String cliMode,
			String sDate, String fNum, String pAmount, String fTerms, int cClass, int cPieces, int cWeight,
			String sCity, String sState, String sZip, String cCity, String cState, String cZip)
	{
		setClient(cli);
		setCarrier(car);
		setScac(cac);
		setClientMode(cliMode);
		setShipDate(sDate);
		setFreightNum(fNum);
		setPaidAmount(pAmount);
		setTerms(fTerms);
		setComClass(cClass);
		setPieces(cPieces);
		setWeight(cWeight);
		setShipCity(sCity);
		setShipState(sState);
		setShipZip(sZip);
		setCosCity(cCity);
		setCosState(cState);
		setCosZip(cZip);
	}


	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getScac() {
		return scac;
	}

	public void setScac(String scac) {
		this.scac = scac;
	}

	public String getClientMode() {
		return clientMode;
	}

	public void setClientMode(String clientMode) {
		this.clientMode = clientMode;
	}

	public String getShipDate() {
		return shipDate;
	}

	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	public String getFreightNum() {
		return freightNum;
	}

	public void setFreightNum(String freightNum) {
		this.freightNum = freightNum;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public int getComClass() {
		return comClass;
	}
	public void setComClass(int comClass) {
		this.comClass = comClass;
	}
	public int getPieces() {
		return pieces;
	}
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getShipCity() {
		return shipCity;
	}
	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}
	public String getShipState() {
		return shipState;
	}
	public void setShipState(String shipState) {
		this.shipState = shipState;
	}
	public String getShipZip() {
		return shipZip;
	}
	public void setShipZip(String shipZip) {
		this.shipZip = shipZip;
	}
	public String getCosCity() {
		return cosCity;
	}
	public void setCosCity(String cosCity) {
		this.cosCity = cosCity;
	}
	public String getCosState() {
		return cosState;
	}
	public void setCosState(String cosState) {
		this.cosState = cosState;
	}
	public String getCosZip() {
		return cosZip;
	}
	public void setCosZip(String cosZip) {
		this.cosZip = cosZip;
	}


}
