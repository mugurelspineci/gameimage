Attribute VB_Name = "ModuleSort"
Option Explicit

Public Const SHEET_SORT = "指摘PJ一覧"
Public Const SHEET_REFLECT = "Reflect Pattern"
Public Const NUM_PATTERN = "[0-9]+"
Private Const SORT_BUTTONCAPTION = "ソート"
Private Const SORT_BUTTONNAME = "sortButton"
Private Const SORT_ACTION = "SortButton_Click"

'****************************************************
'関数名 ：Sort
'変数   ：rowPjBangou、colPjBangou
'返却   ：なし
'目的   ：ソートする。
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Public Sub Sort(rowPjBangou As Long, colPjBangou As Long, key1 As String, asc1 As XlSortOrder, _
    key2 As String, asc2 As XlSortOrder, key3 As String, asc3 As XlSortOrder)
        
    Dim sortSheet As Worksheet      ' ソートシート
    Dim usedColIdx As Long          ' テーブルの上に使用される列
    Dim usedRowIdx As Long          ' テーブルの上に使用される行
    Dim haveMergeCell As Boolean    ' セルをマージする必要があります
    Dim arrayMergeInfo() As String
    Dim startColumnIdx As Long
    
    ' sortSheetを「指摘PJ一覧」シートとして設定する。
    Set sortSheet = ActiveWorkbook.Sheets(SHEET_SORT)
    sortSheet.Select
    
    ' usedColIdxを、使われた列の数として設定する。(UsedRange.Column.Count)
    usedColIdx = sortSheet.UsedRange.Columns.Count
     
    ' usedRowIdxを、該当のテーブルの行の数として設定する。
    usedRowIdx = FindUsedRowIndex(rowPjBangou, colPjBangou)
    
    startColumnIdx = 1
    
    ' ソートするには選択の範囲
    Range(Cells(rowPjBangou - 1, startColumnIdx), Cells(usedRowIdx, usedColIdx)).Select

    haveMergeCell = HaveMergeCellInSelection

    If haveMergeCell Then

        ' 列のマージのリストを取得する
        arrayMergeInfo = GetArrayMergeInfo(rowPjBangou - 1, startColumnIdx)

        ' アンマージ
        UnmergeSelection

    End If

    ' 並べ替え
    If key2 = "" And key3 = "" Then
        Selection.Sort key1:=Range(key1 & rowPjBangou - 1), order1:=asc1, Header:=xlYes
    Else
        If key2 <> "" And key3 <> "" Then
            Selection.Sort _
                key1:=Range(key1 & rowPjBangou - 1), order1:=asc1, _
                key2:=Range(key2 & rowPjBangou - 1), order2:=asc2, _
                key3:=Range(key3 & rowPjBangou - 1), order3:=asc3, Header:=xlYes
        Else
            If key2 <> "" Then
                Selection.Sort _
                    key1:=Range(key1 & rowPjBangou - 1), order1:=asc1, _
                    key2:=Range(key2 & rowPjBangou - 1), order2:=asc2, Header:=xlYes
            Else
                Selection.Sort _
                    key1:=Range(key1 & rowPjBangou - 1), order1:=asc1, _
                    key3:=Range(key3 & rowPjBangou - 1), order3:=asc3, Header:=xlYes
            End If
        End If
    End If

    If haveMergeCell Then
        ReMergeSelection arrayMergeInfo, rowPjBangou - 1, usedRowIdx
    End If
        
End Sub

'****************************************************
'関数名 ：FindUsedRowIndex
'変数   ：startRow、columnIdx
'返却   ：Long
'目的   ：使用される行のインデックスを見つける
'作成者 ：phuonghtt     日付：2011.08.13
'変更者 ：              日付：
'****************************************************
Function FindUsedRowIndex(ByVal startRow As Long, ByVal columnIdx As Long) As Long
    
    Dim cell As Range
    Dim haveFound As Boolean
    Dim maxRow As Long
    
    maxRow = Rows.Count
    
    If startRow > maxRow Then
        FindUsedRowIndex = maxRow
        Exit Function
    End If
    
    Set cell = Cells(startRow, columnIdx)
        
    haveFound = cell.Value = "" And cell.Borders.LineStyle < 0
    
    Do While Not haveFound
        
        '開始の列にデータをセットする。
        startRow = startRow + 1
        
        If startRow > maxRow Then
            FindUsedRowIndex = maxRow
            Exit Function
        End If
        
        Set cell = Cells(startRow, columnIdx)
        
        haveFound = cell.Value = "" And cell.Borders.LineStyle < 0
        
    Loop
        
    '返却する。
    FindUsedRowIndex = startRow - 1
    
End Function

'****************************************************
'関数名 ：HaveMergeCellInSelection
'変数   ：なし
'返却   ：Long
'目的   ：選択中のセルをマージ持っている
'作成者 ：phuonghtt     日付：2011.08.14
'変更者 ：              日付：
'****************************************************
Function HaveMergeCellInSelection() As Long
    
    Dim cell As Range
    
    For Each cell In Selection
        If cell.MergeCells Then
            HaveMergeCellInSelection = True
            Exit Function
        End If
    Next
    
End Function
 
'****************************************************
'関数名 ：UnmergeSelection
'変数   ：なし
'返却   ：なし
'目的   ：アンマージセレクション
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Sub UnmergeSelection()
    
    With Selection
        .WrapText = False
        .Orientation = 0
        .AddIndent = False
        .ShrinkToFit = False
        .ReadingOrder = xlContext
        .MergeCells = False
    End With

End Sub

'****************************************************
'関数名 ：ReMergeSelection
'変数   ：なし
'返却   ：なし
'目的   ：マージ再
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Sub ReMergeSelection(arrayMergeInfo() As String, ByVal startRow As Long, ByVal endRow As Long)
    
    Dim info As Variant
    Dim i As Long
    
    'Nothingをチェックする。
    If IsArrayEmpty(arrayMergeInfo) Then Exit Sub
            
    For i = startRow To endRow
        For Each info In arrayMergeInfo
            If info <> "" Then
                info = RegExpReplace(CStr(info), NUM_PATTERN, CStr(i))
                Range(info).Merge
            End If
        Next
    Next
    
End Sub

'****************************************************
'関数名 ：GetArrayMergeInfo
'変数   ：なし
'返却   ：なし
'目的   ：配列マージ情報の取得
'作成者 ：phuonghtt     日付：2011.08.16
'変更者 ：              日付：
'****************************************************
Function GetArrayMergeInfo(ByVal rowIdx As Long, ByVal startColumnIdx As Long) As String()
    
    Dim mergeArrayInfo() As String  ' 整列
    Dim n As Long                   '配列のサイズ
    Dim i As Long

    Dim rng As Range
    Dim rngStart As Range
    Dim rngEnd As Range
    Dim cell As Range
    
    n = 0
    
    For i = startColumnIdx To Selection.Columns.Count
        
        Set cell = Range(Cells(rowIdx, i), Cells(rowIdx, i))
        
        If cell.MergeCells And cell.Text <> "" Then
            
            Set rng = cell.MergeArea
            Set rngStart = rng.Cells(1, 1)
            Set rngEnd = rng.Cells(rng.Rows.Count, rng.Columns.Count)
                
            n = n + 1
            ReDim Preserve mergeArrayInfo(n)
            
            mergeArrayInfo(n) = rngStart.Address & ":" & rngEnd.Address
                    
        End If
    Next
    
    GetArrayMergeInfo = mergeArrayInfo
    
End Function

'****************************************************
'関数名 ：IsArrayEmpty
'変数   ：Variant
'返却   ：なし
'目的   ：配列は空です
'作成者 ：phuonghtt     日付：2011.08.16
'変更者 ：              日付：
'****************************************************
Function IsArrayEmpty(anArray As Variant)

    Dim i As Integer
    
    On Error Resume Next
        i = UBound(anArray, 1)
    
    IsArrayEmpty = False
    
    If err.Number <> 0 Then
        IsArrayEmpty = True
    End If

End Function

'****************************************************
'関数名 ：RegExpReplace
'変数   ：LookIn、PatternStr、ReplaceWith、ReplaceAll、MatchCase
'返却   ：String
'目的   ：正規表現で置き換える
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Function RegExpReplace(LookIn As String, PatternStr As String, Optional ReplaceWith As String = "", _
    Optional ReplaceAll As Boolean = True, Optional MatchCase As Boolean = True)
     
    Dim RegX As Object
     
    Set RegX = CreateObject("VBScript.RegExp")
    With RegX
        .Pattern = PatternStr
        .Global = ReplaceAll
        .IgnoreCase = Not MatchCase
    End With
     
    RegExpReplace = RegX.Replace(LookIn, ReplaceWith)
     
    Set RegX = Nothing
     
End Function

'****************************************************
'関数名 ：GetStartColumnIdx
'変数   ：colPjBangou、rowIdx
'返却   ：Long
'目的   ：コラムをスタート得る
'作成者 ：phuonght      日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Function GetStartColumnIdx(ByVal colPjBangou As Long, ByVal rowIdx As Long)
    
    Dim i As Long
    
    For i = 1 To colPjBangou
        If Cells(rowIdx, i).Text <> "" Then
            GetStartColumnIdx = i
            Exit Function
        End If
    Next
    
End Function

'****************************************************
'関数名 ：CreateButtonSort
'変数   ：なし
'返却   ：なし
'目的   ：ボタンのソートを作成する。
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Private Sub CreateButtonSort()

    ActiveWorkbook.Sheets(SHEET_SORT).Select
    
    ActiveSheet.Buttons.Add(500, 2, 120, 30).Select
    
    Selection.Name = SORT_BUTTONNAME
    Selection.Characters.Text = SORT_BUTTONCAPTION
    
    With Selection.Characters(Start:=1, Length:=3).Font
        .Name = "ＭＳ Ｐゴシック"
        .FontStyle = "標準 Bold"
        .Size = 18
        .Strikethrough = False
        .Superscript = False
        .Subscript = False
        .OutlineFont = True
        .Shadow = True
        .Underline = xlUnderlineStyleNone
        .ColorIndex = 5
    End With
    
    Selection.OnAction = SORT_ACTION
    
    Range("A1").Select
        
End Sub

'****************************************************
'関数名 ：sortButton_Click
'変数   ：なし
'返却   ：なし
'目的   ：ソートのボタンを選択するが場合。
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Public Sub SortButton_Click()
    
    Dim sortForm As frmSort
    Set sortForm = New frmSort
    
    'ソートの画面を表示する。
    sortForm.Show vbModal
    
End Sub

'****************************************************
'関数名 ：DeleteButtonSort
'変数   ：なし
'返却   ：なし
'目的   ：ソートのボタンを削除する。
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Private Sub DeleteButtonSort()
    
    'ソートSheetが選択する。
    ActiveWorkbook.Sheets(SHEET_SORT).Select
    
    On Error Resume Next

        'ソートのボタンを削除する。
        ActiveSheet.Shapes(SORT_BUTTONNAME).Delete
    
End Sub

'****************************************************
'関数名 ：PutButtonSortInTheSheet
'変数   ：なし
'返却   ：なし
'目的   ：ソートのボタンを追加する。
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Sub PutButtonSortInTheSheet()
    
    If Not IsSheetExists(SHEET_SORT) Then Exit Sub
    
    'ソートのボタンを削除する。
    DeleteButtonSort
    
    'ソートのボタンを追加する。
    CreateButtonSort
    
End Sub

'****************************************************
'関数名 ：IsSheetExists
'変数   ：なし
'返却   ：なし
'目的   ：
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Function IsSheetExists(SheetName As String) As Boolean

    IsSheetExists = False
    
    On Error GoTo NoSuchSheet
    If Len(Sheets(SheetName).Name) > 0 Then
        IsSheetExists = True
        Exit Function
    End If
NoSuchSheet:

End Function

