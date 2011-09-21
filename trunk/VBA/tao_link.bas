Attribute VB_Name = "Module1"
Sub TaoLink()
Attribute TaoLink.VB_ProcData.VB_Invoke_Func = " \n14"
    Dim endRow As Integer
    Dim beginRow As Integer
    Dim col As String
    Dim rCell1, rCell2 As Range
    Dim row As Integer
    Dim key As String
    Dim sheet1 As String
    Dim sheet2 As String
    Dim ws1, ws2 As Worksheet
    Dim arr() As String
    
    Dim r As Integer
    
   
    key = "TEST_"
    col = "M"
    beginRow = 8
    endRow = 40
 
    sheet1 = Sheets(1).Name
    sheet2 = Sheets(2).Name
    

    
    On Error GoTo errMyErrorHandler
    
    ReDim arr(endRow, 1)
    row = 0
    c = 0

     For Each rCell2 In Sheets(sheet2).Range("A1:A" & endRow * 55).Cells
         row = row + 1
         If InStr(UCase(rCell2.Value), key) > 0 Then
            arr(c, 0) = rCell2.Value
            arr(c, 1) = row
            c = c + 1
         End If
     Next rCell2
    
    
    row = beginRow
    For Each rCell1 In Sheets(sheet1).Range(col & beginRow & ":" & col & endRow).Cells
   
     For c = 0 To endRow
        If rCell1.Value = Empty Then
            Exit Sub
        End If
         If (InStr(rCell1.Value, arr(c, 0)) > 0 Or InStr(arr(c, 0), rCell1.Value) > 0) And arr(c, 0) <> "" And rCell1.Value <> Empty Then
             Sheets(sheet1).Select
             Range(col & row & ":" & col & row).Select
             Sheets(sheet1).Hyperlinks.Add Anchor:=Selection, Address:="", SubAddress:= _
              sheet2 & "!A" & arr(c, 1), TextToDisplay:=rCell1.Value
             
             Sheets(sheet2).Select
             Range("A" & arr(c, 1) & ":A" & arr(c, 1)).Select
             
             Sheets(sheet2).Hyperlinks.Add Anchor:=Selection, Address:="", SubAddress:= _
              sheet1 & "!" & col & row, TextToDisplay:=arr(c, 0)
         End If
    
      Next c
      row = row + 1
     
    Next rCell1
errMyErrorHandler:

    'MsgBox Err.Description & "," & tt, vbExclamation + vbOKCancel, "Error: " & CStr(Err.Number)
End Sub
