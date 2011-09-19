VERSION 5.00
Begin {C62A69F0-16DC-11CE-9E98-00AA00574A4F} FrmSort 
   Caption         =   "並べ替え"
   ClientHeight    =   4335
   ClientLeft      =   45
   ClientTop       =   330
   ClientWidth     =   4725
   OleObjectBlob   =   "FrmSort.frx":0000
   StartUpPosition =   2  'CenterScreen
End
Attribute VB_Name = "FrmSort"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Option Explicit

Private Const BLANK_LINE = ""
Private Const SORT_COLUMN1 = "D"
Private Const SORT_COLUMN2 = "F"
Private Const SORT_COLUMN3 = "G"

Private Const COL_PJBANGOU = "A"
Private Const ROW_PJBANGOU = "B"

Private Const MSG_WARNING01 = "「最優先されるキー」選択しなければなりません。"
Private Const MSG_WARNING02 = "２番目に最優先されるキーのコンボボックスに入力される値が" & vbNewLine & "最優先されるキーのコンボボックスに入力される値と違うべきである。"
Private Const MSG_WARNING03 = "３番目に最優先されるキーのコンボボックスに入力される値が" & vbNewLine & "上記の２つのコンボボックスと違うべきである。"

'****************************************************
'関数名 ：UserForm_Initialize
'変数   ：なし
'返却   ：なし
'目的   ：画面を初期化する。
'作成者 ：phuonghtt     日付：2011.08.12
'変更者 ：              日付：
'****************************************************
Private Sub UserForm_Initialize()
    
    ' ComboSourceを作成する
    Dim comboSource() As Variant
    comboSource = Array(BLANK_LINE, SORT_COLUMN1, SORT_COLUMN2, SORT_COLUMN3)
    
    ' ComboBoxにデータをセットする。
    cboSortBy.List = comboSource
    cboThenBy1.List = comboSource
    cboThenBy2.List = comboSource

End Sub

'****************************************************
'関数名 ：btnOk_Click
'変数   ：なし
'返却   ：なし
'目的   ：OKボタンが選択された場合
'作成者 ：phuonghtt     日付：2011.08.15
'変更者 ：              日付：
'****************************************************
Private Sub btnOk_Click()
        
    Dim waitCursor As Hourglass
    Set waitCursor = New Hourglass
            
    ' 並べ替え
    ' ディスプレイの警告を無効に
    Application.DisplayAlerts = False
    Application.ScreenUpdating = False
            
    Dim reflectSheet As Worksheet   '[Reflect Pattern] シート
    Dim colPjBangou As Long         '行のPJ番号
    Dim rowPjBangou As Long         '列のPJ番号
    Dim order1 As XlSortOrder
    Dim order2 As XlSortOrder
    Dim order3 As XlSortOrder
    Dim rowIdx As Long
    
    ' 単体をチェックする。
    '「最優先されるキー」にデータを入力したか確認する。
    If cboSortBy.Text = "" Then
        MsgBox MSG_WARNING01
        cboSortBy.SetFocus
        
        Exit Sub
    End If
    
    '「２最優先されるきー」にデータを入力したか確認する。
    If cboThenBy1.Text <> "" And cboThenBy1.Text = cboSortBy.Text Then
        MsgBox MSG_WARNING02
        cboThenBy1.SetFocus
        
        Exit Sub
    End If
    
    '「３最優先されるきー」にデータを入力したか確認する。
    If cboThenBy2.Text <> "" And (cboThenBy2.Text = cboSortBy.Text Or cboThenBy2.Text = cboThenBy1.Text) Then
        MsgBox MSG_WARNING03
        cboThenBy2.SetFocus
        
        Exit Sub
    End If
        
    If Not IsSheetExists(SHEET_REFLECT) Then
        Unload Me
        Exit Sub
    End If
        
    '「Reflect Pattern」シートをセットする。
    Set reflectSheet = ActiveWorkbook.Sheets(SHEET_REFLECT)
    
    If sortByAsc.Value Then
        order1 = xlAscending    '昇順
    Else
        order1 = xlDescending   '降順
    End If
    
    If thenByAsc1.Value Then
        order2 = xlAscending    '昇順
    Else
        order2 = xlDescending   '降順
    End If
            
    If thenByAsc2.Value Then
        order3 = xlAscending    '昇順
    Else
        order3 = xlDescending   '降順
    End If
    
    For rowIdx = 1 To reflectSheet.UsedRange.Rows.count Step 2
        
        'PJ番号の列
        colPjBangou = reflectSheet.Range(COL_PJBANGOU & rowIdx)
        'PJ番号の行
        rowPjBangou = reflectSheet.Range(ROW_PJBANGOU & rowIdx)
        
        '並べ替え
        Sort rowPjBangou, colPjBangou, cboSortBy.Text, order1, cboThenBy1.Text, order2, cboThenBy2.Text, order3

    Next
                
    Application.DisplayAlerts = True
    Application.ScreenUpdating = True
    
    'ソート機能画面を終了する。
    '終了する。
    Unload Me
    
End Sub

'****************************************************
'関数名 ：btnCancel_Click
'変数   ：なし
'返却   ：なし
'目的   ：キャンセボタンを選択されたする。
'作成者 ：phuonghtt     日付：2011.08.02
'変更者 ：              日付：
'****************************************************
Private Sub btnCancel_Click()
    Unload Me
End Sub
