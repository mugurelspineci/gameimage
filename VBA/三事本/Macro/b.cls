Public WithEvents wsevent As Worksheet

Private Sub wsevent_BeforeDoubleClick(ByVal Target As Range, Cancel As Boolean)
Dim Last As Long
Dim DataSht As Worksheet
Dim W_str As String
Dim C_str As String
Dim T_col As Integer
Dim T_row As Integer
Dim lastRow As Integer
Dim ThisSeasonSt As Date
Dim ThisSeasonEnd As Date
Dim PreSeasonSt As Date
Dim PreSeasonEnd As Date


Set DataSht = ThisWorkbook.Worksheets("全PJ一覧")
T_col = Target.Column
T_row = Target.Row
W_str = Cells(T_row, 2).MergeArea(1, 1).Value
C_str = Cells(T_row, T_col).MergeArea(1, 1).Value
If DataSht.FilterMode = True Then
DataSht.ShowAllData
End If

ThisSeasonSt = Range("今期開始").Value
ThisSeasonEnd = Range("今期終了").Value
PreSeasonSt = Range("前期開始").Value
PreSeasonEnd = Range("前期終了").Value

lastRow = DataSht.Cells.SpecialCells(xlCellTypeLastCell).Row

If Cells(T_row, 2) = "" Or Cells(T_row, 2) = "<PJ原価状況 通期金額ベース>" Then 
 Exit Sub
End If

If T_row >= 6 And T_row <= 12 Then
If Cells(T_row, 2) <> "合計" Then
    DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=6, Criteria1:=W_str
End If
If (T_col = 3) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=8, Criteria1:="=開発"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & ThisSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & ThisSeasonEnd
ElseIf (T_col = 4) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=8, Criteria1:="=サービス"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & ThisSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & ThisSeasonEnd
ElseIf (T_col = 10) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=14, Criteria1:="=開始前"
ElseIf (T_col = 11) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=14, Criteria1:="=遂行中"
ElseIf (T_col = 12) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=14, Criteria1:="=完了"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & ThisSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & ThisSeasonEnd
ElseIf (T_col = 5) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=12, Criteria1:="=S"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & ThisSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & ThisSeasonEnd
ElseIf (T_col = 6) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=12, Criteria1:="=A"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & ThisSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & ThisSeasonEnd
ElseIf (T_col = 7) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=12, Criteria1:="=B"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & ThisSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & ThisSeasonEnd
ElseIf (T_col = 8) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=12, Criteria1:="=C"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & ThisSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & ThisSeasonEnd
ElseIf (T_col = 9) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=12, Criteria1:="=未定"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & ThisSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & ThisSeasonEnd
ElseIf (T_col = 13) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=8, Criteria1:="=開発"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & PreSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & PreSeasonEnd
ElseIf (T_col = 14) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=8, Criteria1:="=サービス"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & PreSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & PreSeasonEnd
ElseIf (T_col = 15) Then
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=14, Criteria1:="=完了"
DataSht.Range("A6:IV" & lastRow).AutoFilter Field:=19, Criteria1:=">=" & PreSeasonSt _
        , Operator:=xlAnd, Criteria2:="<=" & PreSeasonEnd
End If

If C_str <> "" Then
DataSht.Select
ActiveWindow.ScrollColumn = 6

End If

End If

End Sub



