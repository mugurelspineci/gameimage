VERSION 5.00
Begin {C62A69F0-16DC-11CE-9E98-00AA00574A4F} PivotTable 
   Caption         =   "ピボットテーブル"
   ClientHeight    =   2010
   ClientLeft      =   45
   ClientTop       =   435
   ClientWidth     =   5070
   OleObjectBlob   =   "PivotTable.frx":0000
   StartUpPosition =   1  'CenterOwner
End
Attribute VB_Name = "PivotTable"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False




Const filename As String = "Pivot_error.log"
Const msgErr_FieldNotValid As String = "そのピボットテーブルのフィールド名は正しくありません。ピボットテーブル レポートを作成するには､ラベルの付いた列でリストとして編成されたデータを使用する必要があります｡ピボットテーブルのフィールド名を変更する場合は､フィールドの新しい名前を入力する必要があります｡"
Const msgErr_Reference As String = "参照が正しくありません。"
Const msgErr_Row As String = "このコマンドにはデータ ソースが 2 行以上必要です。選択したセル範囲が 1 行だけの場合は、このコマンドを実行できません。"
Const errMsg As String = "処理 エラー"

'****************************************************
'関数名 ：cmbCancel_Click
'変数   ：なし
'返却   ：なし
'目的   ：
'作成者 ：HuynhTran     日付：2011.08.18
'変更者 ：              日付：
'*************************************************************************
Private Sub cmbCancel_Click()
    Unload Me
End Sub

'****************************************************
'関数名 ：CreateSheetName
'変数   ：なし
'返却   ：なし
'目的   ：シート名を作成する｡
'作成者 ：HuynhTran     日付：2011.08.18
'変更者 ：              日付：
'*************************************************************************
Private Sub cmbOK_Click()
    Dim namesheet As String '新規のシート名を格納する。
    Dim ws As Worksheet
    Dim countCol As Integer
    Dim pos As Integer  'テーブルを作成する位置を格納する
    Dim c As Integer
    Dim i As Integer
    Dim pvc As PivotCache
    Dim source As String

    Dim rng As Excel.Range
    Dim arrData() As Variant
    Dim lRows As Long
    Dim lCols As Long
    Dim ii As Long, jj As Long
    
    pos = 15
    countCol = 0

     On Error GoTo errMyErrorHandler
     
    
    If refSelectCells.Text = "" Then
     MsgBox msgErr_Reference, vbInformation
     refSelectCells.SetFocus
    Else
    
        'Range(refSelectCells.Text).Select
        If CheckRange(refSelectCells.Text) = False Then
          refSelectCells.SetFocus
          Exit Sub
        End If
        
        'シートで選択した行数を格納する。
        lRows = Selection.Rows.count
        lCols = Selection.Columns.count
        
        If lCols + pos > 255 Then
            MsgBox msgErr_Reference, vbExclamation
            refSelectCells.SetFocus
            Exit Sub
        End If
        
        ReDim arrData(1 To lRows, 1 To lCols)
        
        Set rng = Selection
        arrData = rng.Value
        
        For i = 1 To lCols
            If arrData(1, i) <> "" Then Exit For
        Next i
        
        If i > lCols Then
             MsgBox msgErr_FieldNotValid, vbExclamation
            refSelectCells.SetFocus
            Exit Sub
        End If
    
        'Hide
        'シート名を作成する｡
        namesheet = CreateSheetName()
          
        If namesheet <> "" Then
            '処理がシートで表示させない
            Application.ScreenUpdating = False
            
            'シートを新規作成する。
            Set ws = Sheets.Add
            ws.name = namesheet
            
            If CreateTableForPivot(arrData, countCol, pos) = False Then
                Application.DisplayAlerts = False
                Sheets(namesheet).Delete
                Application.DisplayAlerts = True
                refSelectCells.SetFocus
                 Application.ScreenUpdating = True
                'Unload Me
                Exit Sub
            End If

            'テーブルを作成する位置を格納する。
            source = namesheet & "!" & "R1C" & pos & ":R" & lRows & "C" & (countCol + pos - 1)
        
            'Check version
            If Application.Version < 12 Then
                'Office 2003
                Set pvc = ActiveWorkbook.PivotCaches.Add(SourceType:=xlDatabase, SourceData:=source)
            Else
                'Office 2007
                Set pvc = ActiveWorkbook.PivotCaches.Create(SourceType:=xlDatabase, SourceData:=source, Version:=xlPivotTableVersion10)
            End If
             
             'ピボットテーブルを作成する。
             pvc.CreatePivotTable TableDestination:=Worksheets(namesheet).Range(Cells(1, 1), Cells(1, 1)), _
        TableName:=namesheet, DefaultVersion:=xlPivotTableVersion10
             ActiveWorkbook.ShowPivotTableFieldList = True
             
             '作成したテーブルを削除する。
             Range(Cells(1, pos), Cells(lRows, countCol + pos - 1)).Select
             Selection.ClearContents
             
             Range(Cells(1, 1), Cells(1, 1)).Select
             '表示更新の制御
             Application.ScreenUpdating = True
             
             Set rng = Nothing
             'ピボットテーブルフォームを閉じる。
             Unload Me
        Else
            If chkLog.Value = False Then
               MsgBox errMsg
            Else
               Call WriteError(errMsg, ThisWorkbook.Path, filename)
            End If
        End If
       
    End If
 
 
    Exit Sub
    
errMyErrorHandler:
    If chkLog.Value = True Then
        Call WriteError(errMsg, ThisWorkbook.Path, filename)
    End If
    
    MsgBox Err.Description, vbExclamation + vbOKCancel, "Error: " & CStr(Err.Number)
    Err.Clear
    Unload Me
End Sub

'****************************************************
'関数名 ：CreateSheetName
'変数   ：なし
'返却   ：シート名
'目的   ：シート名を作成する｡
'作成者 ：HuynhTran     日付：2011.08.18
'変更者 ：              日付：
'*************************************************************************
Function CreateSheetName() As String
    Dim i As Integer
    Dim wSheet As Worksheet
    Dim name As String
    Dim sh As Worksheet, flg As Boolean
    
    name = "ピボットテーブル"
    name1 = name
    CreateSheetName = ""
     On Error Resume Next
     For i = 1 To 100
        For Each sh In Worksheets
           If sh.name = name1 Then flg = True: Exit For
        Next
        If flg = True Then
            name1 = name & i
            flg = False
        Else
            CreateSheetName = name1
            Exit Function
        End If
    Next
End Function

'****************************************************
'関数名 ：CreateTableForPivot
'変数   ：strRange
'返却   ：地域が存在する場合、True
'         地域が存在しない場合､False
'目的   ：地域の存在チェック。
'作成者 ：HuynhTran     日付：2011.08.18
'変更者 ：              日付：
'****************************************************
Function CheckRange(strRange As String) As Boolean
    Dim i As Integer
    On Error GoTo errMyErrorHandler
    Range(strRange).Select
    If Selection.Rows.count = 1 Then
        MsgBox msgErr_Row, vbExclamation
        CheckRange = False
        Exit Function
    End If
    CheckRange = True
    Exit Function
errMyErrorHandler:
    MsgBox msgErr_Reference, vbInformation
    CheckRange = False
End Function

'****************************************************
'関数名 ：CreateTableForPivot
'変数   ：arrData
'         countCol
'         pos
'返却   ：なし
'目的   ：。
'作成者 ：HuynhTran     日付：2011.08.18
'変更者 ：              日付：
'****************************************************
Private Function CreateTableForPivot(arrData() As Variant, countCol As Integer, pos As Integer)
    Dim countColT As Integer
    On Error GoTo errMyErrorHandler
    
    countColT = UBound(arrData, 2) + pos - 1
    
    If countColT + pos > 255 Then
        MsgBox msgErr_Reference, vbExclamation
        CreateTableForPivot = False
        Exit Function
    End If
    Range(Cells(1, pos), Cells(UBound(arrData, 1), countColT)).Select
    Set rng = Selection
    rng.Value = arrData
    
    'セルの値が ""の場合、該当の列を削除する。
    c = pos
    For i = pos To countColT Step 1
        If Cells(1, c) = "" Then
            Columns(c).Delete
        Else
            c = c + 1
        End If
    Next i
     
    '空白以外の値のあるセルの合計を格納する。
    For i = pos To countColT Step 1
        If Cells(1, i) = "" Then
           Exit For
        Else
            countCol = countCol + 1
        End If
    Next i
    
    If countCol < 1 Then
        If chkLog.Value = False Then
            MsgBox msgErr_FieldNotValid, vbExclamation
         Else
            Call WriteError(msgErr_FieldNotValid, ThisWorkbook.Path, filename)
        End If
        
        'Unload Me
        CreateTableForPivot = False
        Exit Function
    ElseIf countCol = 1 Then
        For i = 2 To UBound(arrData, 1) Step 1
            If Cells(i, pos) <> "" Then
               Exit For
            End If
        Next i
        
        If i > UBound(arrData, 1) Then
            MsgBox msgErr_FieldNotValid, vbExclamation
            CreateTableForPivot = False
            Exit Function
        End If
    
    End If
    CreateTableForPivot = True
    Exit Function
    
errMyErrorHandler:

    CreateTableForPivot = False
    If chkLog.Value = True Then
        Call WriteError(errMsg, ThisWorkbook.Path, filename)
    End If
    
    MsgBox Err.Description, vbExclamation + vbOKCancel, "Error: " & CStr(Err.Number)
    Err.Clear
    Unload Me
End Function

'****************************************************
'関数名 ：UserForm_Initialize
'変数   ：なし
'返却   ：なし
'目的   ：。
'作成者 ：HuynhTran     日付：2011.08.18
'変更者 ：              日付：
'****************************************************
Private Sub UserForm_Initialize()
    refSelectCells.SetFocus
    refSelectCells.Text = ActiveSheet.name & "!" & Selection.Address
End Sub

'****************************************************
'関数名 ：WriteError
'変数   ：message ログメッセージ
'         folder ログフォルダ
'         filename ログファイル名
'返却   ：なし
'目的   ：ログに書き込み。
'作成者 ：HuynhTran     日付：2011.08.18
'変更者 ：              日付：
'****************************************************
Public Sub WriteError(message As String, folder As String, filename As String)
        
    Dim logFilePath As String
    
    logFilePath = folder & "\" & filename
        
    Open logFilePath For Append As #1
        
    Print #1, "----------------------------------------------------------------"
    Print #1, " エラーの発生日              : " & Application.Text(Now(), "yyyy/MM/dd HH:mm:ss")
    Print #1, " エラー番号                    : " & Err.Number
    Print #1, " エラーに関連する説明文: " & Err.Description
    Print #1, " エラーの発生元              : " & Err.source
    Print #1, " エラーの内容                  : " & message

    Print #1, "----------------------------------------------------------------" & vbNewLine
    
    Close #1

End Sub
