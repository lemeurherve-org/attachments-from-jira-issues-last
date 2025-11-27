PSEXEC -accepteula \\GPS68 SCHTASKS  /QUERY /TN "\GPP\SDT"  2>&1 >> d:\log.txt
exit %ERRORLEVEL%