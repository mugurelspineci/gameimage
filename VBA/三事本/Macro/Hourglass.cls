VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
END
Attribute VB_Name = "Hourglass"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = False
Option Explicit

'****************************************************
'関数名 ：Class_Initialize
'変数   ：なし
'返却   ：なし
'目的   ：クラス初期化
'作成者 ：phuonghtt     日付：2011.08.02
'変更者 ：              日付：
'****************************************************
Private Sub Class_Initialize()
    Application.Cursor = xlWait
End Sub

'****************************************************
'関数名 ：Class_Terminate
'変数   ：なし
'返却   ：なし
'目的   ：処理が終了。
'作成者 ：phuonghtt     日付：2011.08.02
'変更者 ：              日付：
'****************************************************
Private Sub Class_Terminate()
    Application.Cursor = xlDefault
End Sub

