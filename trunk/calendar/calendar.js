var turnOffYearSpan = false;	 // true = Only show This Year and Next, false = show +/- 5 years
var weekStartsOnSunday = true;  // true = Start the week on Sunday, false = start the week on Monday
var showWeekNumber = false;  // true = show week number,  false = do not show week number

var languageCode = 'jp';	// Possible values: 	en,ge,no,nl,es,pt-br,fr
							// en = english, ge = german, no = norwegian,nl = dutch, es = spanish, pt-br = portuguese, fr = french, da = danish, hu = hungarian(Use UTF-8 doctype for hungarian)
var calendar_display_time = true;
var isHightLightCurrentWeek = true;
var isOweekendOtherMonthDay = true;

var myDivision;
var myDivisionFile;
var yearListCreated = false;
var firstTimeRunning = true;
var yearHaveData;

// Format of current day at the bottom of the calendar
// [todayString] = the value of todayString
// [dayString] = day of week (examle: mon, tue, wed...)
// [UCFdayString] = day of week (examle: Mon, Tue, Wed...) ( First letter in uppercase)
// [day] = Day of month, 1..31
// [monthString] = Name of current month
// [year] = Current year
var todayStringFormat = '[todayString] [UCFdayString]. [day]. [monthString] [year]';
var pathToImages = '';	// Relative to your HTML file

var speedOfSelectBoxSliding = 200;	// Milliseconds between changing year and hour when holding mouse over "-" and "+" - lower value = faster
var intervalSelectBox_minutes = 5;	// Minute select box - interval between each option (5 = default)

var calendar_offsetTop = 0;		// Offset - calendar placement - You probably have to modify this value if you're not using a strict doctype
var calendar_offsetLeft = 0;	// Offset - calendar placement - You probably have to modify this value if you're not using a strict doctype
var calendarDiv = false;

var MSIE = false;
var Opera = false;
if(navigator.userAgent.indexOf('MSIE')>=0 && navigator.userAgent.indexOf('Opera')<0)MSIE=true;
if(navigator.userAgent.indexOf('Opera')>=0)Opera=true;


switch(languageCode){
	case "jp":	/* 日本 */
		var monthArray = ['01','02','03','04','05','06','07','08','09','10','11','12'];
		var monthArrayShort = ['01','02','03','04','05','06','07','08','09','10','11','12'];
		var dayArray = ['月','火','水','木','金','土', '日'];
		var weekString = '週';
		var todayString = '';
		break;
}

if (weekStartsOnSunday) {
   var tempDayName = dayArray[6];
   for(var theIx = 6; theIx > 0; theIx--) {
	  dayArray[theIx] = dayArray[theIx-1];
   }
   dayArray[0] = tempDayName;
}



var daysInMonthArray = [31,28,31,30,31,30,31,31,30,31,30,31];
var currentMonth;
var currentYear;
var currentHour;
var currentMinute;
var calendarContentDiv;
var returnDateTo;
var returnFormat;
var activeSelectBoxMonth;
var activeSelectBoxYear;

var iframeObj = false;
//// fix for EI frame problem on time dropdowns 09/30/2006
var iframeObj2 =false;
function EIS_FIX_EI1(where2fixit)
{

		if(!iframeObj2)return;
		iframeObj2.style.display = 'block';
		iframeObj2.style.height =document.getElementById(where2fixit).offsetHeight+1;
		iframeObj2.style.width=document.getElementById(where2fixit).offsetWidth;
		iframeObj2.style.left=getleftPos(document.getElementById(where2fixit))+1-calendar_offsetLeft;
		iframeObj2.style.top=getTopPos(document.getElementById(where2fixit))-document.getElementById(where2fixit).offsetHeight-calendar_offsetTop;
}

function EIS_Hide_Frame()
{		if(iframeObj2)iframeObj2.style.display = 'none';}

//// fix for EI frame problem on time dropdowns 09/30/2006
var returnDateToYear;
var returnDateToMonth;
var returnDateToDay;
var returnDateToHour;
var returnDateToMinute;

var inputYear;
var inputMonth;
var inputDay;
var inputHour;
var inputMinute;
var calendarDisplayTime = false;

var selectBoxHighlightColor = '#a50404'; // Highlight color of select boxes
var selectBoxRolloverBgColor = '#a2a2cc'; // Background color on drop down lists(rollover)
var hightLightWeekColor = '#FFFFC3';

var selectBoxMovementInProgress = false;
var activeSelectBox = false;

function cancelCalendarEvent()
{
	return false;
}
function isLeapYear(inputYear)
{
	if(inputYear%400==0||(inputYear%4==0&&inputYear%100!=0)) return true;
	return false;

}
var activeSelectBoxMonth = false;
var activeSelectBoxDirection = false;

function highlightMonthYear()
{
	if(activeSelectBoxMonth)activeSelectBoxMonth.className='';
	activeSelectBox = this;


	if(this.className=='monthYearActive'){
		this.className='';
	}else{
		this.className = 'monthYearActive';
		activeSelectBoxMonth = this;
	}

	if(this.innerHTML.indexOf('-')>=0 || this.innerHTML.indexOf('+')>=0){
		if(this.className=='monthYearActive')
			selectBoxMovementInProgress = true;
		else
			selectBoxMovementInProgress = false;
		if(this.innerHTML.indexOf('-')>=0)activeSelectBoxDirection = -1; else activeSelectBoxDirection = 1;

	}else selectBoxMovementInProgress = false;

}

function showMonthDropDown()
{
	if(document.getElementById('monthDropDown').style.display=='block'){
		document.getElementById('monthDropDown').style.display='none';
		//// fix for EI frame problem on time dropdowns 09/30/2006
				EIS_Hide_Frame();
	}else{
		document.getElementById('monthDropDown').style.display='block';
		document.getElementById('yearDropDown').style.display='none';
		if (MSIE) { 
			EIS_FIX_EI1('monthDropDown')
		}
		//// fix for EI frame problem on time dropdowns 09/30/2006
	}
}


function showYearDropDown()
{
	if(document.getElementById('yearDropDown').style.display=='block'){
		document.getElementById('yearDropDown').style.display='none';
		//// fix for EI frame problem on time dropdowns 09/30/2006
				EIS_Hide_Frame();
	}else{
		document.getElementById('yearDropDown').style.display='block';
		document.getElementById('monthDropDown').style.display='none';
			if (MSIE)
		{ EIS_FIX_EI1('yearDropDown')}
		//// fix for EI frame problem on time dropdowns 09/30/2006

	}

}
 
function closeAllDropDown()
{

	if(document.getElementById('yearDropDown')){
		if(document.getElementById('yearDropDown').style.display=='block'){
			document.getElementById('yearDropDown').style.display='none';

		}
	}
	
	if(document.getElementById('yearDropDown')){
		if(document.getElementById('monthDropDown').style.display=='block'){
			document.getElementById('monthDropDown').style.display='none';

		}
	}
}

function selectMonth()
{
	document.getElementById('calendar_month_txt').innerHTML = this.innerHTML
	currentMonth = this.id.replace(/[^\d]/g,'');

	document.getElementById('monthDropDown').style.display='none';
	//// fix for EI frame problem on time dropdowns 09/30/2006
				EIS_Hide_Frame();
	for(var no=0;no<monthArray.length;no++){
		document.getElementById('monthDiv_'+no).style.color = '';
		document.getElementById('monthDiv_'+no).className = '';
	}
	//this.style.color = selectBoxHighlightColor;
	setHighlighDivMonthByMonth(currentMonth);
	activeSelectBoxMonth = this;
	
	writeCalendar(myDivision, myDivisionFile);
	
	EnableNextPrev();
	
	UpdateNextPrev();
	
}

function selectMinute()
{
	document.getElementById('calendar_minute_txt').innerHTML = this.innerHTML
	currentMinute = this.innerHTML.replace(/[^\d]/g,'');
	//// fix for EI frame problem on time dropdowns 09/30/2006
				EIS_Hide_Frame();
	
	this.style.color = selectBoxHighlightColor;
}


function selectYear()
{
	
	document.getElementById('calendar_year_txt').innerHTML = this.innerHTML
	currentYear = this.innerHTML.replace(/[^\d]/g, '');
	document.getElementById('yearDropDown').style.display = 'none';
	//// fix for EI frame problem on time dropdowns 09/30/2006
				EIS_Hide_Frame();
				
	//// Reset years
	/*
	for(var i = 0; i < myDivision.length; i++){
		var year = FormatYear(myDivision[i]);
		if (document.getElementById('yearDiv' + year)){
			document.getElementById('yearDiv' + year).className = '';
		}
	}*/
	
	if(activeSelectBoxYear){
		activeSelectBoxYear.style.color = '';
	}

	activeSelectBoxYear = this;
	this.style.color = selectBoxHighlightColor;
	setHighlighDivMonthByMonth(currentMonth);
	writeCalendar(myDivision, myDivisionFile);
	
	EnableNextPrev();
	 
	UpdateNextPrev();


}

function setHighlighDivMonthByMonth(myMonth) {

	//// Reset months 
	/*for(var no = 0; no < monthArray.length; no++){
		document.getElementById('monthDiv_' + no).style.color = '';
		document.getElementById('monthDiv_' + no).className = '';
	}*/
	for(var no = 0; no < 12; no++){
	if(document.getElementById('monthDiv_' + no)) {
		document.getElementById('monthDiv_' + no).style.color = '';
		document.getElementById('monthDiv_' + no).className = '';
		document.getElementById('monthDiv_' + no).style.fontWeight= '';
	}
}
	//// Reset years
	/*for(var i = 0; i < myDivision.length; i++){
		var year = FormatYear(myDivision[i]);
		if (document.getElementById('yearDiv' + year)){
			document.getElementById('yearDiv' + year).style.color = '';
			document.getElementById('yearDiv' + year).className = '';
		}   
	}*/
	
	for(var yr = GetYearMin(); yr <=GetYearMax(); yr++){
		if (document.getElementById('yearDiv' + yr)){
			document.getElementById('yearDiv' + yr).style.color = '';
			document.getElementById('yearDiv' + yr).className = '';
		}
	}
	/*
	for(var i = 0; i < myDivision.length; i++){
	var year = FormatYear(myDivision[i]);
	//alert(year);
		if(document.getElementById('yearDiv' + year)) {
			document.getElementById('yearDiv' + year).style.color = '003cff';
		}
	}*/
	var currentYearHaveData=false;
	var idxstart = 0;
	var idxend = 0;
	for(var i=0;i<yearHaveData.length;i=i+3){
		//alert(yearHaveData[i]);
		if(document.getElementById('yearDiv' + yearHaveData[i])) {
			document.getElementById('yearDiv' + yearHaveData[i]).style.color = '003cff';
		}
		if(parseInt(currentYear,10)==parseInt(yearHaveData[i],10)){
			currentYearHaveData=true;
			idxstart = parseInt(yearHaveData[i+1].substring(3,yearHaveData[i+1].length),10);
			idxend = parseInt(yearHaveData[i+2].substring(3,yearHaveData[i+2].length),10);
		}
	}
	//alert(currentYear);
	//alert(idxstart);
	//alert(idxend);
	
	if(currentYearHaveData){
		//alert(currentYear);
		var monthtemp=-1;
		//alert(yearHaveData[i+1]);
		//alert(yearHaveData[i+2]);
		for(var j = idxstart; j <= idxend; j++){
		var month = parseInt(FormatMonth(myDivision[j]),10);
			if(month!=monthtemp){
			//alert(month);
				if(document.getElementById('monthDiv_' + (month-1))) {
				document.getElementById('monthDiv_' + (month-1)).style.color = '003cff';
				}
			}
		monthtemp = month;
		}
	}
	
	
	
/*
	for(var i = 0; i < myDivision.length; i++){
		var year = FormatYear(myDivision[i]);
		var month = parseInt(FormatMonth(myDivision[i]),10);
		if(year == parseInt(currentYear,10)){
		//alert(month);
			if(document.getElementById('monthDiv_' + (month-1))) {
				document.getElementById('monthDiv_' + (month-1)).style.color = '003cff';
				//document.getElementById('monthDiv_' + month).className = 'monthYearActive';
			}
		}
	}*/
	
	if(myMonth == -1) myMonth = 11;
	if(myMonth == 12) myMonth = 0;
	var yearValueSelectBox = document.getElementById('calendar_year_txt').innerHTML;
	
	
	var d=new Date();
	if(d.getYear() == currentYear){
	if(document.getElementById('monthDiv_' + d.getMonth() )) {
			document.getElementById('monthDiv_' + d.getMonth() ).style.color = '007f12';
			document.getElementById('monthDiv_' + d.getMonth() ).style.fontWeight= 'bold';
			//document.getElementById('monthDiv_' + month).className = 'monthYearActive';
		}
	}
	
	if(document.getElementById('yearDiv' + d.getYear() )) {
			document.getElementById('yearDiv' + d.getYear() ).style.color = '007f12';
			document.getElementById('yearDiv' + d.getYear() ).style.fontWeight= 'bold';
	}
	
	if(document.getElementById('monthDiv_' + myMonth)) {
		document.getElementById('monthDiv_' + myMonth).style.color = selectBoxHighlightColor;
		document.getElementById('monthDiv_' + myMonth).className = 'monthYearActive';
	}
	if(document.getElementById('yearDiv' + yearValueSelectBox)) {
		document.getElementById('yearDiv' + yearValueSelectBox).style.color = selectBoxHighlightColor;
		document.getElementById('yearDiv' + yearValueSelectBox).className = 'monthYearActive';   
	}
	
}


function switchMonth()
{   
	var yearValueSelectBox = document.getElementById('calendar_year_txt').innerHTML;
	var monthValueSelectBox = document.getElementById('calendar_month_txt').innerHTML;	 		
	var monthCompare = currentMonth + 1;
	var rightClick = document.getElementById('rightClick');
	var leftClick = document.getElementById('leftClick');
	
	
	//
	// Left month buttion clicked
	//
	if(this.src.indexOf('left') >= 0){
		currentMonth = parseInt(currentMonth,10) - 1;
		//alert(currentMonth);
		if(currentMonth < 0) {
			currentMonth = 11;
			currentYear = parseInt(currentYear,10) - 1;
		}
		
		if( currentYear >= GetYearMin() && (parseInt(currentMonth,10)) > -1) {
			writeCalendar(myDivision, myDivisionFile); 
			rightClick.onclick = switchMonth;
			rightClick.src = pathToImages + 'right.jpg'; 
			rightClick.style.cursor = 'pointer';
			createMouseEventNext(rightClick);
			var myMonthPrev = monthValueSelectBox/1-2;
			setHighlighDivMonthByMonth(myMonthPrev);
			
		}else{	
			leftClick.onclick = '';
			leftClick.src = pathToImages + 'left_end_1.jpg';
			leftClick.style.cursor = 'default';
			currentMonth = monthValueSelectBox ;
			currentYear = yearValueSelectBox;
			releaseMouseEvent(leftClick);
		}
	}
	//
	// Right month button clicked
	//
	if(this.src.indexOf('right') >= 0){
		currentMonth = parseInt(currentMonth,10) + 1;		
		if(currentMonth > 11){
			currentMonth = 0;
			currentYear = parseInt(currentYear,10) + 1;
		}
		
		if( currentYear <= GetYearMax() && (parseInt(currentMonth,10)) < 12) {
			writeCalendar(myDivision, myDivisionFile);			 
			leftClick.onclick = switchMonth;
			leftClick.src = pathToImages + 'left.jpg';
			leftClick.style.cursor = 'pointer';
			createMouseEventPrev(leftClick);
			var myMonthNext = monthValueSelectBox/1;
			setHighlighDivMonthByMonth(myMonthNext);
		}else{			
			rightClick.onclick = '';
			rightClick.src = pathToImages + 'right_end_1.jpg';
			rightClick.style.cursor = 'default';
			currentMonth = monthValueSelectBox ;
			currentYear = yearValueSelectBox;
			releaseMouseEvent(rightClick);
		}
	}

	/*
	var today = document.getElementById('today');
	today.src = pathToImages + 'center.jpg';
	today.style.cursor = 'pointer';*/
	
}

function IsContain(array, obj)
{
  for(var i = 0; i < array.length; i++) {
	var year = FormatYear(array[i]);
	if(year == obj){
	  return true;
	}
  }
  return false;

}

function GetYearMin()
{

	if(myDivision.length > 0){
		var yearMin = FormatYear(myDivision[0]);
		/*for(var i = 0; i < myDivision.length; i++){
			var year = FormatYear(myDivision[i]);
			if(year < yearMin){
				yearMin = year;
			}	   
		}*/
		
		if(yearMin > (new Date()).getFullYear()){
			return (new Date()).getFullYear();
		}else{
			return yearMin;
		}
	}else{
		return (new Date()).getFullYear();
	}
	
}

function GetYearMax()
{

	if(myDivision.length > 0 ){
		var yearMax = FormatYear(myDivision[myDivision.length-1]);
		/*
		for(var i = 0; i < myDivision.length; i++){
			var year = FormatYear(myDivision[i]);
			if(year > yearMax){
				yearMax = year;
			}
		}*/
		
		if(yearMax < (new Date()).getFullYear()){
			return (new Date()).getFullYear();
		}else{
			return yearMax;
		}
	}else{
		return (new Date()).getFullYear();
	}
}

function GetMonthMaxOfYear(YearMax)
{
	var monthTmp = 1;
	var monthMax = 0;
	for(var i = 0; i < myDivision.length; i++){
		var year = FormatYear(myDivision[i]);		
		if(YearMax == year){
			var month = myDivision[i].substring(2, 4);			
			if(month >= monthTmp){			   
				monthMax = month;	
			}
		}
	}
	return monthMax/1;
}


function DateListIsConstainYearNow(yearNow)
{
	for(var i = 0; i < myDivision.length; i++){
		var year = FormatYear(myDivision[i]);		
		if(year == yearNow) return true;
	}
	return false;
}


function createMonthDiv()
{
	var div = document.createElement('DIV');
	div.className='monthYearPicker';
	div.id = 'monthPicker';	
	for(var no=0;no<monthArray.length;no++){
		var subDiv = document.createElement('DIV');
		subDiv.innerHTML = monthArray[no];
		subDiv.onmouseover = highlightMonthYear;
		subDiv.onmouseout = highlightMonthYear;
		subDiv.onclick = selectMonth;
		subDiv.id = 'monthDiv_' + no;
		subDiv.style.width = '38px';
		subDiv.onselectstart = cancelCalendarEvent;
		div.appendChild(subDiv);
		if(currentMonth && currentMonth==no){
			subDiv.style.color = selectBoxHighlightColor;
			activeSelectBoxMonth = subDiv;
		}
	}
	return div;
}


function changeSelectBoxYear(e,inputObj)
{
	if(!inputObj)inputObj =this;
	var yearItems = inputObj.parentNode.getElementsByTagName('DIV');
	if(inputObj.innerHTML.indexOf('-')>=0){
		var startYear = yearItems[1].innerHTML/1 -1;
		if(activeSelectBoxYear){
			activeSelectBoxYear.style.color='';
		}
	}else{
		var startYear = yearItems[1].innerHTML/1 +1;
		if(activeSelectBoxYear){
			activeSelectBoxYear.style.color='';

		}
	}

	for(var no=1;no<yearItems.length-1;no++){
		yearItems[no].innerHTML = startYear+no-1;
		yearItems[no].id = 'yearDiv' + (startYear/1+no/1-1);

	}
	if(activeSelectBoxYear){
		activeSelectBoxYear.style.color='';
		if(document.getElementById('yearDiv'+currentYear)){
			activeSelectBoxYear = document.getElementById('yearDiv'+currentYear);
			activeSelectBoxYear.style.color=selectBoxHighlightColor;;
		}
	}
}

function updateMonthDiv()
{
	for(no=0;no<12;no++){
		document.getElementById('monthDiv_' + no).style.color = '';
	}
	document.getElementById('monthDiv_' + currentMonth).style.color = selectBoxHighlightColor;
	activeSelectBoxMonth = 	document.getElementById('monthDiv_' + currentMonth);
	
	setHighlighDivMonthByMonth(currentMonth);
}


function updateYearDiv()
{
	var yearSpan = 5;
	if (turnOffYearSpan) {
	   yearSpan = 0;
	}
	var parentDiv = document.getElementById('yearDropDown');
	/*for(var i = 0; i < myDivision.length; i++){
		var year = FormatYear(myDivision[i]); 
		if (document.getElementById('yearDiv' + year)){
			var child = document.getElementById('yearDiv' + year);
			parentDiv.removeChild(child);
		}
	}*/
	var children = document.getElementById('yearDropDown').getElementsByTagName('*');
	for(var i = children.length-1; i >=0; i--){
		if(children[i]){
			parentDiv.removeChild(children[i]);
		}
	}
	
	createYearDiv();	
}


function createYearDiv()
{
	if(!document.getElementById('yearDropDown')){
		var div = document.createElement('DIV');
		div.className='monthYearPicker';
	}else{
		var div = document.getElementById('yearDropDown');
		var subDivs = div.getElementsByTagName('DIV');
		for(var no=0;no<subDivs.length;no++){
			subDivs[no].parentNode.removeChild(subDivs[no]);
		}
	}

	var d = new Date();
	if(currentYear){
		d.setFullYear(currentYear);
	}
	var startYear = d.getFullYear()/1 - 5;
	var yearSpan = 10;
	var yearLimit = currentYear - 9;
	
	 // for(var no = yearLimit; no < (yearLimit + yearSpan); no++){
	 //var yearTmp = '';

		/*for(var i = 0; i < myDivision.length; i++){
			var no = FormatYear(myDivision[i]); 
			if(yearTmp != no){
				var subDiv = document.createElement('DIV');  
				subDiv.innerHTML = no;
				subDiv.onmouseover = highlightMonthYear;
				subDiv.onmouseout = highlightMonthYear;
				subDiv.onclick = selectYear;
				subDiv.id = 'yearDiv' + no;
				subDiv.onselectstart = cancelCalendarEvent;
				div.appendChild(subDiv);
				if(currentYear && currentYear==no){
					subDiv.style.color = selectBoxHighlightColor;
					activeSelectBoxYear = subDiv;
				}
			}		
			yearTmp = no;
		}*/
		//alert("yearMin : "+GetYearMin());
		//alert("yearMax : "+GetYearMax());
		var ymin = parseInt(GetYearMin(),10);
		var ymax = parseInt(GetYearMax(),10);
		
		for(var yno = ymin; yno <= ymax; yno++){
				var subDiv = document.createElement('DIV');
				subDiv.innerHTML = yno;
				subDiv.onmouseover = highlightMonthYear;
				subDiv.onmouseout = highlightMonthYear;
				subDiv.onclick = selectYear;
				subDiv.id = 'yearDiv' + yno;
				subDiv.onselectstart = cancelCalendarEvent;
				div.appendChild(subDiv);
//				if(currentYear && currentYear==yno){
//					subDiv.style.color = selectBoxHighlightColor;
//					activeSelectBoxYear = subDiv;
//				}
		}		
	return div;
}

function FormatYear(year){
	year = year.substring(0, 2);
	year = '20' + year;
	return year;
}

function FormatMonth(month){
	month = month.substring(2, 4);
	return month;
}

function slideCalendarSelectBox()
{
	if(selectBoxMovementInProgress){
		if(activeSelectBox.parentNode.id=='yearDropDown'){
			changeSelectBoxYear(false,activeSelectBox);
		}

	}
	setTimeout('slideCalendarSelectBox()',speedOfSelectBoxSliding);

}

function highlightSelect()
{

	if(this.className=='selectBoxTime'){
		this.className = 'selectBoxTimeOver';
		this.getElementsByTagName('IMG')[0].src = pathToImages + 'down_time_over.gif';
	}else if(this.className=='selectBoxTimeOver'){
		this.className = 'selectBoxTime';
		this.getElementsByTagName('IMG')[0].src = pathToImages + 'down_time.gif';
	}
}

function highlightArrow()
{  
	if(this.src.indexOf('over') >= 0) {
		if(this.src.indexOf('left') >= 0) { 
			this.src = pathToImages + 'left.jpg';
		}
		if(this.src.indexOf('right') >= 0) {
			this.src = pathToImages + 'right.jpg'; 
		}
	}else {
		if(this.src.indexOf('left') >= 0 && this.src.indexOf('left_end_1.jpg') < 0 ) { 
			//this.src = pathToImages + 'left_over.jpg'; 
		}
		if(this.src.indexOf('right') >= 0 && this.src.indexOf('right_end_1.jpg') < 0 ) { 
			//this.src = pathToImages + 'right_over.jpg'; 
		}
	}
}

function closeCalendar(){

	document.getElementById('yearDropDown').style.display='none';
	document.getElementById('monthDropDown').style.display='none';

	calendarDiv.style.display='none';
//	if(iframeObj){
//		iframeObj.style.display='none';
//		 //// //// fix for EI frame problem on time dropdowns 09/30/2006
//			EIS_Hide_Frame();}
	if(activeSelectBoxMonth)activeSelectBoxMonth.className='';
	if(activeSelectBoxYear)activeSelectBoxYear.className='';


}

function writeTopBar()
{
	var topBar = document.createElement('DIV');
	topBar.id = 'topBar';
	calendarDiv.appendChild(topBar);

	// ---------------------------------------------
	var topBarTop = document.createElement('DIV');
	topBarTop.className = 'topBar';
	
	// Year selector
	var yearDiv = document.createElement('DIV');
	yearDiv.onmouseover = highlightSelect;
	yearDiv.onmouseout = highlightSelect;
	yearDiv.onclick = showYearDropDown;
	var span = document.createElement('SPAN');
	span.innerHTML = currentYear;
	span.id = 'calendar_year_txt';
	yearDiv.appendChild(span);
	topBarTop.appendChild(yearDiv);

	var img = document.createElement('IMG');
	img.src = pathToImages + 'down.jpg';
	yearDiv.appendChild(img);
	yearDiv.className = 'selectBox';

	if(Opera){
		yearDiv.style.width = '50px';
		img.style.cssText = 'float:right';
		img.style.position = 'relative';
		img.style.styleFloat = 'right';
	}

	var yearPicker = createYearDiv();
	yearPicker.style.left = '137px';
	yearPicker.style.top = '26px'; //monthDiv.offsetTop + monthDiv.offsetHeight + 1 + 'px';
	yearPicker.style.width = '52px';
	yearPicker.id = 'yearDropDown';
	calendarDiv.appendChild(yearPicker);
	
	var yearName = document.createElement('DIV');
	yearName.innerHTML = '年';
	yearName.className = 'dateName';
	topBarTop.appendChild(yearName);

	topBar.appendChild(topBarTop);
	
	// Month selector
	var monthDiv = document.createElement('DIV');
	monthDiv.id = 'monthSelect';
	monthDiv.onclick = showMonthDropDown;
	var span = document.createElement('SPAN');
	span.innerHTML = monthArray[currentMonth];
	span.id = 'calendar_month_txt';
	monthDiv.appendChild(span);

	var img = document.createElement('IMG');
	img.src = pathToImages + 'down.jpg';
	img.style.position = 'absolute';
	img.style.right = '0px';
	monthDiv.appendChild(img);
	monthDiv.className = 'selectBox';
	if(Opera){
		img.style.cssText = 'float:right;position:relative';
		img.style.position = 'relative';
		img.style.styleFloat = 'right';
	}
	
	topBarTop.appendChild(monthDiv);

	var monthPicker = createMonthDiv();
	monthPicker.style.left = '243px';
	monthPicker.style.top = '26px'//monthDiv.offsetTop + monthDiv.offsetHeight + 1 + 'px';
	monthPicker.style.width ='43px';
	monthPicker.id = 'monthDropDown';
	var monthName = document.createElement('DIV');
	monthName.innerHTML = '月';
	monthName.className = 'dateName';
	topBarTop.appendChild(monthName);

	calendarDiv.appendChild(monthPicker);

	
	
	//------------------------------------------------
	var topBarBottom = document.createElement('DIV');
	topBarBottom.className = 'topBarBottom';
	topBarBottom.onmouseover = closeAllDropDown;
	// Left arrow
	var leftDiv = document.createElement('DIV');
	leftDiv.style.marginRight = '1px';
	var img = document.createElement('IMG');
	img.src = pathToImages + 'left.jpg';
	img.style.width = '25px';
	img.style.height = '21px';
	img.id = 'leftClick';
	img.style.cursor = 'pointer';	
	img.onclick = switchMonth;
	img.className = '';
	createMouseEventPrev(img);
	leftDiv.appendChild(img);
	topBarBottom.appendChild(leftDiv);
	if(Opera)leftDiv.style.width = '16px';

	
	// Center arrow
	var centerBar = document.createElement('DIV');	
	var imgToday = document.createElement('IMG');
	imgToday.src = pathToImages + 'center.jpg';
	imgToday.style.cursor = 'pointer';
	imgToday.style.width = '87px';
	imgToday.style.height = '21px';
	imgToday.id = 'today';
	imgToday.onclick = setToday;
	createMouseEventToday(imgToday);
	centerBar.appendChild(imgToday);
	topBarBottom.appendChild(centerBar);
	
	// Right arrow
	var rightDiv = document.createElement('DIV');
	rightDiv.style.marginRight = '1px';
	var img = document.createElement('IMG');
	img.src = pathToImages + 'right.jpg';
	img.style.cursor = 'pointer';
	img.style.width = '25px';
	img.style.height = '21px';
	img.id = 'rightClick';	
	img.onclick = switchMonth;
	createMouseEventNext(img);
	rightDiv.appendChild(img);
	if(Opera)rightDiv.style.width = '20px';
	topBarBottom.appendChild(rightDiv);
	
	topBar.appendChild(topBarBottom);

}

function mouseDownEventPrev(){
	this.src = 'b-left-over.jpg';
}

function mouseDownEventToday(){
	this.src = 'b-middle-over.jpg';
}

function mouseDownEventNext(){
	this.src = 'b-right-over.jpg';
}

function mouseUpEventPrev(){
	this.src = 'left.jpg';
}

function mouseUpEventToday(){
	this.src = 'center.jpg';
}

function mouseUpEventNext(){
	this.src = 'right.jpg';
}

function releaseMouseEvent(obj){
	obj.onmousedown = '';
	obj.onmouseup = '';
}

function createMouseEventPrev(obj){
	obj.onmousedown = mouseDownEventPrev;
	obj.onmouseup = mouseUpEventPrev;
}

function createMouseEventToday(obj){
	obj.onmousedown = mouseDownEventToday;
	obj.onmouseup = mouseUpEventToday;
}

function createMouseEventNext(obj){
	obj.onmousedown = mouseDownEventNext;
	obj.onmouseup = mouseUpEventNext;
}

function setToday() {
	var today = document.getElementById('today');	
	var d = new Date();
	currentMonth = d.getMonth();
	currentYear = d.getFullYear();   
	
//	if(DateListIsConstainYearNow(d.getFullYear())) {
//		today.src = pathToImages + 'center.jpg';
//		today.style.cursor = 'pointer';
//		createMouseEventToday(today);
//		setHighlighDivMonthByMonth(currentMonth);
//	}else{
//		currentYear = GetYearMax();
//		currentMonth = GetMonthMaxOfYear(GetYearMax()) - 1;	 
//		today.src = pathToImages + 'center_end_1.jpg';
//		today.style.cursor = 'default';
//		today.onclick = '';
//		releaseMouseEvent(today);
//	}
	
	writeCalendar(myDivision, myDivisionFile);
	setHighlighDivMonthByMonth(currentMonth);
	
	if(document.getElementById('rightClick')){
		var rightClick = document.getElementById('rightClick');
		rightClick.onclick = switchMonth;
		rightClick.src = pathToImages + 'right.jpg';
		rightClick.style.cursor = 'pointer';
		createMouseEventNext(rightClick);
	}
	if(document.getElementById('leftClick')){
		var leftClick = document.getElementById('leftClick');
		leftClick.onclick = switchMonth;
		leftClick.src = pathToImages + 'left.jpg';	  
		leftClick.style.cursor = 'pointer'; 
		createMouseEventPrev(leftClick);
	}	
}

function Cleanup() {
	window.clearInterval(idTmr);
	CollectGarbage();
} 


/**
* -------------------------------------------------------- 
* Open an application by program of window
* @param: fileName
* @return void

* Open IE -> Tools ->Internet Options -> Security -> Custom Level -> 
* ActiveX controls and plug-ins ->Enable "Initialize and script ActiveX controls not marked as safe for scripting" 

* --------------------------------------------------------
*/
//function runApp( fileName ) {
//	if (window.ActiveXObject){   
//	   var xlApp = new ActiveXObject("Excel.Application");
//
//	   if(xlApp != null) {   
//		   fileName = fileName.replace("\\", "\\\\"); 
//		   xlApp.workbooks.open(fileName);
//		   xlApp.Worksheets(1).Cells(1,1).value="=Auto_Open()";
//		   xlApp.Worksheets(1).Cells(1,1).value = "";
//		   xlApp.visible = true;
//		   idTmr = window.setInterval("Cleanup();", 1000); 
//	   }
//	}
//}

/**
 * --------------------------------------------------------
 * Write content of calendar
 * --------------------------------------------------------
 */
function writeCalendar(divisionName, divisionNameFile) {
	var d = new Date();
   
   //HuynhTran 追加 2011/08/11 BEGIN ↓↓↓↓
	var numFile;
	var linka;
	var j;	
	var arrFileName;

	//HuynhTran 追加 2011/08/11 End ↑↑↑↑ 
	
	var calendarContentDivExists = true;  
	if(!calendarContentDiv){
		calendarContentDiv = document.createElement('DIV');
		calendarDiv.appendChild(calendarContentDiv);
		calendarContentDivExists = false;
		calendarContentDiv.onmouseover = closeAllDropDown;
	}
	
	// 
	// Set visible next button for last month of new year 
	// And set visible prev button for first month of old year
	//
	UpdateNextPrev();   
	
	
	//
	// Set Year - Month for first loading
	//
	currentMonth = currentMonth/1;	
	d.setFullYear(currentYear);
	d.setDate(1);
	d.setMonth(currentMonth);	
	

	var dayStartOfMonth = d.getDay();  
	if (! weekStartsOnSunday) {
	  if(dayStartOfMonth==0)dayStartOfMonth=7;
	  dayStartOfMonth--;
	}
	if(document.getElementById('calendar_year_txt')){
	document.getElementById('calendar_year_txt').innerHTML = currentYear;
	document.getElementById('calendar_month_txt').innerHTML = monthArray[currentMonth];
	}

	var existingTable = calendarContentDiv.getElementsByTagName('TABLE');
	if(existingTable.length>0){
		calendarContentDiv.removeChild(existingTable[0]);
	}

	var calTable = document.createElement('TABLE');

	calTable.width = '100%';
	calTable.cellSpacing = '0';
	calTable.cellPadding = '0';
	calTable.style.border = '0px';
	calendarContentDiv.appendChild(calTable);


	var calTBody = document.createElement('TBODY');
	calTable.appendChild(calTBody);
	var row = calTBody.insertRow(-1); 
	row.className = 'calendar_week_row';
	if (showWeekNumber) {
	  var cell = row.insertCell(-1);
	   cell.innerHTML = weekString;
	   cell.className = 'calendar_week_column';
	   cell.style.backgroundColor = selectBoxRolloverBgColor;
	}
	
	//
	// Write date title(日から ～ 土まて)
	//
	for (var no=0;no<dayArray.length;no++) {		
		var cell = row.insertCell(-1);  
		cell.innerHTML = dayArray[no];
		if (no == 0 || no == dayArray.length-1) {			
			cell.style.backgroundColor = '#2418b8';
		}
	}

	var row = calTBody.insertRow(-1);

	if (showWeekNumber) {
	   var cell = row.insertCell(-1);
	   cell.className = 'calendar_week_column';
	   cell.style.backgroundColor = selectBoxRolloverBgColor;
	   var week = getWeek(currentYear,currentMonth,1);
	   cell.innerHTML = week;		// Week
	}
	
	var currentDate = new Date();  
	var yearValueSelectBox = document.getElementById('calendar_year_txt').innerHTML;
	var monthValueSelectBox = document.getElementById('calendar_month_txt').innerHTML;  
	//
	// Write first space
	//
	/*var currentMonthTmp = parseInt(currentMonth,10);
	var currentYearTmp = parseInt(yearValueSelectBox.substring(2, yearValueSelectBox.length),10);
	if(currentMonth == 0) {
		currentMonthTmp = 12;
		currentYearTmp = currentYearTmp -1;
	} 

	if(currentMonthTmp<10){
		currentMonthTmp = "0"+currentMonthTmp;
	}
	if(currentYearTmp<10){
		currentYearTmp = "0"+currentYearTmp;
	}

	for (var no=0;no<dayStartOfMonth;no++){
		var cell = row.insertCell(-1);		
		if(isOweekendOtherMonthDay){

			var dayValue = (daysInMonthArray[currentMonthTmp-1]-dayStartOfMonth) + no + 1;

			var datefull = currentYearTmp +""+ currentMonthTmp +""+ dayValue;
			//alert(datefull);
			var isHaveData = false;
			if(divisionName.length>0){
				for(var i=0; i<divisionName.length; i++) {
					if(datefull == divisionName[i]){
						var fileName = "'" + divisionNameFile[i] + "'";  
						fileName = fixPath(fileName);
						// Detect IE browser
						if(MSIE){
							//cell.innerHTML = '<a href="javascript:runApp('+fileName+')"> ' + no + ' </a>';
							cell.innerHTML = '<a href="'+divisionNameFile[i]+'">' + dayValue + '</a>';
						} 
						else{ // Otherwise
							cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + no + '</a>';
							//cell.innerHTML = '<a href="'+divisionNameFile[i]+'">' + dayValue + '</a>';
						}
						isHaveData = true;
					}
				}
			}
			
			
			if(! isHaveData){
				cell.innerHTML = dayValue;			
			}
			// Day is Sunday
			// Va no ko thuoc tuan hien tai		
			if(no==0){	
				cell.className = 'IsSun';
				cell.id = 'IsSun';
			}	
			else cell.className = 'oweekendOtherMonthDay';
		}else{
			cell.innerHTML = '&nbsp;';
		}
	}*/
	var currentMonthTmp = parseInt(currentMonth,10);
	var currentYearTmp = parseInt(yearValueSelectBox,10);
	if(currentMonth == 0) {
		currentMonthTmp = 12;
		currentYearTmp = currentYearTmp -1;
	}
	var idxstart=0;
	var idxend=0;
	var tempYearHaveData=false;
	for(var i=0;i<yearHaveData.length;i=i+3){
		if(currentYearTmp==yearHaveData[i]){
			idxstart=parseInt(yearHaveData[i+1].substring(3,yearHaveData[i+1].length));
			idxend=parseInt(yearHaveData[i+2].substring(3,yearHaveData[i+2].length));
			tempYearHaveData=true;
			break;
		}
	}
	//alert(currentYearTmp);
	//alert(idxstart);
	//alert(idxend);
	if(currentMonthTmp<10){
		currentMonthTmp = "0"+currentMonthTmp;
	}
	var currentYearTmp2=currentYearTmp+'';
	currentYearTmp2 = currentYearTmp2.substring(2,4);
	for (var no=0;no<dayStartOfMonth;no++){
		var cell = row.insertCell(-1);		
		if(isOweekendOtherMonthDay){
			var dayValue = (daysInMonthArray[currentMonthTmp-1]-dayStartOfMonth) + no + 1;
			var datefull = currentYearTmp2 +""+ currentMonthTmp + dayValue;
			//alert(datefull);
			var isHaveData = false;
			if(tempYearHaveData){
				for(var i=idxstart; i<=idxend; i++) {
					//HuynhTran 修正 2011/08/12 BEGIN ↓↓↓↓
					
					//if(datefull == divisionName[i] && divisionNameFile[i].length>1){
					if(datefull == divisionName[i] && divisionNameFile[i].length>3){
						//var fileName = "'" + divisionNameFile[i] + "'";  
						//fileName = fixPath(fileName);
						//HuynhTran 修正 2011/08/11 End↑↑↑↑
						
						// Detect IE browser
						if(MSIE){
							//cell.innerHTML = '<a href="javascript:runApp('+fileName+')"> ' + no + ' </a>';
							//2011.1.27 cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + dayValue + '</a>';
                            cell.innerHTML = '<a href="http:'+divisionNameFile[i]+'">' + dayValue + '</a>';
						} 
						else{ // Otherwise
							//cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + no + '</a>';
							//2011.1.27 cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + dayValue + '</a>';
                            cell.innerHTML = '<a href="http:'+divisionNameFile[i]+'">' + dayValue + '</a>';
						}
						isHaveData = true;
					}
					//HuynhTran 追加 2011/08/12 BEGIN ↓↓↓↓
					else if(datefull == divisionName[i] && (divisionNameFile[i].length>=1 && divisionNameFile[i].length<=3)){
						
						numFile = divisionNameFile[i];
						arrFileName=new Array();
						for(j = 0; j< numFile; j++){						
							arrFileName[j] = divisionNameFile[++i];
						}
						
						linka=document.createElement('a');
						linka.appendChild(document.createTextNode(dayValue));
						linka.href="javascript:void(0);";
						linka.onclick=CreatePopupWindow(arrFileName);
						
						cell.appendChild(linka);
						isHaveData = true;											
					}
					//HuynhTran 追加 2011/08/12 End↑↑↑↑ 
					
				}
			}
			if(! isHaveData){
				cell.innerHTML = dayValue;			
			}
			// Day is Sunday
			// and it is not belong to current week.
			if(no==0){	
				cell.className = 'IsSun';
				cell.id = 'IsSun';
			}	
			else cell.className = 'oweekendOtherMonthDay';
		}else{
			cell.innerHTML = '&nbsp;';
		}
	}

	var colCounter = dayStartOfMonth;
	var daysInMonth = daysInMonthArray[currentMonth]; 
		
	if(daysInMonth==28){
		if(isLeapYear(currentYear))daysInMonth=29;
	}	
	//
	// Write each day into calendar
	//
	var idxstart=0;
	var idxend=0;
	var tempYearHaveData=false;
	for(var i=0;i<yearHaveData.length;i=i+3){
		if(currentYear==yearHaveData[i]){
			idxstart=parseInt(yearHaveData[i+1].substring(3,yearHaveData[i+1].length));
			idxend=parseInt(yearHaveData[i+2].substring(3,yearHaveData[i+2].length));
			tempYearHaveData=true;
			break;
		}
	}
	//alert(idxstart);
	//alert(idxend);
	var row ;
	for(var no=1;no<=daysInMonth;no++){
		d.setDate(no-1);	
		
		var week = getWeek(currentYear, currentMonth, no);
		if(colCounter>0 && colCounter%7==0){ 
			 row = calTBody.insertRow(-1);			  
			 if (showWeekNumber) {
				var cell = row.insertCell(-1);
				cell.className = 'calendar_week_column';
				var week = getWeek(currentYear,currentMonth,no);
				cell.innerHTML = week;		// Week
				cell.style.backgroundColor = selectBoxRolloverBgColor;
			 }			 
		}
		
		var cell = row.insertCell(-1);
		var weekTmp = 0;
		
		//
		// Hight light all sun
		//
		var dateTmp = new Date();
		if (colCounter >= 0 && colCounter % 7 == 0 ) { 
			/*if(week != weekNo() && dateTmp.getFullYear() == yearValueSelectBox){
				cell.style.backgroundColor = '#cbcbed';	
			}else if (dateTmp.getFullYear() != yearValueSelectBox){
				cell.style.backgroundColor = '#cbcbed';	
			}*/
			if(!(currentDate.getDay() == 0 && no == currentDate.getDate())){
				cell.style.backgroundColor = '#cbcbed';
			}
		}
		 
		//alert(dateTmp.getMonth());
		//alert(currentMonth);
		//
		// Hight light all sat   
		//  
		if (colCounter >= 0 && colCounter % 7 == 6) { 
			if(week != getWeek(currentDate.getFullYear(),currentDate.getMonth(),currentDate.getDate()) && dateTmp.getFullYear() == yearValueSelectBox){
				cell.style.backgroundColor = '#e6e7fb';
			}
			else if (dateTmp.getFullYear() != yearValueSelectBox){
				cell.style.backgroundColor = '#e6e7fb';
			}
			if(week == getWeek(currentDate.getFullYear(),currentDate.getMonth(),currentDate.getDate())&&dateTmp.getMonth()!=currentMonth){
				 cell.style.backgroundColor = '#e6e7fb';
			}
		}
		
		//
		// HightLight current week	
		//
		if(isHightLightCurrentWeek){
			if( (currentDate.getDate() == no) && (currentDate.getMonth() == currentMonth) && (currentDate.getFullYear() == currentYear)){
				cell.className = 'boldCurrentDate';
				row.className = getWeek(currentDate.getFullYear(),currentDate.getMonth(),currentDate.getDate());
				row.id = getWeek(currentDate.getFullYear(),currentDate.getMonth(),currentDate.getDate());

				if(week == getWeek(currentDate.getFullYear(),currentDate.getMonth(),currentDate.getDate())){
					row.style.backgroundColor = hightLightWeekColor;
					//alert(currentDate.getDay());
					if(currentDate.getDay()!=0){
						row.childNodes[0].style.backgroundColor = hightLightWeekColor;
					}
					weekTmp = week;
				} 
			}
		}
		
		
		//alert(currentYear);
		var isHaveData = false;
		var numberDate = numberFormat(no);
		var fullDate = yearValueSelectBox.substring(2, yearValueSelectBox.length) + monthValueSelectBox + numberDate;
		if(tempYearHaveData){
			for(var i=idxstart; i<=idxend; i++) {
				//HuynhTran 修正 2011/08/12 BEGIN ↓↓↓↓				
				//if(fullDate == divisionName[i] && divisionNameFile[i].length>1){
				if(fullDate == divisionName[i] && divisionNameFile[i].length>3){
					//HuynhTran 修正 2011/08/12 End↑↑↑↑
					//var url = trim(divisionNameFile[i])+'';
					//if(url==null){
					//	alert(url.length);
					//}

					//var fileName = "'" + divisionNameFile[i] + "'";  
					//fileName = fixPath(fileName);
					// Detect IE browser
					if(MSIE){
						//cell.innerHTML = '<a href="javascript:runApp('+fileName+')"> ' + no + ' </a>';
						//2011.1.27 cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + no + '</a>';
                        cell.innerHTML = '<a href="http:'+divisionNameFile[i]+'">' + no + '</a>';
					} 
					else{ // Otherwise
						//cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + no + '</a>';
						//2011.1.27 cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + no + '</a>';
                        cell.innerHTML = '<a href="http:'+divisionNameFile[i]+'">' + no + '</a>';
					}
					isHaveData = true;
					break;
				}
				//HuynhTran 追加 2011/08/12 BEGIN ↓↓↓↓
				else if(fullDate == divisionName[i] && (divisionNameFile[i].length>=1 && divisionNameFile[i].length<=3)){
					
					numFile = divisionNameFile[i];
					arrFileName=new Array();
					for(j = 0; j< numFile; j++){						
						arrFileName[j] = divisionNameFile[++i];
					}			
					
					linka=document.createElement('a');
					linka.appendChild(document.createTextNode(no));
					linka.href="javascript:void(0);";
					linka.onclick=CreatePopupWindow(arrFileName);
					
					cell.appendChild(linka);
					isHaveData = true;
					break;					
				}
				//HuynhTran 追加 2011/08/12 End↑↑↑↑
			}
		}
		
		if(! isHaveData){
			cell.innerHTML = no;			
		}		
		colCounter++;
	}
	
	//
	// Write last space
	//
	/*if(isOweekendOtherMonthDay){
		var dayEndOfMonth = getDayEndOfMonth(dayStartOfMonth, daysInMonth);	
		for (var no=0; no<dayEndOfMonth; no++){  
			var cell = row.insertCell(-1);		
			var currentMonthTmp = currentMonth;
			if(currentMonth == 0) {
				currentMonthTmp = 12;
			} 
			cell.innerHTML = no + 1;
			if(no == (dayEndOfMonth-1)) {
				cell.className = 'IsSat';
			}	
			else cell.className = 'oweekendOtherMonthDay';
		}
		
		var weekTmp = getWeek(currentYear, currentMonth+1, dayEndOfMonth);
		if(document.getElementById(weekNo()) && dayEndOfMonth >= 1 && weekTmp == weekNo()) {
			row.childNodes[row.childNodes.length-1].style.backgroundColor = hightLightWeekColor;
			//console.log(weekTmp +'\t'+ weekNo());
		}
	}*/
	
	if(isOweekendOtherMonthDay){
		var dayEndOfMonth = getDayEndOfMonth(dayStartOfMonth, daysInMonth);
		var currentMonthTmp = parseInt(currentMonth,10)+2;
		var currentYearTmp = parseInt(yearValueSelectBox,10);
		
		if(currentMonthTmp == 13) {
			currentMonthTmp = 1;
			currentYearTmp = currentYearTmp +1;
		}
		var idxstart=0;
		var idxend=0;
		var tempYearHaveData=false;
		for(var i=0;i<yearHaveData.length;i=i+3){
			if(currentYearTmp==yearHaveData[i]){
				idxstart=parseInt(yearHaveData[i+1].substring(3,yearHaveData[i+1].length));
				idxend=parseInt(yearHaveData[i+2].substring(3,yearHaveData[i+2].length));
				tempYearHaveData=true;
				break;
			}
		}
		//alert(currentYearTmp);
		//alert(idxstart);
		//alert(idxend);
		
		
		if(currentMonthTmp<10){
			currentMonthTmp = "0"+currentMonthTmp;
		}
		var currentYearTmp2=currentYearTmp+'';
		currentYearTmp2 = currentYearTmp2.substring(2,4);
		//alert(currentYearTmp2);
		for (var no=0; no<dayEndOfMonth; no++){  
			var cell = row.insertCell(-1);		
			var datefull = currentYearTmp2 +""+ currentMonthTmp +"0"+(no + 1);
			//alert(datefull);
			var isHaveData = false;
			if(tempYearHaveData){
				for(var i=idxstart; i<=idxend; i++) {
					//HuynhTran 修正 2011/08/12 BEGIN ↓↓↓↓
					//if(datefull == divisionName[i] && divisionNameFile[i].length>1){
					if(datefull == divisionName[i] && divisionNameFile[i].length>3){
						
					//alert(datefull);
						//var fileName = "'" + divisionNameFile[i] + "'";  
						//fileName = fixPath(fileName);
					//HuynhTran 修正 2011/08/12 End↑↑↑↑
					
						// Detect IE browser
						if(MSIE){
							//cell.innerHTML = '<a href="javascript:runApp('+fileName+')"> ' + no + ' </a>';
							//2011.1.27 cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + (no + 1) + '</a>';
                            cell.innerHTML = '<a href="http:'+divisionNameFile[i]+'">' + (no + 1) + '</a>';
							//cell.innerHTML = '<a href="'+divisionNameFile[i]+'">' + (no + 1) + '</a>';
						} 
						else{ // Otherwise
							//cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + no + '</a>';
							//cell.innerHTML = '<a href="'+divisionNameFile[i]+'">' + (no + 1) + '</a>';
							//2011.1.27 cell.innerHTML = '<a href="file:///'+divisionNameFile[i]+'">' + (no + 1) + '</a>';
                            cell.innerHTML = '<a href="http:'+divisionNameFile[i]+'">' + (no + 1) + '</a>';
						}
						isHaveData = true;
						break;
					}
					//HuynhTran 追加 2011/08/12 BEGIN ↓↓↓↓
					else if(datefull == divisionName[i] && (divisionNameFile[i].length>=1 && divisionNameFile[i].length<=3)){

						numFile = divisionNameFile[i];
						arrFileName=new Array();
						for(j = 0; j< numFile; j++){						
							arrFileName[j] = divisionNameFile[++i];
						}
						
						linka=document.createElement('a');
						linka.appendChild(document.createTextNode(no + 1));
						linka.href="javascript:void(0);";
						linka.onclick=linka.onclick=CreatePopupWindow(arrFileName);
						
						cell.appendChild(linka);
						isHaveData = true;
						break;										
					}
					//HuynhTran 追加 2011/08/12 End↑↑↑↑
				}
			}
			if(! isHaveData){
				cell.innerHTML = (no + 1);			
			}
			//cell.innerHTML = no + 1;
			if(no == (dayEndOfMonth-1)) {
				cell.className = 'IsSat';
			}	
			else cell.className = 'oweekendOtherMonthDay';
		}
		
		var weekTmp = getWeek(currentYear, currentMonth+1, dayEndOfMonth);
		if(document.getElementById(weekNo()) && dayEndOfMonth >= 1 && weekTmp == weekNo()) {
			row.childNodes[row.childNodes.length-1].style.backgroundColor = hightLightWeekColor;
		}
	}
	
	
	if(!document.all){
		if(calendarContentDiv.offsetHeight)
			document.getElementById('topBar').style.top = calendarContentDiv.offsetHeight +  document.getElementById('topBar').offsetHeight -1 + 'px';
		else{
			document.getElementById('topBar').style.top = '';
			document.getElementById('topBar').style.bottom = '0px';
		}
	}

	//if(iframeObj){
	//	if(!calendarContentDivExists)setTimeout('resizeIframe()',350);else setTimeout('resizeIframe()',10);
	//}
}

//****************************************************
//関数名 ：CreatePopupWindow
//変数   ：arrFileName
//返却   ：function
//目的   ：ポップアップ画面を作成する。
//作成者 ：HuynhTran     日付：2011.08.12
//****************************************************
function CreatePopupWindow(arrFileName)
{
	return function () { 
		var left; //左からの座標を保存する
		var top;  //上からの座標を保存する
		var newwindow2;
		var tmp;
		var filenameT;
		var confPop;
		var popWidth=500;
		var popHeight=400;
		var html_p="";
		var f;			
		
		left = (screen.width/2)-(popWidth/2);
		top = (screen.height/2)-(popHeight/2);	

		confPop = 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no, width=' + popWidth + ', height=' + popHeight + ', top=' + top + ', left=' + left;
		
		for(f = 0; f < arrFileName.length; f++){
			filenameT = arrFileName[f].replace(/^.*\\/, '')
			html_p+='<p class="rowp"><a href="http:'+arrFileName[f]+'">'+ filenameT +'</a></p>';	
		};
	
		newwindow2=window.open('','PMeye',confPop);
		tmp = newwindow2.document;
		tmp.write('<html><head><title>PMeye Ver. 2.0</title>');
		tmp.write('<style>');
		tmp.write('a { color:#0606fa; text-decoration:underline;}');
		tmp.write('a:hover { color:#0606fa; text-decoration:none;}');
		tmp.write('.rowh{background-color:#FF9999;}');
		tmp.write('.rowp{background-color:#FFFF99;margin-left:20px;}');
		tmp.write('.rowh2{font-size: 36px; font-family:Arial;}');
		tmp.write('</style>');
		tmp.write('</head><body>');
		tmp.write('<img src="Logo_64x64.gif" /> <span class="rowh2">PMeye　Ver. 2.0  </span>');
		tmp.write('<hr />');
		tmp.write('<h2 class="rowh"> PJ会議資料一覧</h2>');
		tmp.write(html_p);							
		tmp.write('</body></html>');
		tmp.close();
	}
	
}


function EnableNextPrev(){
	if(document.getElementById('rightClick')){
		var rightClick = document.getElementById('rightClick');
		rightClick.onclick = switchMonth;
		rightClick.src = pathToImages + 'right.jpg';
		rightClick.style.cursor = 'pointer';
		createMouseEventNext(rightClick);
	}
	if(document.getElementById('leftClick')){
		var leftClick = document.getElementById('leftClick');
		leftClick.onclick = switchMonth;
		leftClick.src = pathToImages + 'left.jpg';
		leftClick.style.cursor = 'pointer';
		createMouseEventPrev(leftClick);
	}
}

function EnableTodayButton() {
	if(document.getElementById('today')){
		var today = document.getElementById('today');
		today.onclick = setToday;
		today.src = pathToImages + 'center.jpg';
		today.style.cursor = 'pointer';
		createMouseEventToday(today)
	}
}

function UpdateNextPrev() {
	var rightClick = document.getElementById('rightClick');
	var leftClick = document.getElementById('leftClick');
	 
	if( currentYear >= GetYearMax() && (currentMonth/1+1) == 12) {			   
		rightClick.onclick = '';
		rightClick.src = pathToImages + 'right_end_1.jpg';
		rightClick.style.cursor = 'default';
		releaseMouseEvent(rightClick);
		
		leftClick.src = pathToImages + 'left.jpg';
		leftClick.style.cursor = 'pointer';		
		leftClick.onclick = switchMonth;
		createMouseEventPrev(leftClick);
	} else if( currentYear <= GetYearMin()  && (currentMonth/1+1) == 1) {	
		leftClick.onclick = '';
		leftClick.src = pathToImages + 'left_end_1.jpg';
		leftClick.style.cursor = 'default';
		releaseMouseEvent(leftClick);
		
		rightClick.src = pathToImages + 'right.jpg';
		rightClick.style.cursor = 'pointer';
		rightClick.onclick = switchMonth;
		createMouseEventNext(rightClick);
	} 
}


function fixPath( s )
{
	s = s.replace(/\\/g, "/");
	s = s.replace("//", "\\\\");
	return s;
}


function getIndexByDayStartOfMonth(dayStartOfMonth){
	if(dayStartOfMonth == 1) return 1;
	else if(dayStartOfMonth == 2) return 1;
}


function getDayEndOfMonth(dayStartOfMonth, daysOfMonth){
	var acc = 1;
	var index = 1;
	var resultIndex = 0;
	for (var i = 0; i < 6; i ++) {
		for(var no = dayStartOfMonth; no < 7; no ++) {
			if(acc == daysOfMonth) {
				return formatDate(no );
			}
			index = no;
			acc++;
		}
		dayStartOfMonth = 0;
	}
	
	
   
	return formatDate(index);
}

function formatDate(index) {
	if(index == 0) resultIndex = 6;
	else if(index == 1) resultIndex = 5;
	else if(index == 2) resultIndex = 4;
	else if(index == 3) resultIndex = 3;
	else if(index == 4) resultIndex = 2;
	else if(index == 5) resultIndex = 1;
	else if(index == 6) resultIndex = 0;
	
	return resultIndex;
}


function numberFormat(no){
	if(no<=9) return '0'+no;
	else return no;
}


function weekNo() {
	var totalDays = 0;
	now = new Date();
	years = now.getYear();
	var days = new Array(12); // Array to hold the total days in a month
	days[0] = 31;
	days[2] = 31;
	days[3] = 30;
	days[4] = 31;
	days[5] = 30;
	days[6] = 31;
	days[7] = 31;
	days[8] = 30;
	days[9] = 31;
	days[10] = 30;
	days[11] = 31;
	 
	// Check to see if this is a leap year
	if (Math.round(now.getYear()/4) == now.getYear()/4) {
		days[1] = 29
	}else{
		days[1] = 28
	}
	 
	// If this is January no need for any fancy calculation otherwise figure out the
	// total number of days to date and then determine what week
	if (now.getMonth() == 0) { 
		totalDays = totalDays + now.getDate();
	}else{
		var curMonth = now.getMonth();
		for (var count = 1; count <= curMonth; count++) {
			totalDays = totalDays + days[count - 1];
		}
		totalDays = totalDays + now.getDate();
	}
	// Here is the modification: considering when start the 1st week of year.
	// Originally was only: var week = Math.round(totalDays/7)
	// Check if browser is "Microsoft Internet Explorer" or not and apply the right var
	var agt=navigator.userAgent.toLowerCase();
	if ((agt.indexOf("msie") != -1) && (agt.indexOf("opera") == -1)) {
		var firstday=new Date("01/01/"+String(now.getYear())).getDay();
	}
	else {
		var firstday=new Date("01/01/"+String(1900+now.getYear())).getDay();
	}
	var diff=7-firstday+1;
	var week = Math.round((totalDays+diff-firstday)/7); 
	
	return week+1;
}


function resizeIframe()
{
	//iframeObj.style.width = calendarDiv.offsetWidth + 'px';
	//iframeObj.style.height = calendarDiv.offsetHeight + 'px' ;


}

function pickTodaysDate()
{
	var d = new Date();
	currentMonth = d.getMonth();
	currentYear = d.getFullYear();
	pickDate(false,d.getDate());
}

function pickDate(e,inputDay)
{
	var month = currentMonth/1 +1;
	if(month<10)month = '0' + month;
	var day;
	if(!inputDay && this)day = this.innerHTML; else day = inputDay;

	if(day/1<10)day = '0' + day;
	if(returnFormat){
		returnFormat = returnFormat.replace('dd',day);
		returnFormat = returnFormat.replace('mm',month);
		returnFormat = returnFormat.replace('yyyy',currentYear);
		returnFormat = returnFormat.replace('hh',currentHour);
		returnFormat = returnFormat.replace('ii',currentMinute);
		returnFormat = returnFormat.replace('d',day/1);
		returnFormat = returnFormat.replace('m',month/1);

		returnDateTo.value = returnFormat;
		try{
			returnDateTo.onchange();
		}catch(e){

		}
	}else{
		for(var no=0;no<returnDateToYear.options.length;no++){
			if(returnDateToYear.options[no].value==currentYear){
				returnDateToYear.selectedIndex=no;
				break;
			}
		}
		for(var no=0;no<returnDateToMonth.options.length;no++){
			if(returnDateToMonth.options[no].value==parseFloat(month)){
				returnDateToMonth.selectedIndex=no;
				break;
			}
		}
		for(var no=0;no<returnDateToDay.options.length;no++){
			if(returnDateToDay.options[no].value==parseFloat(day)){
				returnDateToDay.selectedIndex=no;
				break;
			}
		}
		if(calendarDisplayTime){
			for(var no=0;no<returnDateToHour.options.length;no++){
				if(returnDateToHour.options[no].value==parseFloat(currentHour)){
					returnDateToHour.selectedIndex=no;
					break;
				}
			}
			for(var no=0;no<returnDateToMinute.options.length;no++){
				if(returnDateToMinute.options[no].value==parseFloat(currentMinute)){
					returnDateToMinute.selectedIndex=no;
					break;
				}
			}
		}
	}
	closeCalendar();

}

// This function is from http://www.codeproject.com/csharp/gregorianwknum.asp
// Only changed the month add
function getWeek(year,month,day){
   if (! weekStartsOnSunday) {
	   day = (day/1);
	} else {
	   day = (day/1)+1;
	}
	year = year /1;
	month = month/1 + 1; //use 1-12
	var a = Math.floor((14-(month))/12);
	var y = year+4800-a;
	var m = (month)+(12*a)-3;
	var jd = day + Math.floor(((153*m)+2)/5) +
				 (365*y) + Math.floor(y/4) - Math.floor(y/100) +
				 Math.floor(y/400) - 32045;	  // (gregorian calendar)
	var d4 = (jd+31741-(jd%7))%146097%36524%1461;
	var L = Math.floor(d4/1460);
	var d1 = ((d4-L)%365)+L;
	NumberOfWeek = Math.floor(d1/7) + 1;
	return NumberOfWeek;
}



function getTopPos(inputObj)
{

  var returnValue = inputObj.offsetTop + inputObj.offsetHeight;
  while((inputObj = inputObj.offsetParent) != null)returnValue += inputObj.offsetTop;
  return returnValue + calendar_offsetTop;
}

function getleftPos(inputObj)
{
  var returnValue = inputObj.offsetLeft;
  while((inputObj = inputObj.offsetParent) != null)returnValue += inputObj.offsetLeft;
  return returnValue + calendar_offsetLeft;
}

function positionCalendar(inputObj)
{
	calendarDiv.style.left = getleftPos(inputObj) + 'px';
	calendarDiv.style.top = getTopPos(inputObj) + 'px';
	/*if(iframeObj){
		iframeObj.style.left = calendarDiv.style.left;
		iframeObj.style.top =  calendarDiv.style.top;
		//// fix for EI frame problem on time dropdowns 09/30/2006
		iframeObj2.style.left = calendarDiv.style.left;
		iframeObj2.style.top =  calendarDiv.style.top;
	}*/

}

function initCalendar()
{
	/*if(MSIE){
		iframeObj = document.createElement('IFRAME');
		iframeObj.style.filter = 'alpha(opacity=0)';
		iframeObj.style.position = 'absolute';
		iframeObj.border='0px';
		iframeObj.style.border = '0px';
		iframeObj.style.backgroundColor = '#FF0000';
		//// fix for EI frame problem on time dropdowns 09/30/2006
		iframeObj2 = document.createElement('IFRAME');
		iframeObj2.style.position = 'absolute';
		iframeObj2.border='0px';
		iframeObj2.style.border = '0px';
		iframeObj2.style.height = '1px';
		iframeObj2.style.width = '1px';
		//// fix for EI frame problem on time dropdowns 09/30/2006
		// Added fixed for HTTPS
		iframeObj2.src = 'blank.html';
		iframeObj.src = 'blank.html';
		document.body.appendChild(iframeObj2);  // gfb move this down AFTER the .src is set
		document.body.appendChild(iframeObj);
	}*/

	calendarDiv = document.createElement('DIV');
	calendarDiv.id = 'calendarDiv';
	calendarDiv.style.zIndex = 1000;
	slideCalendarSelectBox();

	
		if (document.getElementById('caldiv')){
			
			document.getElementById('caldiv').appendChild(calendarDiv);
		}
	
	//document.body.appendChild(calendarDiv);
	// writeBottomBar();
	writeTopBar();

	if(!currentYear){
		var d = new Date();
		currentMonth = d.getMonth();
		currentYear = d.getFullYear();
	}
	
	//writeCalendarContent();
	writeCalendar(myDivision, myDivisionFile);


}


function calendarSortItems(a,b)
{
	return a/1 - b/1;
}

function onArray(element,array){
	if(array.length>0){
		for(var k=array.length-1; k>=0; k--){
			if(array[k]==element){
				return true;
			}
		}
	}
	return false;
}

function calendarCaller(divisionName, divisionNameFile){
	myDivision = divisionName;
	myDivisionFile = divisionNameFile;
	//yearHaveData.length=0;
	yearHaveData = new Array();
	if(myDivision.length>0){
		//alert(FormatYear(myDivision[0].substring(0,2)));
		var yearTemp='';
		for(var i=0; i<myDivision.length; i++) {
			if(yearTemp!=myDivision[i].substring(0,2)){
				if(i!=0){
					yearHaveData.push('ooo'+(i-1));
				}
				yearHaveData.push(FormatYear(myDivision[i].substring(0,2)));
				yearHaveData.push('xxx'+i);
			}
			if(i==myDivision.length-1){
				yearHaveData.push('ooo'+i);
			}
			yearTemp = myDivision[i].substring(0,2);
		}
	}
//	for(var k=0; k<yearHaveData.length;k++){
//		alert(yearHaveData[k]);
//	}
	
	var d = new Date();
	currentMonth = d.getMonth();
	currentYear = d.getFullYear();
	currentHour = '08';
	currentMinute = '00';
	inputDay = d.getDate()/1;
	
	//
	// Default is near month which is have data
	//
	// currentYear = GetYearMax();
	// currentMonth = GetMonthMaxOfYear(GetYearMax()) - 1;
	
	//
	// Set enable for next and prev buttons when user click today button
	// Set enable today button
	//
	EnableNextPrev();
	EnableTodayButton();
	
	
	if(!calendarDiv){
		initCalendar();
	}else{
		if(calendarDiv.style.display=='block'){
			closeCalendar();
			return false;
		}
		//writeTopBar();
		writeCalendar(divisionName, divisionNameFile);
	}
	/////////////////////////////////////////////////////////////////////////////////////
	calendarDiv.style.visibility = 'visible';
	calendarDiv.style.display = 'block';
	//if(iframeObj){
	//	iframeObj.style.display = '';
	//	iframeObj.style.height = '140px';
	//	iframeObj.style.width = '195px';
	//	iframeObj2.style.display = '';
	//	iframeObj2.style.height = '140px';
	//	iframeObj2.style.width = '195px';
	//}
	//if (MSIE) { 
	//	EIS_FIX_EI1('monthDropDown')
	//}



	updateYearDiv();
	updateMonthDiv();
	
}


function displayCalendar(inputField,format,buttonObj,displayTime,timeInput)
{
	
	if(displayTime)calendarDisplayTime=true; else calendarDisplayTime = false;
	
	if(inputField.value.length>6){ //dates must have at least 6 digits...
	   if(!inputField.value.match(/^[0-9]*?$/gi)){
	   	
			var items = inputField.value.split(/[^0-9]/gi);
			var positionArray = new Object();
			positionArray.m = format.indexOf('mm');
			if(positionArray.m==-1)positionArray.m = format.indexOf('m');
			positionArray.d = format.indexOf('dd');
			if(positionArray.d==-1)positionArray.d = format.indexOf('d');
			positionArray.y = format.indexOf('yyyy');
			positionArray.h = format.indexOf('hh');
			positionArray.i = format.indexOf('ii');
			
			this.initialHour = '00';
			this.initialMinute = '00';				
			var elements = ['y','m','d','h','i'];
			var properties = ['currentYear','currentMonth','inputDay','currentHour','currentMinute'];
			var propertyLength = [4,2,2,2,2];
			for(var i=0;i<elements.length;i++) {
				if(positionArray[elements[i]]>=0) {
					window[properties[i]] = inputField.value.substr(positionArray[elements[i]],propertyLength[i])/1;
				}					
			}			
			currentMonth--;
		}else{
			var monthPos = format.indexOf('mm');
			currentMonth = inputField.value.substr(monthPos,2)/1 -1;
			var yearPos = format.indexOf('yyyy');
			currentYear = inputField.value.substr(yearPos,4);
			var dayPos = format.indexOf('dd');
			tmpDay = inputField.value.substr(dayPos,2);

			var hourPos = format.indexOf('hh');
			if(hourPos>=0){
				tmpHour = inputField.value.substr(hourPos,2);
				currentHour = tmpHour;
				if(currentHour.length==1) currentHour = '0'
			}else{
				currentHour = '00';
			}
			var minutePos = format.indexOf('ii');
			if(minutePos>=0){
				tmpMinute = inputField.value.substr(minutePos,2);
				currentMinute = tmpMinute;
			}else{
				currentMinute = '00';
			}
		}
	}else{
		var d = new Date();
		currentMonth = d.getMonth();
		currentYear = d.getFullYear();
		currentHour = '08';
		currentMinute = '00';
		inputDay = d.getDate()/1;
	}

	inputYear = currentYear;
	inputMonth = currentMonth;


	if(!calendarDiv){
		initCalendar();
	}else{
		if(calendarDiv.style.display=='block'){
			closeCalendar();
			return false;
		}
		writeCalendarContent();
	}



	returnFormat = format;
	returnDateTo = inputField;
	positionCalendar(buttonObj);
	calendarDiv.style.visibility = 'visible';
	calendarDiv.style.display = 'block';
	//if(iframeObj){
	//	iframeObj.style.display = '';
	//	iframeObj.style.height = '140px';
	//	iframeObj.style.width = '195px';
	//			iframeObj2.style.display = '';
	//	iframeObj2.style.height = '140px';
	//	iframeObj2.style.width = '195px';
	//}

	updateYearDiv();
	updateMonthDiv();

}

function displayCalendarSelectBox(yearInput,monthInput,dayInput,hourInput,minuteInput,buttonObj)
{
	if(!hourInput)calendarDisplayTime=false; else calendarDisplayTime = true;

	currentMonth = monthInput.options[monthInput.selectedIndex].value/1-1;
	currentYear = yearInput.options[yearInput.selectedIndex].value;
	if(hourInput){
		currentHour = hourInput.options[hourInput.selectedIndex].value;
		inputHour = currentHour/1;
	}
	if(minuteInput){
		currentMinute = minuteInput.options[minuteInput.selectedIndex].value;
		inputMinute = currentMinute/1;
	}

	inputYear = yearInput.options[yearInput.selectedIndex].value;
	inputMonth = monthInput.options[monthInput.selectedIndex].value/1 - 1;
	inputDay = dayInput.options[dayInput.selectedIndex].value/1;

	if(!calendarDiv){
		initCalendar();
	}else{
		writeCalendarContent();
	}



	returnDateToYear = yearInput;
	returnDateToMonth = monthInput;
	returnDateToDay = dayInput;
	returnDateToHour = hourInput;
	returnDateToMinute = minuteInput;




	returnFormat = false;
	returnDateTo = false;
	positionCalendar(buttonObj);
	calendarDiv.style.visibility = 'visible';
	calendarDiv.style.display = 'block';
	//if(iframeObj){
	//	iframeObj.style.display = '';
	//	iframeObj.style.height = calendarDiv.offsetHeight + 'px';
	//	iframeObj.style.width = calendarDiv.offsetWidth + 'px';
	//	//// fix for EI frame problem on time dropdowns 09/30/2006
	//	iframeObj2.style.display = '';
	//	iframeObj2.style.height = calendarDiv.offsetHeight + 'px';
	//	iframeObj2.style.width = calendarDiv.offsetWidth + 'px'
	//}
	updateYearDiv();
	updateMonthDiv();

}
