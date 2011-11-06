package spending.com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


 public class CommonUtil {

	// date validation using SimpleDateFormat
	// it will take a string and make sure it's in the proper
	// format as defined by you, and it will also make sure that
	// it's a legal date

	static public boolean isValidDate(String date) {
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// declare and initialize testDate variable, this is what will hold
		// our converted string

		Date testDate = null;

		// we will now try to parse the string into date form
		try {
			testDate = sdf.parse(date);
		}

		// if the format of the string provided doesn't match the format we
		// declared in SimpleDateFormat() we will get an exception

		catch (ParseException e) {
			// errorMessage = "the date you provided is in an invalid date" +
			// " format.";
			return false;
		}

		if (!sdf.format(testDate).equals(date)) {
			// errorMessage = "The date that you provided is invalid.";
			return false;
		}

		// if we make it to here without getting an error it is assumed that
		// the date was a valid one and that it's in the proper format

		return true;

	} // end isValidDate

	static public boolean checkFloat(String num) {
		try {
			Float.parseFloat(num);
			return true;

		} catch (NumberFormatException e) {
			return false;
		} catch (Exception ex) {
			return false;
		}
	}
	
	static public String trimZeros(String number) {
	    if(!number.contains(".")) {
	        return number;
	    }

	    return number.replaceAll(".?0*$", "");
	}

}