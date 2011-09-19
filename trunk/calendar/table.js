function getEncode(_strsrc)
{
    var _strdes = [];
    var idx = 0;
    var i, j, c;
    for (i=0; i<_strsrc.length; i++)
    {
        c = _strsrc[i];
        if (c <= 0x7f) _strdes[idx++] = c;
        else if (c <= 0x7ff)
        {
            _strdes[idx++] = 0xc0 | (c >>> 6 );
            _strdes[idx++] = 0x80 | (c & 0x3f);
        }
        else if (c <= 0xffff)
        {
            _strdes[idx++] = 0xe0 | (c >>> 12 );
            _strdes[idx++] = 0x80 | ((c >>> 6 ) & 0x3f);
            _strdes[idx++] = 0x80 | (c & 0x3f);
        }
        else
        {
            j = 4;
            while (c >> (6*j)) j++;
            _strdes[idx++] = ((0xff00 >>> j) & 0xff) | (c >>> (6*--j) );
            while (j--)
            _strdes[idx++] = 0x80 | ((c >>> (6*j)) & 0x3f);
        }
    }
    return _strdes;
}

function URLencode(_str)
{
    return _str.replace(/([^a-zA-Z0-9_\-\.])/g, function(_tmp, _c)
        {
            if (_c == "\x20") return "+";
            var tmp = getEncode( [_c.charCodeAt(0)] );
            var c = "";
            for (var i in tmp)
            {
                i = tmp[i].toString(16);
                if (i.length == 1) i = "0"+ i;
                c += "%"+ i;
            }
            return c;
        } );
}

// Does this browser support try-catch?
var resultText;
var count = 0;
var tc = false;
var division;
var file1havedata=false;
var file2havedata=false;
try {
    tc = true;
} catch(f) { }

var objRequestError = 'XML HTTP Request: OK';

function trim(s)
{
	return rtrim(ltrim(s));
}

function ltrim(s)
{
	var l=0;
	while(l < s.length && s[l] == ' ')
	{	l++; }
	return s.substring(l, s.length);
}

function rtrim(s)
{
	var r=s.length -1;
	while(r > 0 && s[r] == ' ')
	{	r-=1;	}
	return s.substring(0, r+1);
}


function getRequestObject() {
    var objRequest;
    if (window.ActiveXObject) {
        if (tc) {
            try {
                objRequest = new ActiveXObject('Msxml2.XMLHTTP');
            }
            catch(e) {
                try {
                    objRequest = new ActiveXObject('Microsoft.XMLHTTP');
                }
                catch(f) { }
            }
        } else {
            objRequest = new ActiveXObject('Microsoft.XMLHTTP');
        }
    } else if (window.XMLHttpRequest) {
        objRequest = new XMLHttpRequest();
    }
    return objRequest;
}

function getFile(pUrl) {
    var objRequest = getRequestObject();
    if (typeof(objRequest)=='object') {
        if (objRequest.readyState>=0) {
			objRequest.onreadystatechange = function() { makeTable(objRequest); };
            objRequest.open('GET', pUrl, true);
            objRequest.send(null);
        }else{
            objRequestError = 'XML HTTP Request Object Unavailable';
            //document.getElementById(pElementId).innerHTML = objRequestError;
            return false;
        }
    }else{
        objRequestError = 'XML HTTP Request Object Not Supported';
        //document.getElementById(pElementId).innerHTML = objRequestError;
        return false;
    }
}

function makeTable(pObjRequest) {

    if (pObjRequest.readyState==4) {
        if (pObjRequest.status==200) {
		count++;
		var lineData=pObjRequest.responseText.split('\n');
		if(trim(lineData[0])!=''){//file has data
			if(count==1){
				file1havedata=true;
				resultText+='<div class="bg03">';
			}
			if(count==2){
				file2havedata=true;
				if(file1havedata==false){
					resultText+='<div class="bg03">';
				}
			}

			if(count==1){
			resultText+='<table width="100%" class="tableclass1">';
			}
			if(count==2){
			resultText+='<table width="100%" class="tableclass2">';
			}
			var wCount = lineData[0].split(",");
			resultText += "<tr>";
		for(var j=0;j<wCount.length;j++){
			tData = wCount[j];
			if(count==1){
			resultText += '<td class="tabletheader1">'+tData + "</td>";
			}
			if(count==2){
			resultText += '<td class="tabletheader2">'+tData + "</td>";
			}

		}
		resultText += "</tr>";
		for (var i=1; i<lineData.length; i++)
		{
			wCount = lineData[i].split(",");
			resultText += "<tr>";
			for(var j=0;j<wCount.length;j++){
				tData = wCount[j];
				if(j==0){
					if(count==1){
						resultText += '<td class="tablefirstcolumn1">'+tData + "</td>";
					}
					if(count==2){
						resultText += '<td class="tablefirstcolumn2">'+tData + "</td>";
					}
				}else{
					if(count==1){
					resultText += '<td class="tablenormalcolumn1">'+tData + "</td>";
					}
					if(count==2){
					resultText += '<td class="tablenormalcolumn2">'+tData + "</td>";
					}
				}
			}
		resultText += "</tr>";
		}
		resultText += '</table>';
		resultText += '<p>&nbsp;</p>';
			}
			if(count==1){
				getFile(division+'02.csv?t=' + new Date().getTime());
			}
			if(count==2){
				if(file1havedata==true||file2havedata==true){
				resultText += '</div>';
				document.getElementById('tables').innerHTML=resultText;
				}
			}
		}
	}
}

function show2tables(div){

resultText = '';
count = 0;
division = URLencode(div);
getFile(division+'01.csv?t=' + new Date().getTime());
}