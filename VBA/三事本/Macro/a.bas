Attribute VB_Name = "Module1"
'*************************************************************************
' グローバル定数／変数
'　11/03/07　新規作成
'*************************************************************************
Const GcnstStrMenuGrayDispOFF As String = "グレーアウトを非表示"
Const GcnstStrMenuGrayDispON  As String = "全データ再表示"
Const GcnstStrMenuFilterForm  As String = "フィルタメニュー"
Const GcnstStrMenuPivotTable As String = "ピボットテーブル"

Public pWS As Worksheet

Dim obj As New Class1
Dim Newb
Dim ws As Worksheet

Sub Auto_Open()

    Application.CommandBars("Cell").Reset

    Set obj.wsevent = Sheets("G別集計一覧")

    Set Newb = Application.CommandBars("Cell").Controls.Add(Temporary:=True)
    With Newb
        .Caption = GcnstStrMenuFilterForm
        .OnAction = "PMeyeFilterFormOpen"   'コマンドを追加
        .BeginGroup = True                  '区切り線
    End With
    
    Set Newb = Application.CommandBars("Cell").Controls.Add(Temporary:=True)
    With Newb
        .Caption = GcnstStrMenuGrayDispOFF
        .OnAction = "GrayDispOFF" 'コマンドを追加
        .BeginGroup = False       '区切り線無し
    End With
    
    Set Newb = Application.CommandBars("Cell").Controls.Add(Temporary:=True)
    With Newb
        .Caption = GcnstStrMenuGrayDispON
        .OnAction = "GrayDispON" 'コマンドを追加
        .BeginGroup = False      '区切り線無し
    End With
    
    '2011/08/10 HuynhTran 追加
    Set Newb = Application.CommandBars("Cell").Controls.Add(Temporary:=True)
    With Newb
        .Caption = GcnstStrMenuPivotTable
        .OnAction = "PivotTableFormOpen" 'コマンドを追加
        .BeginGroup = False      '区切り線無し
    End With
        
        PutButtonSortInTheSheet

End Sub


'*************************************************************************
'ブックを閉じる前の処理
'
'　11/03/04　新規作成
'*************************************************************************
Sub Auto_Close()

    Application.CommandBars("Cell").Reset
    '右クリックメニューに追加したメニューが無い場合エラーになるのでエラートラップする
    '（ファイルを閉じようとして、キャンセルした場合、下記のメソッドが一度実行されるため
    '　その場合追加したメニューが存在しない場合があり、エラーとなるため）
    On Error Resume Next
        '右クリックメニューに追加したメニューを削除する（削除しないと次回残ってしまうため）
        Application.CommandBars("Cell").Controls(GcnstStrMenuGrayDispOFF).Delete
        Application.CommandBars("Cell").Controls(GcnstStrMenuGrayDispON).Delete
        Application.CommandBars("Cell").Controls(GcnstStrMenuFilterForm).Delete
        Application.CommandBars("Cell").Controls(GcnstStrMenuPivotTable).Delete
        
    On Error GoTo 0
    
End Sub

Sub GrayDispOFF()

Dim i, maxline As Integer
Dim cellFlag As String


'表示更新の制御
    Application.ScreenUpdating = False

'   データのＭＡＸを確認
    Set ws = ActiveSheet
    With ws
        maxline = .Range("F65536").End(xlUp).row
        
        If ws.name = "指摘OD一覧" Or ws.name = "指摘PJ一覧" Then
            For i = 8 To maxline
                cellFlag = .Cells(i, 4).Value
                Select Case cellFlag
                    Case "代表PJ"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "子PJ"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "シェアPJ"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "期間系PJ"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "PP開発"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "PP保守"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "社内付替"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "保守支援のみ"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "FSのみ"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case "グレーアウト"
                        .Rows(i & ":" & i).EntireRow.Hidden = True
                    Case Else
                End Select
            Next
        Else
            Exit Sub
        End If
    
    '表示更新の制御
        Application.ScreenUpdating = True

        .Range("A4").Select
    End With
    
End Sub

Sub GrayDispON()

Dim i, maxline As Integer

'表示更新の制御
    Application.ScreenUpdating = False

    Set ws = ActiveSheet
    With ws
        If ws.name = "指摘OD一覧" Or ws.name = "指摘PJ一覧" Then
        
'   データのＭＡＸを確認
            maxline = .Range("F65536").End(xlUp).row

            .Rows(8 & ":" & maxline).EntireRow.Hidden = False

    '表示更新の制御
            Application.ScreenUpdating = True

            .Range("A4").Select
        End If
    End With
    
End Sub

Sub PMeyeFilterFormOpen()
            
    Set ws = ActiveSheet
    With ws
        If ws.name = "指摘OD一覧" Or ws.name = "指摘PJ一覧" Then
            Set pWS = ActiveSheet
            PMeyeFilterForm.Show
        End If
    End With
    
End Sub

Sub RightClickMenuDelete()

    '右クリックメニューに追加したメニューが無い場合エラーになるのでエラートラップする
    '（ファイルを閉じようとして、キャンセルした場合、下記のメソッドが一度実行されるため
    '　その場合追加したメニューが存在しない場合があり、エラーとなるため）
    On Error Resume Next
        '右クリックメニューに追加したメニューを削除する（削除しないと次回残ってしまうため）
        Application.CommandBars("Cell").Controls(GcnstStrMenuGrayDispOFF).Delete
        Application.CommandBars("Cell").Controls(GcnstStrMenuGrayDispON).Delete
        Application.CommandBars("Cell").Controls(GcnstStrMenuFilterForm).Delete
        
    On Error GoTo 0
    
End Sub

'****************************************************
'関数名 ：PivotTableFormOpen
'変数   ：なし
'返却   ：なし
'目的   ：
'作成者 ：HuynhTran     日付：2011.08.18
'変更者 ：              日付：
'*************************************************************************
Sub PivotTableFormOpen()
 PivotTable.Show
End Sub


