package spending.com;

class clsData implements Parcelable {

	private int	income;
	private String amount;
	private String datePay;
	private String reason;
	//private String other;
	
	public clsData(){
	
	}
	
	public clsData(Parcel in){
		this.income = in.readInt();
		this.amount = readString();
		this.dataPay = readString();
		this.reason = readString();
	//	this.other = readString();
	}
	
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeInt(income);
		dest.writeString(amount);
        dest.writeString(datePay);
		dest.writeString(reason);
		//dest.writeString(other);
    }
	
    //@SuppressWarnings("unchecked")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public clsData createFromParcel(Parcel in){
            return new clsData(in);
        }

        public clsData[] newArray(int size){
            return new clsData[size];
        }
    };



}
