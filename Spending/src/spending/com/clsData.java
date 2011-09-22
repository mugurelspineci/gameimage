package spending.com;

import android.os.Parcel;
import android.os.Parcelable;

class clsData implements Parcelable {
	
	private int	pay;
	private String amount;
	private String datePay;
	private String reason;

	public clsData(){
	
	}
	
	public clsData(int pay, String amount, String datePay, String reason){
		this.pay = pay;
		this.amount = amount;
		this.datePay = datePay;
		this.reason = reason;
	}
	
	public int getPay() {
		return pay;
	}

	public void setIncome(int pay) {
		this.pay = pay;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDatePay() {
		return datePay;
	}

	public void setDatePay(String datePay) {
		this.datePay = datePay;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	

	
	public clsData(Parcel in){
		this.pay = in.readInt();
		this.amount = in.readString();
		this.datePay = in.readString();
		this.reason = in.readString();
	//	this.other = readString();
	}
	
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeInt(pay);
		dest.writeString(amount);
        dest.writeString(datePay);
		dest.writeString(reason);
		//dest.writeString(other);
    }
	
  
    @SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public clsData createFromParcel(Parcel in){
            return new clsData(in);
        }

        public clsData[] newArray(int size){
            return new clsData[size];
        }
    };



}
