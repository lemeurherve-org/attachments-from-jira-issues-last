Set objExcel = CreateObject("Excel.Application") 'Not working
objExcel.Application.Visible = False
objExcel.DisplayAlerts = False

'....some code...

objExcel.Application.Quit
WScript.Quit